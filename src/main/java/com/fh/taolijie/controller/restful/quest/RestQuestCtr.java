package com.fh.taolijie.controller.restful.quest;

import cn.fh.security.credential.Credential;
import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.component.ResponseText;
import com.fh.taolijie.constant.ErrorCode;
import com.fh.taolijie.constant.certi.CertiStatus;
import com.fh.taolijie.constant.quest.AssignStatus;
import com.fh.taolijie.constant.quest.CouponStatus;
import com.fh.taolijie.constant.quest.RequestStatus;
import com.fh.taolijie.dao.mapper.MemberModelMapper;
import com.fh.taolijie.domain.CouponModel;
import com.fh.taolijie.domain.TljAuditModel;
import com.fh.taolijie.domain.acc.CashAccModel;
import com.fh.taolijie.domain.acc.MemberModel;
import com.fh.taolijie.domain.quest.FinishRequestModel;
import com.fh.taolijie.domain.quest.QuestAssignModel;
import com.fh.taolijie.domain.quest.QuestModel;
import com.fh.taolijie.dto.CouponInfoDto;
import com.fh.taolijie.exception.checked.*;
import com.fh.taolijie.exception.checked.acc.BalanceNotEnoughException;
import com.fh.taolijie.exception.checked.acc.CashAccNotExistsException;
import com.fh.taolijie.exception.checked.acc.OrderNotFoundException;
import com.fh.taolijie.exception.checked.quest.*;
import com.fh.taolijie.service.acc.CashAccService;
import com.fh.taolijie.service.certi.StuCertiService;
import com.fh.taolijie.service.quest.CouponService;
import com.fh.taolijie.service.quest.QuestFinishService;
import com.fh.taolijie.service.quest.QuestService;
import com.fh.taolijie.service.quest.TljAuditService;
import com.fh.taolijie.service.quest.impl.FeeCalculator;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.PageUtils;
import com.fh.taolijie.utils.SessionUtils;
import com.fh.taolijie.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by whf on 9/24/15.
 */
@RestController
@RequestMapping("/api/user/quest")
public class RestQuestCtr {
    @Autowired
    private QuestService questService;

    @Autowired
    private CashAccService accService;

    @Autowired
    private MemberModelMapper memMapper;

    @Autowired
    private QuestFinishService fiService;

    @Autowired
    private TljAuditService auditService;

    @Autowired
    private CouponService couponService;

    @Autowired
    private FeeCalculator feeService;

    @Autowired
    private StuCertiService stuService;

    /**
     * 商家发布任务
     */
    @RequestMapping(value = "", method = RequestMethod.POST, produces = Constants.Produce.JSON)
    public ResponseText publishQuest(@Valid QuestModel model,
                                     BindingResult br,
                                     //@RequestParam(required = false) String schoolIds,
                                     @RequestParam(required = false) String collegeIds,
                                     @RequestParam(required = false) String cityIds, // 城市id
                                     @RequestParam(required = false) String proIds, // 省id
                                     // coupon信息
                                     @RequestParam(required = false) String couponTitle,
                                     @RequestParam(required = false) String couponDesp,
                                     @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date expiredTime,
                                     @RequestParam(required = false) String logo,
                                     @RequestParam(required = false) Integer couponAmt,

                                     @RequestParam(defaultValue = "0") Integer all, // 任务对象是否是所有人, 1表示所有人

                                     @RequestParam(required = false) Integer orderId,
                                     @RequestParam(defaultValue = "0") Integer save, // 保存(1) or 发布(0)
                                     HttpServletRequest req) throws GeneralCheckedException {

        Credential credential = SessionUtils.getCredential(req);

        if (!SessionUtils.isEmployer(credential)) {
            return new ResponseText(ErrorCode.PERMISSION_ERROR);
        }


        // 参数验证
        if (br.hasErrors() || model.getTotalAmt() <= 0) {
            return new ResponseText(ErrorCode.INVALID_PARAMETER);
        }
        int isAll = all.intValue();
        if (isAll != 0 && isAll != 1) {
            return new ResponseText(ErrorCode.INVALID_PARAMETER);
        }

        // 验证是否已认证
        MemberModel mem = memMapper.selectByPrimaryKey(credential.getId());
        String status = mem.getIdCerti();
        if (null == status || false == status.equals(CertiStatus.DONE.code())) {
            return new ResponseText(ErrorCode.PERMISSION_ERROR);
        }

        // 验证任务对象参数
        // 0 == all 说明任务对象不是所有人
        // 这时验证任务对象参数是否合法
        if (0 == isAll) {
            if (false == StringUtils.validateLadderString(collegeIds, cityIds, proIds)) {
                return new ResponseText(ErrorCode.INVALID_PARAMETER);
            }
        }



        // 标记变量
        // 记录该任务是否需要coupon
        boolean coupon = false;
        // couponTitle, couponDesp, expiredTime和logo要么全为null, 要么都不为null
        if (null != couponAmt) {
            coupon = true;
            if (false == StringUtils.checkAllNotEmpty(couponTitle, couponDesp) || null == expiredTime) {
                return new ResponseText(ErrorCode.INVALID_PARAMETER);
            }
        }

        // 将id string 转换成List<Integer>
        List<Integer> coList = StringUtils.splitIntendIds(collegeIds);
        List<Integer> proList = StringUtils.splitIntendIds(proIds);
        List<Integer> cityList = StringUtils.splitIntendIds(cityIds);

        model.setCollegeIdList(coList);
        model.setProvinceIdList(proList);
        model.setCityIdList(cityList);
        model.setIsTargetAll(isAll == 1);

        // 查出用户对应的现金账户
        Integer accId = accService.findIdByMember(credential.getId());
        if (null == accId) {
            return new ResponseText(ErrorCode.CASH_ACC_NOT_EXIST);
        }
        model.setMemberId(credential.getId());
        model.setLeftAmt(model.getTotalAmt());

        // 任务有coupon信息
        if (coupon) {
            CouponModel couponModel = new CouponModel();
            couponModel.setEmpId(credential.getId());
            couponModel.setTitle(couponTitle);
            couponModel.setDescription(couponDesp);
            couponModel.setLogoPath(logo);
            couponModel.setExpiredTime(expiredTime);
            couponModel.setAmt(couponAmt);

            questService.publishQuest(accId, model, orderId, couponModel, save);
        } else {
            questService.publishQuest(accId, model, orderId, null, save);
        }


        return new ResponseText();
    }

    /**
     * 发布任务草稿
     * @param questId
     * @param orderId
     * @param req
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.PUT, produces = Constants.Produce.JSON)
    public ResponseText publishDraft(@RequestParam Integer questId,
                                     @RequestParam(required = false) Integer orderId,
                                     HttpServletRequest req) throws GeneralCheckedException {

        Integer memId = SessionUtils.getCredential(req).getId();
        questService.publishDraft(memId, questId, orderId);

        return ResponseText.getSuccessResponseText();
    }

    /**
     * 计算任务费用
     * @return
     */
    @RequestMapping(value = "/fee", method = RequestMethod.GET, produces = Constants.Produce.JSON)
    public ResponseText computeQuestFee(@RequestParam BigDecimal award,
                                        @RequestParam Integer amt) {
        BigDecimal fee = feeService.computeQuestFee(award.doubleValue(), amt);

        return new ResponseText(fee);
    }

    /**
     * 刷新帖子
     * @param questId
     * @return
     */
    @RequestMapping(value = "/flush/{questId}", method = RequestMethod.PUT, produces = Constants.Produce.JSON)
    public ResponseText flushPost(@PathVariable Integer questId,
                                  HttpServletRequest req) throws GeneralCheckedException {

        Integer memId = SessionUtils.getCredential(req).getId();

        questService.flush(memId, questId);

        return ResponseText.getSuccessResponseText();
    }

    /**
     * 学生领取任务
     * @param req
     * @return
     */
    @RequestMapping(value = "/assign/{questId}", method = RequestMethod.POST, produces = Constants.Produce.JSON)
    public ResponseText assignQuest(@PathVariable Integer questId,
                                    HttpServletRequest req) throws GeneralCheckedException {
        // 登陆检查
        Credential credential = SessionUtils.getCredential(req);
        if (null == credential) {
            return new ResponseText(ErrorCode.NOT_LOGGED_IN);
        }
        // 判断当前用户是否是学生
        if (!SessionUtils.isStudent(credential)) {
            return new ResponseText(ErrorCode.PERMISSION_ERROR);
        }

        // 判断是否已经认证
        // 判断是否通过了学生认证
        boolean verified = stuService.checkVerified(credential.getId());
        if (!verified) {
            return new ResponseText(ErrorCode.LACK_STU_CERTIFICATION);
        }


        questService.assignQuest(credential.getId(), questId);

        return new ResponseText();
    }


    /**
     * 提交完成申请
     * @param questId
     * @param req
     * @return
     */
    @RequestMapping(value = "/submit/{questId}", method = RequestMethod.POST, produces = Constants.Produce.JSON)
    public ResponseText submitQuest(@PathVariable Integer questId,
                                    @RequestParam String description,
                                    @RequestParam("imageIds") String imgIds,
                                    @RequestParam(required = false) String name,
                                    @RequestParam(required = false) String memo,
                                    HttpServletRequest req) throws GeneralCheckedException {

        // 登陆检查
        Credential credential = SessionUtils.getCredential(req);
        if (null == credential) {
            return new ResponseText(ErrorCode.NOT_LOGGED_IN);
        }
        // 判断当前用户是否是学生
        if (!SessionUtils.isStudent(credential)) {
            return new ResponseText(ErrorCode.PERMISSION_ERROR);
        }

        FinishRequestModel model = new FinishRequestModel();
        model.setQuestId(questId);
        model.setMemberId(credential.getId());
        model.setDescription(description);
        model.setImageIds(imgIds);
        model.setName(name);
        model.setMemo(memo);

        fiService.submitRequest(model);

        return new ResponseText();
    }


    /**
     * 查询完成申请
     * @return
     */
    @RequestMapping(value = "/submit/list", method = RequestMethod.GET, produces = Constants.Produce.JSON)
    public ResponseText submitQuest(@RequestParam(defaultValue = "0") int pn,
                                    @RequestParam(defaultValue = Constants.PAGE_CAP) int ps,
                                    HttpServletRequest req) {

        Credential credential = SessionUtils.getCredential(req);

        ListResult<FinishRequestModel> lr = fiService.findByMember(credential.getId(), pn, ps);

        return new ResponseText(lr);
    }

    /**
     * 查询指定任务的申请
     * @return
     */
    @RequestMapping(value = "/submit/{questId}/list", method = RequestMethod.GET, produces = Constants.Produce.JSON)
    public ResponseText querySubmitQuest(@PathVariable("questId") Integer questId,
                                         @RequestParam(required = false) String status,
                                         @RequestParam(defaultValue = "0") int pn,
                                         @RequestParam(defaultValue = Constants.PAGE_CAP) int ps,
                                         HttpServletRequest req) {

        Credential credential = SessionUtils.getCredential(req);
        Integer memId = credential.getId();

        // 学生用户不能调用该接口
        if (SessionUtils.isStudent(credential)) {
            return new ResponseText(ErrorCode.PERMISSION_ERROR);
        }

        // 判断是否是商家用户
        if (SessionUtils.isEmployer(credential)) {
            // 如果是
            // 判断该任务是不是自己发布的
            QuestModel quest = questService.findById(questId);
            if (null == quest) {
                return new ResponseText(ErrorCode.NOT_FOUND);
            }

            if (!quest.getMemberId().equals(memId)) {
                return new ResponseText(ErrorCode.PERMISSION_ERROR);
            }
        }

        RequestStatus st = RequestStatus.fromCode(status);
        pn = PageUtils.getFirstResult(pn, ps);
        ListResult<FinishRequestModel> lr = fiService.findByQuest(questId, st, pn, ps);

        return new ResponseText(lr);
    }


    /**
     * 商家任务审核
     * @param req
     * @return
     */
    @RequestMapping(value = "/submit/{reqId}", method = RequestMethod.PUT, produces = Constants.Produce.JSON)
    public ResponseText changeStatus(@PathVariable Integer reqId,
                                     @RequestParam String status,
                                     @RequestParam(required = false) String memo,
                                     HttpServletRequest req) throws GeneralCheckedException {

        Credential credential = SessionUtils.getCredential(req);

        // 参数验证
        RequestStatus st = RequestStatus.fromCode(status);
        if (null == st) {
            return new ResponseText(ErrorCode.INVALID_PARAMETER);
        }

        // 学生不能调用
        if (SessionUtils.isStudent(credential)) {
            return new ResponseText(ErrorCode.PERMISSION_ERROR);
        }

        Integer memId = credential.getId();

        // 判断当前是否为商家
        if (SessionUtils.isEmployer(credential)) {
            // 如果是
            // 商家不能使用status = 03, 04, 06
            if (st == RequestStatus.AUTO_PASSED || st == RequestStatus.TLJ_FAILED || st == RequestStatus.TLJ_PASSED) {
                return new ResponseText(ErrorCode.PERMISSION_ERROR);
            }

            // 判断该任务是不是自己发布的
            FinishRequestModel reqModel = fiService.findById(reqId);
            if (null == reqModel) {
                return new ResponseText(ErrorCode.NOT_FOUND);
            }

            Integer questId = reqModel.getQuestId();

            QuestModel quest = questService.findById(questId);
            if (!quest.getMemberId().equals(memId)) {
                return new ResponseText(ErrorCode.PERMISSION_ERROR);
            }

        }



        fiService.updateStatus(reqId, st, memo);

        return new ResponseText();
    }

    /**
     * 查询我发布的任务
     * @return
     */
    @RequestMapping(value = "/publish/list", method = RequestMethod.GET, produces = Constants.Produce.JSON)
    public ResponseText queryPublishQuests(@RequestParam(required = false, defaultValue = "0") int pn,
                                           @RequestParam(required = false, defaultValue = Constants.PAGE_CAP) int ps,
                                           HttpServletRequest req) {

        Credential credential = SessionUtils.getCredential(req);

        pn = PageUtils.getFirstResult(pn, ps);
        ListResult<QuestModel> lr = questService.findByMember(credential.getId(), pn, ps);

        return new ResponseText(lr);
    }


    /**
     * 查询我领取的任务
     * @return
     */
    @RequestMapping(value = "/assign/list", method = RequestMethod.GET, produces = Constants.Produce.JSON)
    public ResponseText queryAssignedQuest(@RequestParam(required = false) String status,
                                           @RequestParam(required = false, defaultValue = "0") int pn,
                                           @RequestParam(required = false, defaultValue = Constants.PAGE_CAP) int ps,
                                           HttpServletRequest req) {

        Credential credential = SessionUtils.getCredential(req);

        AssignStatus st = null;
        if (null != status) {
            st = AssignStatus.fromCode(status);
            if (null == st) {
                return new ResponseText(ErrorCode.INVALID_PARAMETER);
            }
        }

        ListResult<QuestAssignModel> lr = null;

        pn = PageUtils.getFirstResult(pn, ps);
        if (null != st) {
            lr = questService.queryAssignRecords(credential.getId(), st, pn, ps);
        } else {
            lr = questService.queryAssignRecords(credential.getId(), pn, ps);
        }

        return new ResponseText(lr);
    }

    /**
     * 查询我领取的coupon
     * @return
     */
    @RequestMapping(value = "/coupon/list", method = RequestMethod.GET, produces = Constants.Produce.JSON)
    public ResponseText queryMyCoupon(HttpServletRequest req,
                                      @RequestParam(required = false) Integer status,
                                      @RequestParam(required = false, defaultValue = "0") int pn,
                                      @RequestParam(required = false, defaultValue = Constants.PAGE_CAP) int ps
                                      ) {

        // 只有学生才能调用
        Credential credential = SessionUtils.getCredential(req);
        if (!SessionUtils.isStudent(credential)) {
            return new ResponseText(ErrorCode.PERMISSION_ERROR);
        }


        Integer memId = credential.getId();

        pn = PageUtils.getFirstResult(pn, ps);

        CouponModel model = new CouponModel(pn, ps);
        model.setMemId(memId);
        if (null != status) {
            CouponStatus cs = CouponStatus.fromCode(status);
            if (null == cs) {
                return new ResponseText(ErrorCode.INVALID_PARAMETER);
            }

            model.setStatus(status);
        }

        ListResult<CouponModel> lr = couponService.findBy(model);

        return new ResponseText(lr);
    }

    /**
     * 商家查询自己发布的卡券信息
     * @param pn
     * @param ps
     * @param req
     * @return
     */
    @RequestMapping(value = "/coupon/emp/list", method = RequestMethod.GET, produces = Constants.Produce.JSON)
    public ResponseText queryCouponList(@RequestParam(defaultValue = "0") int pn,
                                        @RequestParam(defaultValue = Constants.PAGE_CAP) int ps,
                                        HttpServletRequest req) {
        // 只有商家才能调用
        Credential credential = SessionUtils.getCredential(req);
        if (!SessionUtils.isEmployer(credential)) {
            return new ResponseText(ErrorCode.PERMISSION_ERROR);
        }

        pn = PageUtils.getFirstResult(pn, ps);
        ListResult<CouponInfoDto> lr = couponService.findByEmp(credential.getId(), pn, ps);

        return new ResponseText(lr);
    }

    /**
     * 商家验证coupon
     * @return
     */
    @RequestMapping(value = "/coupon/check", method = RequestMethod.GET, produces = Constants.Produce.JSON)
    public ResponseText validateCoupon(@RequestParam String code,
                                       HttpServletRequest req) throws GeneralCheckedException {

        // 只有商家用户可以调用
        Credential credential = SessionUtils.getCredential(req);
        if (!SessionUtils.isEmployer(credential)) {
            return new ResponseText(ErrorCode.PERMISSION_ERROR);
        }

        couponService.validateCoupon(code);

        return ResponseText.getSuccessResponseText();
    }

    /**
     * 计算代审核所需的费用
     * @param amt
     * @return
     */
    @RequestMapping(value = "/audit/fee", method = RequestMethod.GET, produces = Constants.Produce.JSON)
    public ResponseText calculateAuditFee(@RequestParam Integer amt) {
        BigDecimal tot = auditService.calculateFee(amt);

        return new ResponseText(tot);
    }

    /**
     * 审核代审核任务完成申请
     * @param amt
     * @return
     */
    @RequestMapping(value = "/audit", method = RequestMethod.POST, produces = Constants.Produce.JSON)
    public ResponseText applyAudit(@RequestParam Integer questId,
                                   @RequestParam Integer amt, // 申请数量
                                   HttpServletRequest req) throws GeneralCheckedException {

        Credential credential = SessionUtils.getCredential(req);
        // 判断是否是商家用户
        if (!SessionUtils.isEmployer(credential)) {
            return new ResponseText(ErrorCode.PERMISSION_ERROR);
        }

        TljAuditModel model = new TljAuditModel();
        model.setQuestId(questId);
        model.setEmpId(credential.getId());
        model.setTotAmt(amt);
        model.setLeftAmt(amt);

        auditService.addAudit(model);

        return ResponseText.getSuccessResponseText();
    }


    /**
     * 查询商家的代审核申请
     * @return
     */
    @RequestMapping(value = "/audit/list", method = RequestMethod.GET, produces = Constants.Produce.JSON)
    public ResponseText auditList(@RequestParam(required = false) Integer questId,
                                  @RequestParam(defaultValue = "0") int pn,
                                  @RequestParam(defaultValue = Constants.PAGE_CAP) int ps,
                                  HttpServletRequest req) {

        // 只有商家才能调用该接口
        Credential credential = SessionUtils.getCredential(req);
        if (!SessionUtils.isEmployer(credential)) {
            return new ResponseText(ErrorCode.PERMISSION_ERROR);
        }

        // 判断任务是不是本人发的
        if (null != questId) {
            QuestModel quest = questService.findById(questId);
            if (null == quest) {
                return new ResponseText(ErrorCode.NOT_FOUND);
            }

            if (!quest.getMemberId().equals(credential.getId())) {
                return new ResponseText(ErrorCode.PERMISSION_ERROR);
            }
        }

        TljAuditModel cmd = new TljAuditModel(pn, ps);
        cmd.setQuestId(questId);
        cmd.setEmpId(credential.getId());

        ListResult<TljAuditModel> lr = auditService.findBy(cmd);

        return new ResponseText(lr);
    }
}

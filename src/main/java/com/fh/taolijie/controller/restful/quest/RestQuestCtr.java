package com.fh.taolijie.controller.restful.quest;

import cn.fh.security.credential.Credential;
import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.component.ResponseText;
import com.fh.taolijie.constant.ErrorCode;
import com.fh.taolijie.constant.certi.CertiStatus;
import com.fh.taolijie.constant.quest.AssignStatus;
import com.fh.taolijie.constant.quest.RequestStatus;
import com.fh.taolijie.dao.mapper.MemberModelMapper;
import com.fh.taolijie.domain.CouponModel;
import com.fh.taolijie.domain.TljAuditModel;
import com.fh.taolijie.domain.acc.CashAccModel;
import com.fh.taolijie.domain.acc.MemberModel;
import com.fh.taolijie.domain.quest.FinishRequestModel;
import com.fh.taolijie.domain.quest.QuestAssignModel;
import com.fh.taolijie.domain.quest.QuestModel;
import com.fh.taolijie.exception.checked.HackException;
import com.fh.taolijie.exception.checked.InvalidNumberStringException;
import com.fh.taolijie.exception.checked.acc.BalanceNotEnoughException;
import com.fh.taolijie.exception.checked.acc.CashAccNotExistsException;
import com.fh.taolijie.exception.checked.quest.*;
import com.fh.taolijie.service.acc.CashAccService;
import com.fh.taolijie.service.quest.CouponService;
import com.fh.taolijie.service.quest.QuestFinishService;
import com.fh.taolijie.service.quest.QuestService;
import com.fh.taolijie.service.quest.TljAuditService;
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

    /**
     * 商家发布任务
     */
    @RequestMapping(value = "", method = RequestMethod.POST, produces = Constants.Produce.JSON)
    public ResponseText publishQuest(@Valid QuestModel model,
                                     BindingResult br,
                                     @RequestParam String collegeIds,
                                     @RequestParam String schoolIds,
                                     // coupon信息
                                     @RequestParam(required = false) String couponTitle,
                                     @RequestParam(required = false) String couponDesp,
                                     @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date expiredTime,
                                     @RequestParam(required = false) String logo,
                                     @RequestParam(required = false) Integer couponAmt,
                                     HttpServletRequest req) {
        Credential credential = SessionUtils.getCredential(req);

        if (!SessionUtils.isEmployer(credential)) {
            return new ResponseText(ErrorCode.PERMISSION_ERROR);
        }

        // 参数验证
        if (br.hasErrors()) {
            return new ResponseText(ErrorCode.INVALID_PARAMETER);
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
        try {
            List<Integer> coList = StringUtils.splitIntendIds(collegeIds);
            List<Integer> schList = StringUtils.splitIntendIds(schoolIds);
            model.setCollegeIdList(coList);
            model.setSchoolIdList(schList);

        } catch (InvalidNumberStringException e) {
            return new ResponseText(ErrorCode.BAD_NUMBER);
        }

        // 查出用户对应的现金账户
        Integer accId = accService.findIdByMember(credential.getId());
        if (null == accId) {
            return new ResponseText(ErrorCode.CASH_ACC_NOT_EXIST);
        }
        model.setMemberId(credential.getId());
        model.setLeftAmt(model.getTotalAmt());

        try {
            // 任务有coupon信息
            if (coupon) {
                CouponModel couponModel = new CouponModel();
                couponModel.setEmpId(credential.getId());
                couponModel.setTitle(couponTitle);
                couponModel.setDescription(couponDesp);
                couponModel.setLogoPath(logo);
                couponModel.setExpiredTime(expiredTime);
                couponModel.setAmt(couponAmt);

                questService.publishQuest(accId, model, couponModel);
            } else {
                questService.publishQuest(accId, model, null);
            }

        } catch (BalanceNotEnoughException e) {
            return new ResponseText(ErrorCode.BALANCE_NOT_ENOUGH);

        } catch (CashAccNotExistsException e) {
            return new ResponseText(ErrorCode.CASH_ACC_NOT_EXIST);
        }

        return new ResponseText();
    }

    /**
     * 刷新帖子
     * @param questId
     * @return
     */
    @RequestMapping(value = "/flush/{questId}", method = RequestMethod.PUT, produces = Constants.Produce.JSON)
    public ResponseText flushPost(@PathVariable Integer questId,
                                  HttpServletRequest req) {

        Integer memId = SessionUtils.getCredential(req).getId();

        try {
            questService.flush(memId, questId);
        } catch (QuestNotFoundException e) {
            return new ResponseText(ErrorCode.NOT_FOUND);

        } catch (BalanceNotEnoughException e) {
            return new ResponseText(ErrorCode.BALANCE_NOT_ENOUGH);

        } catch (CashAccNotExistsException e) {
            return new ResponseText(ErrorCode.CASH_ACC_NOT_EXIST);

        }

        return ResponseText.getSuccessResponseText();
    }

    /**
     * 学生领取任务
     * @param req
     * @return
     */
    @RequestMapping(value = "/assign/{questId}", method = RequestMethod.POST, produces = Constants.Produce.JSON)
    public ResponseText assignQuest(@PathVariable Integer questId,
                                    HttpServletRequest req) {
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
        MemberModel mem = memMapper.selectByPrimaryKey(credential.getId());
        String status = mem.getVerified();
        if (null == status || false == status.equals(CertiStatus.DONE.code())) {
            return new ResponseText(ErrorCode.PERMISSION_ERROR);
        }

        try {
            questService.assignQuest(credential.getId(), questId);
        } catch (QuestAssignedException e) {
            return new ResponseText(ErrorCode.QUEST_ASSIGNED);

        } catch (QuestZeroException e) {
            return new ResponseText(ErrorCode.QUEST_ZERO);

        } catch (QuestNotFoundException e) {
            return new ResponseText(ErrorCode.NOT_FOUND);

        } catch (QuestExpiredException e) {
            return new ResponseText(ErrorCode.QUEST_EXPIRED);

        } catch (QuestNotStartException e) {
            return new ResponseText(ErrorCode.NOT_STARTED);
        }

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
                                    HttpServletRequest req) {

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

        try {
            fiService.submitRequest(model);
        } catch (QuestNotAssignedException e) {
            return new ResponseText(ErrorCode.QUEST_NOT_ASSIGNED);

        } catch (RequestRepeatedException e) {
            return new ResponseText(ErrorCode.REPEAT);
        }

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
     * 修改提交状态
     * @param req
     * @return
     */
    @RequestMapping(value = "/submit/{reqId}", method = RequestMethod.PUT, produces = Constants.Produce.JSON)
    public ResponseText changeStatus(@PathVariable Integer reqId,
                                     @RequestParam String status,
                                     @RequestParam(required = false) String memo,
                                     HttpServletRequest req) {

        // 登陆检查
        Credential credential = SessionUtils.getCredential(req);
        if (null == credential) {
            return new ResponseText(ErrorCode.NOT_LOGGED_IN);
        }

        // 学生不能调用
        if (SessionUtils.isStudent(credential)) {
            return new ResponseText(ErrorCode.PERMISSION_ERROR);
        }

        Integer memId = credential.getId();

        // 判断当前是否为商家
        if (SessionUtils.isEmployer(credential)) {
            // 如果是
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


        RequestStatus st = RequestStatus.fromCode(status);
        if (null == st) {
            return new ResponseText(ErrorCode.INVALID_PARAMETER);
        }

        try {
            fiService.updateStatus(reqId, st, memo);

        } catch (CashAccNotExistsException e) {
            return new ResponseText(ErrorCode.CASH_ACC_NOT_EXIST);

        } catch (RequestNotExistException e) {
            return new ResponseText(ErrorCode.NOT_FOUND);

        } catch (RequestCannotChangeException ex) {
            return new ResponseText(ErrorCode.STATUS_CANNOT_CHANGE);

        } catch (AuditNotEnoughException ex) {
            return new ResponseText(ErrorCode.AUDIT_NOT_ENOUGH);

        } catch (NotEnoughCouponException ex) {
            return new ResponseText(ErrorCode.ASSIGN_COUPON_FAILED);

        } catch (HackException ex) {
            return new ResponseText(ErrorCode.HACKER);

        }

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
        ListResult<CouponModel> lr = couponService.findBy(model);

        return new ResponseText(lr);
    }

    /**
     * 商家验证coupon
     * @return
     */
    @RequestMapping(value = "/coupon/check", method = RequestMethod.GET, produces = Constants.Produce.JSON)
    public ResponseText validateCoupon(@RequestParam String code,
                                       HttpServletRequest req) {

        // 只有商家用户可以调用
        Credential credential = SessionUtils.getCredential(req);
        if (!SessionUtils.isEmployer(credential)) {
            return new ResponseText(ErrorCode.PERMISSION_ERROR);
        }

        try {
            couponService.validateCoupon(code);
        } catch (CouponNotFoundException e) {
            return new ResponseText(ErrorCode.NOT_FOUND);

        } catch (InvalidCouponException e) {
            return new ResponseText(ErrorCode.COUPON_INVALID);

        } catch (CouponUsedException e) {
            return new ResponseText(ErrorCode.COUPON_USED);

        } catch (CouponExpiredException e) {
            return new ResponseText(ErrorCode.COUPON_EXPIRED);
        }

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
                                   HttpServletRequest req) {

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

        try {
            auditService.addAudit(model);
        } catch (BalanceNotEnoughException e) {
            return new ResponseText(ErrorCode.BALANCE_NOT_ENOUGH);

        } catch (CashAccNotExistsException e) {
            return new ResponseText(ErrorCode.CASH_ACC_NOT_EXIST);

        } catch (RequestRepeatedException e) {
            return new ResponseText(ErrorCode.REPEAT);

        } catch (QuestNotFoundException e) {
            return new ResponseText(ErrorCode.NOT_FOUND);

        }

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

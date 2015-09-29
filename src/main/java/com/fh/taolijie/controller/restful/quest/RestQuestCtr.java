package com.fh.taolijie.controller.restful.quest;

import cn.fh.security.credential.Credential;
import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.component.ResponseText;
import com.fh.taolijie.constant.ErrorCode;
import com.fh.taolijie.constant.quest.AssignStatus;
import com.fh.taolijie.domain.acc.CashAccModel;
import com.fh.taolijie.domain.quest.FinishRequestModel;
import com.fh.taolijie.domain.quest.QuestAssignModel;
import com.fh.taolijie.domain.quest.QuestModel;
import com.fh.taolijie.exception.checked.acc.BalanceNotEnoughException;
import com.fh.taolijie.exception.checked.acc.CashAccNotExistsException;
import com.fh.taolijie.exception.checked.quest.*;
import com.fh.taolijie.service.acc.CashAccService;
import com.fh.taolijie.service.quest.QuestFinishService;
import com.fh.taolijie.service.quest.QuestService;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.PageUtils;
import com.fh.taolijie.utils.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
    private QuestFinishService fiService;

    /**
     * 商家发布任务
     */
    @RequestMapping(value = "", method = RequestMethod.POST, produces = Constants.Produce.JSON)
    public ResponseText publishQuest(@Valid QuestModel model,
                                     BindingResult br,
                                     HttpServletRequest req) {
        // 登陆检查
        Credential credential = SessionUtils.getCredential(req);
        if (null == credential) {
            return new ResponseText(ErrorCode.NOT_LOGGED_IN);
        }
        // 判断当前用户是否是商家
        if (!SessionUtils.isEmployer(credential)) {
            return new ResponseText(ErrorCode.PERMISSION_ERROR);
        }


        if (br.hasErrors()) {
            return new ResponseText(ErrorCode.INVALID_PARAMETER);
        }

        // 查出用户对应的现金账户
        CashAccModel acc = accService.findByMember(credential.getId());
        if (null == acc) {
            return new ResponseText(ErrorCode.CASH_ACC_NOT_EXIST);
        }
        model.setMemberId(credential.getId());
        model.setLeftAmt(model.getTotalAmt());

        try {
            questService.publishQuest(acc.getId(), model);

        } catch (BalanceNotEnoughException e) {
            return new ResponseText(ErrorCode.BALANCE_NOT_ENOUGH);

        } catch (CashAccNotExistsException e) {
            return new ResponseText(ErrorCode.CASH_ACC_NOT_EXIST);
        }

        return new ResponseText();
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
}

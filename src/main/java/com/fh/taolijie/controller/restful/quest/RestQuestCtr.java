package com.fh.taolijie.controller.restful.quest;

import cn.fh.security.credential.Credential;
import com.fh.taolijie.component.ResponseText;
import com.fh.taolijie.constant.ErrorCode;
import com.fh.taolijie.domain.CashAccModel;
import com.fh.taolijie.domain.QuestAssignModel;
import com.fh.taolijie.domain.QuestModel;
import com.fh.taolijie.exception.checked.acc.BalanceNotEnoughException;
import com.fh.taolijie.exception.checked.acc.CashAccNotExistsException;
import com.fh.taolijie.exception.checked.quest.QuestAssignedException;
import com.fh.taolijie.exception.checked.quest.QuestExpiredException;
import com.fh.taolijie.exception.checked.quest.QuestNotFoundException;
import com.fh.taolijie.exception.checked.quest.QuestZeroException;
import com.fh.taolijie.service.acc.CashAccService;
import com.fh.taolijie.service.quest.QuestService;
import com.fh.taolijie.utils.Constants;
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

        }

        return new ResponseText();
    }
}

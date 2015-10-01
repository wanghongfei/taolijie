package com.fh.taolijie.controller.restful.quest;

import cn.fh.security.credential.Credential;
import com.fh.taolijie.component.ResponseText;
import com.fh.taolijie.constant.ErrorCode;
import com.fh.taolijie.constant.quest.RequestStatus;
import com.fh.taolijie.exception.checked.acc.CashAccNotExistsException;
import com.fh.taolijie.exception.checked.quest.RequestCannotChangeException;
import com.fh.taolijie.exception.checked.quest.RequestNotExistException;
import com.fh.taolijie.service.quest.QuestFinishService;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by whf on 9/24/15.
 */
@RestController
@RequestMapping("/api/manage/quest")
public class RestQuestFinishAdminCtr {
    @Autowired
    private QuestFinishService fiService;

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
        // 判断当前用户是否是ADMIN
        if (!SessionUtils.isAdmin(credential)) {
            return new ResponseText(ErrorCode.PERMISSION_ERROR);
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

        } catch (RequestCannotChangeException e) {
            return new ResponseText(ErrorCode.STATUS_CANNOT_CHANGE);
        }

        return new ResponseText();
    }
}

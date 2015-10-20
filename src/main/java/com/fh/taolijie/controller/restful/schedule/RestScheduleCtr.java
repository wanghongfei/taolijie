package com.fh.taolijie.controller.restful.schedule;

import com.fh.taolijie.component.ResponseText;
import com.fh.taolijie.constant.ErrorCode;
import com.fh.taolijie.constant.quest.RequestStatus;
import com.fh.taolijie.domain.quest.QuestModel;
import com.fh.taolijie.exception.checked.acc.CashAccNotExistsException;
import com.fh.taolijie.exception.checked.quest.AuditNotEnoughException;
import com.fh.taolijie.exception.checked.quest.RequestCannotChangeException;
import com.fh.taolijie.exception.checked.quest.RequestNotExistException;
import com.fh.taolijie.service.quest.QuestFinishService;
import com.fh.taolijie.service.quest.QuestService;
import com.fh.taolijie.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by whf on 10/7/15.
 */
@RestController
@RequestMapping("/api/schedule")
public class RestScheduleCtr {
    @Autowired
    private QuestFinishService fiService;

    @Autowired
    private QuestService questService;

    /**
     * 任务请求自动审核通过
     * @return
     */
    @RequestMapping(value = "/autoAudit", method = RequestMethod.GET, produces = Constants.Produce.JSON)
    public ResponseText autoAudit(@RequestParam Integer reqId) {
        try {
            fiService.updateStatus(reqId, RequestStatus.AUTO_PASSED, null);

        } catch (CashAccNotExistsException e) {
            return new ResponseText(ErrorCode.FAILED);

        } catch (RequestNotExistException e) {
            return new ResponseText(ErrorCode.FAILED);

        } catch (RequestCannotChangeException e) {
            return new ResponseText(ErrorCode.FAILED);

        } catch (AuditNotEnoughException e) {
            return new ResponseText(ErrorCode.FAILED);
        }

        return new ResponseText();
    }

    /**
     * 任务自动过期
     * @return
     */
    @RequestMapping(value = "/autoExpire", method = RequestMethod.GET, produces = Constants.Produce.JSON)
    public ResponseText autoExpire(@RequestParam Integer assignId) {
        questService.assignExpired(assignId);

        return new ResponseText();
    }

    /**
     * 任务到达过期时间后24小时
     * @return
     */
    @RequestMapping(value = "/questExpire", method = RequestMethod.GET, produces = Constants.Produce.JSON)
    public ResponseText questExpire(@RequestParam Integer questId) {
        try {
            questService.questExpired(questId);
        } catch (CashAccNotExistsException e) {
            return new ResponseText(ErrorCode.CASH_ACC_NOT_EXIST);
        }

        return ResponseText.getSuccessResponseText();
    }

}

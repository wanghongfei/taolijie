package com.fh.taolijie.controller.restful.schedule;

import com.fh.taolijie.component.ResponseText;
import com.fh.taolijie.constant.ErrorCode;
import com.fh.taolijie.constant.quest.RequestStatus;
import com.fh.taolijie.domain.quest.QuestModel;
import com.fh.taolijie.exception.checked.HackException;
import com.fh.taolijie.exception.checked.acc.CashAccNotExistsException;
import com.fh.taolijie.exception.checked.quest.AuditNotEnoughException;
import com.fh.taolijie.exception.checked.quest.NotEnoughCouponException;
import com.fh.taolijie.exception.checked.quest.RequestCannotChangeException;
import com.fh.taolijie.exception.checked.quest.RequestNotExistException;
import com.fh.taolijie.service.PVService;
import com.fh.taolijie.service.quest.QuestFinishService;
import com.fh.taolijie.service.quest.QuestService;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.StringUtils;
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

    @Autowired
    private PVService pvService;

    /**
     * 任务自动审核通过
     */
    public static final String URL_AUTO_AUDIT = "/autoAudit";
    /**
     * 领取的任务过期
     */
    public static final String URL_AUTO_EXPIRE = "/autoExpire";
    /**
     * 任务过期后25小时结算退款
     */
    public static final String URL_QUEST_EXPIRE = "/questExpire";
    /**
     * 每日pv落地
     */
    public static final String URL_PV_ALL = "/pv/all";

    public static String fullUrl(String path) {
        return StringUtils.concat(40, "/api/schedule", path);
    }


    /**
     * 任务请求自动审核通过
     * @return
     */
    @RequestMapping(value = URL_AUTO_AUDIT, method = RequestMethod.GET, produces = Constants.Produce.JSON)
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

        } catch (HackException ex) {
            return new ResponseText(ErrorCode.HACKER);

        } catch (NotEnoughCouponException e) {
            return new ResponseText(ErrorCode.ASSIGN_COUPON_FAILED);

        }


        return new ResponseText();
    }

    /**
     * 任务自动过期
     * @return
     */
    @RequestMapping(value = URL_AUTO_EXPIRE, method = RequestMethod.GET, produces = Constants.Produce.JSON)
    public ResponseText autoExpire(@RequestParam Integer assignId) {
        questService.assignExpired(assignId);

        return new ResponseText();
    }

    /**
     * 任务到达过期时间后24小时
     * @return
     */
    @RequestMapping(value = URL_QUEST_EXPIRE, method = RequestMethod.GET, produces = Constants.Produce.JSON)
    public ResponseText questExpire(@RequestParam Integer questId) {
        try {
            questService.questExpired(questId);
        } catch (CashAccNotExistsException e) {
            return new ResponseText(ErrorCode.CASH_ACC_NOT_EXIST);
        }

        return ResponseText.getSuccessResponseText();
    }

    /**
     * 每日总PV落地
     */
    @RequestMapping(value = URL_PV_ALL, method = RequestMethod.GET, produces = Constants.Produce.JSON)
    public ResponseText saveAllPVDaily() {
        try {
            pvService.saveAllPV();

        } catch (Exception e) {
            return new ResponseText(ErrorCode.FAILED);
        }

        return ResponseText.getSuccessResponseText();
    }
}

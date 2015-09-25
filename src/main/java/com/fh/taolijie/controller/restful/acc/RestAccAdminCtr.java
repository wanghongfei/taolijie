package com.fh.taolijie.controller.restful.acc;

import com.fh.taolijie.component.ResponseText;
import com.fh.taolijie.constant.ErrorCode;
import com.fh.taolijie.constant.acc.OrderStatus;
import com.fh.taolijie.constant.acc.WithdrawStatus;
import com.fh.taolijie.exception.checked.acc.BalanceNotEnoughException;
import com.fh.taolijie.exception.checked.acc.CashAccNotExistsException;
import com.fh.taolijie.exception.checked.acc.WithdrawNotExistsException;
import com.fh.taolijie.service.acc.ChargeService;
import com.fh.taolijie.service.acc.WithdrawService;
import com.fh.taolijie.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by whf on 9/25/15.
 */
@RestController
@RequestMapping("/api/manage/acc")
public class RestAccAdminCtr {
    @Autowired
    private WithdrawService drawService;

    @Autowired
    private ChargeService chargeService;

    /**
     * 提现申请审核
     * @return
     */
    @RequestMapping(value = "/withdraw", method = RequestMethod.PUT, produces = Constants.Produce.JSON)
    public ResponseText auditWithdraw(@RequestParam Integer drawId,
                                      @RequestParam String status,
                                      @RequestParam(required = false) String memo,
                                      HttpServletRequest req) {

        WithdrawStatus st = WithdrawStatus.fromCode(status);
        if (null == st) {
            return new ResponseText(ErrorCode.INVALID_PARAMETER);
        }

        try {
            drawService.updateStatus(drawId, st, memo);
        } catch (WithdrawNotExistsException e) {
            return new ResponseText(ErrorCode.NOT_FOUND);

        } catch (CashAccNotExistsException e) {
            return new ResponseText(ErrorCode.NOT_FOUND);

        } catch (BalanceNotEnoughException e) {
            return new ResponseText(ErrorCode.BALANCE_NOT_ENOUGH);
        }

        return ResponseText.getSuccessResponseText();

    }

    /**
     * 充值申请审核
     * @return
     */
    @RequestMapping(value = "/charge", method = RequestMethod.PUT, produces = Constants.Produce.JSON)
    public ResponseText auditCharge(@RequestParam Integer orderId,
                                    @RequestParam String status,
                                    @RequestParam(required = false) String memo,
                                    HttpServletRequest req) {

        OrderStatus st = OrderStatus.fromCode(status);
        if (null == st) {
            return new ResponseText(ErrorCode.INVALID_PARAMETER);
        }

        try {
            chargeService.updateStatus(orderId, st, memo);

        } catch (CashAccNotExistsException e) {
            return new ResponseText(ErrorCode.CASH_ACC_NOT_EXIST);
        }

        return ResponseText.getSuccessResponseText();
    }
}

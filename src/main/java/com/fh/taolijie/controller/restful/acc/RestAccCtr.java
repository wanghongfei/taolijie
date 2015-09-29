package com.fh.taolijie.controller.restful.acc;

import cn.fh.security.credential.Credential;
import cn.fh.security.utils.CredentialUtils;
import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.component.ResponseText;
import com.fh.taolijie.constant.ErrorCode;
import com.fh.taolijie.domain.acc.AccFlowModel;
import com.fh.taolijie.domain.acc.CashAccModel;
import com.fh.taolijie.domain.order.PayOrderModel;
import com.fh.taolijie.domain.acc.WithdrawApplyModel;
import com.fh.taolijie.exception.checked.UserNotExistsException;
import com.fh.taolijie.exception.checked.acc.BalanceNotEnoughException;
import com.fh.taolijie.exception.checked.acc.CashAccExistsException;
import com.fh.taolijie.exception.checked.acc.CashAccNotExistsException;
import com.fh.taolijie.exception.checked.acc.InvalidDealPwdException;
import com.fh.taolijie.service.acc.AccFlowService;
import com.fh.taolijie.service.acc.CashAccService;
import com.fh.taolijie.service.acc.ChargeService;
import com.fh.taolijie.service.acc.WithdrawService;
import com.fh.taolijie.service.acc.impl.PhoneValidationService;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.PageUtils;
import com.fh.taolijie.utils.SessionUtils;
import com.fh.taolijie.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by whf on 9/24/15.
 */
@RestController
@RequestMapping("/api/user/acc")
public class RestAccCtr {
    @Autowired
    private CashAccService accService;

    @Autowired
    private PhoneValidationService codeService;

    @Autowired
    private WithdrawService drawService;

    @Autowired
    private ChargeService chargeService;

    @Autowired
    private AccFlowService flowService;

    /**
     * 开通现金账户
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST, produces = Constants.Produce.JSON)
    public ResponseText createAcc(@RequestParam String dealPwd,
                                  @RequestParam String phone,
                                  @RequestParam(defaultValue = "") String code, // 手机验证码
                                  @RequestParam(required = false) String email,
                                  @RequestParam(required = false) String name,
                                  HttpServletRequest req) {

        // 登陆检查
        Credential credential = SessionUtils.getCredential(req);
        Integer memId = credential.getId();



        // TODO 验证验证码
/*        if (!codeService.validateCode(memId, code)) {
            return new ResponseText(ErrorCode.VALIDATION_CODE_ERROR);
        }*/

        // 验证交易密码
        if (!StringUtils.checkNotEmpty(dealPwd)) {
            return new ResponseText(ErrorCode.INVALID_PARAMETER);
        }

        // 开通
        CashAccModel acc = new CashAccModel();
        acc.setDealPassword(CredentialUtils.sha(dealPwd));
        acc.setPhoneNumber(phone);
        acc.setEmail(email);
        acc.setName(name);
        acc.setMemberId(memId);

        try {
            accService.addAcc(acc);
        } catch (CashAccExistsException e) {
            return new ResponseText(ErrorCode.EXISTS);

        } catch (UserNotExistsException e) {
            return new ResponseText(ErrorCode.NOT_FOUND);
        }

        return ResponseText.getSuccessResponseText();
    }

    /**
     * 查询当前用户的现金账户
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET, produces = Constants.Produce.JSON)
    public ResponseText queryAcc(HttpServletRequest req) {

        Credential credential = SessionUtils.getCredential(req);

        CashAccModel acc = accService.findByMember(credential.getId());
        return new ResponseText(acc);
    }
    /**
     * 发起提现申请
     * @return
     */
    @RequestMapping(value = "/withdraw", method = RequestMethod.POST, produces = Constants.Produce.JSON)
    public ResponseText withdraw(@RequestParam BigDecimal amt,
                                 @RequestParam String dealPwd,
                                 @RequestParam(required = false) String alipayAcc,
                                 @RequestParam(required = false) String bankAcc,
                                 HttpServletRequest req) {

        // 支付宝和银行卡不能同时为空
        if (null == alipayAcc && null == bankAcc) {
            return new ResponseText(ErrorCode.INVALID_PARAMETER);
        }

        Credential credential = SessionUtils.getCredential(req);
        Integer memId = credential.getId();


        WithdrawApplyModel model = new WithdrawApplyModel();
        model.setMemberId(memId);
        model.setAmount(amt);
        model.setAlipayAcc(alipayAcc);
        model.setBankAcc(bankAcc);

        try {
            drawService.addWithdraw(model, CredentialUtils.sha(dealPwd));
        } catch (CashAccNotExistsException e) {
            return new ResponseText(ErrorCode.CASH_ACC_NOT_EXIST);

        } catch (BalanceNotEnoughException e) {
            return new ResponseText(ErrorCode.BALANCE_NOT_ENOUGH);

        } catch (InvalidDealPwdException e) {
            return new ResponseText(ErrorCode.BAD_PASSWORD);

        }

        return ResponseText.getSuccessResponseText();

    }

    /**
     * 申请充值
     * @return
     */
    @RequestMapping(value = "/charge", method = RequestMethod.POST, produces = Constants.Produce.JSON)
    public ResponseText chargeApply(@RequestParam BigDecimal amt,
                                    @RequestParam String tradeNum,
                                    HttpServletRequest req) {

        Credential credential = SessionUtils.getCredential(req);
        Integer memId = credential.getId();

        PayOrderModel order = new PayOrderModel();
        order.setMemberId(memId);
        order.setTitle("账户充值订单");
        order.setAlipayTradeNum(tradeNum);
        order.setAmount(amt);

        try {
            chargeService.chargeApply(order);

        } catch (CashAccNotExistsException e) {
            return new ResponseText(ErrorCode.CASH_ACC_NOT_EXIST);
        }

        return ResponseText.getSuccessResponseText();
    }

    /**
     * 查询账户流水
     * @return
     */
    @RequestMapping(value = "/flow", method = RequestMethod.GET, produces = Constants.Produce.JSON)
    public ResponseText chargeApply(@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date start,
                                    @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date end,

                                    @RequestParam(defaultValue = "0") int pn,
                                    @RequestParam(defaultValue = Constants.PAGE_CAP) int ps,
                                    HttpServletRequest req) {

        Credential credential = SessionUtils.getCredential(req);

        pn = PageUtils.getFirstResult(pn, ps);
        ListResult<AccFlowModel> lr = flowService.findByAcc(credential.getId(), start, end, pn, ps);

        return new ResponseText(lr);
    }
}

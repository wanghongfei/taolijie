package com.fh.taolijie.controller.restful.acc;

import cn.fh.security.credential.Credential;
import cn.fh.security.utils.CredentialUtils;
import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.component.ResponseText;
import com.fh.taolijie.constant.ErrorCode;
import com.fh.taolijie.constant.RegType;
import com.fh.taolijie.domain.acc.AccFlowModel;
import com.fh.taolijie.domain.acc.CashAccModel;
import com.fh.taolijie.domain.acc.MemberModel;
import com.fh.taolijie.domain.order.PayOrderModel;
import com.fh.taolijie.domain.acc.WithdrawApplyModel;
import com.fh.taolijie.exception.checked.UserNotExistsException;
import com.fh.taolijie.exception.checked.acc.BalanceNotEnoughException;
import com.fh.taolijie.exception.checked.acc.CashAccExistsException;
import com.fh.taolijie.exception.checked.acc.CashAccNotExistsException;
import com.fh.taolijie.exception.checked.acc.InvalidDealPwdException;
import com.fh.taolijie.service.AccountService;
import com.fh.taolijie.service.acc.AccFlowService;
import com.fh.taolijie.service.acc.CashAccService;
import com.fh.taolijie.service.acc.ChargeService;
import com.fh.taolijie.service.acc.WithdrawService;
import com.fh.taolijie.service.acc.impl.CodeService;
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
    private CodeService codeService;

    @Autowired
    private WithdrawService drawService;

    @Autowired
    private ChargeService chargeService;

    @Autowired
    private AccFlowService flowService;

    @Autowired
    private AccountService memService;

    /**
     * 开通现金账户
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST, produces = Constants.Produce.JSON)
    public ResponseText createAcc(@RequestParam String dealPwd,
                                  @RequestParam(required = false) String phone,
                                  @RequestParam(defaultValue = "") String code, // 手机验证码
                                  @RequestParam(required = false) String email,
                                  @RequestParam(required = false) String name,
                                  HttpServletRequest req) {

        // 登陆检查
        Credential credential = SessionUtils.getCredential(req);
        Integer memId = credential.getId();



        // 验证验证码
        if (!codeService.validateSMSCode(memId.toString(), code)) {
            return new ResponseText(ErrorCode.VALIDATION_CODE_ERROR);
        }

        // 验证交易密码
        if (!StringUtils.checkNotEmpty(dealPwd)) {
            return new ResponseText(ErrorCode.INVALID_PARAMETER);
        }

        // 创建model对象
        CashAccModel acc = new CashAccModel();
        acc.setDealPassword(CredentialUtils.sha(dealPwd));
        acc.setEmail(email);
        acc.setName(name);
        acc.setMemberId(memId);

        // 判断用户的注册类型是不是手机号注册
        MemberModel mem = memService.findMember(memId);
        if (mem.getRegType().intValue() == RegType.MOBILE.code()) {
            // 如果是
            // 则直接将用户名设置为手机号
            acc.setPhoneNumber(mem.getUsername());
        } else {
            // 如果不是，则设置手机号参数中的号码
            if (!StringUtils.checkNotEmpty(phone)) {
                return new ResponseText(ErrorCode.PERMISSION_ERROR);
            }

            acc.setPhoneNumber(phone);
        }

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
     * 修改支付宝账号
     * @return
     */
    @RequestMapping(value = "/chAlipay", method = RequestMethod.PUT, produces = Constants.Produce.JSON)
    public ResponseText changeAlipay(@RequestParam String alipay,
                                     @RequestParam String code, // 手机验证码
                                     HttpServletRequest req) {

        Credential credential = SessionUtils.getCredential(req);
        Integer memId = credential.getId();

        // 验证验证码
        if (!codeService.validateSMSCode(memId.toString(), code)) {
            return new ResponseText(ErrorCode.VALIDATION_CODE_ERROR);
        }

        CashAccModel acc = accService.findByMember(memId);
        try {
            accService.updateAlipay(acc.getId(), alipay);
        } catch (CashAccNotExistsException e) {
            return new ResponseText(ErrorCode.CASH_ACC_NOT_EXIST);
        }

        return ResponseText.getSuccessResponseText();
    }

    /**
     * 修改银行卡号
     * @return
     */
    @RequestMapping(value = "/chBank", method = RequestMethod.PUT, produces = Constants.Produce.JSON)
    public ResponseText changeBank(@RequestParam String bankAcc,
                                   @RequestParam String code, // 手机验证码
                                   HttpServletRequest req) {

        Credential credential = SessionUtils.getCredential(req);
        Integer memId = credential.getId();

        // 验证验证码
        if (!codeService.validateSMSCode(memId.toString(), code)) {
            return new ResponseText(ErrorCode.VALIDATION_CODE_ERROR);
        }

        CashAccModel acc = accService.findByMember(memId);
        try {
            accService.updateBankAcc(acc.getId(), bankAcc);
        } catch (CashAccNotExistsException e) {
            return new ResponseText(ErrorCode.CASH_ACC_NOT_EXIST);
        }

        return ResponseText.getSuccessResponseText();
    }

    /**
     * 更换手机号
     * @return
     */
    @RequestMapping(value = "/chPhone", method = RequestMethod.PUT, produces = Constants.Produce.JSON)
    public ResponseText changePhone(@RequestParam String phone,
                                    @RequestParam String code, // 手机验证码
                                    HttpServletRequest req) {

        Integer memId = SessionUtils.getCredential(req).getId();

        // 判断有没有解绑手机号
        // 必须先解绑才允许操作
        CashAccModel acc = accService.findByMember(memId);
        String oldPhone = acc.getPhoneNumber();
        if (null != oldPhone && !oldPhone.isEmpty()) {
            return new ResponseText(ErrorCode.UNBIND_FIRST);
        }

        // 判断验证码是否正确
        boolean result = codeService.validateSMSCode(memId.toString(), code);
        if (!result) {
            return new ResponseText(ErrorCode.VALIDATION_CODE_ERROR);
        }

        // 更新手机号
        Integer accId = accService.findByMember(memId).getId();
        accService.updatePhone(accId, phone);

        return new ResponseText();
    }

    /**
     * 解绑手机号
     * @return
     */
    @RequestMapping(value = "/unPhone", method = RequestMethod.PUT, produces = Constants.Produce.JSON)
    public ResponseText unbindPhone(@RequestParam String code, // 手机验证码
                                    HttpServletRequest req) {

        Integer memId = SessionUtils.getCredential(req).getId();



        // 判断验证码是否正确
        boolean result = codeService.validateSMSCode(memId.toString(), code);
        if (!result) {
            return new ResponseText(ErrorCode.VALIDATION_CODE_ERROR);
        }

        // 更新手机号
        Integer accId = accService.findByMember(memId).getId();
        accService.updatePhone(accId, "");

        return new ResponseText();
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

package com.fh.taolijie.controller.restful.acc;

import cn.fh.security.credential.Credential;
import cn.fh.security.utils.CredentialUtils;
import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.component.ResponseText;
import com.fh.taolijie.constant.ErrorCode;
import com.fh.taolijie.constant.RegType;
import com.fh.taolijie.constant.acc.PayType;
import com.fh.taolijie.domain.SeQuestionModel;
import com.fh.taolijie.domain.acc.AccFlowModel;
import com.fh.taolijie.domain.acc.CashAccModel;
import com.fh.taolijie.domain.acc.MemberModel;
import com.fh.taolijie.domain.acc.WithdrawApplyModel;
import com.fh.taolijie.exception.checked.FinalStatusException;
import com.fh.taolijie.exception.checked.GeneralCheckedException;
import com.fh.taolijie.exception.checked.PermissionException;
import com.fh.taolijie.exception.checked.acc.UserNotExistsException;
import com.fh.taolijie.exception.checked.acc.*;
import com.fh.taolijie.exception.checked.code.SMSCodeMismatchException;
import com.fh.taolijie.service.AccountService;
import com.fh.taolijie.service.acc.*;
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
    private AccFlowService flowService;

    @Autowired
    private AccountService memService;

    @Autowired
    private SeQuestionService seService;

    @Autowired
    private OrderService orderService;

    /**
     * 开通现金账户
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST, produces = Constants.Produce.JSON)
    public ResponseText createAcc(@RequestParam String dealPwd,
                                  @RequestParam String seContent,
                                  @RequestParam String seAnswer,
                                  //@RequestParam(required = false) String phone,
                                  //@RequestParam(defaultValue = "") String code, // 手机验证码
                                  @RequestParam(required = false) String email,
                                  @RequestParam(required = false) String name,
                                  HttpServletRequest req) throws GeneralCheckedException {

        // 登陆检查
        Credential credential = SessionUtils.getCredential(req);
        Integer memId = credential.getId();



        // 验证验证码
/*        if (!codeService.validateSMSCode(memId.toString(), code)) {
            return new ResponseText(ErrorCode.VALIDATION_CODE_ERROR);
        }*/

        // 验证交易密码
        if (!StringUtils.checkNotEmpty(dealPwd)) {
            return new ResponseText(ErrorCode.INVALID_PARAMETER);
        }

        // 创建账户model对象
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
            return new ResponseText(ErrorCode.HACKER);
/*            if (!StringUtils.checkNotEmpty(phone)) {
                return new ResponseText(ErrorCode.PERMISSION_ERROR);
            }

            acc.setPhoneNumber(phone);*/
        }

        //

        // 创建密保问题model
        SeQuestionModel question = new SeQuestionModel();
        question.setMemberId(memId);
        question.setContent(seContent);
        question.setAnswer(seAnswer);

        accService.addAcc(acc, question);

        return ResponseText.getSuccessResponseText();
    }

    /**
     * 查询当前账户余额
     * @return
     */
    @RequestMapping(value = "/balance", method = RequestMethod.GET, produces = Constants.Produce.JSON)
    public ResponseText checkBalance(HttpServletRequest req) {
        Integer memId = SessionUtils.getCredential(req).getId();

        BigDecimal res = accService.checkBalance(memId);
        if (null == res ) {
            return new ResponseText(ErrorCode.CASH_ACC_NOT_EXIST);
        }

        return new ResponseText(res);
    }

    /**
     * 修改支付宝账号
     * @return
     */
    @RequestMapping(value = "/alipay", method = RequestMethod.PUT, produces = Constants.Produce.JSON)
    public ResponseText changeAlipay(@RequestParam String alipay,
                                     @RequestParam String code, // 手机验证码
                                     HttpServletRequest req) throws GeneralCheckedException {

        Credential credential = SessionUtils.getCredential(req);
        Integer memId = credential.getId();

        // 验证验证码
        if (!codeService.validateSMSCode(memId.toString(), code)) {
            return new ResponseText(ErrorCode.VALIDATION_CODE_ERROR);
        }

        CashAccModel acc = accService.findByMember(memId);
        accService.updateAlipay(acc.getId(), alipay);

        return ResponseText.getSuccessResponseText();
    }

    /**
     * 修改银行卡号
     * @return
     */
    @RequestMapping(value = "/bank", method = RequestMethod.PUT, produces = Constants.Produce.JSON)
    public ResponseText changeBank(@RequestParam String bankAcc,
                                   @RequestParam String code, // 手机验证码
                                   HttpServletRequest req) throws GeneralCheckedException {

        Credential credential = SessionUtils.getCredential(req);
        Integer memId = credential.getId();

        // 验证验证码
        if (!codeService.validateSMSCode(memId.toString(), code)) {
            return new ResponseText(ErrorCode.VALIDATION_CODE_ERROR);
        }

        CashAccModel acc = accService.findByMember(memId);
        accService.updateBankAcc(acc.getId(), bankAcc);

        return ResponseText.getSuccessResponseText();
    }

    /**
     * 修改交易密码
     * @return
     */
    @RequestMapping(value = "/dealPwd", method = RequestMethod.PUT, produces = Constants.Produce.JSON)
    public ResponseText changeDealPwd(@RequestParam String answer,
                                      @RequestParam String dealPwd,
                                      HttpServletRequest req) throws GeneralCheckedException {

        Integer memId = SessionUtils.getCredential(req).getId();

        // 验证密保
        boolean result = seService.checkAnswer(memId, answer);
        if (!result) {
            return new ResponseText(ErrorCode.WRONG_ANSWER);
        }

        // 验证dealPwd参数
        if (false == StringUtils.checkNotEmpty(dealPwd)) {
            return new ResponseText(ErrorCode.INVALID_PARAMETER);
        }

        // 修改交易密码
        accService.updateDealPwd(memId, dealPwd);

        return new ResponseText();
    }

    /**
     * 更换手机号
     * @return
     */
    @RequestMapping(value = "/phone", method = RequestMethod.PUT, produces = Constants.Produce.JSON)
    public ResponseText changePhone(@RequestParam String phone,
                                    @RequestParam String code, // 手机验证码
                                    @RequestParam(required = false) String answer, // 密保答案
                                    HttpServletRequest req) throws GeneralCheckedException {

        Integer memId = SessionUtils.getCredential(req).getId();

        if (null != answer) {
            memService.changePhoneByQuestionAndCode(memId, answer, phone, code);
        } else {
            memService.changePhoneByCode(memId, phone, code);
        }


        return new ResponseText();
    }

/*    *//**
     * @deprecated
     * 解绑手机号
     * @return
     *//*
    @RequestMapping(value = "/phone", method = RequestMethod.DELETE, produces = Constants.Produce.JSON)
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
    }*/

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
     * 钱包充值接口
     * @return
     */
    @RequestMapping(value = "/charge", method = RequestMethod.POST, produces = Constants.Produce.JSON)
    public ResponseText charge(@RequestParam Integer orderId,
                               HttpServletRequest req) throws GeneralCheckedException {


        accService.charge(orderId, SessionUtils.getCredential(req).getId());

        return ResponseText.getSuccessResponseText();
    }

    /**
     * 发起提现申请
     * @return
     */
    @RequestMapping(value = "/withdraw", method = RequestMethod.POST, produces = Constants.Produce.JSON)
    public ResponseText withdraw(@RequestParam BigDecimal amt,
                                 @RequestParam String dealPwd,
                                 @RequestParam Integer payType,
                                 //@RequestParam(required = false) String alipayAcc,
                                 //@RequestParam(required = false) String bankAcc,
                                 HttpServletRequest req) throws GeneralCheckedException {

        // 参数验证
        PayType type = PayType.fromCode(payType);
        if (null == payType) {
            return new ResponseText(ErrorCode.INVALID_PARAMETER);
        }

        Credential credential = SessionUtils.getCredential(req);
        Integer memId = credential.getId();


        WithdrawApplyModel model = new WithdrawApplyModel();
        model.setMemberId(memId);
        model.setAmount(amt);
        //model.setAlipayAcc(alipayAcc);
        //model.setBankAcc(bankAcc);

        drawService.addWithdraw(model, CredentialUtils.sha(dealPwd), type);

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

    /**
     * 查询密保问题
     * @return
     */
    @RequestMapping(value = "/question", method = RequestMethod.GET, produces = Constants.Produce.JSON)
    public ResponseText querySeQuestion(HttpServletRequest req) {
        Credential credential = SessionUtils.getCredential(req);

        SeQuestionModel model = seService.findByMember(credential.getId(), false);
        if (null == model) {
            return new ResponseText(ErrorCode.NOT_FOUND);
        }

        return new ResponseText(model);
    }

    /**
     * 绑定微信openId
     * @return
     */
    @RequestMapping(value = "/bind/wechat", method = RequestMethod.POST, produces = Constants.Produce.JSON)
    public ResponseText bindWechatToken(@RequestParam String openId,
                                        HttpServletRequest req) {
        Integer memId = SessionUtils.getCredential(req).getId();

        MemberModel mem = new MemberModel();
        mem.setId(memId);
        mem.setWechatToken(openId);

        int row = memService.updateMember(mem);
        if (row <= 0) {
            return new ResponseText(ErrorCode.FAILED);
        }

        return ResponseText.getSuccessResponseText();
    }
}

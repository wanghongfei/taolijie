package com.fh.taolijie.controller.restful;

import cn.fh.security.credential.Credential;
import com.fh.taolijie.component.ResponseText;
import com.fh.taolijie.constant.ErrorCode;
import com.fh.taolijie.constant.RegType;
import com.fh.taolijie.dao.mapper.MemberModelMapper;
import com.fh.taolijie.domain.acc.CashAccModel;
import com.fh.taolijie.domain.acc.MemberModel;
import com.fh.taolijie.exception.checked.code.SMSIntervalException;
import com.fh.taolijie.exception.checked.code.SMSVendorException;
import com.fh.taolijie.service.AccountService;
import com.fh.taolijie.service.acc.CashAccService;
import com.fh.taolijie.service.acc.impl.CodeService;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.SessionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 短信验证码
 * Created by whf on 9/24/15.
 */
@RestController
@RequestMapping("/api/user/code")
public class ValidationCodeCtr {
    @Autowired
    private CodeService codeService;

    @Autowired
    private CashAccService accService;

    @Autowired
    private AccountService accountService;

    /**
     * 向指定手机号发送短信
     * @return
     */
    @RequestMapping(value = "/sms", method = RequestMethod.GET, produces = Constants.Produce.JSON)
    public ResponseText sendSMS(@RequestParam String mobile,
                                @RequestParam String code,
                                HttpServletRequest req) {

        Credential credential = SessionUtils.getCredential(req);
        Integer memId = credential.getId();

        // 验证验证码是否正确
        boolean result = codeService.validateWebCode(memId.toString(), code);
        if (!result) {
            return new ResponseText(ErrorCode.VALIDATION_CODE_ERROR);
        }


        try {
            codeService.genSMSValidationCode(credential.getId().toString(), mobile);
        } catch (SMSIntervalException e) {
            return new ResponseText(ErrorCode.TOO_FREQUENT);

        } catch (SMSVendorException e) {
            return new ResponseText(ErrorCode.SMS_INVOKE_FAILED);

        }

        return new ResponseText();
    }


    /**
     * 给当前用户手机发送短信
     * @return
     */
    @RequestMapping(value = "/sms", method = RequestMethod.POST, produces = Constants.Produce.JSON)
    public ResponseText sendSMS(
                                HttpServletRequest req) {

        Credential credential = SessionUtils.getCredential(req);
        Integer memId = credential.getId();

        // 验证验证码是否正确
/*        boolean result = codeService.validateWebCode(memId.toString(), code);
        if (!result) {
            return new ResponseText(ErrorCode.VALIDATION_CODE_ERROR);
        }*/

        String mobile = null;
        MemberModel mem = accountService.findMember(memId);
        if (mem.getRegType().intValue() == RegType.MOBILE.code()) {
            mobile = mem.getUsername();
        } else {
            CashAccModel acc = accService.findByMember(memId);
            if (null == acc) {
                return new ResponseText(ErrorCode.CASH_ACC_NOT_EXIST);
            }

            mobile = acc.getPhoneNumber();
        }


        try {
            codeService.genSMSValidationCode(credential.getId().toString(), mobile);

        } catch (SMSIntervalException e) {
            return new ResponseText(ErrorCode.TOO_FREQUENT);

        } catch (SMSVendorException e) {
            return new ResponseText(ErrorCode.SMS_INVOKE_FAILED);

        }

        return new ResponseText();
    }

    /**
     * 请求一个验证码
     * @return
     */
    @RequestMapping(value = "/web", method = RequestMethod.GET, produces = Constants.Produce.JSON)
    public ResponseText fetchValidationCode(HttpServletRequest req) {

        Credential credential = SessionUtils.getCredential(req);
        String code = codeService.genWebValidationCode(credential.getId().toString());

        return new ResponseText(code);
    }
}

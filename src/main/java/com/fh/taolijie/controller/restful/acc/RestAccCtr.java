package com.fh.taolijie.controller.restful.acc;

import cn.fh.security.credential.Credential;
import com.fh.taolijie.component.ResponseText;
import com.fh.taolijie.constant.ErrorCode;
import com.fh.taolijie.domain.CashAccModel;
import com.fh.taolijie.exception.checked.UserNotExistsException;
import com.fh.taolijie.exception.checked.acc.CashAccExistsException;
import com.fh.taolijie.service.acc.CashAccService;
import com.fh.taolijie.service.acc.impl.PhoneValidationService;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.SessionUtils;
import com.fh.taolijie.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Created by whf on 9/24/15.
 */
@RestController
@RequestMapping("/api/acc")
public class RestAccCtr {
    @Autowired
    private CashAccService accService;

    @Autowired
    private PhoneValidationService codeService;

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
        if (null == credential) {
            return new ResponseText(ErrorCode.NOT_LOGGED_IN);
        }
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
        acc.setDealPassword(dealPwd);
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

        return new ResponseText();
    }
}

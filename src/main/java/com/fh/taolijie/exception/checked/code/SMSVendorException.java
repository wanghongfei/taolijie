package com.fh.taolijie.exception.checked.code;

import com.fh.taolijie.constant.ErrorCode;
import com.fh.taolijie.exception.checked.GeneralCheckedException;

/**
 * Created by whf on 10/23/15.
 */
public class SMSVendorException extends GeneralCheckedException {
    public SMSVendorException() {
        super("");
        setCode(ErrorCode.SMS_INVOKE_FAILED);
    }
}

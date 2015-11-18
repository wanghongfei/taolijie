package com.fh.taolijie.exception.checked.code;

import com.fh.taolijie.constant.ErrorCode;
import com.fh.taolijie.exception.checked.GeneralCheckedException;

/**
 * Created by whf on 10/31/15.
 */
public class SMSCodeMismatchException extends GeneralCheckedException {
    public SMSCodeMismatchException() {
        super("");
        setCode(ErrorCode.VALIDATION_CODE_ERROR);
    }
}

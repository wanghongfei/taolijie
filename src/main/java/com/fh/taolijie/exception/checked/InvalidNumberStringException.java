package com.fh.taolijie.exception.checked;

import com.fh.taolijie.constant.ErrorCode;

/**
 * Created by whf on 7/21/15.
 */
public class InvalidNumberStringException extends GeneralCheckedException {
    public InvalidNumberStringException(String msg) {
        super(msg);
        setCode(ErrorCode.BAD_NUMBER);
    }
}

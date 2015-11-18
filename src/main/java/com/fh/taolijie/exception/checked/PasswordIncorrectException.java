package com.fh.taolijie.exception.checked;

import com.fh.taolijie.constant.ErrorCode;

/**
 * Created by wanghongfei on 15-3-4.
 */
public class PasswordIncorrectException extends GeneralCheckedException {
    public PasswordIncorrectException() {
        super("");
        setCode(ErrorCode.BAD_PASSWORD);
    }
    public PasswordIncorrectException(String msg) {
        super(msg);
        setCode(ErrorCode.BAD_PASSWORD);
    }
}

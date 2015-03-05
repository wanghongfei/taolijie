package com.fh.taolijie.exception.checked;

/**
 * Created by wanghongfei on 15-3-4.
 */
public class PasswordIncorrectException extends GeneralCheckedException {
    public PasswordIncorrectException(String msg) {
        super(msg);
    }
}

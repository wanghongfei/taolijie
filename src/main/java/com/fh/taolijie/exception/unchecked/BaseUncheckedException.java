package com.fh.taolijie.exception.unchecked;

/**
 * Created by wanghongfei on 15-4-7.
 */
public abstract class BaseUncheckedException extends RuntimeException {
    private String message;

    protected BaseUncheckedException(String msg) {
        this.message = msg;
    }

    public String getMessage() {
        return this.message;
    }
}

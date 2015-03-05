package com.fh.taolijie.exception.checked;

/**
 * Created by wanghongfei on 15-3-5.
 */
public class GeneralCheckedException extends Exception {
    private String message;

    public GeneralCheckedException(String msg) {
        super(msg);
        this.message = msg;
    }

    public String getMessage() {
        return message;
    }
}

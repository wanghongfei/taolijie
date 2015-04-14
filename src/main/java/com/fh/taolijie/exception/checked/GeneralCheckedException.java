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

    /**
     * 重写此方法，性能提高10倍以上
     * @return
     */
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }

    public String getMessage() {
        return message;
    }
}

package com.fh.taolijie.exception.unchecked;

/**
 * Created by wanghongfei on 15-4-7.
 */
public abstract class BaseUncheckedException extends RuntimeException {
    private String message;

    protected BaseUncheckedException(String msg) {
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
        return this.message;
    }
}

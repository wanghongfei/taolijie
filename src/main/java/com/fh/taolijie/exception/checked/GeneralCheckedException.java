package com.fh.taolijie.exception.checked;

import com.fh.taolijie.constant.ErrorCode;

/**
 * Created by wanghongfei on 15-3-5.
 */
public class GeneralCheckedException extends Exception {
    private String message;
    private ErrorCode code;

    public GeneralCheckedException(String msg) {
        super(msg);
        this.message = msg;
    }

    public GeneralCheckedException(ErrorCode code) {
        this.code = code;
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

    public ErrorCode getCode() {
        return code;
    }

    public void setCode(ErrorCode code) {
        this.code = code;
    }
}

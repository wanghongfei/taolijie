package com.fh.taolijie.exception.checked;

import com.fh.taolijie.constant.ErrorCode;

/**
 * Created by whf on 9/30/15.
 */
public class InvalidMessageException extends GeneralCheckedException {
    public InvalidMessageException(String msg) {
        super(msg);
        setCode(ErrorCode.FAILED);
    }

    public InvalidMessageException() {
        super("");
        setCode(ErrorCode.FAILED);
    }
}

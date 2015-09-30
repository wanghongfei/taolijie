package com.fh.taolijie.exception.checked;

/**
 * Created by whf on 9/30/15.
 */
public class InvalidMessageException extends GeneralCheckedException {
    public InvalidMessageException(String msg) {
        super(msg);
    }

    public InvalidMessageException() {
        super("");
    }
}

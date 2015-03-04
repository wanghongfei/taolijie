package com.fh.taolijie.exception.checked;

/**
 * Created by wanghongfei on 15-3-4.
 */
public class DuplicatedUsernameException extends Exception {
    public DuplicatedUsernameException(String msg) {
        super(msg);
    }
}

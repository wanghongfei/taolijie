package com.fh.taolijie.exception.checked;

import com.fh.taolijie.constant.ErrorCode;
import com.sun.java.browser.dom.DOMUnsupportedException;

/**
 * Created by wanghongfei on 15-3-4.
 */
public class DuplicatedUsernameException extends GeneralCheckedException {
    public DuplicatedUsernameException() {
        super("");
        setCode(ErrorCode.USER_EXIST);
    }
    public DuplicatedUsernameException(String msg) {
        super(msg);
        setCode(ErrorCode.USER_EXIST);
    }
}

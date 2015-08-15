package com.fh.taolijie.exception.checked;

import com.sun.java.browser.dom.DOMUnsupportedException;

/**
 * Created by wanghongfei on 15-3-4.
 */
public class DuplicatedUsernameException extends GeneralCheckedException {
    public DuplicatedUsernameException() {
        super("");
    }
    public DuplicatedUsernameException(String msg) {
        super(msg);
    }
}

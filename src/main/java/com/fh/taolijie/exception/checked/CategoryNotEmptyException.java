package com.fh.taolijie.exception.checked;

/**
 * Created by wanghongfei on 15-3-6.
 */
public class CategoryNotEmptyException extends GeneralCheckedException {
    public CategoryNotEmptyException() {
        super("");
    }

    public CategoryNotEmptyException(String msg) {
        super(msg);
    }
}

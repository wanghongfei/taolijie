package com.fh.taolijie.exception.checked;

import com.fh.taolijie.constant.ErrorCode;

/**
 * Created by wanghongfei on 15-3-6.
 */
public class CategoryNotEmptyException extends GeneralCheckedException {
    public CategoryNotEmptyException() {
        super("");
        setCode(ErrorCode.CATE_NOT_EMPTY);
    }

    public CategoryNotEmptyException(String msg) {
        super(msg);
        setCode(ErrorCode.CATE_NOT_EMPTY);
    }
}

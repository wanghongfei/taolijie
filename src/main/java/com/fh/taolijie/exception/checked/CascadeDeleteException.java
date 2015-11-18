package com.fh.taolijie.exception.checked;

import com.fh.taolijie.constant.ErrorCode;

/**
 * Created by wanghongfei on 15-3-8.
 */
public class CascadeDeleteException extends GeneralCheckedException {
    public CascadeDeleteException(String msg) {
        super(msg);
        setCode(ErrorCode.CATE_NOT_EMPTY);
    }

}

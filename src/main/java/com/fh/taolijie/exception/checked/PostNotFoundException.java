package com.fh.taolijie.exception.checked;

import com.fh.taolijie.constant.ErrorCode;

/**
 * Created by whf on 10/17/15.
 */
public class PostNotFoundException extends GeneralCheckedException {
    public PostNotFoundException() {
        super("");
        setCode(ErrorCode.NOT_FOUND);
    }
}

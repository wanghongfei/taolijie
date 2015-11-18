package com.fh.taolijie.exception.checked;

import com.fh.taolijie.constant.ErrorCode;

/**
 * Created by whf on 10/16/15.
 */
public class JobNotFoundException extends GeneralCheckedException {
    public JobNotFoundException() {
        super("");
        setCode(ErrorCode.NOT_FOUND);
    }
}

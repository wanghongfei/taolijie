package com.fh.taolijie.exception.checked;

import com.fh.taolijie.constant.ErrorCode;

/**
 * Created by whf on 10/30/15.
 */
public class FinalStatusException extends GeneralCheckedException {
    public FinalStatusException() {
        super("");
        setCode(ErrorCode.STATUS_CANNOT_CHANGE);
    }
}

package com.fh.taolijie.exception.checked.quest;

import com.fh.taolijie.constant.ErrorCode;
import com.fh.taolijie.exception.checked.GeneralCheckedException;

/**
 * Created by whf on 10/1/15.
 */
public class RequestCannotChangeException extends GeneralCheckedException {
    public RequestCannotChangeException() {
        super("");
        setCode(ErrorCode.STATUS_CANNOT_CHANGE);
    }
}

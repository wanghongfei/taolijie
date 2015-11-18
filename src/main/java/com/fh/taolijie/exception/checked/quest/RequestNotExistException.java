package com.fh.taolijie.exception.checked.quest;

import com.fh.taolijie.constant.ErrorCode;
import com.fh.taolijie.exception.checked.GeneralCheckedException;

/**
 * Created by whf on 9/24/15.
 */
public class RequestNotExistException extends GeneralCheckedException {
    public RequestNotExistException(String msg) {
        super(msg);
        setCode(ErrorCode.NOT_FOUND);
    }
}

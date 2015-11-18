package com.fh.taolijie.exception.checked.acc;

import com.fh.taolijie.constant.ErrorCode;
import com.fh.taolijie.exception.checked.GeneralCheckedException;

/**
 * Created by wanghongfei on 15-3-4.
 */
public class UserNotExistsException extends GeneralCheckedException {
    public UserNotExistsException() {
        super("");
        setCode(ErrorCode.USER_NOT_EXIST);
    }
    public UserNotExistsException(String msg) {
        super(msg);
        setCode(ErrorCode.USER_NOT_EXIST);
    }
}

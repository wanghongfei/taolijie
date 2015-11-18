package com.fh.taolijie.exception.checked.acc;

import com.fh.taolijie.constant.ErrorCode;
import com.fh.taolijie.exception.checked.GeneralCheckedException;

/**
 * Created by wanghongfei on 15-4-9.
 */
public class UserInvalidException extends GeneralCheckedException {
    public UserInvalidException() {
        super("");
        setCode(ErrorCode.USER_INVALID);
    }
    public UserInvalidException(String msg) {
        super(msg);
        setCode(ErrorCode.USER_INVALID);
    }
}

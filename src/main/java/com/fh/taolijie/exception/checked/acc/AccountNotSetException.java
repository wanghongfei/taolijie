package com.fh.taolijie.exception.checked.acc;

import com.fh.taolijie.constant.ErrorCode;
import com.fh.taolijie.exception.checked.GeneralCheckedException;

/**
 * Created by whf on 11/18/15.
 */
public class AccountNotSetException extends GeneralCheckedException {
    public AccountNotSetException() {
        super("");
        setCode(ErrorCode.ACC_NOT_SET);
    }
}

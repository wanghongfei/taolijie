package com.fh.taolijie.exception.checked.acc;

import com.fh.taolijie.constant.ErrorCode;
import com.fh.taolijie.exception.checked.GeneralCheckedException;

/**
 * Created by whf on 9/25/15.
 */
public class WithdrawNotExistsException extends GeneralCheckedException{
    public WithdrawNotExistsException(String msg) {
        super(msg);
        setCode(ErrorCode.NOT_FOUND);
    }
}

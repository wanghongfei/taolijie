package com.fh.taolijie.exception.checked.acc;

import com.fh.taolijie.exception.checked.GeneralCheckedException;

/**
 * Created by whf on 9/27/15.
 */
public class InvalidDealPwdException extends GeneralCheckedException {
    public InvalidDealPwdException(String msg) {
        super(msg);
    }
}

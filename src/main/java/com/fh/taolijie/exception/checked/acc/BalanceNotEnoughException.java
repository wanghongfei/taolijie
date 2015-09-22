package com.fh.taolijie.exception.checked.acc;

import com.fh.taolijie.exception.checked.GeneralCheckedException;

/**
 * Created by whf on 9/20/15.
 */
public class BalanceNotEnoughException extends GeneralCheckedException {
    public BalanceNotEnoughException(String msg) {
        super(msg);
    }
}

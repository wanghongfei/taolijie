package com.fh.taolijie.exception.checked.acc;

import com.fh.taolijie.constant.ErrorCode;
import com.fh.taolijie.exception.checked.GeneralCheckedException;

/**
 * Created by whf on 10/30/15.
 */
public class OrderNotFoundException extends GeneralCheckedException {
    public OrderNotFoundException() {
        super("");
        setCode(ErrorCode.NOT_FOUND);
    }
}

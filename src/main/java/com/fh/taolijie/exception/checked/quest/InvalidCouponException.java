package com.fh.taolijie.exception.checked.quest;

import com.fh.taolijie.constant.ErrorCode;
import com.fh.taolijie.exception.checked.GeneralCheckedException;

/**
 * Created by whf on 10/27/15.
 */
public class InvalidCouponException extends GeneralCheckedException {
    public InvalidCouponException() {
        super("");
        setCode(ErrorCode.COUPON_INVALID);
    }
}

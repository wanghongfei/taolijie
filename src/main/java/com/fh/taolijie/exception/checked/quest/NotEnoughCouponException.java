package com.fh.taolijie.exception.checked.quest;

import com.fh.taolijie.constant.ErrorCode;
import com.fh.taolijie.exception.checked.GeneralCheckedException;

/**
 * Created by whf on 10/27/15.
 */
public class NotEnoughCouponException extends GeneralCheckedException {
    public NotEnoughCouponException() {
        super("");
        setCode(ErrorCode.ASSIGN_COUPON_FAILED);
    }
}

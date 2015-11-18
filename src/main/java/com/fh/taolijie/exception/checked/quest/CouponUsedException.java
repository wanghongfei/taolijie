package com.fh.taolijie.exception.checked.quest;

import com.fh.taolijie.constant.ErrorCode;
import com.fh.taolijie.exception.checked.GeneralCheckedException;

/**
 * Created by whf on 10/27/15.
 */
public class CouponUsedException extends GeneralCheckedException {
    public CouponUsedException() {
        super("");
        setCode(ErrorCode.COUPON_USED);
    }
}

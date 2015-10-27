package com.fh.taolijie.constant.quest;

/**
 * Created by whf on 10/27/15.
 */
public enum CouponStatus {
    /**
     * 未被任取
     */
    NOT_ASSIGNED(0),
    /**
     * 已经领取
     */
    ASSIGNED(1),
    /**
     * 已经使用
     */
    USED(2);

    private int code;
    private CouponStatus(int code) {
        this.code = code;
    }

    public int code() {
        return this.code;
    }
}

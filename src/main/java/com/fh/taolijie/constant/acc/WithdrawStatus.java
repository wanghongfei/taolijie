package com.fh.taolijie.constant.acc;

/**
 * Created by whf on 9/21/15.
 */
public enum WithdrawStatus {
    WAIT_AUDIT("00"),
    WAIT_DISPATCH("01"),
    DONE("02"),
    FAILED("03");

    private String code;

    private WithdrawStatus(String code) {
        this.code = code;
    }

    public String code() {
        return this.code;
    }
}

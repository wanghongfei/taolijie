package com.fh.taolijie.constant.quest;

/**
 * Created by whf on 9/21/15.
 */
public enum OrderStatus {
    WAIT_AUDIT("00"),
    DONE("01"),
    FAILED("02");

    private String code;

    private OrderStatus(String code) {
        this.code = code;
    }

    public String code() {
        return this.code;
    }
}

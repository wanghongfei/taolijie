package com.fh.taolijie.constant.quest;

/**
 * Created by whf on 9/21/15.
 */
public enum OrderType {
    CHARGE("00");

    private String code;

    private OrderType(String code) {
        this.code = code;
    }

    public String code() {
        return this.code;
    }
}

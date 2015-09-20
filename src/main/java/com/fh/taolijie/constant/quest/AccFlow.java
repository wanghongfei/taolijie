package com.fh.taolijie.constant.quest;

/**
 * Created by whf on 9/20/15.
 */
public enum AccFlow {
    WITHDRAW("00"),
    CHARGE("01"),
    FROZEN("02");

    private String code;

    private AccFlow(String code) {
        this.code = code;
    }

    public String code() {
        return this.code;
    }
}

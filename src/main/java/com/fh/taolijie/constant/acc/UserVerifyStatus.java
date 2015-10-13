package com.fh.taolijie.constant.acc;

/**
 * Created by whf on 9/22/15.
 */
public enum UserVerifyStatus {
    NOT_YET("00"),
    ONGOING("01"),
    DONE("02");


    private String code;

    private UserVerifyStatus(String code) {
        this.code = code;
    }

    public String code() {
        return this.code;
    }
}

package com.fh.taolijie.constant.certi;

/**
 * Created by whf on 9/22/15.
 */
public enum CertiStatus {
    WAIT_AUDIT("00"),
    DONE("01"),
    FAILED("02");

    private String code;

    private CertiStatus(String code) {
        this.code = code;
    }

    public String code() {
        return this.code;
    }
}

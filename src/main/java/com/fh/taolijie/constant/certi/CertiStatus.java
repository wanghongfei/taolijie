package com.fh.taolijie.constant.certi;

/**
 * Created by whf on 9/22/15.
 */
public enum CertiStatus {
    NOT_YET("00"),
    WAIT_AUDIT("01"),
    DONE("02"),
    FAILED("03");

    private String code;

    private CertiStatus(String code) {
        this.code = code;
    }

    public String code() {
        return this.code;
    }

    public static CertiStatus fromCode(String code) {
        switch (code) {
            case "00":
                return NOT_YET;

            case "01":
                return WAIT_AUDIT;

            case "02":
                return DONE;

            case "03":
                return FAILED;

            default:
                return null;
        }
    }
}

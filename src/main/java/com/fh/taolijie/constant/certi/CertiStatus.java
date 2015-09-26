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

    public static CertiStatus fromCode(String code) {
        switch (code) {
            case "00":
                return WAIT_AUDIT;

            case "01":
                return DONE;

            case "02":
                return FAILED;

            default:
                return null;
        }
    }
}

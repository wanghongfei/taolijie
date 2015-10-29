package com.fh.taolijie.constant.acc;

/**
 * Created by whf on 9/21/15.
 */
public enum OrderStatus {
    PAY_WAIT("00"),
    DONE("01"),
    PAY_FAILED("02");

    private String code;

    private OrderStatus(String code) {
        this.code = code;
    }

    public String code() {
        return this.code;
    }

    public static OrderStatus fromCode(String code) {
        if (null == code) {
            return null;
        }

        switch (code) {
            case "00":
                return PAY_WAIT;

            case "01":
                return DONE;

            case "02":
                return PAY_FAILED;

            default:
                return null;
        }
    }
}

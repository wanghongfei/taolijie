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

    public static WithdrawStatus fromCode(String code) {
        if (null == code) {
            return null;
        }

        switch (code) {
            case "00":
                return WAIT_AUDIT;

            case "01":
                return WAIT_DISPATCH;

            case "02":
                return DONE;

            case "03":
                return FAILED;

            default:
                return null;
        }
    }
}

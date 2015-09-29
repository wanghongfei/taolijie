package com.fh.taolijie.constant.quest;

/**
 * Created by whf on 9/22/15.
 */
public enum RequestStatus {
    DONE("00"),
    WAIT_AUDIT("01"),
    FAILED("02");

    private String code;

    private RequestStatus(String code) {
        this.code = code;
    }

    public String code() {
        return this.code;
    }

    public static RequestStatus fromCode(String code) {
        if (null == code) {
            return null;
        }

        switch (code) {
            case "00":
                return DONE;

            case "01":
                return WAIT_AUDIT;

            case "02":
                return FAILED;

            default:
                return null;
        }
    }
}

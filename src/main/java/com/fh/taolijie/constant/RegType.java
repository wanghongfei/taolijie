package com.fh.taolijie.constant;

/**
 * Created by whf on 10/3/15.
 */
public enum RegType {
    USERNAME(0),
    MOBILE(1),
    EMAIL(2);

    private int code;

    private RegType(int code) {
        this.code = code;
    }

    public int code() {
        return this.code;
    }

    public static RegType fromCode(int code) {
        switch (code) {
            case 0:
                return USERNAME;

            case 1:
                return MOBILE;

            case 2:
                return EMAIL;

            default:
                return null;
        }
    }
}

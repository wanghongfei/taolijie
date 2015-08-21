package com.fh.taolijie.constant;

/**
 * Created by whf on 8/21/15.
 */
public enum NotiType {
    ADMIN("0"),
    SYSTEM_AUTO("1"),
    USER("2");

    private String code;

    private NotiType(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }
}

package com.fh.taolijie.constant.acc;

/**
 * Created by whf on 9/20/15.
 */
public enum CashAccStatus {
    NORMAL("00"),
    FROZEN("01");

    private String code;

    private CashAccStatus(String code) {
        this.code = code;
    }

    public String code() {
        return this.code;
    }
}

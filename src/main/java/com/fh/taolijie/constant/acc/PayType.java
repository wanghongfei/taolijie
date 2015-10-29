package com.fh.taolijie.constant.acc;

/**
 * Created by whf on 10/29/15.
 */
public enum PayType {
    ALIPAY(0),
    WECHAT(1);

    private int code;

    private PayType(int code) {
        this.code = code;
    }

    public int code() {
        return this.code;
    }
}

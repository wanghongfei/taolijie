package com.fh.taolijie.constant.acc;

/**
 * Created by whf on 10/29/15.
 */
public enum PayType {
    ALIPAY(0),
    WECHAT(1),
    BANK_ACC(2);

    private int code;

    private PayType(int code) {
        this.code = code;
    }

    public static PayType fromCode(Integer code) {
        if (null == code) {
            return null;
        }

        switch (code) {
            case 0:
                return ALIPAY;

            case 1:
                return WECHAT;

            case 2:
                return BANK_ACC;
        }

        return null;
    }

    public int code() {
        return this.code;
    }
}

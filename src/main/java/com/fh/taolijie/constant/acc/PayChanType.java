package com.fh.taolijie.constant.acc;

/**
 * 支付渠道
 * Created by whf on 10/30/15.
 */
public enum  PayChanType {
    ALIPAY(0),
    WECHAT(1);

    private int code;

    private PayChanType(int code) {
        this.code = code;
    }

    public int code() {
        return this.code;
    }

    public static PayChanType fromCode(Integer code) {
        if (null == code) {
            return null;
        }

        switch (code) {
            case 0:
                return ALIPAY;

            case 1:
                return WECHAT;
        }

        return null;
    }
}

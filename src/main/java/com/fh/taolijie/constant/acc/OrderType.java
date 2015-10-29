package com.fh.taolijie.constant.acc;


import com.fh.taolijie.service.acc.ChargeService;

/**
 * Created by whf on 9/21/15.
 */
public enum OrderType {
    /**
     * 充值到钱包订单
     */
    CHARGE("00"),

    /**
     * 直接支付订单
     */
    DIRECT_PAY("01");

    private String code;

    private OrderType(String code) {
        this.code = code;
    }

    public String code() {
        return this.code;
    }

    public static OrderType fromCode(String code) {
        switch (code) {
            case "00":
                return CHARGE;

            case "01":
                return DIRECT_PAY;
        }

        return null;
    }

}

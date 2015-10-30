package com.fh.taolijie.constant.acc;

/**
 * Created by whf on 9/21/15.
 */
public enum OrderStatus {
    /**
     * 等待支付
     */
    PAY_WAIT("00"),
    /**
     * 交易成功
     */
    DONE("01"),
    /**
     * 支付失败
     */
    PAY_FAILED("02"),
    /**
     * 支付成功
     */
    PAY_SUCCEED("03");

    private String code;

    private OrderStatus(String code) {
        this.code = code;
    }

    public String code() {
        return this.code;
    }

    /**
     * 判断是不是最终状态
     * @return
     */
    public boolean isFinalStatus() {
        if (this == PAY_WAIT) {
            return false;
        }

        return true;
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

            case "03":
                return PAY_SUCCEED;

            default:
                return null;
        }
    }
}

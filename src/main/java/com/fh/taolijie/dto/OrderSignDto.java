package com.fh.taolijie.dto;

/**
 * 订单信息 + 签名信息
 * Created by whf on 10/29/15.
 */
public class OrderSignDto {
    private Integer orderId;

    private String sign;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}

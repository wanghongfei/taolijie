package com.fh.taolijie.dto;

/**
 * 订单信息 + 签名信息
 * Created by whf on 10/29/15.
 */
public class OrderSignDto {
    private Integer orderId;

    private String sign;

    private String servName;

    private String partner;

    private String charset;

    private String signType;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getSign() {
        return sign;
    }

    public String getServName() {
        return servName;
    }

    public void setServName(String servName) {
        this.servName = servName;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}

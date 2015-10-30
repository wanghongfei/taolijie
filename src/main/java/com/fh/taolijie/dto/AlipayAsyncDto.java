package com.fh.taolijie.dto;

/**
 * Created by whf on 10/30/15.
 */
public class AlipayAsyncDto {
    public String notify_time;

    public String notify_type;

    public String notify_id;

    public String sign_type;

    public String sign;

    public String out_trade_no;

    public String subject;

    public String payment_type;

    public String trade_no;

    public String trade_status;

    public String seller_id;

    public String seller_email;

    public String buyer_id;

    public String buyer_email;

    public String total_fee;

    public String quantity;

    public String price;

    public String body;

    public String gmt_create;

    public String gmt_payment;

    public String is_total_fee_adjust;

    public String use_coupon;

    public String discount;

    public String refund_status;

    public String gmt_refund;

    public String getNotify_time() {
        return notify_time;
    }

    public void setNotify_time(String notify_time) {
        this.notify_time = notify_time;
    }

    public String getNotify_type() {
        return notify_type;
    }

    public void setNotify_type(String notify_type) {
        this.notify_type = notify_type;
    }

    public String getNotify_id() {
        return notify_id;
    }

    public void setNotify_id(String notify_id) {
        this.notify_id = notify_id;
    }

    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public String getTrade_no() {
        return trade_no;
    }

    public void setTrade_no(String trade_no) {
        this.trade_no = trade_no;
    }

    public String getTrade_status() {
        return trade_status;
    }

    public void setTrade_status(String trade_status) {
        this.trade_status = trade_status;
    }

    public String getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id;
    }

    public String getSeller_email() {
        return seller_email;
    }

    public void setSeller_email(String seller_email) {
        this.seller_email = seller_email;
    }

    public String getBuyer_id() {
        return buyer_id;
    }

    public void setBuyer_id(String buyer_id) {
        this.buyer_id = buyer_id;
    }

    public String getBuyer_email() {
        return buyer_email;
    }

    public void setBuyer_email(String buyer_email) {
        this.buyer_email = buyer_email;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getGmt_create() {
        return gmt_create;
    }

    public void setGmt_create(String gmt_create) {
        this.gmt_create = gmt_create;
    }

    public String getGmt_payment() {
        return gmt_payment;
    }

    public void setGmt_payment(String gmt_payment) {
        this.gmt_payment = gmt_payment;
    }

    public String getIs_total_fee_adjust() {
        return is_total_fee_adjust;
    }

    public void setIs_total_fee_adjust(String is_total_fee_adjust) {
        this.is_total_fee_adjust = is_total_fee_adjust;
    }

    public String getUse_coupon() {
        return use_coupon;
    }

    public void setUse_coupon(String use_coupon) {
        this.use_coupon = use_coupon;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getRefund_status() {
        return refund_status;
    }

    public void setRefund_status(String refund_status) {
        this.refund_status = refund_status;
    }

    public String getGmt_refund() {
        return gmt_refund;
    }

    public void setGmt_refund(String gmt_refund) {
        this.gmt_refund = gmt_refund;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AlipayAsyncDto{");
        sb.append("notify_time='").append(notify_time).append('\'');
        sb.append(", notify_type='").append(notify_type).append('\'');
        sb.append(", notify_id='").append(notify_id).append('\'');
        sb.append(", sign_type='").append(sign_type).append('\'');
        sb.append(", sign='").append(sign).append('\'');
        sb.append(", out_trade_no='").append(out_trade_no).append('\'');
        sb.append(", subject='").append(subject).append('\'');
        sb.append(", payment_type='").append(payment_type).append('\'');
        sb.append(", trade_no='").append(trade_no).append('\'');
        sb.append(", trade_status='").append(trade_status).append('\'');
        sb.append(", seller_id='").append(seller_id).append('\'');
        sb.append(", seller_email='").append(seller_email).append('\'');
        sb.append(", buyer_id='").append(buyer_id).append('\'');
        sb.append(", buyer_email='").append(buyer_email).append('\'');
        sb.append(", total_fee='").append(total_fee).append('\'');
        sb.append(", quantity='").append(quantity).append('\'');
        sb.append(", price='").append(price).append('\'');
        sb.append(", body='").append(body).append('\'');
        sb.append(", gmt_create='").append(gmt_create).append('\'');
        sb.append(", gmt_payment='").append(gmt_payment).append('\'');
        sb.append(", is_total_fee_adjust='").append(is_total_fee_adjust).append('\'');
        sb.append(", use_coupon='").append(use_coupon).append('\'');
        sb.append(", discount='").append(discount).append('\'');
        sb.append(", refund_status='").append(refund_status).append('\'');
        sb.append(", gmt_refund='").append(gmt_refund).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

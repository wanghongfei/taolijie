package com.fh.taolijie.dto;

/**
 * Created by whf on 11/21/15.
 */
public class WeichatRespDto {
    public String return_code;

    public String return_msg;

    public String mch_appid;

    public String mchid;

    public String device_info;

    public String nonce_str;

    public String result_code;

    public String err_code;

    public String err_code_des;


    public String partner_trade_no;

    public String payment_no;

    public String payment_time;


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("WeichatRespDto{");
        sb.append("return_code='").append(return_code).append('\'');
        sb.append(", return_msg='").append(return_msg).append('\'');
        sb.append(", mch_appid='").append(mch_appid).append('\'');
        sb.append(", mchid='").append(mchid).append('\'');
        sb.append(", device_info='").append(device_info).append('\'');
        sb.append(", nonce_str='").append(nonce_str).append('\'');
        sb.append(", result_code='").append(result_code).append('\'');
        sb.append(", err_code='").append(err_code).append('\'');
        sb.append(", err_code_des='").append(err_code_des).append('\'');
        sb.append(", partner_trade_no='").append(partner_trade_no).append('\'');
        sb.append(", payment_no='").append(payment_no).append('\'');
        sb.append(", payment_time='").append(payment_time).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

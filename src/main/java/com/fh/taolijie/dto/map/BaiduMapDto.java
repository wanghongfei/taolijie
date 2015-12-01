package com.fh.taolijie.dto.map;

/**
 * Created by whf on 12/1/15.
 */
public class BaiduMapDto {
    public String status;
    public String message;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BaiduMapDto{");
        sb.append("status='").append(status).append('\'');
        sb.append(", message='").append(message).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

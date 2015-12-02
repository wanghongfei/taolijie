package com.fh.taolijie.dto.map;


/**
 * Created by whf on 12/1/15.
 */
public class BaiduMapNearbyRespDto {
    public String status;
    public String size;
    public String total;

    public BaiduMapNearbyRespContentDto contents[];


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BaiduMapNearbyRespDto{");
        sb.append("status='").append(status).append('\'');
        sb.append(", size='").append(size).append('\'');
        sb.append(", total='").append(total).append('\'');
        sb.append(", contents=").append(contents);
        sb.append('}');
        return sb.toString();
    }
}

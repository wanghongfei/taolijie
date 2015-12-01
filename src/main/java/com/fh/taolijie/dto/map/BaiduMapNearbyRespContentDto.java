package com.fh.taolijie.dto.map;

import java.util.Arrays;

/**
 * Created by whf on 12/2/15.
 */
public class BaiduMapNearbyRespContentDto {
    public String uid;
    public String geotable_id;
    public String title;
    public String address;
    public String province;
    public String city;
    public String district;
    public String coord_type;
    public String[] location;
    public String tags;
    public Integer distance;
    public String weight;
    public Integer quest_id;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Contents{");
        sb.append("uid='").append(uid).append('\'');
        sb.append(", geotable_id='").append(geotable_id).append('\'');
        sb.append(", title='").append(title).append('\'');
        sb.append(", address='").append(address).append('\'');
        sb.append(", province='").append(province).append('\'');
        sb.append(", city='").append(city).append('\'');
        sb.append(", district='").append(district).append('\'');
        sb.append(", coord_type='").append(coord_type).append('\'');
        sb.append(", location=").append(Arrays.toString(location));
        sb.append(", tags='").append(tags).append('\'');
        sb.append(", distance=").append(distance);
        sb.append(", weight='").append(weight).append('\'');
        sb.append(", quest_id=").append(quest_id);
        sb.append('}');
        return sb.toString();
    }

}

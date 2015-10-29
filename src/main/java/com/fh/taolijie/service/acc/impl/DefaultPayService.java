package com.fh.taolijie.service.acc.impl;

import com.fh.taolijie.constant.acc.PayType;
import com.fh.taolijie.service.acc.PayService;
import com.fh.taolijie.utils.StringUtils;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by whf on 10/29/15.
 */
@Service
public class DefaultPayService implements PayService {
    public static final String SIGN_ALGORITHM = "RSA";

/*    @Autowired
    private Jedis jedis;*/


    @Override
    public String sign(Map<String, String> map, PayType type) {
        if (type == PayType.ALIPAY) {
            // 对参数map排序
            SortedMap<String, String> sortedMap = new TreeMap<>( (String key1, String key2) -> {
                return key1.compareTo(key2);
            });

            sortedMap.putAll(map);

            return StringUtils.genUrlQueryString(sortedMap);
        }

        return null;
    }
}

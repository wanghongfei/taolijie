package com.fh.taolijie.service.acc.impl;

import com.fh.taolijie.constant.RedisKey;
import com.fh.taolijie.constant.acc.PayType;
import com.fh.taolijie.dto.OrderSignDto;
import com.fh.taolijie.service.acc.PayService;
import com.fh.taolijie.utils.JedisUtils;
import com.fh.taolijie.utils.LogUtils;
import com.fh.taolijie.utils.SignUtils;
import com.fh.taolijie.utils.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by whf on 10/29/15.
 */
@Service
public class DefaultPayService implements PayService {
    public static Logger infoLog = LogUtils.getInfoLogger();


    @Autowired
    private JedisPool jedisPool;


    @Override
    public OrderSignDto sign(Map<String, String> map, PayType type) {
        if (type == PayType.ALIPAY) {
            // 得到固定的参数值
            Jedis jedis = JedisUtils.getClient(jedisPool);

            Map<String, String> redisMap = jedis.hgetAll(RedisKey.ALIPAY_CONF.toString());
            String priKey = jedis.hget(RedisKey.SYSCONF.toString(), RedisKey.RSA_PRI_KEY.toString());
            String servName = redisMap.get(RedisKey.SERVICE_NAME.toString());
            String pid = redisMap.get(RedisKey.PID.toString());
            String charset = redisMap.get(RedisKey.CHARSET.toString());
            String url = redisMap.get(RedisKey.NOTIFY_URL.toString());
            String acc = redisMap.get(RedisKey.ALIPAY_ACC.toString());
            String signType = redisMap.get(RedisKey.SIGN_TYPE.toString());

            JedisUtils.returnJedis(jedisPool, jedis);


            // 对参数map排序
            SortedMap<String, String> sortedMap = new TreeMap<>( (String key1, String key2) -> {
                return key1.compareTo(key2);
            });

            sortedMap.put("service", StringUtils.surroundQuotation(servName));
            sortedMap.put("partner", StringUtils.surroundQuotation(pid));
            sortedMap.put("_input_charset", StringUtils.surroundQuotation(charset));
            sortedMap.put("notify_url", StringUtils.surroundQuotation(url));
            sortedMap.put("seller_id", StringUtils.surroundQuotation(acc));

            sortedMap.putAll(map);

            // 拼接请求参数
            String queryStr = StringUtils.genUrlQueryString(sortedMap);
            if (infoLog.isDebugEnabled()) {
                infoLog.debug("query = {}, priKey = {}, charset = {}", queryStr, priKey, charset);

            }
            // 签名
            String sign = SignUtils.sign(queryStr, priKey, charset);

            OrderSignDto dto = new OrderSignDto();
            dto.setCharset(charset);
            dto.setSign(sign);
            dto.setPartner(pid);
            dto.setServName(servName);
            dto.setSignType(signType);

            return dto;
        }

        return null;
    }
}

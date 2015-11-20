package com.fh.taolijie.service.acc.impl;

import com.fh.taolijie.component.http.HttpClientFactory;
import com.fh.taolijie.constant.RedisKey;
import com.fh.taolijie.constant.acc.PayType;
import com.fh.taolijie.dto.OrderSignDto;
import com.fh.taolijie.service.acc.PayService;
import com.fh.taolijie.utils.JedisUtils;
import com.fh.taolijie.utils.LogUtils;
import com.fh.taolijie.utils.SignUtils;
import com.fh.taolijie.utils.StringUtils;
import org.apache.commons.codec.digest.Md5Crypt;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
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
    public static Logger infoLog = LoggerFactory.getLogger(DefaultPayService.class);

    /**
     * 支付宝网关
     */
    public static final String ALIPAY_GATEWAY = "https://mapi.alipay.com/gateway.do";
    public static final String ALIPAY_VERIFY_SERV_NAME = "notify_verify";

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
                infoLog.debug("ALIPAY: query = {}, priKey = {}, charset = {}", queryStr, priKey, charset);
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

        } else if (type == PayType.WECHAT) {
            // 微信支付签名
            Jedis jedis = JedisUtils.getClient(jedisPool);
            Map<String, String> wechatConf = jedis.hgetAll(RedisKey.WECHAT_CONF.toString());
            JedisUtils.returnJedis(jedisPool, jedis);

            String appId = wechatConf.get(RedisKey.WECHAT_APPID.toString());
            String mchId = wechatConf.get(RedisKey.WECHAT_MCHID.toString());
            String secret = wechatConf.get(RedisKey.WECHAT_SECRET.toString());

            // 对参数map排序
            SortedMap<String, String> sortedMap = new TreeMap<>( (String key1, String key2) -> {
                return key1.compareTo(key2);
            });

            sortedMap.putAll(map);
            sortedMap.put("mch_appid", appId);
            sortedMap.put("mchid", mchId);
            String randStr = RandomStringUtils.randomAlphabetic(30);
            sortedMap.put("nonce_str", randStr);

            // 拼接请求参数
            String queryStr = StringUtils.genUrlQueryString(sortedMap);

            // 拼接API密钥
            queryStr = StringUtils.concat(queryStr.length() + 40, queryStr, "&", secret);
            if (infoLog.isDebugEnabled()) {
                infoLog.debug("WECHAT: query = {}", queryStr);
            }

            // MD5
            String md5 = DigestUtils.md5DigestAsHex(queryStr.getBytes()).toUpperCase();
            if (infoLog.isDebugEnabled()) {
                infoLog.debug("WECHAT: sign = {}", md5);
            }

            OrderSignDto dto = new OrderSignDto();
            dto.setSign(md5);
            dto.mch_appid = appId;
            dto.mchid = mchId;
            dto.nonce_str = randStr;
            dto.check_name = "NO_CHECK";
            dto.spbill_create_ip = "127.0.0.1";

            return dto;
        }

        return null;
    }

    @Override
    public boolean verifyNotify(String notifyId) throws IOException {
        if (false == StringUtils.checkNotEmpty(notifyId)) {
            return false;
        }

        // 拼接GET请求
        Jedis jedis = JedisUtils.getClient(jedisPool);
        // 得到PID
        String pid = jedis.hget(RedisKey.ALIPAY_CONF.toString(), RedisKey.PID.toString());
        JedisUtils.returnJedis(jedisPool, jedis);

        String get = StringUtils.concat(150, ALIPAY_GATEWAY, "?", "service=", ALIPAY_VERIFY_SERV_NAME, "&partner=", pid, "&notify_id=", notifyId);


        HttpClient client = HttpClientFactory.getClient();
        HttpGet getMethod = new HttpGet(get);
        HttpResponse resp = client.execute(getMethod);

        String response = StringUtils.stream2String(resp.getEntity().getContent());
        infoLog.info("alipay verify response: {}", response);


        return response.equals("true");
    }
}

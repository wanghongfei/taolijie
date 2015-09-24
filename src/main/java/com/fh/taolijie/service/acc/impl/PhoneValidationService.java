package com.fh.taolijie.service.acc.impl;

import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.StringUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 处理手机验证码
 * Created by whf on 9/24/15.
 */
@Service
public class PhoneValidationService {
    public static final String KEY_PREFIX = "SMS" + Constants.DELIMITER + "PHONE";

    @Qualifier("redisTemplateForString")
    @Autowired
    StringRedisTemplate rt;

    /**
     * 生成短信验证码, 并调用短信发送接口
     * @return
     */
    public String genValidationCode(Integer memId, String mobile) {
        String code = RandomStringUtils.randomNumeric(6);

        // 调用短信接口
        // ... ...

        // 存入Redis
        // 过期时间5min
        rt.opsForValue().set(genKey(memId), code, 5, TimeUnit.MINUTES);


        return code;
    }

    /**
     * 验证短信验证码
     * @param memId
     * @return
     */
    public boolean validateCode(Integer memId, String code) {
        String key = genKey(memId);
        String realCode = rt.opsForValue().get(key);
        if (null == realCode) {
            // 已经过期
            return false;
        }

        boolean result = realCode.equals(code);
        // 从Redis中清除
        rt.delete(key);

        return result;
    }

    /**
     * 生成key名
     * @param memId
     * @return
     */
    private String genKey(Integer memId) {
        return StringUtils.concat(KEY_PREFIX, Constants.DELIMITER, memId);
    }
}

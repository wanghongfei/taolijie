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
    public static final String SMS_KEY_PREFIX = "code" + Constants.DELIMITER + "SMS";

    public static final String WEB_KEY_PREFIX = "code" + Constants.DELIMITER + "WEB";

    @Qualifier("redisTemplateForString")
    @Autowired
    StringRedisTemplate rt;

    /**
     * 生成一个在网页中显示的验证码
     * @param memId
     * @return
     */
    public String genWebValidationCode(Integer memId) {
        String key = genKeyForWEB(memId);

        // 生成随机6位验证码
        String code = RandomStringUtils.randomAlphabetic(6).toLowerCase();
        // 放入redis中
        // 有效时间10min
        rt.opsForValue().set(key, code, 10, TimeUnit.MINUTES);

        return code;
    }

    /**
     * 验证页面上的验证码
     * @param memId
     * @param code
     * @return
     */
    public boolean validateWebCode(Integer memId, String code) {
        String key = genKeyForWEB(memId);

        // 从redis中取出code
        String redisCode = rt.opsForValue().get(key);
        if (null == code) {
            // 已经过期了
            return false;
        }

        // 从redis中清除该code
        rt.delete(key);

        return redisCode.equals(code);
    }

    /**
     * 生成短信验证码, 并调用短信发送接口
     * @return
     */
    public String genSMSValidationCode(Integer memId, String mobile) {
        String code = RandomStringUtils.randomNumeric(6);

        // 调用短信接口
        // ... ...

        // 存入Redis
        // 过期时间5min
        rt.opsForValue().set(genKeyForSMS(memId), code, 5, TimeUnit.MINUTES);


        return code;
    }

    /**
     * 验证短信验证码
     * @param memId
     * @return
     */
    public boolean validateSMSCode(Integer memId, String code) {
        String key = genKeyForSMS(memId);
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
    private String genKeyForSMS(Integer memId) {
        return StringUtils.concat(SMS_KEY_PREFIX, Constants.DELIMITER, memId);
    }

    private String genKeyForWEB(Integer memId) {
        return StringUtils.concat(WEB_KEY_PREFIX, Constants.DELIMITER, memId);
    }
}

package com.fh.taolijie.service.acc.impl;

import com.fh.taolijie.exception.checked.code.SMSIntervalException;
import com.fh.taolijie.exception.checked.code.SMSVendorException;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.LogUtils;
import com.fh.taolijie.utils.SMSUtils;
import com.fh.taolijie.utils.StringUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
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
public class CodeService {
    private static final Logger infoLog = LogUtils.getInfoLogger();

    public static final String SMS_KEY_PREFIX = "code" + Constants.DELIMITER + "SMS";

    public static final String WEB_KEY_PREFIX = "code" + Constants.DELIMITER + "WEB";


    /**
     * 用于验证用户两次发送短信的间隔
     */
    public static final String SMS_CONSTRAIN_KEY_PREFIX = "code" + Constants.DELIMITER + "WEB" + Constants.DELIMITER + "CONSTRAIN";

    @Qualifier("redisTemplateForString")
    @Autowired
    StringRedisTemplate rt;

    /**
     * 生成一个在网页中显示的验证码
     * @param memId
     * @return
     */
    public String genWebValidationCode(String memId) {
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
    public boolean validateWebCode(String memId, String code) {
        String key = genKeyForWEB(memId);

        // 从redis中取出code
        String redisCode = rt.opsForValue().get(key);
        if (null == redisCode) {
            // 已经过期了
            return false;
        }

        // 从redis中清除该code
        rt.delete(key);

        return redisCode.equals(code);
    }

    /**
     * 生成短信验证码, 并调用短信发送接口
     *
     * @return 返回null表示两次短信发送间隔太短
     */
    public String genSMSValidationCode(String memId, String mobile)
            throws SMSIntervalException, SMSVendorException {

        if (!checkSMSInterval(memId)) {
            throw new SMSIntervalException();
        }

        String code = RandomStringUtils.randomNumeric(6);
        if (infoLog.isDebugEnabled()) {
            infoLog.debug("SMS code generated: {}", code);
        }

        // 调用短信接口
        boolean result = SMSUtils.sendCode(code, mobile);
        if (false == result) {
            throw new SMSVendorException();
        }


        // 验证码存入Redis
        // 过期时间5min
        rt.opsForValue().set(genKeyForSMS(memId), code, 5, TimeUnit.MINUTES);


        return code;
    }

    /**
     * 验证短信验证码
     * @param memId
     * @return
     */
    public boolean validateSMSCode(String memId, String code) {
        String key = genKeyForSMS(memId);
        String realCode = rt.opsForValue().get(key);
        if (null == realCode) {
            // 已经过期
            return false;
        }

        boolean result = realCode.equals(code);
        // 只有当验证码正确时才
        // 从Redis中清除
        if (result) {
            rt.delete(key);
        }

        return result;
    }

    /**
     * 检查距离上次发送的间隔是否合法
     * @param memId
     * @return
     */
    private boolean checkSMSInterval(String memId) {
        String key = genKeyForSMSConstrain(memId);

        String val = rt.opsForValue().get(key);

        // 如果没取到
        // 说明距离上次发送已经超过1min了
        if (null == val) {
            // 将本次发送验证码的状态保存到redis中
            // 过期时间为1min
            rt.opsForValue().set(key, "T", 1, TimeUnit.MINUTES);

            return true;
        }

        return false;
    }

    /**
     * 生成key名
     * @param memId
     * @return
     */
    private String genKeyForSMS(String memId) {
        return StringUtils.concat(SMS_KEY_PREFIX, Constants.DELIMITER, memId);
    }

    private String genKeyForSMSConstrain(String memId) {
        return StringUtils.concat(SMS_CONSTRAIN_KEY_PREFIX, Constants.DELIMITER, memId);
    }

    private String genKeyForWEB(String memId) {
        return StringUtils.concat(WEB_KEY_PREFIX, Constants.DELIMITER, memId);
    }
}

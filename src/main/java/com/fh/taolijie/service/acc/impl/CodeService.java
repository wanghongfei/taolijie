package com.fh.taolijie.service.acc.impl;

import com.fh.taolijie.exception.checked.code.SMSIntervalException;
import com.fh.taolijie.exception.checked.code.SMSVendorException;
import com.fh.taolijie.service.pool.FixSizeThreadPool;
import com.fh.taolijie.utils.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;

import java.util.concurrent.TimeUnit;

/**
 * 处理手机验证码
 * Created by whf on 9/24/15.
 */
@Service
public class CodeService {
    private static final Logger infoLog = LoggerFactory.getLogger(CodeService.class);

    public static final String SMS_KEY_PREFIX = "code" + Constants.DELIMITER + "SMS";

    public static final String WEB_KEY_PREFIX = "code" + Constants.DELIMITER + "WEB";


    /**
     * 用于验证用户两次发送短信的间隔
     */
    public static final String SMS_CONSTRAIN_KEY_PREFIX = "code" + Constants.DELIMITER + "WEB" + Constants.DELIMITER + "CONSTRAIN";

    @Autowired
    private JedisPool jedisPool;

    /**
     * 线程池
     */
    @Autowired
    private FixSizeThreadPool pool;


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
        Jedis jedis = JedisUtils.getClient(jedisPool);
        Pipeline pip = jedis.pipelined();
        pip.set(key, code);
        pip.expire(key, (int) TimeUnit.MINUTES.toSeconds(10));
        pip.sync();
        JedisUtils.returnJedis(jedisPool, jedis);


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
        Jedis jedis = JedisUtils.getClient(jedisPool);
        String redisCode = jedis.get(key);
        if (null == redisCode) {
            // 已经过期了
            JedisUtils.returnJedis(jedisPool, jedis);
            return false;
        }

        // 从redis中清除该code
        jedis.del(key);
        JedisUtils.returnJedis(jedisPool, jedis);

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
        pool.getPool().submit(() -> {
            SMSUtils.sendCode(code, mobile);
        });

/*        boolean result = SMSUtils.sendCode(code, mobile);
        if (false == result) {
            throw new SMSVendorException();
        }*/


        // 验证码存入Redis
        // 过期时间5min
        String key = genKeyForSMS(memId);
        Jedis jedis = JedisUtils.getClient(jedisPool);
        Pipeline pip = jedis.pipelined();
        pip.set(key, code);
        pip.expire(key, (int) TimeUnit.MINUTES.toSeconds(5));
        pip.sync();
        JedisUtils.returnJedis(jedisPool, jedis);


        return code;
    }

    /**
     * 验证短信验证码
     * @param memId
     * @return
     */
    public boolean validateSMSCode(String memId, String code) {
        String key = genKeyForSMS(memId);

        Jedis jedis = JedisUtils.getClient(jedisPool);
        String realCode = jedis.get(key);

        if (null == realCode) {
            // 已经过期
            JedisUtils.returnJedis(jedisPool, jedis);
            return false;
        }

        boolean result = realCode.equals(code);
        // 只有当验证码正确时才
        // 从Redis中清除
        if (result) {
            jedis.del(key);
        }
        JedisUtils.returnJedis(jedisPool, jedis);

        return result;
    }

    /**
     * 检查距离上次发送的间隔是否合法
     * @param memId
     * @return
     */
    private boolean checkSMSInterval(String memId) {
        String key = genKeyForSMSConstrain(memId);

        Jedis jedis = JedisUtils.getClient(jedisPool);
        String val = jedis.get(key);

        // 如果没取到
        // 说明距离上次发送已经超过1min了
        if (null == val) {
            // 将本次发送验证码的状态保存到redis中
            // 过期时间为1min
            Pipeline pip = jedis.pipelined();
            pip.set(key, "T");
            pip.expire(key, (int) TimeUnit.MINUTES.toSeconds(1));
            pip.sync();

            JedisUtils.returnJedis(jedisPool, jedis);
            return true;
        }

        JedisUtils.returnJedis(jedisPool, jedis);
        return false;
    }

    /**
     * 生成key名
     * @param memId
     * @return
     */
    private String genKeyForSMS(String memId) {
        return StringUtils.concat(0, SMS_KEY_PREFIX, Constants.DELIMITER, memId);
    }

    private String genKeyForSMSConstrain(String memId) {
        return StringUtils.concat(0, SMS_CONSTRAIN_KEY_PREFIX, Constants.DELIMITER, memId);
    }

    private String genKeyForWEB(String memId) {
        return StringUtils.concat(0, WEB_KEY_PREFIX, Constants.DELIMITER, memId);
    }
}

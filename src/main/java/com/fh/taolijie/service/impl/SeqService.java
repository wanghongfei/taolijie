package com.fh.taolijie.service.impl;

import com.fh.taolijie.constant.PicType;
import com.fh.taolijie.constant.RedisKey;
import com.fh.taolijie.dao.mapper.SeqAvatarModelMapper;
import com.fh.taolijie.dao.mapper.SeqJobModelMapper;
import com.fh.taolijie.dao.mapper.SeqShModelMapper;
import com.fh.taolijie.domain.sequence.SeqAvatarModel;
import com.fh.taolijie.domain.sequence.SeqJobModel;
import com.fh.taolijie.domain.sequence.SeqShModel;
import com.fh.taolijie.dto.SignAndPolicy;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.JedisUtils;
import com.fh.taolijie.utils.LogUtils;
import com.fh.taolijie.utils.StringUtils;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;

import java.io.File;
import java.util.Calendar;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by whf on 9/25/15.
 */
@Service
public class SeqService {
    private static final String TOKEN_PREFIX = "TOKEN" + Constants.DELIMITER;

    @Autowired
    private SeqAvatarModelMapper avaMapper;

    @Autowired
    private SeqShModelMapper shMapper;

    @Autowired
    private SeqJobModelMapper jobMapper;

    @Autowired
    private JedisPool jedisPool;

    private Auth qiniuAuth;
    private String bucket;

    /**
     * 生成7牛上传token
     * @return
     */
    public SignAndPolicy genQiniuToken() {
        if (null == qiniuAuth) {
            initQiniu();
        }

        String key = genShKey();
        String token = qiniuAuth.uploadToken(bucket, key);

        SignAndPolicy sap = new SignAndPolicy();
        sap.saveKey = key;
        sap.sign = token;

        return sap;
    }

    /**
     * 初始化7牛Auth对象
     */
    private void initQiniu() {
        Jedis jedis = JedisUtils.getClient(jedisPool);

        Map<String, String> map = jedis.hgetAll(RedisKey.CONF_QINIU.toString());
        String AK = map.get(RedisKey.CONF_QINIU_AK.toString());
        String SK = map.get(RedisKey.CONF_QINIU_SK.toString());
        this.bucket = map.get(RedisKey.CONF_QINIU_BUCKET.toString());

        JedisUtils.returnJedis(jedisPool, jedis);

        this.qiniuAuth = Auth.create(AK, SK);
    }

    /**
     * 生成上传key
     * @param type
     * @return
     */
    public String genKey(PicType type) {
        switch (type) {
            case JOB:
                return genJobKey();

            case SH:
                return genShKey();

            case EMP_CERTI: // 商家认证图片跟二手用同一个算法
                return genShKey();

            case STU_CERTI:
                return genShKey();

            case AVATAR:
                return genAvatarKey();
        }

        return null;
    }

    /**
     * 检查该用户上次请求间隔是否合理
     * @param memId
     * @return
     */
    public boolean checkInterval(Integer memId) {
        String key = genRedisKey(memId);

        Jedis jedis = JedisUtils.getClient(jedisPool);
        String value = jedis.get(key);

        if (null != value) {
            // 如果有值
            // 说明间隔太短
            return false;
        }

        // 如果没值，则将当前的值放到redis中
        // 并设置过期时间为2s
        Pipeline pip = jedis.pipelined();
        pip.set(key, "T");
        pip.expire(key, 1);
        pip.sync();

        JedisUtils.returnJedis(jedisPool, jedis);

        return true;
    }

    private String genRedisKey(Integer memId) {
        return StringUtils.concat(0, TOKEN_PREFIX, memId, Constants.DELIMITER);
    }


    private String genAvatarKey() {
        SeqAvatarModel model = new SeqAvatarModel();
        avaMapper.insert(model);

        return StringUtils.concat(0, genDatePath(), "avatar-", model.getId());
    }

    private String genShKey() {
        SeqShModel model = new SeqShModel();
        shMapper.insert(model);


        String datePath = genDatePath();
        String picId = StringUtils.concat(0, "sh-", model.getId());
        String key = StringUtils.concat(0, datePath, DigestUtils.md5DigestAsHex(picId.getBytes()));
        return key;
    }

    private String genJobKey() {
        SeqJobModel model = new SeqJobModel();
        jobMapper.insert(model);

        return StringUtils.concat(0, genDatePath(), "job-", model.getId());
    }

    /**
     * 生成/2015/3/14日期路径
     * @return
     */
    private String genDatePath() {
        Calendar calendar = Calendar.getInstance();

        return StringUtils.concat(50, File.separator,
                calendar.get(Calendar.YEAR),
                File.separator,
                calendar.get(Calendar.MONTH) + 1,
                File.separator,
                calendar.get(Calendar.DAY_OF_MONTH),
                File.separator);
    }
}

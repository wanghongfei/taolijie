package com.fh.taolijie.service.impl;

import com.fh.taolijie.constant.PicType;
import com.fh.taolijie.dao.mapper.SeqAvatarModelMapper;
import com.fh.taolijie.dao.mapper.SeqJobModelMapper;
import com.fh.taolijie.dao.mapper.SeqShModelMapper;
import com.fh.taolijie.domain.SeqAvatarModel;
import com.fh.taolijie.domain.SeqJobModel;
import com.fh.taolijie.domain.SeqShModel;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

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

    @Qualifier("redisTemplateForString")
    @Autowired
    StringRedisTemplate rt;


    /**
     * 生成上传凭证
     * @param type
     * @return
     */
    public String genKey(PicType type) {
        switch (type) {
            case JOB:
                return genJobKey();

            case SH:
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
        String value = rt.opsForValue().get(key);

        if (null != value) {
            // 如果有值
            // 说明间隔太短
            return false;
        }

        // 如果没值，则将当前的值放到redis中
        // 并设置过期时间为2s
        rt.opsForValue().set(key, "T", 2, TimeUnit.SECONDS);
        return true;
    }

    private String genRedisKey(Integer memId) {
        return StringUtils.concat(TOKEN_PREFIX, memId, Constants.DELIMITER);
    }


    private String genAvatarKey() {
        SeqAvatarModel model = new SeqAvatarModel();
        avaMapper.insert(model);

        return "avatar-" + model.getId();
    }

    private String genShKey() {
        SeqShModel model = new SeqShModel();
        shMapper.insert(model);

        return "sh-" + model.getId();
    }

    private String genJobKey() {
        SeqJobModel model = new SeqJobModel();
        jobMapper.insert(model);

        return "job-" + model.getId();
    }
}

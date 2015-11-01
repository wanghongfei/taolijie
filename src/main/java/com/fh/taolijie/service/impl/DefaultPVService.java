package com.fh.taolijie.service.impl;

import com.fh.taolijie.constant.RedisKey;
import com.fh.taolijie.service.PVService;
import com.fh.taolijie.utils.JedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by whf on 11/1/15.
 */
@Service
public class DefaultPVService implements PVService {

    @Autowired
    private JedisPool jedisPool;

    @Override
    public String getJobPV(Integer jobId) {
        return queryPV(jobId, RedisKey.HASH_PV_JOB);
    }

    @Override
    public String getShPV(Integer shId) {
        return queryPV(shId, RedisKey.HASH_PV_SH);
    }

    private String queryPV(Integer postId, RedisKey key) {
        Jedis jedis = JedisUtils.getClient(jedisPool);
        String pv = jedis.hget(key.toString(), postId.toString());
        if (null == pv) {
            pv = "0";
        }

        JedisUtils.returnJedis(jedisPool, jedis);

        return pv;
    }
}

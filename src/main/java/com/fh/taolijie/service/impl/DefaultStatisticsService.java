package com.fh.taolijie.service.impl;

import com.fh.taolijie.service.StatisticsService;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.JedisUtils;
import com.fh.taolijie.utils.LogUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Map;

/**
 * Created by whf on 7/7/15.
 */
@Service
public class DefaultStatisticsService implements StatisticsService {

    @Autowired
    private JedisPool jedisPool;

    @Override
    public Map<String, String> getPageViewStatistics() {
        Jedis jedis = JedisUtils.getClient(jedisPool);

        Map<String, String> map = jedis.hgetAll(Constants.RedisKey.PAGE_STATISTICS);

        JedisUtils.returnJedis(jedisPool, jedis);

        return map;
    }
}

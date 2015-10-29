package com.fh.taolijie.service.impl;

import com.fh.taolijie.service.StatisticsService;
import com.fh.taolijie.utils.Constants;
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
        Jedis jedis = null;

        try {
            jedis = jedisPool.getResource();
            return jedis.hgetAll(Constants.RedisKey.PAGE_STATISTICS);

        } catch (Exception ex) {
            LogUtils.logException(ex);

        } finally {
            jedisPool.returnResourceObject(jedis);
        }

        return null;
    }
}

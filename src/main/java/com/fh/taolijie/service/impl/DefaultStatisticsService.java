package com.fh.taolijie.service.impl;

import com.fh.taolijie.service.StatisticsService;
import com.fh.taolijie.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.Map;

/**
 * Created by whf on 7/7/15.
 */
@Service
public class DefaultStatisticsService implements StatisticsService {

    @Autowired
    private Jedis jedis;

    @Override
    public Map<String, String> getPageViewStatistics() {
        return jedis.hgetAll(Constants.RedisKey.PAGE_STATISTICS);
    }
}

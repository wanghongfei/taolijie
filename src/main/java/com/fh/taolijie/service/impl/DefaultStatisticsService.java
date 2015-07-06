package com.fh.taolijie.service.impl;

import com.fh.taolijie.service.StatisticsService;
import com.fh.taolijie.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by whf on 7/7/15.
 */
@Service
public class DefaultStatisticsService implements StatisticsService {
    @Autowired
    RedisTemplate rt;

    @Override
    public Map<String, Integer> getPageViewStatistics() {
        return rt.opsForHash().entries(Constants.RedisKey.PAGE_STATISTICS);
    }
}

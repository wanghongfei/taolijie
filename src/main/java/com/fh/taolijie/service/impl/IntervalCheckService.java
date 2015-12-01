package com.fh.taolijie.service.impl;

import com.fh.taolijie.service.AccountService;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.JedisUtils;
import com.fh.taolijie.utils.StringUtils;
import com.fh.taolijie.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by whf on 8/15/15.
 */
@Service
public class IntervalCheckService {
    @Autowired
    private AccountService accountService;

    @Autowired
    private JedisPool jedisPool;

    /**
     * 线下任务发布时间间隔检查key值
     */
    private final String OFF_QUEST_INTERVAL_PREFIX = "interval" + Constants.DELIMITER + "offquest";


    /**
     * 检查线下任务时间间隔是否合法
     * @param memId
     * @return
     */
    public final boolean checkOffQuestPostInterval(Integer memId) {
        String key = genKey(memId);


        boolean res = JedisUtils.performOnce(jedisPool, jedis -> {
            String val = jedis.get(key);
            boolean result = !StringUtils.checkNotEmpty(val);

            // 如果result == true
            // 表示redis中没有对应的key, 即间隔合法
            if (result) {
                Pipeline pip = jedis.pipelined();
                jedis.set(key, "T");
                jedis.expire(key, 10);
                pip.sync();
            }

            return result;
        });

        return res;
    }

    private String genKey(Integer memId) {
        return StringUtils.concat(OFF_QUEST_INTERVAL_PREFIX.length() + 10, OFF_QUEST_INTERVAL_PREFIX, Constants.DELIMITER, memId);
    }

    /**
     * 检查时间间隔是否足够
     * @param lastDate 较早的时间点
     * @param interval 时间间隔值
     * @param unit 时间间隔的单位, {@link java.util.Calendar}中定义的常量
     * @return
     */
    public final boolean checkInterval(Date lastDate, int interval, TimeUnit unit) {
        Date nowTime = new Date();
        if (null == lastDate) {
            return true;
        }

        boolean result = doCheck(lastDate, nowTime, interval, unit);
        if (false == result) {
            return false;
        }

        return true;
    }


    private boolean doCheck(Date lastJobTime, Date nowTime, int value, TimeUnit unit) {
        // 检查上次发布的时间间隔
        // 如果时间为空，说明这是用户第一次发帖
        if (null != lastJobTime) {
            boolean enoughInterval = TimeUtil.intervalGreaterThan(nowTime, lastJobTime, value, unit);

            // 时间间隔少于1min
            if (false == enoughInterval) {
                return false;
            }
        }

        return true;
    }
}

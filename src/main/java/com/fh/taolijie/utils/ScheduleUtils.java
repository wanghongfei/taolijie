package com.fh.taolijie.utils;

import com.alibaba.fastjson.JSON;
import com.fh.taolijie.cache.message.model.MsgProtocol;
import com.fh.taolijie.constant.ScheduleChannel;
import com.fh.taolijie.exception.checked.ScheduleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;


/**
 * 定时任务工具类
 * Created by whf on 12/18/15.
 */
@Component
public class ScheduleUtils {
    private static Logger log = LoggerFactory.getLogger(ScheduleUtils.class);

    @Autowired
    private JedisPool jedisPool;

    /**
     * 向调度中心投递任务
     * @param body 任务对象
     * @param chan 要投递的Redis channel
     */
    public void postMessage(MsgProtocol body, ScheduleChannel chan) throws ScheduleException {
        // 序列化成JSON
        String json = JSON.toJSONString(body);
        log.info("sending message: {}", json);


        // 发布消息
        Long recvAmt = JedisUtils.performOnce(jedisPool, jedis -> {
            return jedis.publish(chan.code(), json);
        });

        if (recvAmt.longValue() <= 0) {
            throw new ScheduleException("receive amount:" + recvAmt);
        }

    }
}

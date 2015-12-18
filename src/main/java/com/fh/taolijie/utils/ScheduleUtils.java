package com.fh.taolijie.utils;

import com.alibaba.fastjson.JSON;
import com.fh.taolijie.cache.message.model.MsgProtocol;
import com.fh.taolijie.constant.MsgType;
import com.fh.taolijie.constant.ScheduleChannel;
import com.fh.taolijie.controller.restful.schedule.RestScheduleCtr;
import com.fh.taolijie.exception.checked.ScheduleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;
import redis.clients.jedis.JedisPool;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;


/**
 * 定时任务工具类
 * Created by whf on 12/18/15.
 */
@Component
public class ScheduleUtils implements ServletContextAware {
    private static Logger log = LoggerFactory.getLogger(ScheduleUtils.class);

    @Autowired
    private JedisPool jedisPool;

    /**
     * 任务调度配置文件
     */
    private Map<String, Address> scheduleConf;

    /**
     * 向调度中心发信息
     * @param address 回调接口名
     * @param paramMap 参数列表
     * @param chan redis频道名
     * @param exeAt 任务执行时间
     * @param cronExp 任务执行的cron表达式
     *
     * @throws ScheduleException
     */
    public void postMessage(String address, Map<String, String> paramMap, ScheduleChannel chan, Date exeAt, String cronExp) throws ScheduleException {
        Address addr = scheduleConf.get(address);

        // 构造消息对象
        MsgProtocol msg = new MsgProtocol.Builder(
                MsgType.fromCode(addr.type),
                addr.host,
                addr.port,
                RestScheduleCtr.fullUrl(addr.path),
                addr.method,
                exeAt
        ).setParmMap(paramMap)
                .build();

        // 序列化成JSON
        String json = JSON.toJSONString(msg);
        log.info("sending message: {}", json);


        // 发布消息
        Long recvAmt = JedisUtils.performOnce(jedisPool, jedis -> {
            return jedis.publish(chan.code(), json);
        });

        if (recvAmt.longValue() <= 0) {
            throw new ScheduleException("receive amount:" + recvAmt);
        }

    }

    /**
     * 通过ServletContext载入Schedule配置文件
     * @param servletContext
     */
    @Override
    public void setServletContext(ServletContext servletContext) {
        log.info("loading schedule configuration");

        // 读取属性文件
        InputStream in = servletContext.getResourceAsStream("/WEB-INF/classes/schedule/addr.properties");
        if (null == in) {
            // 终止部署
            LogUtils.getErrorLogger().error("addr.properties not found!");
            throw new IllegalStateException("addr.properties not found!");
        }

        Properties prop = new Properties();

        try {
            prop.load(in);

            Map<String, Address> map = new HashMap<>(10);
            for (Map.Entry<Object, Object> entry : prop.entrySet()) {
                map.put((String) entry.getKey(), parseAddress(entry));
            }
            log.debug("schedule configuration: {}", map);

            this.scheduleConf = map;

        } catch (IOException e) {
            // 终止部署
            e.printStackTrace();
            throw new IllegalStateException(e.getMessage());
        }


        log.info("loading schedule conf done");
    }

    /**
     * 解析property的一行
     * @param entry
     * @return
     */
    private Address parseAddress(Map.Entry<Object, Object> entry) {
        String addrInfo = (String) entry.getValue();

        String[] infos = addrInfo.split(Constants.DELIMITER);
        if (null == infos || infos.length != 5) {
            throw new IllegalStateException("invalid schedule configuration!");
        }

        Address addr = new Address();
        addr.type = Integer.valueOf(infos[0]);
        addr.host = infos[1];
        addr.port = Integer.valueOf(infos[2]);
        addr.path = infos[3];
        addr.method = infos[4];

        return addr;
    }

    private class Address {
        public Integer type;
        public String host;
        public Integer port;
        public String path;
        public String method;

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Address{");
            sb.append("type=").append(type);
            sb.append(", host='").append(host).append('\'');
            sb.append(", port=").append(port);
            sb.append(", path='").append(path).append('\'');
            sb.append(", method='").append(method).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }

}

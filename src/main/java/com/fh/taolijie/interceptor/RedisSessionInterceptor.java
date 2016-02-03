package com.fh.taolijie.interceptor;

import cn.fh.security.credential.Credential;
import com.fh.taolijie.service.acc.impl.SessionServ;
import com.fh.taolijie.utils.JedisUtils;
import com.fh.taolijie.utils.SessionUtils;
import com.fh.taolijie.utils.StatefulCredential;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 刷新Credential中的自定义参数到redis中
 * Created by whf on 2/3/16.
 */
@Component
public class RedisSessionInterceptor extends HandlerInterceptorAdapter {
    private Logger log = LoggerFactory.getLogger(RedisSessionInterceptor.class);

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private SessionServ sessionServ;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        Credential credential = SessionUtils.getCredential(request);

        if (null != credential && credential instanceof StatefulCredential) {
            StatefulCredential statefulCredential = (StatefulCredential) credential;

            // 判断参数有无变化
            String sid = SessionUtils.getSid(request);
            if (statefulCredential.isDirty()) {
                log.debug("session changes for {} detected, perform sync", sid);

                // 有变化
                // 同步到redis
                // 在redis中创建
                String key = sessionServ.genRedisKey4Session(sid);

                JedisUtils.performOnce( jedisPool, jedis -> {
                    Pipeline pip = jedis.pipelined();
                    // 先删除老的key
                    pip.del(key);

                    // 再创建新的key
                    // 同步固定参数
                    pip.hset(key, "id", statefulCredential.getId().toString());
                    pip.hset(key, "username", statefulCredential.getUsername());
                    pip.hset(key, "role", statefulCredential.getRoleList().get(0));
                    pip.expire(key, (int) TimeUnit.DAYS.toSeconds(30));

                    // 同步自定义参数
                    for (Map.Entry<String, String> entry : statefulCredential.getAllParameters().entrySet()) {
                        pip.hset(key, entry.getKey(), entry.getValue());
                    }

                    pip.sync();

                    return null;
                });

                statefulCredential.resetDirty();
            } else {
                log.debug("no changes for current session {}", sid);
            }
        }

        super.postHandle(request, response, handler, modelAndView);
    }
}

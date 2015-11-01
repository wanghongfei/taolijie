package com.fh.taolijie.interceptor;

import com.fh.taolijie.constant.RedisKey;
import com.fh.taolijie.utils.JedisUtils;
import com.fh.taolijie.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 二手PV统计拦截器
 * Created by whf on 11/1/15.
 */
public class ShPVInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private JedisPool jedisPool;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String id = StringUtils.getLastToken(request.getRequestURI());

        if (false == StringUtils.isAllDigitChar(id)) {
            return super.preHandle(request, response, handler);
        }

        Jedis jedis = JedisUtils.getClient(jedisPool);
        jedis.hincrBy(RedisKey.HASH_PV_SH.toString(), id, 1);
        JedisUtils.returnJedis(jedisPool, jedis);


        return super.preHandle(request, response, handler);
    }
}

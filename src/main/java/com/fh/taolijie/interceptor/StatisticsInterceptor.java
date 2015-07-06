package com.fh.taolijie.interceptor;

import com.fh.taolijie.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 统计页面访问量
 * Created by whf on 7/6/15.
 */
@Component
public class StatisticsInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    RedisTemplate rt;

    /**
     * 用hash set数据结构存放 uri - number 键值对
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        rt.opsForHash().increment(Constants.RedisKey.PAGE_STATISTICS, uri, 1);

        return super.preHandle(request, response, handler);
    }
}

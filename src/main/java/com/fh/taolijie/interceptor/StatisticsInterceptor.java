package com.fh.taolijie.interceptor;

import cn.fh.security.servlet.PageProtectionContextListener;
import cn.fh.security.servlet.PageProtectionFilter;
import com.fh.taolijie.service.PVService;
import com.fh.taolijie.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * 统计总PV
 * Created by whf on 7/6/15.
 */
@Component
public class StatisticsInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private PVService pvService;

    /**
     * 用hash set数据结构存放 uri - number 键值对
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        pvService.incrAllPV();

        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        super.postHandle(request, response, handler, modelAndView);
    }

}

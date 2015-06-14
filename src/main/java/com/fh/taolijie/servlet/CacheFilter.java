package com.fh.taolijie.servlet;

import com.fh.taolijie.component.ResponseWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by wanghongfei on 15-6-14.
 */

/**
 * 缓存过虑器
 */
public class CacheFilter implements Filter, ApplicationContextAware {
    private static final Logger log = LoggerFactory.getLogger(CacheFilter.class);

    private static ApplicationContext ctx;

    @Override
    public void init(FilterConfig config) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        HttpServletRequest req = (HttpServletRequest) servletRequest;

        // 如果不是访问主页，放行
        if (false == req.getRequestURI().equals("/")) {
            filterChain.doFilter(servletRequest, resp);
            return;
        }


        // 访问的是主页
        // 从缓存中得到主页html
        String html = getHtmlFromCache();
        if (null == html) {
            // 缓存中没有
            // 截取生成的html并放入缓存
            log.info("缓存不存在，生成缓存");
            ResponseWrapper wrapper = new ResponseWrapper(resp);
            // ***** 以上代码在请求被处理之前执行 *****

            filterChain.doFilter(servletRequest, wrapper);

            // ***** 以下代码在请求被处理后前执行 *****

            // 放入缓存
            html = wrapper.getResult();
            putIntoCache(html);

        }

        // 返回响应
        resp.setContentType("text/html; charset=utf-8");
        resp.getWriter().print(html);
    }

    @Override
    public void destroy() {

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }

    private String getHtmlFromCache() {
        StringRedisTemplate redis = (StringRedisTemplate)ctx.getBean("redisTemplate");
        return redis.opsForValue().get("home");
    }

    private void putIntoCache(String html) {
        StringRedisTemplate redis = (StringRedisTemplate)ctx.getBean("redisTemplate");
        redis.opsForValue().set("home", html, TimeUnit.MINUTES.toSeconds(10)); // 10分钟
    }
}

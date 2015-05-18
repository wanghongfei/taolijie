package com.fh.taolijie.interceptor;

import com.alibaba.fastjson.JSON;
import com.fh.taolijie.controller.dto.NewsDto;
import com.fh.taolijie.service.NewsService;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.ObjWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by wynfrith on 15-5-18.
 */

@Component
public class CommonInterceptor  extends HandlerInterceptorAdapter{
    @Autowired
    NewsService newsService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        List<NewsDto> list = newsService.getNewsList(0,3, new ObjWrapper());
        request.setAttribute("titles",list);
        return  true;
    }

}

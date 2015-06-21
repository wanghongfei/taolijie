package com.fh.taolijie.controller.exception;

import com.fh.taolijie.utils.LogUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by wanghongfei on 15-6-20.
 */
@Component
public class ExceptionHandler implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest req, HttpServletResponse resp, Object handler, Exception ex) {
        // 捕获错误信息
        String trace = LogUtils.getStackTrace(ex);
        System.err.println(trace);

        // 置HTTP状态码为500
        resp.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

        // 将错误信息写入日志
        LogUtils.getErrorLogger().error(trace);

        return new ModelAndView("pc/500");
    }
}

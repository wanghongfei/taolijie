package com.fh.taolijie.controller.exception;

import com.fh.taolijie.utils.LogUtils;
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
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, Exception ex) {
        String trace = LogUtils.getStackTrace(ex);
        System.err.println(trace);

        // write error message to log file
        LogUtils.getErrorLogger().error(trace);

        return new ModelAndView("pc/500");
    }
}

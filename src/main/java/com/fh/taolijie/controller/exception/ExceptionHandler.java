package com.fh.taolijie.controller.exception;

import com.alibaba.fastjson.JSON;
import com.fh.taolijie.component.ResponseText;
import com.fh.taolijie.constant.ErrorCode;
import com.fh.taolijie.exception.checked.*;
import com.fh.taolijie.exception.checked.acc.*;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.LogUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.PerformanceMonitorInterceptor;
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
    private static Logger log = LoggerFactory.getLogger(ExceptionHandler.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest req, HttpServletResponse resp, Object handler, Exception ex) {
        log.info("got exception: {}", ex.getClass());

        // 运行时异常
        // 报500
        if (ex instanceof RuntimeException) {
            logException(ex);

            // 置HTTP状态码为500
            resp.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

            // 如果是掊调用报错
            if (isApiRequest(req)) {
                // 返回JSON信息,而不是HTTP页面
                ResponseText rt = new ResponseText(ErrorCode.INTERNAL_ERROR);
                String json = JSON.toJSONString(rt);
                req.setAttribute("json", json);
                resp.setContentType(Constants.Produce.JSON);

                return new ModelAndView("rest-page");
            }


            return new ModelAndView("pc/500");
        }

        // 自定义的异常信息
        // 200
        if (ex instanceof GeneralCheckedException) {
            GeneralCheckedException gex = (GeneralCheckedException) ex;

            // 创建返回JSON信息
            ResponseText rt = new ResponseText(gex.getCode());
            String json = JSON.toJSONString(rt);
            req.setAttribute("json", json);
            resp.setContentType(Constants.Produce.JSON);

            log.info("return = {}", json);

            return new ModelAndView("rest-page");
        }

        // 其它异常
        logException(ex);
        // 置HTTP状态码为500
        resp.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ModelAndView("pc/500");

    }

    private void logException(Exception ex) {
        // 捕获错误信息
        String trace = LogUtils.getStackTrace(ex);
        System.err.println(trace);


        // 将错误信息写入日志
        LogUtils.getErrorLogger().error(trace);

    }

    private boolean isApiRequest(HttpServletRequest req) {
        String uri = req.getRequestURI();

        return uri.startsWith(Constants.URL_API);
    }

}

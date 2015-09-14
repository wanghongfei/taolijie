package com.fh.taolijie.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by wanghongfei on 15-6-20.
 */
public class LogUtils {
    private LogUtils() {}

    public static final String ERROR_LOGGER = "error";
    public static final String INFO_LOG = "info";

    private static Logger errorLog = LoggerFactory.getLogger(ERROR_LOGGER);
    private static Logger infoLog = LoggerFactory.getLogger(INFO_LOG);

    /**
     * 得到错误级别的logger.
     * 该logger输出的内容会被写入到文件中
     * @return
     */
    public static Logger getErrorLogger() {
        return errorLog;
    }

    /**
     * 得到info级别的logger
     * @return
     */
    public static Logger getInfoLogger() {
        return infoLog;
    }

    /**
     * 捕获StackTrace信息到String对象中
     * @param ex
     * @return
     */
    public static String getStackTrace(Exception ex) {
        StringWriter sw = new StringWriter();
        ex.printStackTrace(new PrintWriter(sw, true));

        return sw.toString();
    }

    /**
     * 直接将栈异常信息写入到错误日志中
     * @param ex
     */
    public static void logException(Exception ex) {
        String stack = getStackTrace(ex);
        getErrorLogger().error(stack);
    }
}

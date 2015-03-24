package com.fh.taolijie.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by wanghongfei on 15-3-24.
 */
public class ExceptionUtils {
    private ExceptionUtils() {

    }

    public static String convertToString(Exception ex) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        ex.printStackTrace(pw);

        return sw.toString();
    }
}

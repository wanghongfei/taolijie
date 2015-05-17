package com.fh.taolijie.utils;

import javax.servlet.http.HttpServletRequest;

/**
 *
 */
public class ResponseUtils {
    public enum DeviceToken {
        Android,
        iPad,
        iPhone
    }

    private ResponseUtils() {
    }

    /**
     * 根据请求头User-Agent字段返回不同的View名
     * @param req Request对象
     * @param jspPage JSP示图名(不包含扩展名)
     * @return
     */
    public static String determinePage(HttpServletRequest req, String jspPage) {
        String agent = req.getHeader("User-Agent");

        StringBuilder sb = new StringBuilder();

        if (agent!=null&&(agent.contains(DeviceToken.Android.toString()) || agent.contains(DeviceToken.iPhone.toString()) || agent.contains(DeviceToken.iPad.toString()))) {
            sb.append("mobile/");
        } else {
            sb.append("mobile/");
        }

        sb.append(jspPage);

        return sb.toString();
    }
}

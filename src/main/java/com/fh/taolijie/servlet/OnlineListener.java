package com.fh.taolijie.servlet;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * 用于统计在线人数
 * Created by wanghongfei on 15-4-9.
 */
public class OnlineListener implements HttpSessionListener {
    public static int onlineAmount = 0;

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        onlineAmount++;
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        onlineAmount--;
    }
}

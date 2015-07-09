package com.fh.taolijie.servlet;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 用于统计在线人数
 * Created by wanghongfei on 15-4-9.
 */
public class OnlineListener implements HttpSessionListener {
    /**
     * 使用原子整数避免线程安全问题
     */
    private static AtomicInteger onlineAmount;

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        onlineAmount.incrementAndGet();
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        onlineAmount.decrementAndGet();
    }

    public static int getAOnlineAmount() {
        return onlineAmount.get();
    }
}

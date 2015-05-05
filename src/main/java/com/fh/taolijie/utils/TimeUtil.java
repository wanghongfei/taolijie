package com.fh.taolijie.utils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by wanghongfei on 15-5-5.
 */
public class TimeUtil {
    private TimeUtil() {}

    public static boolean intervalGreaterThan(Date newerDate, Date olderDate, long interval, TimeUnit timeUnit) {
        long olderTime = olderDate.getTime();
        long newerTime = newerDate.getTime();

        long expectedInterval = timeUnit.toMillis(interval);

        return expectedInterval >= newerTime - olderTime;
    }

    public static boolean intervalLessThan(Date newerDate, Date olderDate, long interval, TimeUnit timeUnit) {
        long olderTime = olderDate.getTime();
        long newerTime = newerDate.getTime();

        long expectedInterval = timeUnit.toMillis(interval);

        return expectedInterval <= newerTime - olderTime;

    }
}

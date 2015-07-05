package com.fh.taolijie.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by wanghongfei on 15-5-5.
 */
public class TimeUtil {
    private TimeUtil() {}

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 2030-12-30
     * @return
     */
    public static Date getMaxDate() {
        try {
            return sdf.parse("2030-12-30");
        } catch (ParseException e) {
            e.printStackTrace();
            String msg = LogUtils.getStackTrace(e);
            throw new IllegalStateException(e);
        }
    }

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

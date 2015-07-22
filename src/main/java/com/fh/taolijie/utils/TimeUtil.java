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


    /**
     * 2030-12-30
     * @return
     */
    public static Date getMaxDate() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // SimpleDateFormat不是线程安全的
            return sdf.parse("2030-12-30");
        } catch (ParseException e) {
            e.printStackTrace();
            String msg = LogUtils.getStackTrace(e);
            throw new IllegalStateException(e);
        }
    }

    public static String date2String(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // SimpleDateFormat不是线程安全的
        return sdf.format(date);
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

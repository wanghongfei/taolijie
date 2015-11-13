package com.fh.taolijie.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by wanghongfei on 15-5-5.
 */
public class TimeUtil {
    private TimeUtil() {}

    /**
     * 精确到日的格式
     */
    public static final String FORMAT_DAY = "yyyyMMdd";

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

    public static String date2String(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format); // SimpleDateFormat不是线程安全的
        return sdf.format(date);
    }

    /**
     * 计算指定日期之前或之后的日期.
     * @param date 传入null表示以当前时间为起点
     * @param unit
     * @param value
     * @return
     */
    public static Date calculateDate(Date date, int unit, int value) {
        Calendar calendar = Calendar.getInstance();
        if (null == date) {
            date = new Date();
        }
        calendar.setTime(date);
        calendar.add(unit, value);

        return calendar.getTime();
    }

    /**
     * 获取今天的日期, 精确到天, 以紧凑字符串形式返回
     * @return
     */
    public static String today() {
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DAY);
        return sdf.format(now);
    }

    public static boolean intervalGreaterThan(Date newerDate, Date olderDate, long interval, TimeUnit timeUnit) {
        long olderTime = olderDate.getTime();
        long newerTime = newerDate.getTime();

        long expectedInterval = timeUnit.toMillis(interval);
        long realInterval = newerTime - olderTime;

        return realInterval >= expectedInterval;
    }

    public static boolean intervalLessThan(Date newerDate, Date olderDate, long interval, TimeUnit timeUnit) {
        long olderTime = olderDate.getTime();
        long newerTime = newerDate.getTime();

        long expectedInterval = timeUnit.toMillis(interval);

        return newerTime - olderTime <= expectedInterval;

    }
}

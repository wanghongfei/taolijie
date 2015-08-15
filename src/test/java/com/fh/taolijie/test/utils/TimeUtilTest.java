package com.fh.taolijie.test.utils;

import com.fh.taolijie.utils.TimeUtil;
import org.junit.Assert;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by wanghongfei on 15-5-5.
 */
public class TimeUtilTest {
    @Test
    public void testGreaterThan() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

        // 相差5天
        Date newerDate = sdf.parse("2010/10/15");
        Date olderDate = sdf.parse("2010/10/10");

        Assert.assertTrue(TimeUtil.intervalGreaterThan(newerDate, olderDate, 3, TimeUnit.DAYS));
        Assert.assertTrue(TimeUtil.intervalGreaterThan(newerDate, olderDate, 4, TimeUnit.DAYS));
        Assert.assertTrue(TimeUtil.intervalGreaterThan(newerDate, olderDate, 5, TimeUnit.DAYS));
        Assert.assertFalse(TimeUtil.intervalGreaterThan(newerDate, olderDate, 6, TimeUnit.DAYS));

        // 相差等于一分钟
        sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        newerDate = sdf.parse("2010/1/1 10:10:00");
        olderDate = sdf.parse("2010/1/1 10:9:00");

        boolean result = TimeUtil.intervalGreaterThan(newerDate, olderDate, 1, TimeUnit.MINUTES);
        Assert.assertTrue(result);

        // 相差小于1分钟
        newerDate = sdf.parse("2010/1/1 10:10:00");
        olderDate = sdf.parse("2010/1/1 10:9:1");
        result = TimeUtil.intervalGreaterThan(newerDate, olderDate, 1, TimeUnit.MINUTES);
        Assert.assertFalse(result);
    }
}

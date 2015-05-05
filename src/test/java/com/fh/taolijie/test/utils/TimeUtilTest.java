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

        Assert.assertFalse(TimeUtil.intervalGreaterThan(newerDate, olderDate, 3, TimeUnit.DAYS));
        Assert.assertFalse(TimeUtil.intervalGreaterThan(newerDate, olderDate, 4, TimeUnit.DAYS));
        Assert.assertTrue(TimeUtil.intervalGreaterThan(newerDate, olderDate, 5, TimeUnit.DAYS));
        Assert.assertTrue(TimeUtil.intervalGreaterThan(newerDate, olderDate, 6, TimeUnit.DAYS));
    }
}

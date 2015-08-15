package com.fh.taolijie.test.service;

import com.fh.taolijie.dao.mapper.TestMapper;
import com.fh.taolijie.domain.MemberModel;
import com.fh.taolijie.domain.SqlWrapper;
import com.fh.taolijie.domain.SysNotificationModel;
import com.fh.taolijie.service.AccountService;
import com.fh.taolijie.service.impl.DefaultAccountService;
import com.fh.taolijie.service.impl.IntervalCheckService;
import com.fh.taolijie.test.BaseSpringDataTestClass;
import com.fh.taolijie.utils.TimeUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by whf on 8/15/15.
 */
@ContextConfiguration(classes = {
        IntervalCheckService.class,
        DefaultAccountService.class
})
public class IntervalServiceTest extends BaseSpringDataTestClass {
    @Autowired
    private IntervalCheckService icService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private TestMapper tm;



    @Before
    public void initData() {
        Date now = new Date();
        // 现在的时间
        String nowTime = TimeUtil.date2String(now, "yyyy-MM-dd HH:mm:ss");
        // 现在时间之前1分钟
        Date _1minBefore = TimeUtil.calculateDate(now, Calendar.MINUTE, -1);
        String _1minBeforeStr = TimeUtil.date2String(_1minBefore, "yyyy-MM-dd HH:mm:ss");
        // 现在时间之前2分钟
        Date _2minBefore = TimeUtil.calculateDate(now, Calendar.MINUTE, -2);
        String _2minBeforeStr = TimeUtil.date2String(_1minBefore, "yyyy-MM-dd HH:mm:ss");

        String sql = "insert into member(username, password, last_job_date) values('bruce', '3d4f2bf07dc1be38b20cd6e46949a1071f9d0e3d','" + nowTime + "')";
        System.out.println("执行 sql: " + sql);
        tm.execute(new SqlWrapper(sql));

        sql = "insert into member(username, password, last_job_date) values('bruce2', '3d4f2bf07dc1be38b20cd6e46949a1071f9d0e3d','" + _1minBeforeStr + "')";
        System.out.println("执行 sql: " + sql);
        tm.execute(new SqlWrapper(sql));

        sql = "insert into member(username, password, last_job_date) values('bruce3', '3d4f2bf07dc1be38b20cd6e46949a1071f9d0e3d','" + _2minBeforeStr + "')";
        System.out.println("执行 sql: " + sql);
        tm.execute(new SqlWrapper(sql));

        sql = "insert into member(username, password) values('bruce4', '3d4f2bf07dc1be38b20cd6e46949a1071f9d0e3d')";
        System.out.println("执行 sql: " + sql);
        tm.execute(new SqlWrapper(sql));
    }

    @Test
    public void test() {
        Assert.assertNotNull(icService);
    }

    @Test
    public void testNotEnoughInterval() {
        MemberModel mem = accountService.findMember("bruce", false);
        boolean result = icService.checkInterval(mem.getId(), mem.getLastJobDate(), 1, TimeUnit.MINUTES);
        Assert.assertFalse(result);
    }

    @Test
    public void testJustEnough() {
        MemberModel mem = accountService.findMember("bruce2", false);
        boolean result = icService.checkInterval(mem.getId(), mem.getLastJobDate(), 1, TimeUnit.MINUTES);
        Assert.assertTrue(result);
    }

    @Test
    public void testMuchMoreEnough() {
        MemberModel mem = accountService.findMember("bruce3", false);
        boolean result = icService.checkInterval(mem.getId(), mem.getLastJobDate(), 1, TimeUnit.MINUTES);
        Assert.assertTrue(result);
    }

    @Test
    public void testFlushUpdateTime() {
        MemberModel mem = accountService.findMember("bruce3", false);
        boolean result = icService.checkInterval(mem.getId(), mem.getLastJobDate(), 1, TimeUnit.MINUTES);
        Assert.assertTrue(result);

        mem = accountService.findMember("bruce3", false);
        result = icService.checkInterval(mem.getId(), mem.getLastJobDate(), 1, TimeUnit.MINUTES);
        Assert.assertFalse(result);

    }

    @Test
    public void testTimeIsNull() {
        MemberModel mem = accountService.findMember("bruce4", false);
        boolean result = icService.checkInterval(mem.getId(), mem.getLastJobDate(), 1, TimeUnit.MINUTES);
        Assert.assertTrue(result);
    }
}

package com.fh.taolijie.test.service;

import com.fh.taolijie.dao.mapper.TestMapper;
import com.fh.taolijie.domain.MemberModel;
import com.fh.taolijie.domain.SqlWrapper;
import com.fh.taolijie.domain.SysNotificationModel;
import com.fh.taolijie.service.AccountService;
import com.fh.taolijie.service.impl.DefaultAccountService;
import com.fh.taolijie.service.impl.IntervalCheckService;
import com.fh.taolijie.test.BaseSpringDataTestClass;
import com.fh.taolijie.test.utils.DataUtils;
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
        DataUtils.insertMemberData(tm);
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
    public void testTimeIsNull() {
        MemberModel mem = accountService.findMember("bruce4", false);
        boolean result = icService.checkInterval(mem.getId(), mem.getLastJobDate(), 1, TimeUnit.MINUTES);
        Assert.assertTrue(result);
    }
}

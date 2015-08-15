package com.fh.taolijie.test.service;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.dao.mapper.TestMapper;
import com.fh.taolijie.domain.PrivateNotificationModel;
import com.fh.taolijie.domain.SqlWrapper;
import com.fh.taolijie.service.NotificationService;
import com.fh.taolijie.service.impl.DefaultAccountService;
import com.fh.taolijie.service.impl.DefaultNotificationService;
import com.fh.taolijie.test.BaseSpringDataTestClass;
import com.fh.taolijie.test.utils.DataUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.sql.DataTruncation;
import java.util.Map;

/**
 * Created by whf on 8/15/15.
 */
@ContextConfiguration(classes = {
        DefaultNotificationService.class,
        DefaultAccountService.class
})
public class NotificationServiceTest extends BaseSpringDataTestClass {
    @Autowired
    private TestMapper tm;


    @Autowired
    NotificationService noService;

    private Map<String, Integer> memIdMap;

    @Before
    public void initData() {
        memIdMap = DataUtils.insertMemberData(tm);
        DataUtils.insertPriNotificationData(tm, memIdMap);
    }

    @Test
    public void test() {
        Assert.assertNotNull(memIdMap);
    }

    @Test
    public void testGetPri() {
        ListResult<PrivateNotificationModel> priList = noService.getPriNotification(memIdMap.get("bruce2"), 0, Integer.MAX_VALUE);
        Assert.assertNotNull(priList.getList());
        Assert.assertEquals(2, priList.getResultCount());
    }

    @Test
    public void testGetAllPri() {
/*        ListResult<PrivateNotificationModel> priList = noService.getAllPriNotification(0, Integer.MAX_VALUE);
        Assert.assertNotNull(priList.getList());
        Assert.assertEquals(3, priList.getResultCount());*/
    }
}

package com.fh.taolijie.test.dao.mapper;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.domain.PrivateNotificationModel;
import com.fh.taolijie.service.NotificationService;
import com.fh.taolijie.service.impl.DefaultNotificationService;
import com.fh.taolijie.test.BaseSpringDataTestClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.Arrays;
import java.util.List;

/**
 * Created by wanghongfei on 15-6-12.
 */
@ContextConfiguration(classes = {
        DefaultNotificationService.class
})
public class NotificationServiceTest extends BaseSpringDataTestClass {
    @Autowired
    NotificationService noService;

    @Test
    public void testAll() {
        PrivateNotificationModel model = new PrivateNotificationModel();
        model.setTitle("hello");

        noService.deletePriNotification(1);
        noService.deleteSysNotification(1);
        ListResult<PrivateNotificationModel> list = noService.getPriNotification(1, 0, 100);
        noService.getSysNotification(1, Arrays.asList("GLOBAL", "ADMIN"), 0, 100);
    }
}

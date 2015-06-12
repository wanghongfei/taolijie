package com.fh.taolijie.test.dao.mapper;

import com.fh.taolijie.dao.mapper.NotificationModelMapper;
import com.fh.taolijie.domain.PrivateNotificationModel;
import com.fh.taolijie.service.NotificationService;
import com.fh.taolijie.service.impl.DefaultNotificationService;
import com.fh.taolijie.test.BaseSpringDataTestClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.Date;

/**
 * Created by wanghongfei on 15-6-12.
 */
@ContextConfiguration(classes = {
        DefaultNotificationService.class
})
public class NotificationServiceTest extends BaseSpringDataTestClass {
    @Autowired
    NotificationModelMapper noMapper;

    @Autowired
    NotificationService noService;

    @Test
    public void testAll() {
        noService.findNotification(1);
        noService.getNotificationAmount(1, false);
        noService.markAsRead(1);
        noService.deleteNotification(1);

        PrivateNotificationModel model = new PrivateNotificationModel();
        model.setTitle("hello");
        noService.addNotification(model);
        noService.getNotificationList(1, "GLOBLE",0, 100, null);
        noService.getNotificationList(1, "GLOBLE",false, 0, 100, null);
        noService.getNotificationList(1, "GLOBLE",new Date(), 0, 100, null);
        noService.getNotificationAmount(1, false);
    }
}

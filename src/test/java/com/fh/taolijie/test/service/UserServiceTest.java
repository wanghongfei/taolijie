package com.fh.taolijie.test.service;

import com.fh.taolijie.service.UserService;
import com.fh.taolijie.service.impl.DefaultUserService;
import com.fh.taolijie.test.BaseSpringDataTestClass;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

/**
 * Created by wanghongfei on 15-6-17.
 */
@ContextConfiguration(classes = {
        DefaultUserService.class
})
public class UserServiceTest extends BaseSpringDataTestClass {

    @Autowired
    UserService service;

    @Test
    public void testAll() {
/*        service.likeSHPost(1, 1);
        Assert.assertTrue(service.isJobPostAlreadyLiked(1, 1));

        service.likeSHPost(1, 1);
        Assert.assertTrue(service.isSHPostAlreadyLiked(1, 1));*/
    }
}

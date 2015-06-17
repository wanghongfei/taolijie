package com.fh.taolijie.test.dao.mapper;

import com.fh.taolijie.dao.mapper.ImageModelMapper;
import com.fh.taolijie.domain.BulletinModel;
import com.fh.taolijie.domain.ImageModel;
import com.fh.taolijie.service.BulletinService;
import com.fh.taolijie.service.impl.DefaultBulletinService;
import com.fh.taolijie.service.impl.DefaultImageResourceService;
import com.fh.taolijie.test.BaseSpringDataTestClass;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.Arrays;
import java.util.List;

/**
 * Created by wanghongfei on 15-6-17.
 */
@ContextConfiguration(classes = {
        DefaultBulletinService.class
})
public class ButtetinServiceTest extends BaseSpringDataTestClass {

    @Autowired
    BulletinService service;

    @Test
    public void testAll() {
        BulletinModel b = new BulletinModel();
        b.setContent("1");
        service.addBulletin(b);

        b = service.findOne(1);
        service.deleteBulletin(1);

        List<BulletinModel> list = service.getAll(0, 100, null);
    }
}

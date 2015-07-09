package com.fh.taolijie.test.dao.mapper;

import com.fh.taolijie.domain.BannerPicModel;
import com.fh.taolijie.service.BannerPicService;
import com.fh.taolijie.service.impl.DefaultBannerPicService;
import com.fh.taolijie.test.BaseSpringDataTestClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.Date;

/**
 * Created by wanghongfei on 15-6-17.
 */
@ContextConfiguration(classes = {
        DefaultBannerPicService.class
})
public class BannerServiceTest extends BaseSpringDataTestClass {

    @Autowired
    BannerPicService service;

    @Test
    public void testAll() {
        service.getBannerList(0, 100);

        BannerPicModel ban = new BannerPicModel();
        ban.setId(1);
        ban.setCreatedTime(new Date());
        service.updateBanner(1, ban);

        service.deleteBanner(1);
        service.addBanner(ban);
        service.findBanner(1);
    }
}

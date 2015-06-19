package com.fh.taolijie.test.dao.mapper;

import com.fh.taolijie.domain.ApplicationIntendModel;
import com.fh.taolijie.service.ApplicationIntendService;
import com.fh.taolijie.service.impl.DefaultAppIntendService;
import com.fh.taolijie.test.BaseSpringDataTestClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

/**
 * Created by wanghongfei on 15-6-19.
 */

@ContextConfiguration(classes = {
        DefaultAppIntendService.class
})
public class AppIntendServiceTest extends BaseSpringDataTestClass {
    @Autowired
    ApplicationIntendService service;

    @Test
    public void testAll() {
        service.getByIntend(1, 0, 100);
        service.getByResume(1);

        ApplicationIntendModel model = new ApplicationIntendModel();
        model.setResumeId(1);
        model.setJobPostCategoryId(1);
        service.addIntend(model);

        service.deleteIntend(model);
    }
}

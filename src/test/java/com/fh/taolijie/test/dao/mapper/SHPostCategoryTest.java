package com.fh.taolijie.test.dao.mapper;

import com.fh.taolijie.domain.SHPostCategoryModel;
import com.fh.taolijie.service.ShPostCategoryService;
import com.fh.taolijie.service.impl.DefaultShPostCategoryService;
import com.fh.taolijie.service.impl.Mail;
import com.fh.taolijie.test.BaseSpringDataTestClass;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

/**
 * Created by wanghongfei on 15-6-6.
 */
@ContextConfiguration(classes = {
        DefaultShPostCategoryService.class,
})
public class SHPostCategoryTest extends BaseSpringDataTestClass {
    @Autowired
    ShPostCategoryService cateS;

    @Test
    public void testAdd() {
        SHPostCategoryModel model = new SHPostCategoryModel();
        model.setName("hihi");
        cateS.addCategory(model);
    }
}

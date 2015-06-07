package com.fh.taolijie.test.dao.mapper;

import com.fh.taolijie.dao.mapper.JobPostCategoryModelMapper;
import com.fh.taolijie.domain.JobPostCategoryModel;
import com.fh.taolijie.service.JobPostCateService;
import com.fh.taolijie.service.impl.DefaultJobPostCategoryService;
import com.fh.taolijie.test.BaseSpringDataTestClass;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

/**
 * Created by wanghongfei on 15-6-7.
 */
@ContextConfiguration(classes = {
        DefaultJobPostCategoryService.class
})
public class JobPostCategoryTest extends BaseSpringDataTestClass {
    @Autowired
    JobPostCateService cateService;

    @Autowired
    JobPostCategoryModelMapper cateMapper;

    @Test
    public void testGetAll() {
        List<JobPostCategoryModel> list = cateService.getCategoryList(0, 100, null);
        Assert.assertFalse(list.isEmpty());

        JobPostCategoryModel cate = cateService.findCategory(1);
        Assert.assertNotNull(cate);
    }
}

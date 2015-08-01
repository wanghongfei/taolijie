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
/*        List<JobPostCategoryModel> list = cateService.getCategoryList(0, 100, null);
        Assert.assertFalse(list.isEmpty());
        Assert.assertEquals(2, list.size());

        JobPostCategoryModel cate = cateService.findCategory(1);
        Assert.assertNotNull(cate);
        Assert.assertEquals("name1", cate.getName());*/
    }

    /*@Test
    public void testAll() throws Exception {
        JobPostCategoryModel model = new JobPostCategoryModel();
        model.setName("category A");
        cateService.addCategory(model);

        model.setId(1);
        model.setName("category B");
        cateService.updateCategory(1, model);
        model = cateService.findCategory(1);
        Assert.assertEquals("category B", model.getName());

        try {
            cateService.deleteCategory(1);
        } catch (Exception e) {
            return;
        }
        Assert.assertTrue(false);

    }*/
}

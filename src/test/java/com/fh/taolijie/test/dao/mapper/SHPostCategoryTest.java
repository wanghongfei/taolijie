package com.fh.taolijie.test.dao.mapper;

import com.fh.taolijie.dao.mapper.ShPostCategoryModelMapper;
import com.fh.taolijie.domain.SHPostCategoryModel;
import com.fh.taolijie.service.ShPostCategoryService;
import com.fh.taolijie.service.impl.DefaultShPostCategoryService;
import com.fh.taolijie.test.BaseSpringDataTestClass;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

/**
 * Created by wanghongfei on 15-6-6.
 */
@ContextConfiguration(classes = {
        DefaultShPostCategoryService.class,
})
public class SHPostCategoryTest extends BaseSpringDataTestClass {
    @Autowired
    ShPostCategoryService cateS;

    @Autowired
    ShPostCategoryModelMapper cateMapper;

    @Test
    public void testAdd() {
        SHPostCategoryModel model = new SHPostCategoryModel();
        model.setName("hihi");
        cateS.addCategory(model);
    }

    @Test
    public void testGetAll() {
        List<SHPostCategoryModel> list = cateS.getCategoryList(0,100, null);
        Assert.assertFalse(list.isEmpty());
    }

    @Test
    public void testUpdate() {
        SHPostCategoryModel model = new SHPostCategoryModel();
        model.setId(1);
        model.setName("phone");
        cateS.updateCategory(1, model);

        SHPostCategoryModel cate = cateMapper.selectByPrimaryKey(1);
        Assert.assertEquals("phone", cate.getName());
    }

    @Test
    public void testDelete() throws Exception {
        //cateS.deleteCategory(1);
    }
}

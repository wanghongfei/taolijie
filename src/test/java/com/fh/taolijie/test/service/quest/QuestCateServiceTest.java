package com.fh.taolijie.test.service.quest;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.domain.QuestCategoryModel;
import com.fh.taolijie.service.quest.QuestCateService;
import com.fh.taolijie.service.quest.impl.DefaultQuestCateService;
import com.fh.taolijie.test.BaseSpringDataTestClass;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

/**
 * Created by whf on 9/18/15.
 */
@ContextConfiguration(classes = {
        DefaultQuestCateService.class
})
public class QuestCateServiceTest extends BaseSpringDataTestClass {
    @Autowired
    QuestCateService service;

    @Test
    public void testFindById() {
        QuestCategoryModel model = service.find(0);
        Assert.assertNull(model);

        model = service.find(1);
        Assert.assertNotNull(model);
        Assert.assertEquals("cate1", model.getName());
    }

    @Test
    public void testFindByName() {
        QuestCategoryModel model = service.find("cate1");
        Assert.assertNotNull(model);

        model = service.find("hello");
        Assert.assertNull(model);
    }

    @Test
    public void testFindAll() {
        ListResult<QuestCategoryModel> lr = service.findAll(0, 100);
        Assert.assertNotNull(lr);
        Assert.assertEquals(2, lr.getResultCount());

        Assert.assertTrue(lr.getList().stream().anyMatch(cate -> cate.getName().equals("cate1")));;
        Assert.assertTrue(lr.getList().stream().anyMatch(cate -> cate.getName().equals("cate2")));;


        lr = service.findAll(0, 1);
        Assert.assertNotNull(lr);
        Assert.assertEquals(2, lr.getResultCount());

        Assert.assertTrue(lr.getList().stream().anyMatch(cate -> cate.getName().equals("cate1")));;
        Assert.assertFalse(lr.getList().stream().anyMatch(cate -> cate.getName().equals("cate2")));;
    }

    @Test
    public void testAdd() {
        QuestCategoryModel model = new QuestCategoryModel();
        model.setName("cate3");
        service.add(model);

        model = service.find("cate3");
        Assert.assertNotNull(model);
        Assert.assertEquals("cate3", model.getName());
    }

    @Test
    public void testDelete() throws Exception {
        service.delete(1);

        Assert.assertNull(service.find(1));
    }

    @Test
    public void testUpdate() {
        QuestCategoryModel example = new QuestCategoryModel();
        example.setId(1);
        example.setName("new cate");
        service.update(example);

        example = service.find(1);
        Assert.assertEquals("new cate", example.getName());
    }

}

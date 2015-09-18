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
}

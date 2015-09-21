package com.fh.taolijie.test.service.quest;

import com.fh.taolijie.domain.QuestModel;
import com.fh.taolijie.service.quest.QuestService;
import com.fh.taolijie.service.quest.impl.DefaultQuestService;
import com.fh.taolijie.service.quest.impl.FeeCalculator;
import com.fh.taolijie.test.BaseSpringDataTestClass;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by whf on 9/21/15.
 */
@ContextConfiguration(classes = {
        DefaultQuestService.class,
        FeeCalculator.class
})
public class QuestServiceTest extends BaseSpringDataTestClass {
    @Autowired
    private QuestService service;

    @Test
    //@Rollback(false)
    public void testPublish() {
        QuestModel model = new QuestModel();
        model.setTitle("quest1");
        model.setAward(new BigDecimal(10));
        model.setMemberId(1);
        model.setQuestCateId(1);
        model.setStartTime(new Date());
        model.setEndTime(new Date());
        model.setLeftAmt(100);
        model.setTotalAmt(100);

        service.publishQuest(model);
    }
}

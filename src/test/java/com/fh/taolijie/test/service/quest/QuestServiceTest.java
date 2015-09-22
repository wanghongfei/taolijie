package com.fh.taolijie.test.service.quest;

import com.fh.taolijie.constant.quest.AssignStatus;
import com.fh.taolijie.dao.mapper.CashAccModelMapper;
import com.fh.taolijie.domain.CashAccModel;
import com.fh.taolijie.domain.QuestModel;
import com.fh.taolijie.exception.checked.acc.CashAccNotExistsException;
import com.fh.taolijie.exception.checked.quest.QuestAssignedException;
import com.fh.taolijie.exception.checked.quest.QuestZeroException;
import com.fh.taolijie.service.acc.impl.DefaultAccFlowService;
import com.fh.taolijie.service.acc.impl.DefaultCashAccService;
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
        DefaultAccFlowService.class,
        DefaultCashAccService.class,
        FeeCalculator.class
})
public class QuestServiceTest extends BaseSpringDataTestClass {
    @Autowired
    private QuestService service;

    @Autowired
    private CashAccModelMapper accMapper;

    @Test
    //@Rollback(false)
    public void testPublish() throws Exception {
        QuestModel model = new QuestModel();
        model.setTitle("quest1");
        model.setAward(new BigDecimal(5));
        model.setMemberId(1);
        model.setQuestCateId(1);
        model.setStartTime(new Date());
        model.setEndTime(new Date());
        model.setLeftAmt(10);
        model.setTotalAmt(10);

        service.publishQuest(3, model);


        // 检查钱扣了没有
        // 当前费率是20%
        CashAccModel acc = accMapper.selectByPrimaryKey(3);
        Assert.assertEquals(new BigDecimal("40.00"), acc.getAvailableBalance());
    }

    @Test
    //@Rollback(false)
    public void testAssign() throws Exception {
        try {
            service.assignQuest(1, 10);
        } catch (QuestAssignedException e) {
            return;
        }

        Assert.assertTrue(false);
    }

    @Test
    //@Rollback(false)
    public void testAssignToZero() throws Exception {
        testAssign();

        try {
            service.assignQuest(2, 10);
        } catch (QuestZeroException e) {
            return;
        }

        Assert.assertTrue(false);
    }

    @Test
    public void testByCate() {
        service.findByCate(1, new BigDecimal(10), new BigDecimal(100), 0, 100);
        service.findByCate(1, 0, 100);
    }

    @Test
    public void testQueryAssign() {
        service.queryAssignRecords(1, 0, 100);
        service.queryAssignRecords(1, AssignStatus.ASSIGNED, 0, 100);
    }
}

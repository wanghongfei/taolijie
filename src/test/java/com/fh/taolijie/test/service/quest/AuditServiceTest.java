package com.fh.taolijie.test.service.quest;

import com.fh.taolijie.constant.quest.AssignStatus;
import com.fh.taolijie.domain.TljAuditModel;
import com.fh.taolijie.exception.checked.quest.AuditNotEnoughException;
import com.fh.taolijie.service.quest.TljAuditService;
import com.fh.taolijie.service.quest.impl.DefaultTljAuditService;
import com.fh.taolijie.test.BaseSpringDataTestClass;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

/**
 * Created by whf on 10/1/15.
 */
@ContextConfiguration(classes = {
        DefaultTljAuditService.class
})
public class AuditServiceTest extends BaseSpringDataTestClass {
    @Autowired
    private TljAuditService service;

    @Test
    public void testFind() {
        TljAuditModel model = service.findById(1);
        Assert.assertNotNull(model);
    }

    @Test
    public void testInsert() {
        TljAuditModel model = new TljAuditModel();
        model.setEmpId(10);
        model.setEmpUsername("whhf");
        model.setLeftAmt(100);
        model.setTotAmt(1000);
        model.setQuestId(1);
        model.setQuestTitle("title");

        service.addAudit(model);
    }

    @Test
    public void testDecrease() {
        try {
            service.decreaseLeftAmt(1);

        } catch (AuditNotEnoughException e) {
            Assert.assertTrue(false);
            return;
        }

        Assert.assertEquals(0, service.findById(1).getLeftAmt().intValue());

        try {
            service.decreaseLeftAmt(2);
        } catch (AuditNotEnoughException e) {
            return;
        }

        Assert.assertTrue(false);
    }
}

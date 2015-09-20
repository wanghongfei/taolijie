package com.fh.taolijie.test.service.quest;

import com.fh.taolijie.constant.quest.CashAccStatus;
import com.fh.taolijie.domain.CashAccModel;
import com.fh.taolijie.exception.checked.UserNotExistsException;
import com.fh.taolijie.exception.checked.quest.BalanceNotEnoughException;
import com.fh.taolijie.exception.checked.quest.CashAccExistsException;
import com.fh.taolijie.exception.checked.quest.CashAccNotExistsException;
import com.fh.taolijie.service.quest.CashAccService;
import com.fh.taolijie.service.quest.impl.DefaultCashAccService;
import com.fh.taolijie.test.BaseSpringDataTestClass;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by whf on 9/20/15.
 */
@ContextConfiguration(classes = {
        DefaultCashAccService.class
})
public class CashAccServiceTest extends BaseSpringDataTestClass {
    @Autowired
    private CashAccService service;

    @Test
    public void testAdd() throws Exception {
        CashAccModel model = new CashAccModel();
        model.setMemberId(2);
        model.setCreatedTime(new Date());


        try {
            model.setMemberId(1);
            service.addAcc(model);
        } catch (CashAccExistsException ex) {
            return;
        }

        Assert.assertTrue(false);
    }

    @Test
    public void testAddNoUser() throws Exception {
        CashAccModel model = new CashAccModel();
        model.setMemberId(100); // 不存在的id
        model.setCreatedTime(new Date());


        try {
            service.addAcc(model);
        } catch (UserNotExistsException ex) {
            return;
        }

        Assert.assertTrue(false);
    }

    @Test
    public void testAccExists() {
        boolean result = service.checkCashAccExists(1);
        Assert.assertTrue(result);

        result = service.checkCashAccExists(2);
        Assert.assertFalse(result);


        result = service.checkAccIdExists(3);
        Assert.assertTrue(result);

        result = service.checkAccIdExists(100);
        Assert.assertFalse(result);
    }

    @Test
    public void updateStatus() throws Exception {
        service.updateStatus(3, CashAccStatus.FROZEN);
        Assert.assertTrue(CashAccStatus.FROZEN.code().equals(service.findByAcc(3).getStatus()) );

        try {
            service.updateStatus(100, CashAccStatus.FROZEN);
        } catch (CashAccNotExistsException e) {
            return;
        }

        Assert.assertTrue(false);
    }

    @Test
    public void addAvailableAmt() throws Exception {
        service.addAvailableMoney(3, new BigDecimal("100.45"));

        CashAccModel acc = service.findByAcc(3);
        Assert.assertEquals(new BigDecimal("200.45"), acc.getAvailableBalance());
        Assert.assertEquals(new BigDecimal("200.45"), acc.getTotalBalance());
    }

    @Test
    public void testReduceAvailableAmt() throws Exception {
        service.reduceAvailableMoney(3, new BigDecimal(20));

        CashAccModel acc = service.findByAcc(3);
        Assert.assertEquals(new BigDecimal("80.00"), acc.getAvailableBalance());
        Assert.assertEquals(new BigDecimal("80.00"), acc.getTotalBalance());

        try {
            service.reduceAvailableMoney(3, new BigDecimal(120));
        } catch (BalanceNotEnoughException e) {
            return;
        }

        Assert.assertTrue(false);
    }

    @Test
    public void testFrozenAmt() throws Exception {
        service.frozenMoney(3, new BigDecimal(40));

        CashAccModel acc = service.findByAcc(3);
        Assert.assertEquals(new BigDecimal("60.00"), acc.getAvailableBalance());
        Assert.assertEquals(new BigDecimal("100.00"), acc.getTotalBalance());

        Assert.assertEquals(new BigDecimal("40.00"), acc.getFrozenBalance());


        try {
            service.frozenMoney(3, new BigDecimal(2000));
        } catch (BalanceNotEnoughException e) {
            return;
        }

        Assert.assertTrue(false);
    }
}

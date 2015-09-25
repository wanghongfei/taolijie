package com.fh.taolijie.test.service.quest;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.constant.acc.OrderStatus;
import com.fh.taolijie.domain.PayOrderModel;
import com.fh.taolijie.exception.checked.acc.CashAccNotExistsException;
import com.fh.taolijie.service.acc.ChargeService;
import com.fh.taolijie.service.acc.impl.DefaultAccFlowService;
import com.fh.taolijie.service.acc.impl.DefaultCashAccService;
import com.fh.taolijie.service.acc.impl.DefaultChargeService;
import com.fh.taolijie.test.BaseSpringDataTestClass;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;

/**
 * Created by whf on 9/21/15.
 */
@ContextConfiguration(classes = {
        DefaultCashAccService.class,
        DefaultChargeService.class,
        DefaultAccFlowService.class
})
public class ChargeServiceTest extends BaseSpringDataTestClass {
    @Autowired
    private ChargeService service;

    @Test
    //@Rollback(false)
    public void testApplyCharge() throws Exception {
        PayOrderModel model = new PayOrderModel();
        model.setMemberId(1);
        model.setCashAccId(3);
        model.setTitle("充值");
        model.setAmount(new BigDecimal(200));
        model.setAlipayTradeNum("2012394759037459023");

        service.chargeApply(model);
    }

    @Test
    //@Rollback(false)
    public void testUpdateStatus() throws CashAccNotExistsException {

        service.updateStatus(2, OrderStatus.DONE, "OK");
    }

    @Test
    public void testFind() {
        ListResult<PayOrderModel> lr = service.findByAcc(3, 0, 100);
        lr = service.findByAcc(3, OrderStatus.DONE, 0, 100);
        Assert.assertEquals(1, lr.getResultCount());
    }
}

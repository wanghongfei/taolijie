package com.fh.taolijie.test.service.quest;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.constant.acc.WithdrawStatus;
import com.fh.taolijie.domain.WithdrawApplyModel;
import com.fh.taolijie.exception.checked.acc.BalanceNotEnoughException;
import com.fh.taolijie.service.acc.WithdrawService;
import com.fh.taolijie.service.acc.impl.DefaultWithdrawService;
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
        DefaultWithdrawService.class
})
public class WithdrawServiceTest extends BaseSpringDataTestClass {
    @Autowired
    private WithdrawService service;


    @Test
    //@Rollback(false)
    public void testWithdraw() throws Exception {
        WithdrawApplyModel model = new WithdrawApplyModel();
        model.setMemberId(2);
        model.setAccId(3);
        model.setAmount(new BigDecimal(20));

        service.addWithdraw(model, "");


        try {
            model.setAmount(new BigDecimal(200));
            service.addWithdraw(model, "");
        } catch (BalanceNotEnoughException e) {
            return;
        }

        Assert.assertFalse(true);
    }

    @Test
    //@Rollback(false)
    public void testUpdateStatus() throws Exception {
        service.updateStatus(2, WithdrawStatus.DONE, "OK");
    }

    @Test
    public void testFindAll() {
        ListResult<WithdrawApplyModel> lr = service.findAll(0, 100);
        Assert.assertNotNull(lr);
        Assert.assertEquals(2, lr.getResultCount());
        Assert.assertTrue(lr.getList().stream().anyMatch(with -> with.getId().equals(2)));
    }

    @Test
    public void testFind() {
        ListResult<WithdrawApplyModel> lr = service.findAllByStatus(WithdrawStatus.WAIT_AUDIT, 0, 100);
        Assert.assertEquals(1, lr.getResultCount());

        lr = service.findByMember(1, 0, 100);
        Assert.assertEquals(1, lr.getResultCount());

        lr = service.findByMember(1, WithdrawStatus.DONE, 0, 100);
        Assert.assertEquals(1, lr.getResultCount());

    }
}

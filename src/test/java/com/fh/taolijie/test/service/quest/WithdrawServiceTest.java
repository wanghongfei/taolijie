package com.fh.taolijie.test.service.quest;

import com.fh.taolijie.constant.quest.WithdrawStatus;
import com.fh.taolijie.domain.WithdrawApplyModel;
import com.fh.taolijie.exception.checked.quest.BalanceNotEnoughException;
import com.fh.taolijie.service.quest.WithdrawService;
import com.fh.taolijie.service.quest.impl.DefaultWithdrawService;
import com.fh.taolijie.test.BaseSpringDataTestClass;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
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
        model.setMemberId(1);
        model.setAccId(3);
        model.setAmount(new BigDecimal(20));

        service.addWithdraw(model);


        try {
            model.setAmount(new BigDecimal(200));
            service.addWithdraw(model);
        } catch (BalanceNotEnoughException e) {
            return;
        }

        Assert.assertFalse(true);
    }

    @Test
    @Rollback(false)
    public void testUpdateStatus() throws Exception {
        service.updateStatus(2, WithdrawStatus.DONE, "OK");
    }
}

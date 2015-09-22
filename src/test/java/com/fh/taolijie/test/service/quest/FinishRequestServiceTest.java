package com.fh.taolijie.test.service.quest;

import com.fh.taolijie.constant.quest.RequestStatus;
import com.fh.taolijie.domain.FinishRequestModel;
import com.fh.taolijie.service.acc.impl.DefaultAccFlowService;
import com.fh.taolijie.service.acc.impl.DefaultCashAccService;
import com.fh.taolijie.service.quest.QuestFinishService;
import com.fh.taolijie.service.quest.impl.DefaultQuestFinishService;
import com.fh.taolijie.test.BaseSpringDataTestClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;

/**
 * Created by whf on 9/22/15.
 */
@ContextConfiguration(classes = {
        DefaultAccFlowService.class,
        DefaultCashAccService.class,
        DefaultQuestFinishService.class
})
public class FinishRequestServiceTest extends BaseSpringDataTestClass {
    @Autowired
    private QuestFinishService service;

    @Test
    //@Rollback(false)
    public void testSubmit() throws Exception {
        FinishRequestModel model = new FinishRequestModel();
        model.setQuestId(2);
        model.setMemberId(1);
        model.setDescription("i've finished this quest!!");

        service.submitRequest(model);
    }

    @Test
    public void updateStatus() throws Exception {
        service.updateStatus(7, RequestStatus.FAILED, "");
    }
}

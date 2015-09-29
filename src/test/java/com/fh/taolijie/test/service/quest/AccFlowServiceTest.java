package com.fh.taolijie.test.service.quest;

import com.fh.taolijie.service.acc.AccFlowService;
import com.fh.taolijie.service.acc.impl.DefaultAccFlowService;
import com.fh.taolijie.test.BaseSpringDataTestClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

/**
 * Created by whf on 9/21/15.
 */

@ContextConfiguration(classes = {
        DefaultAccFlowService.class
})
public class AccFlowServiceTest extends BaseSpringDataTestClass {
    @Autowired
    private AccFlowService service;

    @Test
    public void testFind() {
/*        ListResult<AccFlowModel> lr = service.findByAcc(3, 0, 100);
        lr = service.findByAccAndInterval(3, new Date(), new Date(), 0, 100);*/
    }
}

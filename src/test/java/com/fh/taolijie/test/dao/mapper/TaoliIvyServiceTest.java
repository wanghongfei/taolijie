package com.fh.taolijie.test.dao.mapper;

import com.fh.taolijie.dao.mapper.v2.TaoliIvyModelMapper;
import com.fh.taolijie.domain.v2.TaoliIvyModel;
import com.fh.taolijie.service.impl.v2.DefaultTaoliIvyService;
import com.fh.taolijie.service.v2.TaoliIvyService;
import com.fh.taolijie.test.BaseSpringDataTestClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

/**
 * Created by wanghongfei on 15-6-10.
 */
@ContextConfiguration(classes = {
        DefaultTaoliIvyService.class
})
public class TaoliIvyServiceTest extends BaseSpringDataTestClass {
    @Autowired
    TaoliIvyModelMapper ivyMapper;

    @Autowired
    TaoliIvyService ivyService;

    @Test
    public void testALl() {
        ivyMapper.selectByPrimaryKey(1);

        TaoliIvyModel model = new TaoliIvyModel(0, 100);
        model.setTitle("he");
        ivyMapper.findBy(model);
        ivyMapper.searchBy(model);

    }
}

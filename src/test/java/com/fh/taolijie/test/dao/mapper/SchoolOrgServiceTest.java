package com.fh.taolijie.test.dao.mapper;

import com.fh.taolijie.dao.mapper.v2.SchoolOrgModelMapper;
import com.fh.taolijie.domain.v2.SchoolOrgModel;
import com.fh.taolijie.service.impl.v2.DefaultSchoolOrgService;
import com.fh.taolijie.service.v2.SchoolOrgService;
import com.fh.taolijie.test.BaseSpringDataTestClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

/**
 * Created by wanghongfei on 15-6-10.
 */
@ContextConfiguration(classes = {
        DefaultSchoolOrgService.class
})
public class SchoolOrgServiceTest extends BaseSpringDataTestClass {
    @Autowired
    SchoolOrgModelMapper orgMapper;

    @Autowired
    SchoolOrgService orgService;

    @Test
    public void testAll() {
        SchoolOrgModel model = new SchoolOrgModel(0, 100);
        model.setId(1);
        model.setTitle("h");
        model.setMemberId(1);
        model.setSchoolOrganizationCategoryId(1);

        orgService.joinSchoolOrg(model);
        orgService.findByCategory(1, 0, 100);
        orgService.joinSchoolOrg(model);
    }
}

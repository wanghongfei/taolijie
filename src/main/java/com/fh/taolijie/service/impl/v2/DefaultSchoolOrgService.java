package com.fh.taolijie.service.impl.v2;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.dao.mapper.MemberModelMapper;
import com.fh.taolijie.dao.mapper.v2.SchoolOrgModelMapper;
import com.fh.taolijie.domain.MemberModel;
import com.fh.taolijie.domain.v2.SchoolOrgModel;
import com.fh.taolijie.service.v2.SchoolOrgService;
import com.fh.taolijie.utils.CollectionUtils;
import com.fh.taolijie.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by wanghongfei on 15-6-10.
 */
@Service
@Transactional(readOnly = true)
public class DefaultSchoolOrgService implements SchoolOrgService {
    @Autowired
    SchoolOrgModelMapper orgMapper;

    @Autowired
    MemberModelMapper memMapper;

    @Override
    @Transactional(readOnly = false)
    public void joinSchoolOrg(SchoolOrgModel model) {
        // 将member表的school_organization字段设为VERIFIED
        MemberModel mem = memMapper.selectByPrimaryKey(model.getMemberId());
        mem.setSchoolOrganization(Constants.VerifyStatus.VERIFIED.toString());
        memMapper.updateByPrimaryKeySelective(mem);

        // 向school org表添加记录
        orgMapper.insert(model);

    }

    @Override
    @Deprecated
    @Transactional(readOnly = false)
    public void deleteSchoolOrg(Integer orgId) {

    }

    @Override
    public ListResult<SchoolOrgModel> findByCategory(Integer cateId, int page, int capacity) {
        SchoolOrgModel model = new SchoolOrgModel(page, CollectionUtils.determineCapacity(capacity));
        model.setSchoolOrganizationCategoryId(cateId);

        List<SchoolOrgModel> list = orgMapper.findBy(model);

        return new ListResult<>(list);
    }
}

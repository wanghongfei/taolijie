package com.fh.taolijie.service.impl.v2;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.dao.mapper.MemberModelMapper;
import com.fh.taolijie.dao.mapper.v2.SchoolActModelMapper;
import com.fh.taolijie.domain.MemberModel;
import com.fh.taolijie.domain.v2.SchoolActModel;
import com.fh.taolijie.service.v2.SchoolActService;
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
public class DefaultSchoolActService implements SchoolActService {
    @Autowired
    SchoolActModelMapper actMapper;

    @Autowired
    MemberModelMapper memMapper;

    @Override
    @Transactional(readOnly = false)
    public boolean postActivity(SchoolActModel model) {
        Integer memId = model.getMemberId();
        MemberModel mem = memMapper.selectByPrimaryKey(memId);
        String status = mem.getSchoolOrganization();
        if (null == status || false == status.equals(Constants.VerifyStatus.VERIFIED)) {
            // 没有校园组织的认证
            return false;
        }

        // 插入活动记录
        actMapper.insert(model);

        return true;
    }


    @Override
    public ListResult<SchoolActModel> findByCategory(Integer cateId, int page, int capacity) {
        SchoolActModel model = new SchoolActModel(page, CollectionUtils.determineCapacity(capacity));
        model.setSchoolActivityCategoryId(cateId);

        List<SchoolActModel> list = actMapper.findBy(model);

        return new ListResult<>(list);
    }

    @Override
    @Transactional(readOnly = false)
    public void update(SchoolActModel model) {
        actMapper.updateByPrimaryKeySelective(model);
    }
}

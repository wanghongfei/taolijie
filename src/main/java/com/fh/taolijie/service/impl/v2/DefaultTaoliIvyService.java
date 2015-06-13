package com.fh.taolijie.service.impl.v2;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.dao.mapper.v2.TaoliIvyModelMapper;
import com.fh.taolijie.domain.v2.TaoliIvyModel;
import com.fh.taolijie.service.v2.TaoliIvyService;
import com.fh.taolijie.utils.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by wanghongfei on 15-6-10.
 */
@Service
@Transactional(readOnly = true)
public class DefaultTaoliIvyService implements TaoliIvyService {
    @Autowired
    TaoliIvyModelMapper ivyMapper;

    @Override
    @Transactional(readOnly = false)
    public void addIvy(TaoliIvyModel model) {
        ivyMapper.insert(model);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteIvy(Integer ivyId) {
        ivyMapper.deleteByPrimaryKey(ivyId);
    }

    @Override
    @Transactional(readOnly = false)
    public void update(TaoliIvyModel model) {
        ivyMapper.updateByPrimaryKeySelective(model);
    }

    @Override
    public ListResult<TaoliIvyModel> getByCategory(Integer cateId, int page, int capacity) {
        TaoliIvyModel model = new TaoliIvyModel(page, CollectionUtils.determineCapacity(capacity));
        model.setTaoliIvyCategoryId(cateId);

        List<TaoliIvyModel> list = ivyMapper.findBy(model);

        return new ListResult<>(list);
    }

    @Override
    public ListResult<TaoliIvyModel> searchBy(TaoliIvyModel model) {
        List<TaoliIvyModel> list = ivyMapper.searchBy(model);

        return new ListResult<>(list);
    }
}

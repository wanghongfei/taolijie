package com.fh.taolijie.service.impl.v2;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.dao.mapper.v2.SchoolActCategoryModelMapper;
import com.fh.taolijie.domain.v2.SchoolActCategoryModel;
import com.fh.taolijie.service.v2.SchoolActCateService;
import com.fh.taolijie.utils.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by wanghongfei on 15-6-12.
 */
@Service
@Transactional(readOnly = true)
public class DefaultSchoolActCateService implements SchoolActCateService {
    @Autowired
    SchoolActCategoryModelMapper cateMapper;

    @Override
    public ListResult<SchoolActCategoryModel> getAll(int pageNumber, int pageSize) {
        List<SchoolActCategoryModel> list = cateMapper.getAll(pageNumber, CollectionUtils.determineCapacity(pageSize));

        return new ListResult<>(list);
    }

    @Override
    public SchoolActCategoryModel getById(Integer cateId) {
        return cateMapper.selectByPrimaryKey(cateId);
    }

    @Override
    @Transactional(readOnly = false)
    public boolean delete(Integer cateId) {
        if (false == cateMapper.checkCategoryEmpty(cateId)) {
            return false;
        }

        cateMapper.deleteByPrimaryKey(cateId);

        return true;
    }

    @Override
    @Transactional(readOnly = false)
    public void update(SchoolActCategoryModel model) {
        cateMapper.updateByPrimaryKeySelective(model);
    }
}

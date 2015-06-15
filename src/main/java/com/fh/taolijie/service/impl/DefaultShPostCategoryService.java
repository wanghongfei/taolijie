package com.fh.taolijie.service.impl;

import com.fh.taolijie.dao.mapper.ShPostCategoryModelMapper;
import com.fh.taolijie.domain.SHPostCategoryModel;
import com.fh.taolijie.exception.checked.CascadeDeleteException;
import com.fh.taolijie.service.ShPostCategoryService;
import com.fh.taolijie.utils.CollectionUtils;
import com.fh.taolijie.utils.ObjWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by wanghongfei on 15-6-5.
 */
@Service
@Transactional(readOnly = true)
public class DefaultShPostCategoryService implements ShPostCategoryService {
    @Autowired
    ShPostCategoryModelMapper cateMapper;

    @Override
    public List<SHPostCategoryModel> getCategoryList(int firstResult, int capacity, ObjWrapper wrapper) {
        return cateMapper.getAll(firstResult, CollectionUtils.determineCapacity(capacity));
    }

    @Override
    public SHPostCategoryModel findCategory(Integer cateId) {
        return cateMapper.selectByPrimaryKey(cateId);
    }

    @Override
    @Transactional(readOnly = false)
    public void addCategory(SHPostCategoryModel dto) {
        cateMapper.insert(dto);
    }

    @Override
    @Transactional(readOnly = false)
    public boolean updateCategory(Integer cateId, SHPostCategoryModel model) {
        model.setId(cateId);
        cateMapper.updateByPrimaryKeySelective(model);

        return true;
    }

    @Override
    @Transactional(readOnly = false)
    public boolean deleteCategory(Integer cateId) throws CascadeDeleteException {
        cateMapper.deleteByPrimaryKey(cateId);

        return true;
    }
}

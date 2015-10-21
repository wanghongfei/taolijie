package com.fh.taolijie.service.sh.impl;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.dao.mapper.ShPostCategoryModelMapper;
import com.fh.taolijie.domain.sh.SHPostCategoryModel;
import com.fh.taolijie.exception.checked.CascadeDeleteException;
import com.fh.taolijie.service.sh.ShPostCategoryService;
import com.fh.taolijie.utils.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by wanghongfei on 15-6-5.
 */
@Service
public class DefaultShPostCategoryService implements ShPostCategoryService {
    @Autowired
    ShPostCategoryModelMapper cateMapper;

    @Override
    @Transactional(readOnly = true)
    public ListResult<SHPostCategoryModel> getCategoryList(int firstResult, int capacity) {
        List<SHPostCategoryModel> list =  cateMapper.getAll(firstResult, CollectionUtils.determineCapacity(capacity));
        long tot = cateMapper.countGetAll();

        return new ListResult<>(list, tot);
    }


    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public boolean deleteCategory(Integer cateId) throws CascadeDeleteException {
        if (false == cateMapper.isCategoryEmpty(cateId)) {
            throw new CascadeDeleteException("category is not empty!");
        }
        cateMapper.deleteByPrimaryKey(cateId);

        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public SHPostCategoryModel findByName(String name) {
        return cateMapper.findByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public SHPostCategoryModel findById(Integer cateId) {
        return cateMapper.selectByPrimaryKey(cateId);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public void updateCategory(SHPostCategoryModel model) {
        cateMapper.updateByPrimaryKeySelective(model);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public void addCategory(SHPostCategoryModel model) {
        cateMapper.insertSelective(model);
    }
}

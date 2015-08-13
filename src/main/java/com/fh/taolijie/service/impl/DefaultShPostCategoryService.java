package com.fh.taolijie.service.impl;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.dao.mapper.BaseMapper;
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
public class DefaultShPostCategoryService extends AbstractBaseService<SHPostCategoryModel>
        implements ShPostCategoryService {
    @Autowired
    ShPostCategoryModelMapper cateMapper;

    @Override
    public ListResult<SHPostCategoryModel> getCategoryList(int firstResult, int capacity, ObjWrapper wrapper) {
        List<SHPostCategoryModel> list =  cateMapper.getAll(firstResult, CollectionUtils.determineCapacity(capacity));
        long tot = cateMapper.countGetAll();

        return new ListResult<>(list, tot);
    }


    @Override
    @Transactional(readOnly = false)
    public boolean deleteCategory(Integer cateId) throws CascadeDeleteException {
        if (false == cateMapper.isCategoryEmpty(cateId)) {
            throw new CascadeDeleteException("category is not empty!");
        }
        cateMapper.deleteByPrimaryKey(cateId);

        return true;
    }

    @Override
    public SHPostCategoryModel findByName(String name) {
        return cateMapper.findByName(name);
    }

    @Override
    protected BaseMapper<SHPostCategoryModel> getMapper() {
        return this.cateMapper;
    }
}

package com.fh.taolijie.service.impl;

import com.fh.taolijie.dao.mapper.SHPostCategoryModelMapper;
import com.fh.taolijie.domain.Pagination;
import com.fh.taolijie.domain.SHPostCategoryModel;
import com.fh.taolijie.exception.checked.CascadeDeleteException;
import com.fh.taolijie.service.SHPostCategoryService;
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
public class DefaultSHPostCategoryService implements SHPostCategoryService {
    @Autowired
    SHPostCategoryModelMapper cateMapper;

    @Override
    public List<SHPostCategoryModel> getCategoryList(int firstResult, int capacity, ObjWrapper wrapper) {
        Pagination page = new Pagination(firstResult, CollectionUtils.determineCapacity(capacity));

        return cateMapper.getAll(page.getMap());
    }

    @Override
    public SHPostCategoryModel findCategory(Integer cateId) {
        return null;
    }

    @Override
    public void addCategory(SHPostCategoryModel dto) {

    }

    @Override
    public boolean updateCategory(Integer cateId, SHPostCategoryModel cateDto) {
        return false;
    }

    @Override
    public boolean deleteCategory(Integer cateId) throws CascadeDeleteException {
        return false;
    }
}

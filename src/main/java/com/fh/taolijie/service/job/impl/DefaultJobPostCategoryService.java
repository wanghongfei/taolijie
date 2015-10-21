package com.fh.taolijie.service.job.impl;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.dao.mapper.JobPostCategoryModelMapper;
import com.fh.taolijie.domain.job.JobPostCategoryModel;
import com.fh.taolijie.exception.checked.CategoryNotEmptyException;
import com.fh.taolijie.service.job.JobPostCateService;
import com.fh.taolijie.utils.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanghongfei on 15-6-6.
 */
@Service
public class DefaultJobPostCategoryService implements JobPostCateService {
    @Autowired
    JobPostCategoryModelMapper cateMapper;

    @Override
    @Transactional(readOnly = true)
    public ListResult<JobPostCategoryModel> getCategoryList(int firstResult, int capacity) {
        List<JobPostCategoryModel> list =  cateMapper.getAll(firstResult, CollectionUtils.determineCapacity(capacity));
        long tot = cateMapper.countGetAll();

        return new ListResult<>(list, tot);
    }

    @Override
    @Transactional(readOnly = true)
    public ListResult<JobPostCategoryModel> getInBatch(List<Integer> idList) {
        List<JobPostCategoryModel> list = null;
        if (!idList.isEmpty()) {
            list = cateMapper.getInBatch(idList);
        } else {
            list = new ArrayList<>(0);
        }

        return new ListResult<>(list, list.size());
    }
    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public void addCategory(JobPostCategoryModel model) {
        cateMapper.insertSelective(model);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public boolean deleteCategory(Integer cateId) throws CategoryNotEmptyException {
        if (false == cateMapper.isCategoryEmpty(cateId)) {
            throw new CategoryNotEmptyException();
        }

        int row = cateMapper.deleteByPrimaryKey(cateId);

        return row <= 0 ? false : true;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public boolean updateCategory(Integer cateId, JobPostCategoryModel model) {
        model.setId(cateId);
        return cateMapper.updateByPrimaryKey(model) <= 0 ? false : true;
    }

    @Override
    public JobPostCategoryModel findCategory(Integer cateId) {
        return cateMapper.selectByPrimaryKey(cateId);
    }

    @Override
    @Transactional(readOnly = true)
    public JobPostCategoryModel findByName(String name) {
        return cateMapper.findByName(name);
    }

}

package com.fh.taolijie.service.impl;

import com.fh.taolijie.controller.dto.JobPostCategoryDto;
import com.fh.taolijie.domain.JobPostCategoryEntity;
import com.fh.taolijie.exception.checked.CategoryNotEmptyException;
import com.fh.taolijie.service.JobPostCateService;
import com.fh.taolijie.service.repository.JobPostCategoryRepo;
import com.fh.taolijie.utils.CheckUtils;
import com.fh.taolijie.utils.CollectionUtils;
import com.fh.taolijie.utils.ObjWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by wanghongfei on 15-3-6.
 */
@Repository
public class DefaultJobPostCategoryService implements JobPostCateService {
    @PersistenceContext
    private EntityManager em;

    @Autowired
    JobPostCategoryRepo cateRepo;

    @Override
    @Transactional(readOnly = true)
    public List<JobPostCategoryDto> getCategoryList(int firstResult, int capacity, ObjWrapper wrapper) {
        int cap = CollectionUtils.determineCapacity(capacity);

        Page<JobPostCategoryEntity> cateList = cateRepo.findAll(new PageRequest(firstResult, cap));
        wrapper.setObj(cateList.getTotalPages());

        return CollectionUtils.transformCollection(cateList, JobPostCategoryDto.class, entity -> {
            return CollectionUtils.entity2Dto(entity, JobPostCategoryDto.class, null);
        });
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public boolean deleteCategory(Integer cateId) throws CategoryNotEmptyException{
        // 判断分类下是否为空
        JobPostCategoryEntity cate = em.find(JobPostCategoryEntity.class, cateId);
        CheckUtils.nullCheck(cate);

        if (null != cate.getJobPostCollection() && false == cate.getJobPostCollection().isEmpty()) {
            throw new CategoryNotEmptyException("分类" + cate.getName() + "不是空的");
        }

        // 删除分类
        em.remove(cate);

        return true;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public boolean updateCategory(Integer cateId, JobPostCategoryDto dto) {
        JobPostCategoryEntity cate = em.find(JobPostCategoryEntity.class, cateId);
        CheckUtils.nullCheck(cate);

        CollectionUtils.updateEntity(cate, dto, null);

        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public JobPostCategoryDto findCategory(Integer cateId) {
        JobPostCategoryEntity cate = em.find(JobPostCategoryEntity.class, cateId);
        CheckUtils.nullCheck(cate);

        return CollectionUtils.entity2Dto(cate, JobPostCategoryDto.class, null);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void addCategory(JobPostCategoryDto dto) {
        JobPostCategoryEntity cate = CollectionUtils.dto2Entity(dto, JobPostCategoryEntity.class, null);
        em.persist(cate);
    }

}

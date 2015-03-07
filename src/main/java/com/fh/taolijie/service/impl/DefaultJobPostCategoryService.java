package com.fh.taolijie.service.impl;

import com.fh.taolijie.controller.dto.JobPostCategoryDto;
import com.fh.taolijie.domain.JobPostCategoryEntity;
import com.fh.taolijie.exception.checked.CategoryNotEmptyException;
import com.fh.taolijie.service.JobPostCateService;
import com.fh.taolijie.utils.Constants;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanghongfei on 15-3-6.
 */
@Repository
public class DefaultJobPostCategoryService implements JobPostCateService {
    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional(readOnly = true)
    public List<JobPostCategoryDto> getCategoryList(int firstResult, int capacity) {
        int cap = capacity;
        if (0 == cap) {
            cap = Constants.PAGE_CAPACITY;
        }

        List<JobPostCategoryEntity> cateList = em.createNamedQuery("jobPostCategoryEntity.findAll", JobPostCategoryEntity.class)
                .setFirstResult(firstResult)
                .setMaxResults(cap)
                .getResultList();

        List<JobPostCategoryDto> dtoList = new ArrayList<>();
        for (JobPostCategoryEntity cate : cateList) {
            dtoList.add(makeCategory(cate));
        }

        return dtoList;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public boolean deleteCategory(Integer cateId) throws CategoryNotEmptyException{
        // 判断分类下是否为空
        JobPostCategoryEntity cate = em.find(JobPostCategoryEntity.class, cateId);
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
        updateCategory(cate, dto);

        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public JobPostCategoryDto findCategory(Integer cateId) {
        return makeCategory(em.find(JobPostCategoryEntity.class, cateId));
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void addCategory(JobPostCategoryDto dto) {
        JobPostCategoryEntity cate = makeCategory(dto);
        em.persist(cate);
    }

    private JobPostCategoryEntity makeCategory(JobPostCategoryDto dto) {
        JobPostCategoryEntity cate = new JobPostCategoryEntity(dto.getName(), dto.getMemo(),
                dto.getLevel());

        return cate;
    }
    private JobPostCategoryDto makeCategory(JobPostCategoryEntity cate) {
        return new JobPostCategoryDto(cate.getName(), cate.getMemo(), cate.getLevel());
    }
    private void updateCategory(JobPostCategoryEntity cate, JobPostCategoryDto dto) {
        cate.setName(dto.getName());
        cate.setMemo(dto.getMemo());
        cate.setLevel(dto.getLevel());
    }
}

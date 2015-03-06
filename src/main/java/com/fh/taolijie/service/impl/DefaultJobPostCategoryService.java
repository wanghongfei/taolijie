package com.fh.taolijie.service.impl;

import com.fh.taolijie.controller.dto.JobPostCategoryDto;
import com.fh.taolijie.controller.dto.JobPostDto;
import com.fh.taolijie.domain.JobPostCategoryEntity;
import com.fh.taolijie.service.JobPostCateService;
import com.fh.taolijie.utils.Constants;
import org.springframework.stereotype.Repository;

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

    @Override
    public List<JobPostCategoryDto> getCategoryList(int firstResult, int capacity) {
        int cap = capacity;
        if (0 == cap) {
            cap = Constants.PAGE_CAPACITY;
        }

        List<JobPostCategoryEntity> cateList = em.createNamedQuery("jobPostCategoryEntity.findAll", JobPostCategoryEntity.class)
                .setFirstResult(firstResult)
                .setMaxResults(cap)
                .getResultList();

        return null;
    }

    @Override
    public boolean deleteCategory(Integer cateId) {
        return false;
    }

    @Override
    public boolean updateCategory(Integer cateId, JobPostDto jobPostDto) {
        return false;
    }

    @Override
    public JobPostCategoryDto findCategory(Integer cateId) {
        return null;
    }

    //private JobPostDto make
}

package com.fh.taolijie.dao.mapper;

import com.fh.taolijie.domain.JobPostCategoryModel;

import java.util.List;

/**
 * Created by wanghongfei on 15-5-31.
 */
public interface JobPostCategoryMapper {
    JobPostCategoryModel getCategoryById(Integer categoryId);

    /**
     * Used filed: pageSize, pageNumber
     */
    List<JobPostCategoryModel> getAllCategory(JobPostCategoryModel model);

    void addCategory(JobPostCategoryModel model);

    void deleteByBatch(List<Integer> idList);
}

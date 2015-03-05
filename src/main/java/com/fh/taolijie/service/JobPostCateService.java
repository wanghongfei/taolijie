package com.fh.taolijie.service;

import com.fh.taolijie.controller.dto.JobPostCategoryDto;
import com.fh.taolijie.controller.dto.JobPostDto;

import java.util.List;

/**
 * 规定与兼职信息分类相关的操作
 * Created by wanghongfei on 15-3-5.
 */
public interface JobPostCateService {
    /**
     * 获取所有分类
     * @return
     */
    List<JobPostDto> getCategoryList();

    /**
     * 删除一个分类.
     * <p> 同时也会删除分类下的所有实体
     * @param cateId
     * @return
     */
    boolean deleteCategory(Integer cateId);

    /**
     * 修改一个兼职分类信息
     * @param cateId
     * @param jobPostDto
     * @return
     */
    boolean updateCategory(Integer cateId, JobPostDto jobPostDto);

    /**
     * 根据id查找兼职分类
     * @param cateId
     * @return
     */
    JobPostCategoryDto findCategory(Integer cateId);
}

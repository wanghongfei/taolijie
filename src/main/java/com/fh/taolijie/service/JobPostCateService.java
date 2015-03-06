package com.fh.taolijie.service;

import com.fh.taolijie.controller.dto.JobPostCategoryDto;
import com.fh.taolijie.exception.checked.CategoryNotEmptyException;

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
    List<JobPostCategoryDto> getCategoryList(int firstResult, int capacity);

    /**
     * 删除一个分类.
     * <p> 只有分类下没有数据时才能成功删除分类
     * @param cateId
     * @return
     */
    boolean deleteCategory(Integer cateId) throws CategoryNotEmptyException;

    /**
     * 修改一个兼职分类信息
     * @param cateId
     * @param dto
     * @return
     */
    boolean updateCategory(Integer cateId, JobPostCategoryDto dto);

    /**
     * 根据id查找兼职分类
     * @param cateId
     * @return
     */
    JobPostCategoryDto findCategory(Integer cateId);
}

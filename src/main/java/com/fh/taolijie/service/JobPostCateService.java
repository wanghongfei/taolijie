package com.fh.taolijie.service;

import com.fh.taolijie.domain.JobPostCategoryModel;
import com.fh.taolijie.exception.checked.CategoryNotEmptyException;
import com.fh.taolijie.utils.ObjWrapper;

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
    List<JobPostCategoryModel> getCategoryList(int firstResult, int capacity, ObjWrapper wrapper);

    /**
     * 添加一个分类
     * @param dto
     */
    void addCategory(JobPostCategoryModel dto);

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
    boolean updateCategory(Integer cateId, JobPostCategoryModel dto);

    /**
     * 根据id查找兼职分类
     * @param cateId
     * @return
     */
    JobPostCategoryModel findCategory(Integer cateId);

    JobPostCategoryModel findByName(String name);
}

package com.fh.taolijie.service;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.domain.JobPostCategoryModel;
import com.fh.taolijie.exception.checked.CategoryNotEmptyException;
import com.fh.taolijie.utils.ObjWrapper;

import java.util.List;

/**
 * 规定与兼职信息分类相关的操作
 * Created by wanghongfei on 15-3-5.
 */
public interface JobPostCateService extends BaseService<JobPostCategoryModel> {
    /**
     * 获取所有分类
     * @return
     */
    ListResult<JobPostCategoryModel> getCategoryList(int firstResult, int capacity, ObjWrapper wrapper);

    /**
     * 删除一个分类.
     * <p> 只有分类下没有数据时才能成功删除分类
     * @param cateId
     * @return
     */
    boolean deleteCategory(Integer cateId) throws CategoryNotEmptyException;

    JobPostCategoryModel findByName(String name);
}

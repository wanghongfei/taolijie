package com.fh.taolijie.service;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.dao.mapper.BaseMapper;
import com.fh.taolijie.domain.SHPostCategoryModel;
import com.fh.taolijie.exception.checked.CascadeDeleteException;
import com.fh.taolijie.service.impl.AbstractBaseService;
import com.fh.taolijie.utils.ObjWrapper;

import java.util.List;

/**
 * 规定与二手帖子分类相关的操作
 * Created by wanghongfei on 15-3-5.
 */
public interface ShPostCategoryService extends BaseService<SHPostCategoryModel> {
    /**
     * 获取(所有)分类
     * @param firstResult
     * @param capacity
     * @return
     */
    ListResult<SHPostCategoryModel> getCategoryList(int firstResult, int capacity);


    /**
     * 删除一个分类. 仅当该分类下没有数据时才能删除
     * @param cateId
     * @return
     */
    boolean deleteCategory(Integer cateId) throws CascadeDeleteException;

    SHPostCategoryModel findByName(String name);
}

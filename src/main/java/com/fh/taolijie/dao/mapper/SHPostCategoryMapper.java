package com.fh.taolijie.dao.mapper;

import com.fh.taolijie.domain.SecondHandPostCategoryModel;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by wanghongfei on 15-6-3.
 */
@Repository
public interface SHPostCategoryMapper {
    SecondHandPostCategoryModel getCategoryById(Integer categoryId);

    /**
     * Used filed: pageSize, pageNumber
     */
    List<SecondHandPostCategoryModel> getAllCategory(SecondHandPostCategoryModel model);

    void addCategory(SecondHandPostCategoryModel model);

    void deleteByBatch(List<Integer> idList);
}

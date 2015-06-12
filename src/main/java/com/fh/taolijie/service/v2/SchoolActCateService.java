package com.fh.taolijie.service.v2;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.domain.v2.SchoolActCategoryModel;

/**
 * Created by wanghongfei on 15-6-12.
 */
public interface SchoolActCateService {
    ListResult<SchoolActCategoryModel> getAll(int pageNumber, int pageSize);
    SchoolActCategoryModel getById(Integer cateId);

    boolean delete(Integer cateId);

    void update(SchoolActCategoryModel model);
}

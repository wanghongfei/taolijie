package com.fh.taolijie.service.v2;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.domain.v2.SchoolOrgCategory;

/**
 * Created by wanghongfei on 15-6-12.
 */
public interface SchoolOrgCateService {
    ListResult<SchoolOrgCategory> getAll(int pageNumber, int pageSize);

    SchoolOrgCategory getById(Integer cateId);

    /**
     * 只有当分类下没有数据时才能删除.
     * @param cateId
     * @return 成功返回true, false表示分类不为空，无法删除
     */
    boolean deleteById(Integer cateId);

    /**
     * 根据id更新字段
     * @param model
     */
    void update(SchoolOrgCateService model);
}

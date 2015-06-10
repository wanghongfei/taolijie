package com.fh.taolijie.service.v2;

import com.fh.taolijie.domain.v2.SchoolOrgModel;
import com.fh.taolijie.utils.ObjWrapper;

import java.util.List;

/**
 * Created by wanghongfei on 15-6-10.
 */
public interface SchoolOrgService {
    /**
     * 让一个用户加入校园组织
     */
    void joinSchoolOrg(SchoolOrgModel model);

    /**
     * @deprecated
     * 删除一个校园组织,同时取消认证
     * @param orgId
     */
    void deleteSchoolOrg(Integer orgId);

    /**
     * 根据分类查找校园组织
     * @param cateId
     * @param page
     * @param capacity
     * @param wrapper
     * @return
     */
    List<SchoolOrgModel> findByCategory(Integer cateId, int page, int capacity, ObjWrapper wrapper);
}

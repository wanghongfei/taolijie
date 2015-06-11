package com.fh.taolijie.service.v2;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.domain.v2.SchoolActModel;

/**
 * Created by wanghongfei on 15-6-10.
 */
public interface SchoolActService {
    /**
     * 发布校园活动.
     * 只有认证组织才能发布。如果不是认证组织，返回false
     */
    boolean postActivity(SchoolActModel model);

    ListResult<SchoolActModel> findByCategory(Integer cateId, int page, int capacity);

    void update(SchoolActModel model);
}

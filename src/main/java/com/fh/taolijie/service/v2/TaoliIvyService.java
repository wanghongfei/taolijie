package com.fh.taolijie.service.v2;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.domain.v2.TaoliIvyModel;

/**
 * Created by wanghongfei on 15-6-10.
 */
public interface TaoliIvyService {
    void addIvy(TaoliIvyModel model);

    void deleteIvy(Integer ivyId);

    /**
     * 更新内容. 不包括分类,作者和时间
     */
    void update(TaoliIvyModel model);

    /**
     * 通过分类查找桃李青藤
     * @param cateId
     */
    ListResult<TaoliIvyModel> getByCategory(Integer cateId, int page, int capacity);
}

package com.fh.taolijie.service.v2;

import com.fh.taolijie.domain.v2.IvyActImgModel;
import com.fh.taolijie.domain.v2.SchoolActModel;
import com.fh.taolijie.utils.ObjWrapper;

import java.util.List;

/**
 * Created by wanghongfei on 15-6-10.
 */
public interface SchoolActService {
    /**
     * 发布校园活动.
     * 只有认证组织才能发布。如果不是认证组织，返回false
     */
    boolean postActivity(SchoolActModel model);

    /**
     * 返回图片的id
     * @return
     */
    Integer saveImage(IvyActImgModel model, boolean isMain);

    List<SchoolActModel> findByCategory(Integer cateId, int page, int capacity, ObjWrapper wrapper);

    /**
     * 修改基本信息,包括过期时间
     */
    void updateBasicInfo(SchoolActModel model);
}

package com.fh.taolijie.service;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.domain.BannerPicModel;
import com.fh.taolijie.utils.ObjWrapper;

import java.util.List;

/**
 * 规定与banner有关的操作
 * Created by wanghongfei on 15-3-5.
 */
public interface BannerPicService {
    /**
     * 获取所有banner信息
     * @return
     */
    ListResult<BannerPicModel> getBannerList(int firstResult, int capacity);

    /**
     * 更新banner信息
     * @param banId
     * @return
     */
    boolean updateBanner(Integer banId, BannerPicModel model);

    /**
     * 删除一个banner
     * @param id
     * @return
     */
    boolean deleteBanner(Integer id);

    /**
     * 添加一个banner
     * @return 刚刚添加的banner的主键
     */
    int addBanner(BannerPicModel model);

    /**
     * 根据id查找banner
     * @param id
     * @return
     */
    BannerPicModel findBanner(Integer id);
}

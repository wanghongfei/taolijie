package com.fh.taolijie.service;

import com.fh.taolijie.controller.dto.BannerPicDto;

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
    List<BannerPicDto> getBannerList();

    /**
     * 更新banner信息
     * @param banId
     * @return
     */
    boolean updateBanner(Integer banId, BannerPicDto banDto);

    /**
     * 删除一个banner
     * @param id
     * @return
     */
    boolean deleteBanner(Integer id);

    /**
     * 添加一个banner
     * @param banDto
     * @return
     */
    boolean addBanner(BannerPicDto banDto);

    /**
     * 根据id查找banner
     * @param id
     * @return
     */
    BannerPicDto findBanner(Integer id);
}

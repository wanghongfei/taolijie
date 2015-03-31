package com.fh.taolijie.service;

import com.fh.taolijie.controller.dto.ImageDto;

/**
 * Created by wanghongfei on 15-3-31.
 */
public interface ImageService {
    ImageDto findImage(Integer imgId);

    /**
     * 保存一个图片
     * @return 返回图片id
     */
    Integer saveImage(ImageDto dto);
}

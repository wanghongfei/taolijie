package com.fh.taolijie.service;

import com.fh.taolijie.domain.ImageModel;

import java.util.List;

/**
 * Created by wanghongfei on 15-3-31.
 */
public interface ImageService {
    ImageModel findImage(Integer imgId);

    /**
     * 保存一个图片
     * @return 返回图片id
     */
    Integer saveImage(ImageModel model);

    void deleteImage(Integer imageId);

    List<ImageModel> getInBatch(List<Integer> idList);
}

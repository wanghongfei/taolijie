package com.fh.taolijie.dao.mapper;

import com.fh.taolijie.domain.ImageResourceModel;

import java.util.List;

/**
 * Created by wanghongfei on 15-6-5.
 */
public interface ImageResourceMapper {
    /**
     * 返回主键id
     * @param model
     * @return
     */
    int saveImage(ImageResourceModel model);

    List<ImageResourceModel> getImageInBatch(List<Integer> idList);

    void deleteInBatch(List<Integer> idList);
}

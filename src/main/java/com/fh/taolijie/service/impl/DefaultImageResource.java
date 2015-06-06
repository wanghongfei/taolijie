package com.fh.taolijie.service.impl;

import com.fh.taolijie.dao.mapper.ImageModelMapper;
import com.fh.taolijie.domain.ImageModel;
import com.fh.taolijie.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by wanghongfei on 15-6-6.
 */
@Service
@Transactional(readOnly = true)
public class DefaultImageResource implements ImageService {
    @Autowired
    ImageModelMapper imgMapper;

    @Override
    public ImageModel findImage(Integer imgId) {
        return imgMapper.selectByPrimaryKey(imgId);
    }

    @Override
    @Transactional(readOnly = false)
    public Integer saveImage(ImageModel model) {
        return imgMapper.insert(model);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteImage(Integer imageId) {
        imgMapper.deleteByPrimaryKey(imageId);
    }

    public List<ImageModel> getInBatch(List<Integer> idList) {
        return imgMapper.getInBatch(idList);
    }
}

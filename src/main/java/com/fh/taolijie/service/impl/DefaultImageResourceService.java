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
public class DefaultImageResourceService implements ImageService {
    @Autowired
    ImageModelMapper imgMapper;

    @Override
    public ImageModel findImage(Integer imgId) {
        return imgMapper.selectByPrimaryKey(imgId);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public Integer saveImage(ImageModel model) {
        imgMapper.insert(model);

        return model.getId();
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public void deleteImage(Integer imageId) {
        imgMapper.deleteByPrimaryKey(imageId);
    }

    @Override
    public List<ImageModel> getImageByMember(Integer memberId) {
        return imgMapper.getImageByMember(memberId);
    }


    @Override
    public Integer getImageIdByMember(Integer memberId) {

        return null;
    }

    @Override
    public List<ImageModel> getImageByJob(Integer postId) {
        return imgMapper.getImageByJob(postId);
    }

    @Override
    public List<ImageModel> getImageByNews(Integer newsId) {
        return imgMapper.getImageByNews(newsId);
    }

    public List<ImageModel> getInBatch(List<Integer> idList) {
        return imgMapper.getInBatch(idList);
    }
}

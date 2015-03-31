package com.fh.taolijie.service.impl;

import com.fh.taolijie.controller.dto.ImageDto;
import com.fh.taolijie.domain.ImageResourceEntity;
import com.fh.taolijie.service.ImageService;
import com.fh.taolijie.service.repository.ImageRepo;
import com.fh.taolijie.utils.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by wanghongfei on 15-3-31.
 */
@Service
public class DefaultImageService implements ImageService {
    ImageRepo imageRepo;

    @Override
    @Transactional(readOnly = true)
    public ImageDto findImage(Integer imgId) {
        ImageResourceEntity entity = imageRepo.findOne(imgId);

        return CollectionUtils.entity2Dto(entity, ImageDto.class, null);
    }
}

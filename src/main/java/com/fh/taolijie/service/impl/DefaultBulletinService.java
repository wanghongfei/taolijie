package com.fh.taolijie.service.impl;

import com.fh.taolijie.controller.dto.BulletinDto;
import com.fh.taolijie.domain.BulletinEntity;
import com.fh.taolijie.service.BulletinService;
import com.fh.taolijie.service.repository.BulletinRepo;
import com.fh.taolijie.utils.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by wanghongfei on 15-3-31.
 */
@Service
public class DefaultBulletinService implements BulletinService {
    @Autowired
    BulletinRepo repo;

    @Override
    @Transactional(readOnly = false)
    public void addBulletin(BulletinDto dto) {
        BulletinEntity entity = CollectionUtils.dto2Entity(dto, BulletinEntity.class, null);
        repo.save(entity);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteBulletin(Integer id) {
        repo.delete(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BulletinDto> getAll(int firstResult, int capacity) {
        int cap = CollectionUtils.determineCapacity(capacity);


        Page<BulletinEntity> entityList = repo.findAll(new PageRequest(firstResult, cap));

        return CollectionUtils.transformCollection(entityList, BulletinDto.class, (entity) -> {
            return CollectionUtils.entity2Dto(entity, BulletinDto.class, null);
        });
    }

    @Override
    @Transactional(readOnly = true)
    public BulletinDto findOne(Integer id) {
        BulletinEntity entity = repo.findOne(id);

        return CollectionUtils.entity2Dto(entity, BulletinDto.class, null);
    }
}

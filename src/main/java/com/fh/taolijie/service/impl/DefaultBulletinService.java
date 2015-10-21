package com.fh.taolijie.service.impl;

import com.fh.taolijie.dao.mapper.BulletinModelMapper;
import com.fh.taolijie.domain.BulletinModel;
import com.fh.taolijie.service.BulletinService;
import com.fh.taolijie.utils.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by wanghongfei on 15-6-7.
 */
@Service
public class DefaultBulletinService implements BulletinService {
    @Autowired
    BulletinModelMapper buMapper;

    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public void addBulletin(BulletinModel model) {
        buMapper.insert(model);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public void deleteBulletin(Integer id) {
        buMapper.deleteByPrimaryKey(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BulletinModel> getAll(int firstResult, int capacity) {
        return buMapper.getAll(firstResult, CollectionUtils.determineCapacity(capacity));
    }

    @Override
    @Transactional(readOnly = true)
    public BulletinModel findOne(Integer id) {
        return buMapper.selectByPrimaryKey(id);
    }
}

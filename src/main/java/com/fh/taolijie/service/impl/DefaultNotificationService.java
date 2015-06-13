package com.fh.taolijie.service.impl;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.dao.mapper.PrivateNotificationModelMapper;
import com.fh.taolijie.dao.mapper.SysNotificationModelMapper;
import com.fh.taolijie.domain.PrivateNotificationModel;
import com.fh.taolijie.domain.SysNotificationModel;
import com.fh.taolijie.service.NotificationService;
import com.fh.taolijie.utils.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by wanghongfei on 15-6-12.
 */
@Service
@Transactional(readOnly = true)
public class DefaultNotificationService implements NotificationService {
    @Autowired
    SysNotificationModelMapper sysMapper;
    @Autowired
    PrivateNotificationModelMapper priMapper;

    @Override
    public ListResult<SysNotificationModel> getSysNotification(List<String> rangeList, int pageNumber, int pageSize) {
        List<SysNotificationModel> list = sysMapper.findSysByAccessRange(rangeList, pageNumber, CollectionUtils.determineCapacity(pageSize));

        return new ListResult<>(list);
    }

    @Override
    public ListResult<PrivateNotificationModel> getPriNotification(Integer memberId, int pageNumber, int pageSize) {
        PrivateNotificationModel model = new PrivateNotificationModel(pageNumber, pageSize);
        model.setToMemberId(memberId);
        List<PrivateNotificationModel> list = priMapper.findBy(model);

        return new ListResult<>(list);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteSysNotification(Integer sysId) {
        sysMapper.deleteByPrimaryKey(sysId);
    }

    @Override
    @Transactional(readOnly = false)
    public void deletePriNotification(Integer priId) {
        priMapper.deleteByPrimaryKey(priId);
    }
}

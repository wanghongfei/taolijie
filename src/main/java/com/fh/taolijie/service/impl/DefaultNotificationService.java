package com.fh.taolijie.service.impl;

import com.fh.taolijie.dao.mapper.NotificationModelMapper;
import com.fh.taolijie.domain.NotificationModel;
import com.fh.taolijie.service.NotificationService;
import com.fh.taolijie.utils.CollectionUtils;
import com.fh.taolijie.utils.ObjWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by wanghongfei on 15-6-12.
 */
@Service
@Transactional(readOnly = true)
public class DefaultNotificationService implements NotificationService {
    @Autowired
    NotificationModelMapper noMapper;

    @Override
    public List<NotificationModel> getNotificationList(Integer memId, String roleName, int firstResult, int capacity, ObjWrapper wrapper) {
        NotificationModel model = new NotificationModel(firstResult, CollectionUtils.determineCapacity(capacity));
        model.setMemberId(memId);
        model.setAccessRange(roleName.toString());

        return noMapper.findBy(model);
    }

    @Override
    public List<NotificationModel> getNotificationList(Integer memId, String roleName, boolean isRead, int firstResult, int capacity, ObjWrapper wrapper) {
        NotificationModel model = new NotificationModel(firstResult, CollectionUtils.determineCapacity(capacity));
        model.setMemberId(memId);
        model.setAccessRange(roleName.toString());
        model.setIsRead(isRead);

        return noMapper.findBy(model);
    }

    @Override
    public List<NotificationModel> getNotificationList(Integer memId, String roleName, Date time, int firstResult, int capacity, ObjWrapper wrapper) {
        return noMapper.findByTimeRange(memId, roleName, time, new Date(), firstResult, CollectionUtils.determineCapacity(capacity));
    }

    @Override
    public Long getNotificationAmount(Integer memId, boolean isRead) {
        return noMapper.getNotificationAmount(memId, isRead);
    }

    @Override
    public NotificationModel findNotification(Integer notificationId) {
        return noMapper.selectByPrimaryKey(notificationId);
    }

    @Override
    @Transactional(readOnly = false)
    public void addNotification(NotificationModel model) {
        noMapper.insert(model);
    }

    @Override
    @Transactional(readOnly = false)
    public boolean deleteNotification(Integer notificationId) {
        return noMapper.deleteByPrimaryKey(notificationId) <= 0 ? false : true;
    }

    @Override
    @Transactional(readOnly = false)
    public boolean markAsRead(Integer noticicationId) {
        NotificationModel model = new NotificationModel();
        model.setId(noticicationId);
        model.setIsRead(true);

        return noMapper.updateByPrimaryKeySelective(model) <= 0 ? false : true;
    }
}

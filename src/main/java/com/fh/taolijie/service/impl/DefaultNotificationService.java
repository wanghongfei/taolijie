package com.fh.taolijie.service.impl;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.dao.mapper.MemberModelMapper;
import com.fh.taolijie.dao.mapper.PrivateNotificationModelMapper;
import com.fh.taolijie.dao.mapper.SysNotificationModelMapper;
import com.fh.taolijie.domain.MemberModel;
import com.fh.taolijie.domain.PrivateNotificationModel;
import com.fh.taolijie.domain.SysNotificationModel;
import com.fh.taolijie.service.NotificationService;
import com.fh.taolijie.utils.CollectionUtils;
import com.fh.taolijie.utils.StringUtils;
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
    @Autowired
    MemberModelMapper memMapper;

    @Override
    public ListResult<SysNotificationModel> getSysNotification(Integer memberId, List<String> rangeList, int pageNumber, int pageSize) {
        List<SysNotificationModel> list = sysMapper.findSysByAccessRange(rangeList, pageNumber, CollectionUtils.determineCapacity(pageSize));

        // 进行已读标记
        MemberModel mem = memMapper.selectByPrimaryKey(memberId);
        String ids = mem.getReadSysNotificationIds();
        String[] idArray = StringUtils.splitIds(ids);

        // 全部未读
        // 直接返回
        if (null == idArray || 0 == idArray.length) {
            return new ListResult<>(list);
        }
        // 标记已读的通知
        list.stream().forEach(noti -> {
            String id = noti.getId().toString();
            if (StringUtils.checkIdExists(idArray, id)) {
                noti.setIsRead(true);
            }
        });


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
    public void markPriAsRead(Integer sysId) {
        PrivateNotificationModel model = new PrivateNotificationModel();
        model.setId(sysId);
        model.setIsRead(true);

        priMapper.updateByPrimaryKeySelective(model);
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

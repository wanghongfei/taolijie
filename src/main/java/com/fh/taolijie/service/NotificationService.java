package com.fh.taolijie.service;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.domain.PrivateNotificationModel;
import com.fh.taolijie.domain.SysNotificationModel;

import java.util.List;

/**
 * 规定与通知有关的操作
 * Created by wanghongfei on 15-3-5.
 */
public interface NotificationService {
    ListResult<SysNotificationModel> getSysNotification(List<String> rangeList, int pageNumber, int pageSize);

    void deleteSysNotification(Integer sysId);

    ListResult<PrivateNotificationModel> getPriNotification(Integer memberId, int pageNumber, int pageSize);

    void deletePriNotification(Integer priId);
}

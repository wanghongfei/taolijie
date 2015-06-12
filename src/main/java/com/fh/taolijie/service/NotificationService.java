package com.fh.taolijie.service;

import com.fh.taolijie.domain.PrivateNotificationModel;
import com.fh.taolijie.utils.ObjWrapper;

import java.util.Date;
import java.util.List;

/**
 * 规定与通知有关的操作
 * Created by wanghongfei on 15-3-5.
 */
public interface NotificationService {
    /**
     * 获取某一用户的所有通知
     * @param memId
     * @return
     */
    List<PrivateNotificationModel> getNotificationList(Integer memId, String roleName, int firstResult, int capacity, ObjWrapper wrapper);

    /**
     * 获取某一用户已读或未读的所有通知
     * @param memId
     * @param isRead true为已读, false为未读
     * @return
     */
    List<PrivateNotificationModel> getNotificationList(Integer memId, String roleName, boolean isRead, int firstResult, int capacity, ObjWrapper wrapper);

    /**
     * 获取某个日期之后的所有通知
     * @param memId
     * @param time
     * @return
     */
    List<PrivateNotificationModel> getNotificationList(Integer memId, String roleName, Date time, int firstResult, int capacity, ObjWrapper wrapper);

    Long getNotificationAmount(Integer memId, boolean isRead);

    /**
     * 查找一条通知
     * @param notificationId
     * @return
     */
    PrivateNotificationModel findNotification(Integer notificationId);

    /**
     * 创建一条通知
     * @param dto
     */
    void addNotification(PrivateNotificationModel model);

    /**
     * 删除一条通知
     * @param notificationId
     * @return
     */
    boolean deleteNotification(Integer notificationId);

    /**
     * 标记某条通知为已读
     * @param noticicationId
     * @return
     */
    boolean markAsRead(Integer notificationId);
}

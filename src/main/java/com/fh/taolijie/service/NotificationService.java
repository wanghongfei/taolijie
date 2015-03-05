package com.fh.taolijie.service;

import com.fh.taolijie.controller.dto.NotificationDto;

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
    List<NotificationDto> getNotificationList(Integer memId);

    /**
     * 获取某一用户已读或未读的所有通知
     * @param memId
     * @param isRead true为已读, false为未读
     * @return
     */
    List<NotificationDto> getNotificationList(Integer memId, boolean isRead);

    /**
     * 获取某个日期之后的所有通知
     * @param memId
     * @param time
     * @return
     */
    List<NotificationDto> getNotificationList(Integer memId, Date time);

    /**
     * 查找一条通知
     * @param notificationId
     * @return
     */
    NotificationDto findNotification(Integer notificationId);

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
    boolean markAsRead(Integer noticicationId);
}

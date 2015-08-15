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
    /**
     * 查询当前用户的系统通知
     * @param memberId 当前登陆用户的id
     * @param rangeList {@link com.fh.taolijie.utils.Constants.NotificationRange}常量的字面值
     * @return
     */
    ListResult<SysNotificationModel> getSysNotification(Integer memberId, List<String> rangeList, int pageNumber, int pageSize);

    /**
     * 查询当前用户的所有个人通知
     * @param memberId 当前登陆用户的id
     */
    ListResult<PrivateNotificationModel> getPriNotification(Integer memberId, int pageNumber, int pageSize);

    /**
     * 查询所有系统通知
     * @return
     */
    ListResult<SysNotificationModel> getAllSysNotification(int pageNumber, int pageSize);

    /**
     * 查询所有个人通知
     * @return
     */
    ListResult<PrivateNotificationModel> getAllPriNotification(int pageNumber, int pageSize);

    /**
     * 根据id查询个人通知
     */
    PrivateNotificationModel findPriById(Integer priNotiId);

    /**
     * 查询系统通知
     */
    SysNotificationModel findSysById(Integer notiId);

    /**
     * 添加一条私人通知
     * @param model
     */
    void addNotification(PrivateNotificationModel model);

    /**
     * 添加一条系统通知
     * @param model
     */
    void addNotification(SysNotificationModel model);

    void deleteSysNotification(Integer sysId);
    void deletePriNotification(Integer priId);

    /**
     * 将个人通知标记为已读
     * @param sysId
     */
    void markPriAsRead(Integer notiId);

    /**
     * 将系统通知标记为已读
     * @param notiId
     */
    void markSysAsRead(Integer memId, Integer notiId);
}

package com.fh.taolijie.service.impl;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.constant.NotiType;
import com.fh.taolijie.dao.mapper.MemberModelMapper;
import com.fh.taolijie.dao.mapper.PrivateNotificationModelMapper;
import com.fh.taolijie.dao.mapper.SysNotificationModelMapper;
import com.fh.taolijie.domain.acc.MemberModel;
import com.fh.taolijie.domain.noti.PrivateNotificationModel;
import com.fh.taolijie.domain.noti.SysNotificationModel;
import com.fh.taolijie.service.NotificationService;
import com.fh.taolijie.utils.CollectionUtils;
import com.fh.taolijie.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by wanghongfei on 15-6-12.
 */
@Service
public class DefaultNotificationService implements NotificationService {
    @Autowired
    SysNotificationModelMapper sysMapper;
    @Autowired
    PrivateNotificationModelMapper priMapper;
    @Autowired
    MemberModelMapper memMapper;

    @Override
    @Transactional(readOnly = true)
    public ListResult<SysNotificationModel> getSysNotification(Integer memberId, List<String> rangeList, int pageNumber, int pageSize) {
        List<SysNotificationModel> list = sysMapper.findSysByAccessRange(rangeList, pageNumber, CollectionUtils.determineCapacity(pageSize));
        long tot = sysMapper.countFindSysByAccessRange(rangeList);

        // 进行已读标记
        MemberModel mem = memMapper.selectByPrimaryKey(memberId);
        if (null == mem) {
            return new ListResult<>(list);
        }
        String ids = mem.getReadSysNotificationIds();
        if (null == ids) {
            return new ListResult<>(list);
        }

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


        return new ListResult<>(list, tot);
    }

    @Override
    @Transactional(readOnly = true)
    public ListResult<PrivateNotificationModel> getPriNotification(Integer memberId, int pageNumber, int pageSize) {
        PrivateNotificationModel model = new PrivateNotificationModel(pageNumber, pageSize);

        model.setToMemberId(memberId);
        model.setNotiTypeList(Arrays.asList(
                NotiType.ADMIN.getCode(),
                NotiType.SYSTEM_AUTO.getCode(),
                NotiType.USER.getCode()
        ));

        List<PrivateNotificationModel> list = priMapper.findBy(model);
        long tot = priMapper.countFindBy(model);

        return new ListResult<>(list, tot);
    }

    @Override
    @Transactional(readOnly = true)

    public ListResult<PrivateNotificationModel> getUnreadPriNotification(Integer memberId, int pageNumber, int pageSize) {
        PrivateNotificationModel model = new PrivateNotificationModel(pageNumber, pageSize);

        model.setToMemberId(memberId);
        model.setNotiTypeList(Arrays.asList(
                NotiType.ADMIN.getCode(),
                NotiType.SYSTEM_AUTO.getCode(),
                NotiType.USER.getCode()
        ));
        model.setIsRead(false);

        List<PrivateNotificationModel> list = priMapper.findBy(model);
        long tot = priMapper.countFindBy(model);

        return new ListResult<>(list, tot);
    }

    @Override
    @Transactional(readOnly = true)
    public ListResult<SysNotificationModel> getUnreadSysNotification(List<Integer> readList, List<String> rangeList, int pn, int ps) {
        List<SysNotificationModel> list = sysMapper.findSysByRangeAndFilter(readList, rangeList, pn, ps);
        long tot = sysMapper.countFindSysByRangeAndFilter(readList, rangeList);

        return new ListResult<>(list, tot);
    }

    @Override
    @Transactional(readOnly = true)
    public ListResult<SysNotificationModel> getAllSysNotification(int pageNumber, int pageSize) {
        List<SysNotificationModel> list = sysMapper.findAll(pageNumber, pageSize);
        int tot = sysMapper.countFindAll();

        return new ListResult<>(list, tot);
    }

    @Override
    @Transactional(readOnly = true)
    public ListResult<PrivateNotificationModel> getAllPriNotification(int pageNumber, int pageSize) {
        PrivateNotificationModel model = new PrivateNotificationModel(pageNumber, pageSize);
        model.setNotiType(NotiType.ADMIN.getCode());

        List<PrivateNotificationModel> list = priMapper.findBy(model);
        long tot = priMapper.countFindBy(model);

        return new ListResult<>(list, tot);
    }

    @Override
    @Transactional(readOnly = true)
    public PrivateNotificationModel findPriById(Integer priNotiId) {
        return priMapper.selectByPrimaryKey(priNotiId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PrivateNotificationModel> findPriByIdInBatch(List<Integer> idList) {
        return priMapper.findInBatch(idList);
    }

    @Override
    @Transactional(readOnly = true)
    public SysNotificationModel findSysById(Integer notiId) {
        return sysMapper.selectByPrimaryKey(notiId);
    }

    @Override
    @Transactional(readOnly = false)
    public void addNotification(PrivateNotificationModel model) {
        priMapper.insertSelective(model);
    }

    @Override
    @Transactional(readOnly = false)
    public void addNotification(SysNotificationModel model) {
        sysMapper.insertSelective(model);
    }

    @Override
    @Transactional(readOnly = false)
    public void addCommentNotification(Integer memberId, String title, String content, Integer postId, String postType) {
        // 创建通知实体
        PrivateNotificationModel priNoti = new PrivateNotificationModel();

        priNoti.setToMemberId(memberId);
        priNoti.setNotiType(NotiType.SYSTEM_AUTO.getCode());
        priNoti.setTitle(title);
        priNoti.setContent(content);
        priNoti.setTime(new Date());
        priNoti.setPostId(postId);
        priNoti.setPostType(postType);

        // 保存到db
        priMapper.insertSelective(priNoti);

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
    public void markPriAsReadInBatch(List<Integer> priNotiList) {
        priMapper.markAsReadInBatch(priNotiList);
    }

    @Override
    @Transactional(readOnly = false)
    public void markSysAsRead(Integer memId, Integer notiId) {
        MemberModel mem = memMapper.selectByPrimaryKey(memId);
        String ids = mem.getReadSysNotificationIds();
        String newIds = StringUtils.addToString(ids, notiId.toString());

        MemberModel example = new MemberModel();
        example.setId(mem.getId());
        mem.setReadSysNotificationIds(newIds);
        memMapper.updateByPrimaryKeySelective(example);
    }

    @Override
    @Transactional(readOnly = false)
    public void markSysAsReadInBatch(Integer memId, List<Integer> idList) {
        MemberModel mem = memMapper.selectByPrimaryKey(memId);
        String ids = mem.getReadSysNotificationIds();
        String newIds = StringUtils.addToString(ids, StringUtils.listToString(idList));

        MemberModel example = new MemberModel();
        example.setId(mem.getId());
        example.setReadSysNotificationIds(newIds);
        memMapper.updateByPrimaryKeySelective(example);

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

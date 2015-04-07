package com.fh.taolijie.service.impl;

import com.fh.taolijie.controller.dto.NotificationDto;
import com.fh.taolijie.domain.MemberEntity;
import com.fh.taolijie.domain.NotificationEntity;
import com.fh.taolijie.service.NotificationService;
import com.fh.taolijie.service.repository.MemberRepo;
import com.fh.taolijie.service.repository.NotificationRepo;
import com.fh.taolijie.utils.CheckUtils;
import com.fh.taolijie.utils.CollectionUtils;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.ObjWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by wanghongfei on 15-3-9.
 */
@Service
public class DefaultNotificationService implements NotificationService {
    @Autowired
    NotificationRepo notRepo;
    @Autowired
    MemberRepo memRepo;

    @Override
    @Transactional(readOnly = true)
    public List<NotificationDto> getNotificationList(Integer memId, String roleName, int firstResult, int capacity, ObjWrapper wrapper) {
        int cap = capacity;
        if (cap <= 0) {
            cap = Constants.PAGE_CAPACITY;
        }

        MemberEntity mem = memRepo.getOne(memId);
        CheckUtils.nullCheck(mem);
        List<NotificationEntity> allList = retrieveAll(mem, roleName);


        // 分页
        Page<NotificationEntity> noList = new PageImpl<NotificationEntity>(allList, new PageRequest(firstResult, cap), allList.size());

        //Page<NotificationEntity> noList = notRepo.findByMember(mem, new PageRequest(firstResult, cap));
        wrapper.setObj(noList.getTotalPages());

        return CollectionUtils.transformCollection(noList, NotificationDto.class, (entity) -> {
            return CollectionUtils.entity2Dto(entity, NotificationDto.class, (dto) -> {
                dto.setMemberId(entity.getMember().getId());
            });
        });
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationDto> getNotificationList(Integer memId, String roleName, boolean isRead, int firstResult, int capacity, ObjWrapper wrapper) {
        int cap = capacity;
        if (cap <= 0) {
            cap = Constants.PAGE_CAPACITY;
        }

        MemberEntity mem = memRepo.getOne(memId);
        CheckUtils.nullCheck(mem);
        List<NotificationEntity> allList = retrieveAll(mem, roleName);

        // 根据isRead过虑
        allList = allList.stream()
                .filter( (entity) -> {
                    if (1 == entity.getIsRead().intValue()) {
                        return isRead ? true : false;
                    } else {
                        return isRead ? false : true;
                    }
                }).collect(Collectors.toList());

        // 分页
        Page<NotificationEntity> noList = new PageImpl<NotificationEntity>(allList, new PageRequest(firstResult, cap), allList.size());
        wrapper.setObj(noList.getTotalPages());

        return CollectionUtils.transformCollection(noList, NotificationDto.class, (entity) -> {
            return CollectionUtils.entity2Dto(entity, NotificationDto.class, (dto) -> {
                //System.out.println("set title:" + dto.getTitle());
                dto.setMemberId(entity.getMember().getId());
            });
        });
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationDto> getNotificationList(Integer memId, String roleName, Date time, int firstResult, int capacity, ObjWrapper wrapper) {
        int cap = capacity;
        if (cap <= 0) {
            cap = Constants.PAGE_CAPACITY;
        }

        Page<NotificationEntity> noList = notRepo.findAfterTheTime(memRepo.getOne(memId), time, roleName, new PageRequest(firstResult, cap));
        wrapper.setObj(noList.getTotalPages());

        return CollectionUtils.transformCollection(noList, NotificationDto.class, (entity) -> {
            return CollectionUtils.entity2Dto(entity, NotificationDto.class, (dto) -> {
                dto.setMemberId(entity.getMember().getId());
            });
        });
    }

    @Override
    public Long getNotificationAmount(Integer memId, boolean isRead) {
        MemberEntity mem = memRepo.getOne(memId);
        CheckUtils.nullCheck(mem);

        return notRepo.getNotificationAmount(mem, isRead ? 1 : 0);
    }

    @Override
    @Transactional(readOnly = true)
    public NotificationDto findNotification(Integer notificationId) {
        NotificationEntity entity = notRepo.findOne(notificationId);
        CheckUtils.nullCheck(entity);

        return CollectionUtils.entity2Dto(entity, NotificationDto.class, (dto) -> {
            dto.setMemberId(entity.getMember().getId());
        });
        //return makeDto(notRepo.findOne(notificationId));
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public boolean deleteNotification(Integer notificationId) {
        NotificationEntity no = notRepo.getOne(notificationId);
        CheckUtils.nullCheck(no);

        // remove connection to member
        CollectionUtils.removeFromCollection(no.getMember().getNotificationCollection(), (entity) -> {
            return entity.getId().equals(notificationId);
        });

        notRepo.delete(no);

        return true;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public boolean markAsRead(Integer noticicationId) {
        NotificationEntity no = notRepo.getOne(noticicationId);
        CheckUtils.nullCheck(no);

        no.setIsRead(1);

        return true;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void addNotification(NotificationDto dto) {
/*        NotificationEntity no = new NotificationEntity(dto.getTitle(), dto.getContent(), dto.getIsRead(),
                dto.getTime(), null);*/
        NotificationEntity no = CollectionUtils.dto2Entity(dto, NotificationEntity.class, (entity) -> {
            if (null != dto.getMemberId()) {
                MemberEntity mem = memRepo.getOne(dto.getMemberId());
                entity.setMember(mem);

                // add to member collection
                List<NotificationEntity> list = CollectionUtils.addToCollection(mem.getNotificationCollection(), entity);
                if (null != list) {
                    mem.setNotificationCollection(list);
                }
            }
        });

/*        if (null != dto.getMemberId()) {
            MemberEntity mem = memRepo.getOne(dto.getMemberId());
            no.setMember(mem);

            // add to member collection
            List<NotificationEntity> list = CollectionUtils.addToCollection(mem.getNotificationCollection(), no);
            if (null != list) {
                mem.setNotificationCollection(list);
            }
        }*/

        notRepo.save(no);
    }

    private List<NotificationEntity> retrieveAll(MemberEntity member, String roleName) {
        List<NotificationEntity> list = retrieveNoByRange(roleName);
        list.addAll(retrievePrivateRange(member));

        return list;
    }

    private List<NotificationEntity> retrieveNoByRange(String roleName) {
        List<NotificationEntity> noList = notRepo.findByAccessRange(roleName);

        return noList;
    }

    private List<NotificationEntity> retrievePrivateRange(MemberEntity member) {
        return notRepo.findByMember(member);
    }

    /*private NotificationDto makeDto(NotificationEntity no) {
        NotificationDto dto = new NotificationDto();
        dto.setId(no.getId());
        dto.setTitle(no.getTitle());
        dto.setContent(no.getContent());
        dto.setIsRead(no.getIsRead());
        dto.setTime(no.getTime());

        dto.setMemberId(no.getMember().getId());

        return dto;
    }*/
}

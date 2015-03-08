package com.fh.taolijie.service.repository;

import com.fh.taolijie.domain.MemberEntity;
import com.fh.taolijie.domain.NotificationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

/**
 * Created by wanghongfei on 15-3-8.
 */
public interface NotificationRepo extends JpaRepository<NotificationEntity, Integer> {
    Page<NotificationEntity> findByMember(MemberEntity member, Pageable pageable);
    Page<NotificationEntity> findByMemberAndIsRead(MemberEntity member, Integer isRead, Pageable pageable);

    @Query("SELECT no FROM NotificationEntity no WHERE no.member = :member AND no.time > :time")
    Page<NotificationEntity> findAfterTheTime(@Param("member") MemberEntity member, @Param("time") Date thatTime, Pageable pageable);
}

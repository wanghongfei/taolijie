package com.fh.taolijie.service.repository;

import com.fh.taolijie.domain.MemberEntity;
import com.fh.taolijie.domain.ResumeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by wanghongfei on 15-3-8.
 */
public interface ResumeRepo extends JpaRepository<ResumeEntity, Integer> {
    @Query
    Page<ResumeEntity> findAll(Pageable pageable);

    @Query
    Page<ResumeEntity> findByMemberAndAuthority(@Param("member") MemberEntity member, @Param("authority") String authority, Pageable pageable);

    @Query
    Page<ResumeEntity> findByAuthority(@Param("authority") String authority, Pageable pageable);
}

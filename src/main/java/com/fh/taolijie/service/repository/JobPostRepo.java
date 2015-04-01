package com.fh.taolijie.service.repository;

import com.fh.taolijie.domain.JobPostCategoryEntity;
import com.fh.taolijie.domain.JobPostEntity;
import com.fh.taolijie.domain.MemberEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


/**
 * Created by wanghongfei on 15-3-8.
 */
public interface JobPostRepo extends JpaRepository<JobPostEntity, Integer> {
    @Query
    Page<JobPostEntity> findAllOrderByPostTime(Pageable pageable);

    @Query
    Page<JobPostEntity> findAllOrderByExpiredTime(Pageable pageable);

    @Query
    Page<JobPostEntity> findByMember(@Param("member") MemberEntity member, Pageable pageable);

    Page<JobPostEntity> findByCategory(JobPostCategoryEntity category, Pageable pageable);

    @Query("SELECT job FROM JobPostEntity job WHERE job.verified = :verified ORDER BY job.postTime DESC")
    Page<JobPostEntity> findByVerified(@Param("verified")String verified, Pageable pageable);
}

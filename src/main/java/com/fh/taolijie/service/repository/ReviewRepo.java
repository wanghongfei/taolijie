package com.fh.taolijie.service.repository;

import com.fh.taolijie.domain.JobPostEntity;
import com.fh.taolijie.domain.ReviewEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by wanghongfei on 15-3-8.
 */
public interface ReviewRepo extends JpaRepository<ReviewEntity, Integer> {
    Page<ReviewEntity> findByJobPost(JobPostEntity jobPost, Pageable pageable);
}

package com.fh.taolijie.service.repository;

import com.fh.taolijie.domain.ResumeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by wanghongfei on 15-3-8.
 */
public interface ResumeRepo extends JpaRepository<ResumeEntity, Integer> {
    @Query
    Page<ResumeEntity> findAll(Pageable pageable);
}

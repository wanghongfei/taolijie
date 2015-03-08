package com.fh.taolijie.service.repository;

import com.fh.taolijie.domain.JobPostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by wanghongfei on 15-3-8.
 */
public interface JobPostRepo extends JpaRepository<JobPostEntity, Integer> {
}

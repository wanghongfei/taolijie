package com.fh.taolijie.service.repository;

import com.fh.taolijie.domain.JobPostEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by wanghongfei on 15-3-8.
 */
public interface JobPostRepo extends CrudRepository<JobPostEntity, Integer> {
}

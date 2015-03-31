package com.fh.taolijie.service.repository;

import com.fh.taolijie.domain.ImageResourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by wanghongfei on 15-3-31.
 */
public interface ImageRepo extends JpaRepository<ImageResourceEntity, Integer> {
}

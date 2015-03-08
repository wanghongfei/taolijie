package com.fh.taolijie.service.repository;

import com.fh.taolijie.domain.SecondHandPostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by wanghongfei on 15-3-8.
 */
public interface SHPostRepo extends JpaRepository<SecondHandPostEntity, Integer> {
}

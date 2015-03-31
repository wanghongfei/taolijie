package com.fh.taolijie.service.repository;

import com.fh.taolijie.domain.BulletinEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by wanghongfei on 15-3-31.
 */
public interface BulletinRepo extends JpaRepository<BulletinEntity, Integer> {
}

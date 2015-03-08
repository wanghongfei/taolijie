package com.fh.taolijie.service.repository;

import com.fh.taolijie.domain.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by wanghongfei on 15-3-8.
 */
public interface NotificationRepo extends JpaRepository<NotificationEntity, Integer> {
}

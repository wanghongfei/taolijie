package com.fh.taolijie.service.repository;

import com.fh.taolijie.domain.AcademyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by wanghongfei on 15-3-8.
 */
public interface AcademyRepo extends JpaRepository<AcademyEntity, Integer> {
    //List<AcademyEntity> findBy
}

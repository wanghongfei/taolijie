package com.fh.taolijie.service.repository;

import com.fh.taolijie.domain.AcademyEntity;
import com.fh.taolijie.domain.SchoolEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by wanghongfei on 15-3-8.
 */
public interface AcademyRepo extends JpaRepository<AcademyEntity, Integer> {
    List<AcademyEntity> findBySchool(SchoolEntity school);
}

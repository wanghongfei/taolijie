package com.fh.taolijie.service.repository;

import com.fh.taolijie.domain.EducationExperienceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by wanghongfei on 15-3-8.
 */
public interface EduExpRepo extends JpaRepository<EducationExperienceEntity, Integer> {
}

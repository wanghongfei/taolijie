package com.fh.taolijie.service.repository;

import com.fh.taolijie.domain.EducationExperienceEntity;
import com.fh.taolijie.domain.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by wanghongfei on 15-3-8.
 */
public interface EduExpRepo extends JpaRepository<EducationExperienceEntity, Integer> {
    List<EducationExperienceEntity> findByMember(MemberEntity member);
}

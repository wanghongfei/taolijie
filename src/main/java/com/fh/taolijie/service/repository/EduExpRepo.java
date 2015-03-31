package com.fh.taolijie.service.repository;

import com.fh.taolijie.domain.EducationExperienceEntity;
import com.fh.taolijie.domain.MemberEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by wanghongfei on 15-3-8.
 */
public interface EduExpRepo extends JpaRepository<EducationExperienceEntity, Integer> {
    Page<EducationExperienceEntity> findByMember(MemberEntity member, Pageable pageable);
}

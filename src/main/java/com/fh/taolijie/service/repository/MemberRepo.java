package com.fh.taolijie.service.repository;

import com.fh.taolijie.domain.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by wanghongfei on 15-3-8.
 */
public interface MemberRepo extends JpaRepository<MemberEntity, Integer> {
}

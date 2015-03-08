package com.fh.taolijie.service.repository;

import com.fh.taolijie.domain.SchoolEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Created by wanghongfei on 15-3-8.
 */
public interface SchoolRepo extends JpaRepository<SchoolEntity, Integer> {
    Page<SchoolEntity> findByProvince(String province, Pageable pageReq);
}

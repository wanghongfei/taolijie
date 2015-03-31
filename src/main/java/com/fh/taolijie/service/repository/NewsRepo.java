package com.fh.taolijie.service.repository;

import com.fh.taolijie.domain.NewsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

/**
 * Created by wanghongfei on 15-3-8.
 */
public interface NewsRepo extends JpaRepository<NewsEntity, Integer> {
    @Query
    Page<NewsEntity> findByDate(@Param("date")Date date, Pageable pageable);

    @Query
    Page<NewsEntity> findAll(Pageable pageable);
}

package com.fh.taolijie.dao.mapper;

import com.fh.taolijie.cache.annotation.RedisCache;
import com.fh.taolijie.cache.annotation.RedisEvict;
import com.fh.taolijie.domain.JobPostCategoryModel;
import com.fh.taolijie.domain.JobPostModel;
import com.fh.taolijie.domain.SqlWrapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestMapper {
    int execute(SqlWrapper sql);
}
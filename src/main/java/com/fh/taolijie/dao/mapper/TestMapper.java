package com.fh.taolijie.dao.mapper;

import com.fh.taolijie.domain.SqlWrapper;
import org.springframework.stereotype.Repository;

@Repository
public interface TestMapper {
    int execute(SqlWrapper sql);
}
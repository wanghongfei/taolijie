package com.fh.taolijie.dao.mapper;

import com.fh.taolijie.domain.SeqJobModel;
import com.fh.taolijie.domain.SeqJobModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SeqJobModelMapper {
    int insert(SeqJobModel record);
}
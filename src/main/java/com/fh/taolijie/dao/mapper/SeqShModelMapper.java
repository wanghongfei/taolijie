package com.fh.taolijie.dao.mapper;

import com.fh.taolijie.domain.SeqShModel;
import com.fh.taolijie.domain.SeqShModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SeqShModelMapper {
    int insert(SeqShModel record);
}
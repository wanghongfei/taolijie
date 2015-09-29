package com.fh.taolijie.dao.mapper;

import com.fh.taolijie.domain.sequence.SeqJobModel;
import org.springframework.stereotype.Repository;

@Repository
public interface SeqJobModelMapper {
    int insert(SeqJobModel record);
}
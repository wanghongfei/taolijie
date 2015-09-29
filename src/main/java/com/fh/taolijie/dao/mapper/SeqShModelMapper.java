package com.fh.taolijie.dao.mapper;

import com.fh.taolijie.domain.sequence.SeqShModel;
import org.springframework.stereotype.Repository;

@Repository
public interface SeqShModelMapper {
    int insert(SeqShModel record);
}
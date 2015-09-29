package com.fh.taolijie.dao.mapper;

import com.fh.taolijie.domain.sequence.SeqAvatarModel;
import org.springframework.stereotype.Repository;

@Repository
public interface SeqAvatarModelMapper {
    int insert(SeqAvatarModel record);
}
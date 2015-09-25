package com.fh.taolijie.dao.mapper;

import com.fh.taolijie.domain.SeqAvatarModel;
import com.fh.taolijie.domain.SeqAvatarModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SeqAvatarModelMapper {
    int insert(SeqAvatarModel record);
}
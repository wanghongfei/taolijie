package com.fh.taolijie.dao.mapper;

import com.fh.taolijie.domain.QuestCiRel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestCiRelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(QuestCiRel record);

    int insertSelective(QuestCiRel record);

    QuestCiRel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(QuestCiRel record);

    int updateByPrimaryKey(QuestCiRel record);


    int insertInBatch(List<QuestCiRel> list);
}
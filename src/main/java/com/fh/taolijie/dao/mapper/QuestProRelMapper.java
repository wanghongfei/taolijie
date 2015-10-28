package com.fh.taolijie.dao.mapper;

import com.fh.taolijie.domain.QuestProRel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestProRelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(QuestProRel record);

    int insertSelective(QuestProRel record);

    QuestProRel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(QuestProRel record);

    int updateByPrimaryKey(QuestProRel record);


    int insertInBatch(List<QuestProRel> list);
}
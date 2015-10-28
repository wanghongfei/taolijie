package com.fh.taolijie.dao.mapper;

import com.fh.taolijie.domain.quest.QuestCollRelModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestCollRelModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(QuestCollRelModel record);

    int insertSelective(QuestCollRelModel record);

    QuestCollRelModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(QuestCollRelModel record);

    int updateByPrimaryKey(QuestCollRelModel record);


    void insertInBatch(List<QuestCollRelModel> list);

    List<QuestCollRelModel> selectByQuestId(Integer questId);
}
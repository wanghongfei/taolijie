package com.fh.taolijie.dao.mapper;

import com.fh.taolijie.domain.QuestSchRelModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestSchRelModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(QuestSchRelModel record);

    int insertSelective(QuestSchRelModel record);

    QuestSchRelModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(QuestSchRelModel record);

    int updateByPrimaryKey(QuestSchRelModel record);

    void insertInBatch(List<QuestSchRelModel> list);
}
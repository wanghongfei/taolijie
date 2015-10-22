package com.fh.taolijie.dao.mapper;

import com.fh.taolijie.domain.QuestionModel;
import com.fh.taolijie.domain.quest.QuestModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(QuestionModel record);

    int insertSelective(QuestionModel record);

    QuestionModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(QuestionModel record);

    int updateByPrimaryKey(QuestionModel record);

    /**
     * 根据任务查询问题列表
     * @param questId
     * @return
     */
    List<QuestionModel> selectByQuestId(Integer questId);
}
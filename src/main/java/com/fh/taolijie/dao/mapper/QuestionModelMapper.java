package com.fh.taolijie.dao.mapper;

import com.fh.taolijie.domain.quest.QuestionModel;
import org.apache.ibatis.annotations.Param;
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

    int increaseAnswerAmt(@Param("correct") boolean correct, @Param("questionId") Integer questionId);
}
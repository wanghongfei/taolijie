package com.fh.taolijie.dao.mapper;

import com.fh.taolijie.domain.quest.QuestionOptModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionOptModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(QuestionOptModel record);

    int insertSelective(QuestionOptModel record);

    QuestionOptModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(QuestionOptModel record);

    int updateByPrimaryKey(QuestionOptModel record);

    void insertInBatch(@Param("questionId") Integer questionId, @Param("optList") List<QuestionOptModel> optList);

    List<QuestionOptModel> selectInBatch(List<Integer> idList);

    int increaseAnswerAmt(List<Integer> idList);
}
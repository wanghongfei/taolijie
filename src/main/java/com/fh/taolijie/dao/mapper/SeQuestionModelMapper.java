package com.fh.taolijie.dao.mapper;

import com.fh.taolijie.domain.SeQuestionModel;
import org.springframework.stereotype.Repository;

@Repository
public interface SeQuestionModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SeQuestionModel record);

    int insertSelective(SeQuestionModel record);

    SeQuestionModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SeQuestionModel record);

    int updateByPrimaryKey(SeQuestionModel record);


    SeQuestionModel selectByMemberId(Integer memId);
    SeQuestionModel selectByMemberIdWithoutAnswer(Integer memId);

    SeQuestionModel selectByAccId(Integer accId);

    boolean checkExistByMemberId(Integer memId);
    boolean checkExistByAccId(Integer accId);

    int deleteByMemberId(Integer memId);
}
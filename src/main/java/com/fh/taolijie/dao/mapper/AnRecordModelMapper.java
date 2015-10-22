package com.fh.taolijie.dao.mapper;

import com.fh.taolijie.domain.AnRecordModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnRecordModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AnRecordModel record);

    int insertSelective(AnRecordModel record);

    AnRecordModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AnRecordModel record);

    int updateByPrimaryKey(AnRecordModel record);


    boolean checkExist(@Param("memId") Integer memId, @Param("questionId") Integer questionId);

    List<AnRecordModel> selectByQuestAndMember(@Param("memId") Integer memId, @Param("questId") Integer questId);
}
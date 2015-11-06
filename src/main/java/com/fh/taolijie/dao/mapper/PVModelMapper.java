package com.fh.taolijie.dao.mapper;

import com.fh.taolijie.domain.PVModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PVModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PVModel record);

    int insertSelective(PVModel record);

    PVModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PVModel record);

    int updateByPrimaryKey(PVModel record);


    List<PVModel> selectByInterval(@Param("start") Date start, @Param("end") Date end);
}
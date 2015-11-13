package com.fh.taolijie.dao.mapper;

import com.fh.taolijie.domain.DictIndustryModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DictIndustryModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DictIndustryModel record);

    int insertSelective(DictIndustryModel record);

    DictIndustryModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DictIndustryModel record);

    int updateByPrimaryKey(DictIndustryModel record);

    List<DictIndustryModel> selectAll();
}
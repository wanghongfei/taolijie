package com.fh.taolijie.dao.mapper;

import com.fh.taolijie.domain.RecoPostModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecoPostModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RecoPostModel record);

    int insertSelective(RecoPostModel record);

    RecoPostModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RecoPostModel record);

    int updateByPrimaryKey(RecoPostModel record);

    List<RecoPostModel> findBy(RecoPostModel example);
    long countFindBy(RecoPostModel example);
}
package com.fh.taolijie.dao.mapper;

import com.fh.taolijie.cache.annotation.NoCache;
import com.fh.taolijie.domain.RecoPostModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecoPostModelMapper {
    @NoCache
    int deleteByPrimaryKey(Integer id);

    @NoCache
    int insert(RecoPostModel record);

    @NoCache
    int insertSelective(RecoPostModel record);

    @NoCache
    RecoPostModel selectByPrimaryKey(Integer id);

    @NoCache
    int updateByPrimaryKeySelective(RecoPostModel record);

    @NoCache
    int updateByPrimaryKey(RecoPostModel record);

    @NoCache
    List<RecoPostModel> findBy(RecoPostModel example);
    @NoCache
    long countFindBy(RecoPostModel example);
}
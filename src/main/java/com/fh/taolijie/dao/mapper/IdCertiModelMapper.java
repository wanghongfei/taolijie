package com.fh.taolijie.dao.mapper;

import com.fh.taolijie.domain.IdCertiModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IdCertiModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(IdCertiModel record);

    int insertSelective(IdCertiModel record);

    IdCertiModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(IdCertiModel record);

    int updateByPrimaryKey(IdCertiModel record);


    List<IdCertiModel> findBy(IdCertiModel example);
    long countFindBy(IdCertiModel example);
}
package com.fh.taolijie.dao.mapper;

import com.fh.taolijie.domain.certi.EmpCertiModel;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface EmpCertiModelMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(EmpCertiModel record);

    int insertSelective(EmpCertiModel record);

    EmpCertiModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EmpCertiModel record);

    int updateByPrimaryKey(EmpCertiModel record);

    List<EmpCertiModel> findBy(EmpCertiModel example);
    long countFindBy(EmpCertiModel example);

    boolean checkApplyExists(Integer memId);
}
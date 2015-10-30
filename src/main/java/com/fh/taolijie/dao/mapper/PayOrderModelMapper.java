package com.fh.taolijie.dao.mapper;

import com.fh.taolijie.domain.order.PayOrderModel;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface PayOrderModelMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(PayOrderModel record);

    int insertSelective(PayOrderModel record);


    PayOrderModel selectByPrimaryKey(Integer id);


    int updateByPrimaryKeySelective(PayOrderModel record);

    int updateByPrimaryKey(PayOrderModel record);

    List<PayOrderModel> findBy(PayOrderModel example);
    long countFindBy(PayOrderModel example);

    boolean checkExist(Integer orderId);
}
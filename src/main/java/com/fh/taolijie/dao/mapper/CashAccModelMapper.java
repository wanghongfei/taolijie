package com.fh.taolijie.dao.mapper;

import com.fh.taolijie.domain.CashAccModel;
import com.fh.taolijie.domain.CashAccModelExample;

import java.math.BigDecimal;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CashAccModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CashAccModel record);

    int insertSelective(CashAccModel record);

    CashAccModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CashAccModel record);

    int updateByPrimaryKey(CashAccModel record);


    boolean checkAccExists(Integer memId);

    boolean checkAccIdExists(Integer id);

    /**
     * 添加可用余额
     * @param accId
     * @param amt
     * @return
     */
    int addAvailableAmt(@Param("accId") Integer accId, @Param("amt") BigDecimal amt);

    CashAccModel findByMemberId(Integer memId);
}
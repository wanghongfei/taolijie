package com.fh.taolijie.dao.mapper;

import com.fh.taolijie.domain.CouponModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CouponModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CouponModel record);

    int insertSelective(CouponModel record);

    CouponModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CouponModel record);

    int updateByPrimaryKey(CouponModel record);


    int insertInBatch(@Param("codeList") List<String> codeList, @Param("coupon") CouponModel coupon);
}

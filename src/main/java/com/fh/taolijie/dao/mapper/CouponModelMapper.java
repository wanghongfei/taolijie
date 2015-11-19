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


    /**
     * 批量插入coupon
     * @param codeList
     * @param coupon
     * @return
     */
    int insertInBatch(@Param("codeList") List<String> codeList, @Param("coupon") CouponModel coupon);

    /**
     * 根据任务返回一个coupon, 然后加行锁
     * @return
     */
    CouponModel selectOneByQuestIdWithLock(Integer questId);

    int updateStatus(Integer couponId, Integer status);

    List<CouponModel> findBy(CouponModel model);
    long countFindBy(CouponModel model);

    /**
     * 加行锁
     * @param code
     * @return
     */
    CouponModel selectByCode(String code);

    /**
     * 每个qusetId各取一条卡券记录
     * @return
     */
    List<CouponModel> selectOneGroupByQuestId(List<Integer> questIdList);
}

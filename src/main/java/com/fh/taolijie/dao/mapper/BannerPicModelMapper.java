package com.fh.taolijie.dao.mapper;

import com.fh.taolijie.domain.BannerPicModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BannerPicModelMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table banner_pic
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table banner_pic
     *
     * @mbggenerated
     */
    int insert(BannerPicModel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table banner_pic
     *
     * @mbggenerated
     */
    int insertSelective(BannerPicModel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table banner_pic
     *
     * @mbggenerated
     */
    BannerPicModel selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table banner_pic
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(BannerPicModel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table banner_pic
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(BannerPicModel record);

    List<BannerPicModel> getAll(@Param("pageNumber") int pageNumber, @Param("pageSize") int pageSize);
}
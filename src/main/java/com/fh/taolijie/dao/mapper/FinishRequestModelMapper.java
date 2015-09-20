package com.fh.taolijie.dao.mapper;

import com.fh.taolijie.domain.FinishRequestModel;
import com.fh.taolijie.domain.FinishRequestModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FinishRequestModelMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table finish_request
     *
     * @mbggenerated
     */
    int countByExample(FinishRequestModelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table finish_request
     *
     * @mbggenerated
     */
    int deleteByExample(FinishRequestModelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table finish_request
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table finish_request
     *
     * @mbggenerated
     */
    int insert(FinishRequestModel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table finish_request
     *
     * @mbggenerated
     */
    int insertSelective(FinishRequestModel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table finish_request
     *
     * @mbggenerated
     */
    List<FinishRequestModel> selectByExample(FinishRequestModelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table finish_request
     *
     * @mbggenerated
     */
    FinishRequestModel selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table finish_request
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") FinishRequestModel record, @Param("example") FinishRequestModelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table finish_request
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") FinishRequestModel record, @Param("example") FinishRequestModelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table finish_request
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(FinishRequestModel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table finish_request
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(FinishRequestModel record);
}
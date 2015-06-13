package com.fh.taolijie.dao.mapper;

import com.fh.taolijie.domain.MemberRoleModel;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRoleModelMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_role
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_role
     *
     * @mbggenerated
     */
    int insert(MemberRoleModel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_role
     *
     * @mbggenerated
     */
    int insertSelective(MemberRoleModel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_role
     *
     * @mbggenerated
     */
    MemberRoleModel selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_role
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(MemberRoleModel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_role
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(MemberRoleModel record);

    void deleteRelation(MemberRoleModel model);
}
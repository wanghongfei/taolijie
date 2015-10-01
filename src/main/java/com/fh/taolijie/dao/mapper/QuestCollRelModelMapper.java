package com.fh.taolijie.dao.mapper;

import com.fh.taolijie.domain.QuestCollRelModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestCollRelModelMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table quest_college_rel
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table quest_college_rel
     *
     * @mbggenerated
     */
    int insert(QuestCollRelModel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table quest_college_rel
     *
     * @mbggenerated
     */
    int insertSelective(QuestCollRelModel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table quest_college_rel
     *
     * @mbggenerated
     */
    QuestCollRelModel selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table quest_college_rel
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(QuestCollRelModel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table quest_college_rel
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(QuestCollRelModel record);

    void insertInBatch(List<QuestCollRelModel> list);
}
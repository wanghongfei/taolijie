package com.fh.taolijie.dao.mapper;

import com.fh.taolijie.domain.TljAuditModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TljAuditModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TljAuditModel record);

    int insertSelective(TljAuditModel record);

    TljAuditModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TljAuditModel record);

    int updateByPrimaryKey(TljAuditModel record);


    List<TljAuditModel> findBy(TljAuditModel example);
    long countFindBy(TljAuditModel example);

    /**
     * 检查剩余审核数量是否充足. 该查询会加行锁
     * @param auditId
     * @return
     */
    long queryLeftAmt(Integer auditId);
    long queryLeftAmtByQuest(Integer questId);

    int decreaseLeftAmt(Integer auditId);
    int decreaseLeftAmtByQuest(Integer questId);

    /**
     * 检查对同一个任务是否已经申请过
     * @return
     */
    boolean checkMemberAndQuestExists(@Param("memId") Integer memId, @Param("questId") Integer questId);
}
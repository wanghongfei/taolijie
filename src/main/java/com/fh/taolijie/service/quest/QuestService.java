package com.fh.taolijie.service.quest;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.constant.quest.AssignStatus;
import com.fh.taolijie.domain.QuestAssignModel;
import com.fh.taolijie.domain.QuestModel;
import com.fh.taolijie.exception.checked.acc.BalanceNotEnoughException;
import com.fh.taolijie.exception.checked.acc.CashAccNotExistsException;
import com.fh.taolijie.exception.checked.quest.*;

import java.math.BigDecimal;

/**
 * 轻兼职业务接口
 * Created by whf on 9/21/15.
 */
public interface QuestService {
    /**
     * 商家发布任务
     * @param accId 商家账户
     * @param model
     */
    void publishQuest(Integer accId, QuestModel model)
            throws BalanceNotEnoughException, CashAccNotExistsException;

    /**
     * 用户领取任务
     * @param memId 领取任务的用户id.
     * @param questId 任务id
     */
    void assignQuest(Integer memId, Integer questId)
            throws QuestAssignedException, QuestZeroException, QuestNotFoundException, QuestExpiredException, QuestNotStartException;

    /**
     * 根据分类查找任务
     * @param cateId 分类id
     * @return
     */
    ListResult<QuestModel> findByCate(Integer cateId, int pn, int ps);

    /**
     * 根据分类和赏金查询任务
     * @param cateId 分类id
     * @param min 最低赏金
     * @param max 最高赏金
     * @return
     */
    ListResult<QuestModel> findByCate(Integer cateId, BigDecimal min, BigDecimal max, int pn, int ps);

    /**
     * 查询指定用户发布的任务记录
     * @param memId
     * @param pn
     * @param ps
     * @return
     */
    ListResult<QuestModel> findByMember(Integer memId, int pn, int ps);


    /**
     * 查询指定用户的任务领取记录
     * @param memId
     * @param pn
     * @param ps
     * @return
     */
    ListResult<QuestAssignModel> queryAssignRecords(Integer memId, int pn, int ps);

    /**
     * 查询指定用户指定状态的任务领取记录
     *
     * @param memId
     * @param status
     * @param pn
     * @param ps
     * @return
     */
    ListResult<QuestAssignModel> queryAssignRecords(Integer memId, AssignStatus status, int pn, int ps);
}

package com.fh.taolijie.service.quest;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.domain.QuestModel;
import com.fh.taolijie.exception.checked.acc.BalanceNotEnoughException;
import com.fh.taolijie.exception.checked.acc.CashAccNotExistsException;
import com.fh.taolijie.exception.checked.quest.QuestAssignedException;
import com.fh.taolijie.exception.checked.quest.QuestZeroException;

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
            throws QuestAssignedException, QuestZeroException;

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
}

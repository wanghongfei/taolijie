package com.fh.taolijie.service.quest.impl;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.constant.quest.AssignStatus;
import com.fh.taolijie.dao.mapper.*;
import com.fh.taolijie.domain.CashAccModel;
import com.fh.taolijie.domain.MemberModel;
import com.fh.taolijie.domain.QuestAssignModel;
import com.fh.taolijie.domain.QuestModel;
import com.fh.taolijie.exception.checked.acc.BalanceNotEnoughException;
import com.fh.taolijie.exception.checked.quest.QuestAssignedException;
import com.fh.taolijie.exception.checked.quest.QuestZeroException;
import com.fh.taolijie.service.quest.QuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 轻兼职业务实现
 * Created by whf on 9/21/15.
 */
@Service
public class DefaultQuestService implements QuestService {
    @Autowired
    private QuestModelMapper questMapper;

    @Autowired
    private SysConfigModelMapper configMapper;

    @Autowired
    private FeeCalculator feeCal;

    @Autowired
    private QuestAssignModelMapper assignMapper;

    @Autowired
    private MemberModelMapper memMapper;

    @Autowired
    private CashAccModelMapper accMapper;


    /**
     * 商家发布任务.
     *
     * 要先检查商家账户余额是否充足
     * @param accId 商家账户id
     * @param model
     */
    @Override
    @Transactional(readOnly = false)
    public void publishQuest(Integer accId, QuestModel model)
            throws BalanceNotEnoughException {

        // 计算单个任务的最终价格
        // 获取费率
        int rate = configMapper.selectByPrimaryKey(1).getQuestFeeRate();
        // 单个任务的原始价格
        BigDecimal single = model.getAward();
        // 单个任务的最终价格
        single = feeCal.calculateFee(rate, single);
        // 将最终价格设置到model中
        model.setFinalAward(single);
        // 计算总钱数
        BigDecimal tot = single.multiply(new BigDecimal(model.getTotalAmt()));



        // 判断账户余额
        BigDecimal left = accMapper.selectByPrimaryKey(accId).getAvailableBalance();
        if (left.compareTo(tot) < 0) {
            // 余额不足
            throw new BalanceNotEnoughException("");
        }

        // 扣钱
        CashAccModel example = new CashAccModel();
        example.setId(accId);
        example.setAvailableBalance(left.subtract(tot));
        accMapper.updateByPrimaryKeySelective(example);

        // 发布任务
        model.setCreatedTime(new Date());
        questMapper.insertSelective(model);
    }

    /**
     * 领取任务。
     * 需要行锁。
     *
     * @param memId 领取任务的用户id.
     * @param questId 任务id
     */
    @Override
    @Transactional(readOnly = false)
    public void assignQuest(Integer memId, Integer questId)
            throws QuestAssignedException, QuestZeroException {

        // 检查任务是否已经领取
        boolean repeat = assignMapper.checkMemberIdAndQuestIdExists(memId, questId);
        if (repeat) {
            throw new QuestAssignedException("");
        }

        // 判断剩余任务数量
        // 这句查询会加行锁
        int leftAmt = questMapper.selectQuestLeftAmountWithLock(questId);
        if (leftAmt <= 0) {
            // 任务已经没了
            throw new QuestZeroException("");
        }


        // 领取任务
        // 向分配表中插入记录
        QuestAssignModel assignModel = new QuestAssignModel();
        assignModel.setMemberId(memId);
        assignModel.setQuestId(questId);
        assignModel.setAssignTime(new Date());
        // 设置冗余字段username
        MemberModel m = memMapper.selectByPrimaryKey(memId);
        assignModel.setUsername(m.getUsername());
        // 设置冗余字段quest_title
        QuestModel quest = questMapper.selectByPrimaryKey(questId);
        assignModel.setQuestTitle(quest.getTitle());
        assignModel.setStatus(AssignStatus.ASSIGNED.code());
        assignMapper.insertSelective(assignModel);


        // 任务剩余数量减少1
        questMapper.decreaseLeftAmount(questId);


        // 方法结束 == 事务结束，行锁释放
    }

    @Override
    public ListResult<QuestModel> findByCate(Integer cateId, int pn, int ps) {
        return null;
    }

    @Override
    public ListResult<QuestModel> findByCate(Integer cateId, BigDecimal min, BigDecimal max, int pn, int ps) {
        return null;
    }
}

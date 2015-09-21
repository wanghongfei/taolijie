package com.fh.taolijie.service.quest.impl;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.dao.mapper.QuestModelMapper;
import com.fh.taolijie.dao.mapper.SysConfigModelMapper;
import com.fh.taolijie.domain.QuestModel;
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


    @Override
    @Transactional(readOnly = false)
    public void publishQuest(QuestModel model) {
        // 获取费率
        int rate = configMapper.selectByPrimaryKey(1).getQuestFeeRate();
        // 单个任务的原始价格
        BigDecimal single = model.getAward();
        // 单个任务的最终价格
        single = feeCal.calculateFee(rate, single);
        // 将最终价格设置到model中
        model.setFinalAward(single);

        model.setCreatedTime(new Date());

        questMapper.insertSelective(model);
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

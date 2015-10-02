package com.fh.taolijie.service.collect.impl;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.dao.mapper.QuestCoModelMapper;
import com.fh.taolijie.domain.QuestCoModel;
import com.fh.taolijie.domain.quest.QuestModel;
import com.fh.taolijie.service.collect.QuestCoService;
import com.fh.taolijie.service.quest.QuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by whf on 10/3/15.
 */
@Service
public class DefaultQuestCoService implements QuestCoService {
    @Autowired
    private QuestCoModelMapper coMapper;

    @Autowired
    private QuestService questService;

    @Override
    @Transactional(readOnly = false)
    public void collect(Integer memId, Integer questId) {
        QuestCoModel model = new QuestCoModel();
        model.setQuestId(questId);
        model.setMemberId(memId);

        // 设置冗余字段
        QuestModel quest = questService.findById(questId);
        model.setQuestTitle(quest.getTitle());

        model.setCreatedTime(new Date());

        coMapper.insertSelective(model);
    }

    @Override
    @Transactional(readOnly = true)
    public ListResult<QuestCoModel> findBy(QuestCoModel example) {
        List<QuestCoModel> list = coMapper.findBy(example);
        long tot = coMapper.countFindBy(example);

        return new ListResult<>(list, tot);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean collected(Integer memId, Integer questId) {
        return coMapper.checkMemberAndQuestExists(memId, questId);
    }

    @Override
    @Transactional(readOnly = false)
    public void unCollect(Integer memId, Integer questId) {
        coMapper.deleteByMemberAndQuest(memId, questId);
    }
}

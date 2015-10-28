package com.fh.taolijie.service.quest.impl;

import com.fh.taolijie.dao.mapper.QuestCiRelMapper;
import com.fh.taolijie.dao.mapper.QuestCollRelModelMapper;
import com.fh.taolijie.dao.mapper.QuestProRelMapper;
import com.fh.taolijie.dao.mapper.QuestSchRelModelMapper;
import com.fh.taolijie.domain.quest.QuestCollRelModel;
import com.fh.taolijie.domain.quest.QuestModel;
import com.fh.taolijie.domain.quest.QuestSchRelModel;
import com.fh.taolijie.service.quest.QuestTargetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by whf on 10/28/15.
 */
@Service
public class DefaultQuestTargetService implements QuestTargetService {
    @Autowired
    private QuestSchRelModelMapper schMapper;
    @Autowired
    private QuestCollRelModelMapper collMapper;
    @Autowired
    private QuestCiRelMapper cityMapper;
    @Autowired
    private QuestProRelMapper proMapper;


    @Override
    @Transactional(readOnly = true)
    public void fillTarget(QuestModel quest) {
        Integer questId = quest.getId();

        quest.setSchools(schMapper.selectByQuestId(questId));
        quest.setColleges(collMapper.selectByQuestId(questId));
        quest.setCities(cityMapper.selectByQuestId(questId));
        quest.setPros(proMapper.selectByQuestId(questId));

    }
}

package com.fh.taolijie.service.quest.impl;

import com.fh.taolijie.dao.mapper.QuestModelMapper;
import com.fh.taolijie.dao.mapper.QuestionModelMapper;
import com.fh.taolijie.dao.mapper.QuestionOptModelMapper;
import com.fh.taolijie.domain.QuestionModel;
import com.fh.taolijie.domain.QuestionOptModel;
import com.fh.taolijie.domain.quest.QuestModel;
import com.fh.taolijie.exception.checked.acc.BalanceNotEnoughException;
import com.fh.taolijie.exception.checked.acc.CashAccNotExistsException;
import com.fh.taolijie.service.acc.CashAccService;
import com.fh.taolijie.service.quest.QuestService;
import com.fh.taolijie.service.quest.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by whf on 10/22/15.
 */
@Service
public class DefaultQuestionService implements QuestionService {
    @Autowired
    private QuestionModelMapper qMapper;

    @Autowired
    private QuestModelMapper questMapper;

    @Autowired
    private QuestionOptModelMapper optMapper;

    @Autowired
    private QuestService questService;

    @Autowired
    private CashAccService accService;

    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public void publishQuestions(QuestModel quest, List<QuestionModel> questionList)
            throws CashAccNotExistsException, BalanceNotEnoughException {

        Date now = new Date();

        // S1: 创建Quest
        Integer accId = accService.findIdByMember(quest.getMemberId());
        questService.publishQuest(accId, quest);


        // S2: 创建问题对象
        for (QuestionModel q : questionList) {
            q.setQuestId(quest.getId());
            q.setCreatedTime(now);
            qMapper.insertSelective(q);
            // S2.5: 创建问题对应的选项
            optMapper.insertInBatch(q.getId(), q.getOpts());
        }
    }
}

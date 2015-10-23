package com.fh.taolijie.service.quest.impl;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.constant.acc.AccFlow;
import com.fh.taolijie.constant.quest.QuestionType;
import com.fh.taolijie.dao.mapper.*;
import com.fh.taolijie.domain.AnRecordModel;
import com.fh.taolijie.domain.QuestionModel;
import com.fh.taolijie.domain.QuestionOptModel;
import com.fh.taolijie.domain.SysConfigModel;
import com.fh.taolijie.domain.quest.QuestModel;
import com.fh.taolijie.dto.QuestionAnalyzeDto;
import com.fh.taolijie.exception.checked.HackException;
import com.fh.taolijie.exception.checked.acc.BalanceNotEnoughException;
import com.fh.taolijie.exception.checked.acc.CashAccNotExistsException;
import com.fh.taolijie.exception.checked.quest.NotQuestionQuestException;
import com.fh.taolijie.exception.checked.quest.QuestNotFoundException;
import com.fh.taolijie.exception.checked.quest.QuestionNotFoundException;
import com.fh.taolijie.exception.checked.quest.RequestRepeatedException;
import com.fh.taolijie.service.acc.CashAccService;
import com.fh.taolijie.service.quest.QuestService;
import com.fh.taolijie.service.quest.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

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

    @Autowired
    private SysConfigModelMapper sysMapper;

    @Autowired
    private AnRecordModelMapper recordMapper;

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

    @Override
    @Transactional(readOnly = true)
    public ListResult<QuestionModel> findQuestionList(Integer questId) throws QuestNotFoundException, NotQuestionQuestException {
        QuestModel quest = questMapper.selectByPrimaryKey(questId);
        if (null == quest) {
            throw new QuestNotFoundException("");
        }

        // 根据任务查询问题列表
        List<QuestionModel> questions = qMapper.selectByQuestId(questId);
        // 没有查到说明这不是一个答题问卷任务
        if (questions.isEmpty()) {
            throw new NotQuestionQuestException();
        }

        return new ListResult<>(questions, questions.size());
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuestionOptModel> findOptInBatch(List<Integer> idList) {
        if (idList.isEmpty()) {
            return new ArrayList<>(0);
        }

        return optMapper.selectInBatch(idList);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public Boolean validateAnswer(Integer memId, Integer questionId, List<Integer> optIdList)
            throws QuestionNotFoundException, CashAccNotExistsException, HackException, RequestRepeatedException {
        //todo
        // 检查问题存在性
        QuestionModel question = qMapper.selectByPrimaryKey(questionId);
        if (null == question) {
            throw new QuestionNotFoundException();
        }

        // 检查选项与问题是否匹配
        List<QuestionOptModel> optList = findOptInBatch(optIdList);
        if (!checkOptMatchQuestion(questionId, optList)) {
            throw new HackException();
        }

        // 检查是否重复答题
        if (true == recordMapper.checkExist(memId, questionId)) {
            throw new RequestRepeatedException("");
        }

        // 判断任务类型
        QuestionType type = QuestionType.fromCode(question.getType());
        if (null == type) {
            throw new IllegalStateException("invalid question type" + type.code());
        }


        // 得到各类任务对应的钱数
        SysConfigModel sys = sysMapper.selectByPrimaryKey(1);
        BigDecimal singleFee = null;
        boolean result = false;

        // 是答题类问题
        if (type == QuestionType.EXAMINATION) {
            // 答题类问题
            // 需要检查正确性
            singleFee = sys.getQuestionFee();

            // 判断是否答对
            result = checkAllOptRight(question.getAnswerAmt(), optList);

            // 如答对
            // 加钱
            if (result) {
                // 增加钱包金额
                Integer accId = accService.findIdByMember(memId);
                accService.addAvailableMoney(accId, singleFee, AccFlow.AWARD);
            }

            // 统计信息: 增加答题人数
            qMapper.increaseAnswerAmt(result, questionId);

        } else if (type == QuestionType.SURVEY) {
            // 问卷类问题
            // 直接加钱
            singleFee = sys.getSurveyFee();


            // 增加钱包金额
            Integer accId = accService.findIdByMember(memId);
            accService.addAvailableMoney(accId, singleFee, AccFlow.AWARD);

            // 统计信息: 增加答题人数
            qMapper.increaseAnswerAmt(false, questionId);
        }

        // 统计信息: 增加选择该选项的人数
        optMapper.increaseAnswerAmt(optIdList);

        // 答题记录
        AnRecordModel record = new AnRecordModel();
        record.setMemberId(memId);
        record.setQuestionId(questionId);
        record.setCreatedTime(new Date());
        record.setQuestId(question.getQuestId());
        recordMapper.insertSelective(record);

        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AnRecordModel> findAnRecordByQuest(Integer questId, Integer memId) {
        return recordMapper.selectByQuestAndMember(memId, questId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuestionAnalyzeDto> analyzeData(Integer questId) throws QuestNotFoundException, NotQuestionQuestException {
        // 查出该任务下所有的问题
        ListResult<QuestionModel> questionLr = findQuestionList(questId);


        return doAnalyze(questionLr.getList());
    }

    private List<QuestionAnalyzeDto> doAnalyze(List<QuestionModel> qList) {
        List<QuestionAnalyzeDto> dtoList = new ArrayList<>(qList.size() * 5);

        // 遍历每个问题
        qList.forEach( q -> {
            // 计算正确率
            int correctAmt = q.getCorrectAmt();
            int userAmt = q.getUserAmt();
            float percentage = 0;
            if (userAmt > 0) {
                percentage = (float)correctAmt / userAmt;
            }


            // 创建分析结果对象
            QuestionAnalyzeDto dto = new QuestionAnalyzeDto();
            dto.setCorrectUser(q.getCorrectAmt());
            dto.setTotUser(q.getUserAmt());
            dto.setPercentage(percentage);
            dto.setQuestionId(q.getId());


            // 设置每个选项的统计信息
            Map<Integer, String> map = new HashMap<>(5);
            List<QuestionOptModel> opts = q.getOpts();
            opts.forEach( opt -> {
                map.put(opt.getId(), opt.getAnswerAmt().toString());
            });
            dto.setOpts(map);

            dtoList.add(dto);
        });

        return dtoList;
    }

    private boolean checkAllOptRight(int correctAmt, List<QuestionOptModel> optList) {
        int sum = 0;
        for (QuestionOptModel opt : optList) {
            Boolean right = opt.getCorrect();
            if (null != right && true == right) {
                ++sum;
            }
        }

        return sum == correctAmt;
    }

    private boolean checkOptMatchQuestion(Integer questionId, List<QuestionOptModel> optList) {
        return optList.stream().allMatch( opt -> opt.getQuestionId().equals(questionId) );
    }
}

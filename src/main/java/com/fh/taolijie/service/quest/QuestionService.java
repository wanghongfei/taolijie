package com.fh.taolijie.service.quest;

import com.fh.taolijie.domain.QuestionModel;
import com.fh.taolijie.domain.quest.QuestModel;
import com.fh.taolijie.exception.checked.acc.BalanceNotEnoughException;
import com.fh.taolijie.exception.checked.acc.CashAccNotExistsException;

import java.util.List;

/**
 * 与答题问卷相关业务接口
 * Created by whf on 10/22/15.
 */
public interface QuestionService {
    /**
     * 添加问题
     * @param questionList
     */
    void publishQuestions(QuestModel quest, List<QuestionModel> questionList) throws CashAccNotExistsException, BalanceNotEnoughException;
}

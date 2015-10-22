package com.fh.taolijie.service.quest;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.domain.QuestionModel;
import com.fh.taolijie.domain.QuestionOptModel;
import com.fh.taolijie.domain.quest.QuestModel;
import com.fh.taolijie.exception.checked.HackException;
import com.fh.taolijie.exception.checked.acc.BalanceNotEnoughException;
import com.fh.taolijie.exception.checked.acc.CashAccNotExistsException;
import com.fh.taolijie.exception.checked.quest.NotQuestionQuestException;
import com.fh.taolijie.exception.checked.quest.QuestNotFoundException;
import com.fh.taolijie.exception.checked.quest.QuestionNotFoundException;
import com.fh.taolijie.exception.checked.quest.RequestRepeatedException;

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
    void publishQuestions(QuestModel quest, List<QuestionModel> questionList)
            throws CashAccNotExistsException, BalanceNotEnoughException;

    /**
     * 查询答题问卷任务下的所有问题
     * @param questId
     * @return
     */
    ListResult<QuestionModel> findQuestionList(Integer questId)
            throws QuestNotFoundException, NotQuestionQuestException;

    List<QuestionOptModel> findOptInBatch(List<Integer> idList);

    /**
     * 判断答案正误
     * @return
     */
    Boolean validateAnswer(Integer memId, Integer questionId, List<Integer> optIdList)
            throws QuestionNotFoundException, CashAccNotExistsException, HackException, RequestRepeatedException;
}

package com.fh.taolijie.service.quest;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.domain.AnRecordModel;
import com.fh.taolijie.domain.quest.QuestionModel;
import com.fh.taolijie.domain.quest.QuestionOptModel;
import com.fh.taolijie.domain.quest.QuestModel;
import com.fh.taolijie.dto.QuestionAnalyzeDto;
import com.fh.taolijie.exception.checked.FinalStatusException;
import com.fh.taolijie.exception.checked.HackException;
import com.fh.taolijie.exception.checked.PermissionException;
import com.fh.taolijie.exception.checked.acc.BalanceNotEnoughException;
import com.fh.taolijie.exception.checked.acc.CashAccNotExistsException;
import com.fh.taolijie.exception.checked.acc.OrderNotFoundException;
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
    void publishQuestions(QuestModel quest, List<QuestionModel> questionList, Integer orderId)
            throws CashAccNotExistsException, BalanceNotEnoughException, FinalStatusException, OrderNotFoundException, PermissionException;

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

    /**
     * 查询指定任务的答题记录
     * @param questId
     * @return
     */
    List<AnRecordModel> findAnRecordByQuest(Integer questId, Integer memId);

    /**
     * 统计答题数据
     * @param questId
     * @return
     */
    List<QuestionAnalyzeDto> analyzeData(Integer questId)
            throws QuestNotFoundException, NotQuestionQuestException;
}

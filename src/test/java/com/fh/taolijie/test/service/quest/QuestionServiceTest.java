package com.fh.taolijie.test.service.quest;

import com.fh.taolijie.constant.quest.EmpQuestStatus;
import com.fh.taolijie.domain.quest.QuestionModel;
import com.fh.taolijie.domain.quest.QuestionOptModel;
import com.fh.taolijie.domain.quest.QuestModel;
import com.fh.taolijie.service.quest.QuestionService;
import com.fh.taolijie.service.quest.impl.DefaultQuestionService;
import com.fh.taolijie.test.BaseSpringDataTestClass;
import com.fh.taolijie.utils.TimeUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Created by whf on 10/22/15.
 */
@ContextConfiguration(classes = {
        DefaultQuestionService.class
})
public class QuestionServiceTest extends BaseSpringDataTestClass {
    @Autowired
    private QuestionService service;

    @Test
    //@Rollback(false)
    public void testPublish() throws Exception {
        QuestModel quest = new QuestModel();
        quest.setEmpStatus(EmpQuestStatus.DONE.code());
        quest.setAward(new BigDecimal(1));
        quest.setTitle("question");
        quest.setQuestCateId(1);
        quest.setTotalAmt(5);
        quest.setCityId(1);
        quest.setCollegeId(1);
        quest.setRegionId(1);
        quest.setEndTime(TimeUtil.calculateDate(null, Calendar.YEAR, 1));
        quest.setMemberId(1);

        List<QuestionModel> questionList = new ArrayList<>(10);
        QuestionModel questionModel = new QuestionModel();
        QuestionOptModel optModel = new QuestionOptModel();
        optModel.setOptName("name");
        optModel.setOptContent("content");
        questionModel.setOpts(Arrays.asList(optModel));
        questionList.add(questionModel);

        //service.publishQuestions(quest, questionList);
    }
}

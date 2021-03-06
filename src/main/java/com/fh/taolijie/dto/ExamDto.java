package com.fh.taolijie.dto;

/**
 * Created by whf on 10/22/15.
 */

import com.fh.taolijie.domain.quest.QuestionModel;
import com.fh.taolijie.domain.quest.QuestModel;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 答题问卷DTO
 */
public class ExamDto {
    @NotNull
    private List<QuestionModel> questions;
    @NotNull
    private QuestModel quest;

    private Integer orderId;

    // 任务对象信息
    private String collegeIds;
    private String schoolIds;
    private String cityIds;
    private String proIds;

    private Integer save = 0;

    public List<QuestionModel> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionModel> questions) {
        this.questions = questions;
    }

    public QuestModel getQuest() {
        return quest;
    }

    public String getCollegeIds() {
        return collegeIds;
    }

    public void setCollegeIds(String collegeIds) {
        this.collegeIds = collegeIds;
    }

    public String getSchoolIds() {
        return schoolIds;
    }

    public void setSchoolIds(String schoolIds) {
        this.schoolIds = schoolIds;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getCityIds() {
        return cityIds;
    }

    public void setCityIds(String cityIds) {
        this.cityIds = cityIds;
    }

    public String getProIds() {
        return proIds;
    }

    public void setProIds(String proIds) {
        this.proIds = proIds;
    }

    public Integer getSave() {
        return save;
    }

    public void setSave(Integer save) {
        this.save = save;
    }

    public void setQuest(QuestModel quest) {
        this.quest = quest;
    }
}

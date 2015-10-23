package com.fh.taolijie.dto;

import java.util.Map;

/**
 * 单个问题的统计信息
 * Created by whf on 10/23/15.
 */
public class QuestionAnalyzeDto {
    private Integer questionId;

    /**
     * 总答题人数
     */
    private Integer totUser;
    /**
     * 答对人数
     */
    private Integer correctUser;

    /**
     * 正确率
     */
    private Float percentage;

    /**
     * 选项id -> 选择该选项的人数
     */
    private Map<Integer, String> opts;

    public Integer getTotUser() {
        return totUser;
    }

    public void setTotUser(Integer totUser) {
        this.totUser = totUser;
    }

    public Integer getCorrectUser() {
        return correctUser;
    }

    public void setCorrectUser(Integer correctUser) {
        this.correctUser = correctUser;
    }

    public Map<Integer, String> getOpts() {
        return opts;
    }

    public void setOpts(Map<Integer, String> opts) {
        this.opts = opts;
    }

    public Float getPercentage() {
        return percentage;
    }

    public void setPercentage(Float percentage) {
        this.percentage = percentage;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }
}

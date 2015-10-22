package com.fh.taolijie.domain;

import java.math.BigDecimal;

public class SysConfigModel {
    private Integer id;

    private Integer questFeeRate;

    private BigDecimal auditFee;

    private BigDecimal topFee;

    private BigDecimal tagFee;

    private BigDecimal questionFee;

    private BigDecimal surveyFee;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuestFeeRate() {
        return questFeeRate;
    }

    public BigDecimal getAuditFee() {
        return auditFee;
    }

    public void setAuditFee(BigDecimal auditFee) {
        this.auditFee = auditFee;
    }

    public BigDecimal getTopFee() {
        return topFee;
    }

    public void setTopFee(BigDecimal topFee) {
        this.topFee = topFee;
    }

    public BigDecimal getTagFee() {
        return tagFee;
    }

    public void setTagFee(BigDecimal tagFee) {
        this.tagFee = tagFee;
    }

    public BigDecimal getQuestionFee() {
        return questionFee;
    }

    public void setQuestionFee(BigDecimal questionFee) {
        this.questionFee = questionFee;
    }

    public BigDecimal getSurveyFee() {
        return surveyFee;
    }

    public void setSurveyFee(BigDecimal surveyFee) {
        this.surveyFee = surveyFee;
    }

    public void setQuestFeeRate(Integer questFeeRate) {
        this.questFeeRate = questFeeRate;
    }
}
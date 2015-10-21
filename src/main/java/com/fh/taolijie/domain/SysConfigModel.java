package com.fh.taolijie.domain;

import java.math.BigDecimal;

public class SysConfigModel {
    private Integer id;

    private Integer questFeeRate;

    private BigDecimal auditFee;

    private BigDecimal topFee;

    private BigDecimal tagFee;

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

    public void setQuestFeeRate(Integer questFeeRate) {
        this.questFeeRate = questFeeRate;
    }
}
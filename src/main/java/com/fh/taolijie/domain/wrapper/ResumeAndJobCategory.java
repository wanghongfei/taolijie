package com.fh.taolijie.domain.wrapper;

/**
 * Created by wanghongfei on 15-4-1.
 */
public class ResumeAndJobCategory {
    private Integer resumeId;
    private Integer categoryId;

    public ResumeAndJobCategory(Integer resumeId, Integer categoryId) {
        this.resumeId = resumeId;
        this.categoryId = categoryId;
    }

    public Integer getResumeId() {
        return resumeId;
    }

    public void setResumeId(Integer resumeId) {
        this.resumeId = resumeId;
    }
}

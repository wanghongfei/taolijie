package com.fh.taolijie.controller.dto;

import java.util.Date;

/**
 * Created by wanghongfei on 15-4-3.
 */
public class PostRecordDto {
    private Integer jobPostId;
    private Date postTime;

    public PostRecordDto() {}

    public PostRecordDto(Integer jobPostId, Date postTime) {
        this.jobPostId = jobPostId;
        this.postTime = postTime;
    }

    public Integer getJobPostId() {
        return jobPostId;
    }

    public void setJobPostId(Integer jobPostId) {
        this.jobPostId = jobPostId;
    }
}

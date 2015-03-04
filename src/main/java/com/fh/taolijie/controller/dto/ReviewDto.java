package com.fh.taolijie.controller.dto;

import java.util.Date;

/**
 * Created by wanghongfei on 15-3-4.
 */
public class ReviewDto {
    private String content;
    private Date time;

    private Integer memberId;
    private Integer jobPostId;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getJobPostId() {
        return jobPostId;
    }

    public void setJobPostId(Integer jobPostId) {
        this.jobPostId = jobPostId;
    }
}

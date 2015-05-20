package com.fh.taolijie.controller.dto;

import java.util.Date;
import java.util.List;

/**
 * Created by wynfrith on 15-5-20.
 */
public class ReviewShowDto {
    private Integer id;
    private String content;
    private Date time;
    private Integer jobPostId;

    public GeneralMemberDto getMember() {
        return member;
    }

    public void setMember(GeneralMemberDto member) {
        this.member = member;
    }

    private GeneralMemberDto member;

    /**
     * Replies to this comment, if any.
     */
    private List<ReviewDto> replyList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public List<ReviewDto> getReplyList() {
        return replyList;
    }

    public void setReplyList(List<ReviewDto> replyList) {
        this.replyList = replyList;
    }


    public Integer getJobPostId() {
        return jobPostId;
    }

    public void setJobPostId(Integer jobPostId) {
        this.jobPostId = jobPostId;
    }
}

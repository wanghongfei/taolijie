package com.fh.taolijie.controller.dto;

import java.util.Date;
import java.util.List;

/**
 * Created by wanghongfei on 15-3-4.
 */
public class ReviewDto {
    private Integer id;
    private String content;
    private Date time;

    private Integer memberId;
    private GeneralMemberDto memberDto;


    private Integer jobPostId;
    private JobPostDto jobPostDto;

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

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getJobPostId() {
        return jobPostId;
    }

    public GeneralMemberDto getMemberDto() {
        return memberDto;
    }

    public void setMemberDto(GeneralMemberDto memberDto) {
        this.memberDto = memberDto;
    }

    public JobPostDto getJobPostDto() {
        return jobPostDto;
    }

    public void setJobPostDto(JobPostDto jobPostDto) {
        this.jobPostDto = jobPostDto;
    }

    public void setJobPostId(Integer jobPostId) {
        this.jobPostId = jobPostId;
    }
}

package com.fh.taolijie.domain;

import java.util.Date;
import java.util.List;

/**
 * Created by wanghongfei on 15-3-4.
 */
public class ReviewModel {
    private Integer id;
    private String content;
    private Date time;
    private JobPostModel jobPost;
    private MemberModel member;

    private List<ReviewModel> replyList;
    private ReviewModel baseReview;

    public ReviewModel() {}
    public ReviewModel(String content, Date time, JobPostModel jobPost, MemberModel member) {
        this.content = content;
        this.time = time;
        this.jobPost = jobPost;
        this.member = member;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReviewModel that = (ReviewModel) o;

        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (time != null ? !time.equals(that.time) : that.time != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        return result;
    }

    public List<ReviewModel> getReplyList() {
        return replyList;
    }

    public void setReplyList(List<ReviewModel> replyList) {
        this.replyList = replyList;
    }

    public ReviewModel getBaseReview() {
        return baseReview;
    }

    public void setBaseReview(ReviewModel baseReview) {
        this.baseReview = baseReview;
    }

    public JobPostModel getJobPost() {
        return jobPost;
    }

    public void setJobPost(JobPostModel jobPost) {
        this.jobPost = jobPost;
    }

    public MemberModel getMember() {
        return member;
    }

    public void setMember(MemberModel member) {
        this.member = member;
    }
}

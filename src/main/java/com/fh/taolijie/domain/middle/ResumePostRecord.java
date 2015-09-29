package com.fh.taolijie.domain.middle;

/**
 * Created by wanghongfei on 15-6-7.
 */

import com.fh.taolijie.domain.job.JobPostModel;
import com.fh.taolijie.domain.acc.MemberModel;
import com.fh.taolijie.domain.resume.ResumeModel;

import java.util.Date;

/**
 * 简历投递记录
 */
public class ResumePostRecord {
    private MemberModel member;
    private ResumeModel resume;
    private JobPostModel jobPost;
    private Date createdTime;

    public MemberModel getMember() {
        return member;
    }

    public void setMember(MemberModel member) {
        this.member = member;
    }

    public ResumeModel getResume() {
        return resume;
    }

    public void setResume(ResumeModel resume) {
        this.resume = resume;
    }

    public JobPostModel getJobPost() {
        return jobPost;
    }

    public void setJobPost(JobPostModel jobPost) {
        this.jobPost = jobPost;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
}

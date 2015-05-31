package com.fh.taolijie.domain;

import com.fh.taolijie.service.PageViewAware;

import java.util.Collection;
import java.util.Date;

/**
 * Created by wanghongfei on 15-3-4.
 */
public class JobPostModel implements PageViewAware {
    private Integer id;
    private String title;
    private Date expiredTime;
    private Date postTime;
    private String workPlace;
    private Double wage;
    private String timeToPay;
    private String jobDescription;
    private String contact;
    private String contactPhone;
    private String contactEmail;
    private String contactQq;
    private String jobDetail;
    private String introduce;
    private Integer likes;
    private Integer dislikes;
    private String educationLevel;

    private Integer complaint;
    private String workTime;
    private String salaryUnit;
    private Integer pageView;
    private String verified;
    private String applicationResumeIds;
    private Integer applicantAmount;

    private JobPostCategoryModel category;
    private MemberModel member;
    private Collection<ReviewModel> reviewCollection;

    public JobPostModel() {}
    public JobPostModel(String title, Date expiredTime, Date postTime, String workPlace, Double wage, String timeToPay, String jobDescription, String contact, String contactPhone, String contactEmail, String contactQq, String jobDetail, String introduce, Integer likes, Integer dislikes, String educationLevel, JobPostCategoryModel category, MemberModel member) {
        this.title = title;
        this.expiredTime = expiredTime;
        this.postTime = postTime;
        this.workPlace = workPlace;
        this.wage = wage;
        this.timeToPay = timeToPay;
        this.jobDescription = jobDescription;
        this.contact = contact;
        this.contactPhone = contactPhone;
        this.contactEmail = contactEmail;
        this.contactQq = contactQq;
        this.jobDetail = jobDetail;
        this.introduce = introduce;
        this.likes = likes;
        this.dislikes = dislikes;
        this.educationLevel = educationLevel;
        this.category = category;
        this.member = member;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVerified() {
        return verified;
    }

    public void setVerified(String verified) {
        this.verified = verified;
    }

    public Integer getApplicantAmount() {
        return applicantAmount;
    }

    public void setApplicantAmount(Integer applicantAmount) {
        this.applicantAmount = applicantAmount;
    }

    public String getApplicationResumeIds() {
        return applicationResumeIds;
    }

    public void setApplicationResumeIds(String applicationResumeIds) {
        this.applicationResumeIds = applicationResumeIds;
    }

    public Integer getPageView() {
        return pageView;
    }

    public void setPageView(Integer pageView) {
        this.pageView = pageView;
    }

    public Date getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Date expiredTime) {
        this.expiredTime = expiredTime;
    }

    public Integer getComplaint() {
        return complaint;
    }

    public void setComplaint(Integer complaint) {
        this.complaint = complaint;
    }

    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    public String getSalaryUnit() {
        return salaryUnit;
    }

    public void setSalaryUnit(String salaryUnit) {
        this.salaryUnit = salaryUnit;
    }

    public Date getPostTime() {
        return postTime;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }

    public String getWorkPlace() {
        return workPlace;
    }

    public void setWorkPlace(String workPlace) {
        this.workPlace = workPlace;
    }

    public Double getWage() {
        return wage;
    }

    public void setWage(Double wage) {
        this.wage = wage;
    }

    public String getTimeToPay() {
        return timeToPay;
    }

    public void setTimeToPay(String timeToPay) {
        this.timeToPay = timeToPay;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactQq() {
        return contactQq;
    }

    public void setContactQq(String contactQq) {
        this.contactQq = contactQq;
    }

    public String getJobDetail() {
        return jobDetail;
    }

    public void setJobDetail(String jobDetail) {
        this.jobDetail = jobDetail;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getDislikes() {
        return dislikes;
    }

    public void setDislikes(Integer dislikes) {
        this.dislikes = dislikes;
    }

    public String getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JobPostModel that = (JobPostModel) o;

        if (contact != null ? !contact.equals(that.contact) : that.contact != null) return false;
        if (contactEmail != null ? !contactEmail.equals(that.contactEmail) : that.contactEmail != null) return false;
        if (contactPhone != null ? !contactPhone.equals(that.contactPhone) : that.contactPhone != null) return false;
        if (contactQq != null ? !contactQq.equals(that.contactQq) : that.contactQq != null) return false;
        if (dislikes != null ? !dislikes.equals(that.dislikes) : that.dislikes != null) return false;
        if (educationLevel != null ? !educationLevel.equals(that.educationLevel) : that.educationLevel != null)
            return false;
        if (expiredTime != null ? !expiredTime.equals(that.expiredTime) : that.expiredTime != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (introduce != null ? !introduce.equals(that.introduce) : that.introduce != null) return false;
        if (jobDescription != null ? !jobDescription.equals(that.jobDescription) : that.jobDescription != null)
            return false;
        if (jobDetail != null ? !jobDetail.equals(that.jobDetail) : that.jobDetail != null) return false;
        if (likes != null ? !likes.equals(that.likes) : that.likes != null) return false;
        if (postTime != null ? !postTime.equals(that.postTime) : that.postTime != null) return false;
        if (timeToPay != null ? !timeToPay.equals(that.timeToPay) : that.timeToPay != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (wage != null ? !wage.equals(that.wage) : that.wage != null) return false;
        if (workPlace != null ? !workPlace.equals(that.workPlace) : that.workPlace != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (expiredTime != null ? expiredTime.hashCode() : 0);
        result = 31 * result + (postTime != null ? postTime.hashCode() : 0);
        result = 31 * result + (workPlace != null ? workPlace.hashCode() : 0);
        result = 31 * result + (wage != null ? wage.hashCode() : 0);
        result = 31 * result + (timeToPay != null ? timeToPay.hashCode() : 0);
        result = 31 * result + (jobDescription != null ? jobDescription.hashCode() : 0);
        result = 31 * result + (contact != null ? contact.hashCode() : 0);
        result = 31 * result + (contactPhone != null ? contactPhone.hashCode() : 0);
        result = 31 * result + (contactEmail != null ? contactEmail.hashCode() : 0);
        result = 31 * result + (contactQq != null ? contactQq.hashCode() : 0);
        result = 31 * result + (jobDetail != null ? jobDetail.hashCode() : 0);
        result = 31 * result + (introduce != null ? introduce.hashCode() : 0);
        result = 31 * result + (likes != null ? likes.hashCode() : 0);
        result = 31 * result + (dislikes != null ? dislikes.hashCode() : 0);
        result = 31 * result + (educationLevel != null ? educationLevel.hashCode() : 0);
        return result;
    }

    public JobPostCategoryModel getCategory() {
        return category;
    }

    public void setCategory(JobPostCategoryModel category) {
        this.category = category;
    }

    public MemberModel getMember() {
        return member;
    }

    public void setMember(MemberModel member) {
        this.member = member;
    }

    public Collection<ReviewModel> getReviewCollection() {
        return reviewCollection;
    }

    public void setReviewCollection(Collection<ReviewModel> reviewCollection) {
        this.reviewCollection = reviewCollection;
    }
}

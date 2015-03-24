package com.fh.taolijie.controller.dto;

import com.fh.taolijie.utils.Constants;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Date;

/**
 * Created by wanghongfei on 15-3-4.
 */
public class JobPostDto {
    private Integer id;

    @NotEmpty(message = Constants.ErrorType.NOT_EMPTY)
    private String title;
    @NotEmpty(message = Constants.ErrorType.NOT_EMPTY)
    private Date expiredTime;
    private Date postTime;
    @NotEmpty(message = Constants.ErrorType.NOT_EMPTY)
    private String workPlace;
    @NotEmpty(message = Constants.ErrorType.NOT_EMPTY)
    private Double wage;
    @NotEmpty(message = Constants.ErrorType.NOT_EMPTY)
    private String timeToPay;
    @NotEmpty(message = Constants.ErrorType.NOT_EMPTY)
    private String jobDescription;
    @NotEmpty(message = Constants.ErrorType.NOT_EMPTY)
    private String contact;
    @NotEmpty(message = Constants.ErrorType.NOT_EMPTY)
    private String contactPhone;
    @NotEmpty(message = Constants.ErrorType.NOT_EMPTY)
    private String contactEmail;
    @NotEmpty(message = Constants.ErrorType.NOT_EMPTY)
    private String contactQq;
    @NotEmpty(message = Constants.ErrorType.NOT_EMPTY)
    private String jobDetail;
    @NotEmpty(message = Constants.ErrorType.NOT_EMPTY)
    private String introduce;
    private Integer likes;
    private Integer dislikes;
    private String educationLevel;

    private Integer categoryId;
    private Integer memberId;

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

    public Date getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Date expiredTime) {
        this.expiredTime = expiredTime;
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

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }
}

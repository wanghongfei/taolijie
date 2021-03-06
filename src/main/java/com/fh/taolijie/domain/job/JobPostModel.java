package com.fh.taolijie.domain.job;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fh.taolijie.domain.PVable;
import com.fh.taolijie.domain.acc.MemberModel;
import com.fh.taolijie.domain.Pageable;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class JobPostModel extends Pageable implements Serializable, PVable {
    private Integer id;

    @Null
    private String introduce; // 这个字段是干啥的来着??

    @Null
    private String applicationResumeIds;

    @NotEmpty
    @Length(min = 1, max = 30)
    private String title;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date expiredTime;

    private Boolean expired;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date postTime;

    @NotEmpty
    @Length(min = 1, max = 100)
    private String workPlace;

    private String workRegion;

    @NotNull
    @Min(0) @Max(99999)
    private BigDecimal wage;

    @NotNull
    @Length(max = 10)
    private String timeToPay;


    private boolean deleted = false;

    @NotNull
    @Length(min = 0, max = 600)
    private String jobDescription;

    /**
     * 联系人名
     */
    @NotNull
    @Length(min = 0, max = 15)
    private String contact;

    @NotNull
    @Length(min = 0, max = 30)
    private String contactPhone;

    private String contactEmail;

    //@NotNull
    @Length(max = 15)
    private String contactQq;

    @NotNull
    @Length(min = 0, max = 600)
    private String jobDetail;

    @Null
    private Integer likes;

    @Null
    private Integer dislikes;

    @NotNull
    private Integer jobPostCategoryId;

    @Null
    private Integer memberId;

    @Null
    private String educationLevel;

    @Null
    private Integer complaint;

    /**
     * 工作时间
     */
    @NotNull
    @Length(min = 0, max = 120)
    private String workTime;

    private String salaryUnit;

    @Null
    private Integer pageView;

    @Null
    private String verified;

    @Null
    private String jobPostcol;

    @Null
    private Integer applicantAmount;

    private String province;
    private String city;
    private String region;

    @Null
    private String pv;

    private MemberModel member;
    private JobPostCategoryModel category;


    /* 查询用 */
    @JsonIgnore
    private boolean orderByDate = true;
    @JsonIgnore
    private boolean orderByVisit = false;
    @JsonIgnore
    private boolean filterExpiredPost = true;

    private Integer cateId;

    public JobPostModel() {}

    public JobPostModel(int pageNumber, int pageSize) {
        super(pageNumber, pageSize);
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public Integer getCateId() {
        return cateId;
    }

    public void setCateId(Integer cateId) {
        this.cateId = cateId;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getWorkRegion() {
        return workRegion;
    }

    public void setWorkRegion(String workRegion) {
        this.workRegion = workRegion;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isOrderByDate() {
        return orderByDate;
    }

    public void setOrderByDate(boolean orderByDate) {
        this.orderByDate = orderByDate;
    }

    public boolean isFilterExpiredPost() {
        return filterExpiredPost;
    }

    public void setFilterExpiredPost(boolean filterExpiredPost) {
        this.filterExpiredPost = filterExpiredPost;
    }

    public boolean isOrderByVisit() {
        return orderByVisit;
    }

    public void setOrderByVisit(boolean orderByVisit) {
        this.orderByVisit = orderByVisit;
    }

    public MemberModel getMember() {
        return member;
    }

    public void setMember(MemberModel member) {
        this.member = member;
    }

    public JobPostCategoryModel getCategory() {
        return category;
    }

    public void setCategory(JobPostCategoryModel category) {
        this.category = category;
    }

    public Boolean getExpired() {
        return expired;
    }

    public void setExpired(Boolean expired) {
        this.expired = expired;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column job_post.id
     *
     * @return the value of job_post.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column job_post.id
     *
     * @param id the value for job_post.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column job_post.title
     *
     * @return the value of job_post.title
     *
     * @mbggenerated
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column job_post.title
     *
     * @param title the value for job_post.title
     *
     * @mbggenerated
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column job_post.expired_time
     *
     * @return the value of job_post.expired_time
     *
     * @mbggenerated
     */
    public Date getExpiredTime() {
        return expiredTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column job_post.expired_time
     *
     * @param expiredTime the value for job_post.expired_time
     *
     * @mbggenerated
     */
    public void setExpiredTime(Date expiredTime) {
        this.expiredTime = expiredTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column job_post.post_time
     *
     * @return the value of job_post.post_time
     *
     * @mbggenerated
     */
    public Date getPostTime() {
        return postTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column job_post.post_time
     *
     * @param postTime the value for job_post.post_time
     *
     * @mbggenerated
     */
    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column job_post.work_place
     *
     * @return the value of job_post.work_place
     *
     * @mbggenerated
     */
    public String getWorkPlace() {
        return workPlace;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column job_post.work_place
     *
     * @param workPlace the value for job_post.work_place
     *
     * @mbggenerated
     */
    public void setWorkPlace(String workPlace) {
        this.workPlace = workPlace == null ? null : workPlace.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column job_post.wage
     *
     * @return the value of job_post.wage
     *
     * @mbggenerated
     */
    public BigDecimal getWage() {
        return wage;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column job_post.wage
     *
     * @param wage the value for job_post.wage
     *
     * @mbggenerated
     */
    public void setWage(BigDecimal wage) {
        this.wage = wage;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column job_post.time_to_pay
     *
     * @return the value of job_post.time_to_pay
     *
     * @mbggenerated
     */
    public String getTimeToPay() {
        return timeToPay;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column job_post.time_to_pay
     *
     * @param timeToPay the value for job_post.time_to_pay
     *
     * @mbggenerated
     */
    public void setTimeToPay(String timeToPay) {
        this.timeToPay = timeToPay == null ? null : timeToPay.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column job_post.job_description
     *
     * @return the value of job_post.job_description
     *
     * @mbggenerated
     */
    public String getJobDescription() {
        return jobDescription;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column job_post.job_description
     *
     * @param jobDescription the value for job_post.job_description
     *
     * @mbggenerated
     */
    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription == null ? null : jobDescription.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column job_post.contact
     *
     * @return the value of job_post.contact
     *
     * @mbggenerated
     */
    public String getContact() {
        return contact;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column job_post.contact
     *
     * @param contact the value for job_post.contact
     *
     * @mbggenerated
     */
    public void setContact(String contact) {
        this.contact = contact == null ? null : contact.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column job_post.contact_phone
     *
     * @return the value of job_post.contact_phone
     *
     * @mbggenerated
     */
    public String getContactPhone() {
        return contactPhone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column job_post.contact_phone
     *
     * @param contactPhone the value for job_post.contact_phone
     *
     * @mbggenerated
     */
    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone == null ? null : contactPhone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column job_post.contact_email
     *
     * @return the value of job_post.contact_email
     *
     * @mbggenerated
     */
    public String getContactEmail() {
        return contactEmail;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column job_post.contact_email
     *
     * @param contactEmail the value for job_post.contact_email
     *
     * @mbggenerated
     */
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail == null ? null : contactEmail.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column job_post.contact_qq
     *
     * @return the value of job_post.contact_qq
     *
     * @mbggenerated
     */
    public String getContactQq() {
        return contactQq;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column job_post.contact_qq
     *
     * @param contactQq the value for job_post.contact_qq
     *
     * @mbggenerated
     */
    public void setContactQq(String contactQq) {
        this.contactQq = contactQq == null ? null : contactQq.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column job_post.job_detail
     *
     * @return the value of job_post.job_detail
     *
     * @mbggenerated
     */
    public String getJobDetail() {
        return jobDetail;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column job_post.job_detail
     *
     * @param jobDetail the value for job_post.job_detail
     *
     * @mbggenerated
     */
    public void setJobDetail(String jobDetail) {
        this.jobDetail = jobDetail == null ? null : jobDetail.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column job_post.likes
     *
     * @return the value of job_post.likes
     *
     * @mbggenerated
     */
    public Integer getLikes() {
        return likes;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column job_post.likes
     *
     * @param likes the value for job_post.likes
     *
     * @mbggenerated
     */
    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column job_post.dislikes
     *
     * @return the value of job_post.dislikes
     *
     * @mbggenerated
     */
    public Integer getDislikes() {
        return dislikes;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column job_post.dislikes
     *
     * @param dislikes the value for job_post.dislikes
     *
     * @mbggenerated
     */
    public void setDislikes(Integer dislikes) {
        this.dislikes = dislikes;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column job_post.job_post_category_id
     *
     * @return the value of job_post.job_post_category_id
     *
     * @mbggenerated
     */
    public Integer getJobPostCategoryId() {
        return jobPostCategoryId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column job_post.job_post_category_id
     *
     * @param jobPostCategoryId the value for job_post.job_post_category_id
     *
     * @mbggenerated
     */
    public void setJobPostCategoryId(Integer jobPostCategoryId) {
        this.jobPostCategoryId = jobPostCategoryId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column job_post.member_id
     *
     * @return the value of job_post.member_id
     *
     * @mbggenerated
     */
    public Integer getMemberId() {
        return memberId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column job_post.member_id
     *
     * @param memberId the value for job_post.member_id
     *
     * @mbggenerated
     */
    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column job_post.education_level
     *
     * @return the value of job_post.education_level
     *
     * @mbggenerated
     */
    public String getEducationLevel() {
        return educationLevel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column job_post.education_level
     *
     * @param educationLevel the value for job_post.education_level
     *
     * @mbggenerated
     */
    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel == null ? null : educationLevel.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column job_post.complaint
     *
     * @return the value of job_post.complaint
     *
     * @mbggenerated
     */
    public Integer getComplaint() {
        return complaint;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column job_post.complaint
     *
     * @param complaint the value for job_post.complaint
     *
     * @mbggenerated
     */
    public void setComplaint(Integer complaint) {
        this.complaint = complaint;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column job_post.work_time
     *
     * @return the value of job_post.work_time
     *
     * @mbggenerated
     */
    public String getWorkTime() {
        return workTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column job_post.work_time
     *
     * @param workTime the value for job_post.work_time
     *
     * @mbggenerated
     */
    public void setWorkTime(String workTime) {
        this.workTime = workTime == null ? null : workTime.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column job_post.salary_unit
     *
     * @return the value of job_post.salary_unit
     *
     * @mbggenerated
     */
    public String getSalaryUnit() {
        return salaryUnit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column job_post.salary_unit
     *
     * @param salaryUnit the value for job_post.salary_unit
     *
     * @mbggenerated
     */
    public void setSalaryUnit(String salaryUnit) {
        this.salaryUnit = salaryUnit == null ? null : salaryUnit.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column job_post.page_view
     *
     * @return the value of job_post.page_view
     *
     * @mbggenerated
     */
    public Integer getPageView() {
        return pageView;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getApplicationResumeIds() {
        return applicationResumeIds;
    }

    public void setApplicationResumeIds(String applicationResumeIds) {
        this.applicationResumeIds = applicationResumeIds;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column job_post.page_view
     *
     * @param pageView the value for job_post.page_view
     *
     * @mbggenerated
     */
    public void setPageView(Integer pageView) {
        this.pageView = pageView;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column job_post.verified
     *
     * @return the value of job_post.verified
     *
     * @mbggenerated
     */
    public String getVerified() {
        return verified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column job_post.verified
     *
     * @param verified the value for job_post.verified
     *
     * @mbggenerated
     */
    public void setVerified(String verified) {
        this.verified = verified == null ? null : verified.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column job_post.job_postcol
     *
     * @return the value of job_post.job_postcol
     *
     * @mbggenerated
     */
    public String getJobPostcol() {
        return jobPostcol;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column job_post.job_postcol
     *
     * @param jobPostcol the value for job_post.job_postcol
     *
     * @mbggenerated
     */
    public void setJobPostcol(String jobPostcol) {
        this.jobPostcol = jobPostcol == null ? null : jobPostcol.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column job_post.applicant_amount
     *
     * @return the value of job_post.applicant_amount
     *
     * @mbggenerated
     */
    public Integer getApplicantAmount() {
        return applicantAmount;
    }


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer(300);

        sb.append(id == null ? "-" : id);
        sb.append(introduce == null ? "-" : introduce);
        sb.append(applicationResumeIds == null ? "-" : applicationResumeIds);
        sb.append(title == null ? "-" : timeToPay);
        sb.append(expiredTime == null ? "-" : expiredTime);
        sb.append(expired == null ? "-" : expired);
        sb.append(postTime == null ? "-" : postTime);
        sb.append(workPlace == null ? "-" : workPlace);
        sb.append(workRegion == null ? "-" : workRegion);
        sb.append(wage == null ? "-" : wage);
        sb.append(timeToPay == null ? "-" : timeToPay);
        sb.append(deleted);
        sb.append(jobDescription == null ? "-" : jobDescription);
        sb.append(contact == null ? "-" : contact);
        sb.append(contactPhone == null ? "-" : contactPhone);
        sb.append(contactEmail == null ? "-" : contactEmail);
        sb.append(contactQq == null ? "-" : contactQq);
        sb.append(jobDetail == null ? "-" : jobDescription);
        sb.append(likes == null ? "-" : likes);
        sb.append(dislikes == null ? "-" : dislikes);
        sb.append(jobPostCategoryId == null ? "-" : jobPostCategoryId);
        sb.append(memberId == null ? "-" : memberId);
        sb.append(educationLevel == null ? "-" : educationLevel);
        sb.append(complaint == null ? "-" : complaint);
        sb.append(workTime == null ? "-" : workTime);
        sb.append(salaryUnit == null ? "-" : salaryUnit);
        sb.append(pageView == null ? "-" : pageView);
        sb.append(verified == null ? "-" : verified);
        sb.append(jobPostcol == null ? "-" : jobPostcol);
        sb.append(applicantAmount == null ? "-" : applicantAmount);
        sb.append(province == null ? "-" : province);
        sb.append(city == null ? "-" : city);
        sb.append(region == null ? "-" : region);
        sb.append(orderByDate);
        sb.append(orderByVisit);
        sb.append(filterExpiredPost);
        sb.append(cateId == null ? "-" : cateId);
        sb.append(pageNumber);
        sb.append(pageSize);

        return sb.toString();
    }

    public String getPv() {
        return pv;
    }

    public void setPv(String pv) {
        this.pv = pv;
    }

    public void setApplicantAmount(Integer applicantAmount) {
        this.applicantAmount = applicantAmount;
    }
}

package com.fh.taolijie.domain;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

public class JobPostModel extends Pageable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column job_post.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column job_post.introduce
     *
     * @mbggenerated
     */
    private String introduce;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column job_post.application_resume_ids
     *
     * @mbggenerated
     */
    private String applicationResumeIds;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column job_post.title
     *
     * @mbggenerated
     */
    @NotEmpty
    @Length(min = 1, max = 20)
    private String title;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column job_post.expired_time
     *
     * @mbggenerated
     */
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date expiredTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column job_post.post_time
     *
     * @mbggenerated
     */
    private Date postTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column job_post.work_place
     *
     * @mbggenerated
     */
    @NotEmpty
    @Length(min = 0, max = 128)
    private String workPlace;

    private String workRegion;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column job_post.wage
     *
     * @mbggenerated
     */
    private BigDecimal wage;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column job_post.time_to_pay
     *
     * @mbggenerated
     */
    private String timeToPay;

    private boolean deleted = false;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column job_post.job_description
     *
     * @mbggenerated
     */
    @NotNull
    @Length(min = 1, max = 500)
    private String jobDescription;

    /**
     * 联系人名
     *
     * @mbggenerated
     */
    @NotNull
    @Length(min = 1, max = 10)
    private String contact;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column job_post.contact_phone
     *
     * @mbggenerated
     */
    @NotNull
    @Length(min = 7, max = 20)
    private String contactPhone;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column job_post.contact_email
     *
     * @mbggenerated
     */
    private String contactEmail;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column job_post.contact_qq
     *
     * @mbggenerated
     */
    private String contactQq;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column job_post.job_detail
     *
     * @mbggenerated
     */
    @NotNull
    @Length(min = 1, max = 500)
    private String jobDetail;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column job_post.likes
     *
     * @mbggenerated
     */
    private Integer likes;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column job_post.dislikes
     *
     * @mbggenerated
     */
    private Integer dislikes;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column job_post.job_post_category_id
     *
     * @mbggenerated
     */
    @NotNull
    private Integer jobPostCategoryId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column job_post.member_id
     *
     * @mbggenerated
     */
    private Integer memberId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column job_post.education_level
     *
     * @mbggenerated
     */
    private String educationLevel;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column job_post.complaint
     *
     * @mbggenerated
     */
    private Integer complaint;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column job_post.work_time
     *
     * @mbggenerated
     */
    @NotNull
    @Length(min = 1, max = 64)
    private String workTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column job_post.salary_unit
     *
     * @mbggenerated
     */
    private String salaryUnit;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column job_post.page_view
     *
     * @mbggenerated
     */
    private Integer pageView;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column job_post.verified
     *
     * @mbggenerated
     */
    private String verified;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column job_post.job_postcol
     *
     * @mbggenerated
     */
    private String jobPostcol;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column job_post.applicant_amount
     *
     * @mbggenerated
     */
    private Integer applicantAmount;

    private MemberModel member;
    private JobPostCategoryModel category;


    /* 查询用 */
    private boolean orderByDate = true;
    private boolean orderByVisit = false;
    private boolean filterExpiredPost = true;

    public JobPostModel() {}

    public JobPostModel(int pageNumber, int pageSize) {
        super(pageNumber, pageSize);
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

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column job_post.applicant_amount
     *
     * @param applicantAmount the value for job_post.applicant_amount
     *
     * @mbggenerated
     */
    public void setApplicantAmount(Integer applicantAmount) {
        this.applicantAmount = applicantAmount;
    }
}
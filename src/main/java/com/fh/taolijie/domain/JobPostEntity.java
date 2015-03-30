package com.fh.taolijie.domain;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

/**
 * Created by wanghongfei on 15-3-4.
 */
@NamedQueries({
        @NamedQuery(name = "JobPostEntity.findAllOrderByPostTime",
                query = "SELECT j FROM JobPostEntity j ORDER BY j.postTime DESC "),
        @NamedQuery(name = "JobPostEntity.findAllOrderByExpiredTime",
                query = "SELECT j FROM JobPostEntity j ORDER BY j.expiredTime DESC "),
        @NamedQuery(name = "JobPostEntity.findByMember",
                query = "SELECT j FROM JobPostEntity j WHERE j.member = :member ORDER BY j.postTime DESC"),
        @NamedQuery(name = "jobPostEntity.findByCategory",
                query = "SELECT j FROM JobPostEntity j WHERE j.category = :category ORDER BY j.postTime DESC")
})

@Entity
@Table(name = "job_post")
public class JobPostEntity {
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
    private String pageView;

    private JobPostCategoryEntity category;
    private MemberEntity member;
    private Collection<ReviewEntity> reviewCollection;

    public JobPostEntity() {}
    public JobPostEntity(String title, Date expiredTime, Date postTime, String workPlace, Double wage, String timeToPay, String jobDescription, String contact, String contactPhone, String contactEmail, String contactQq, String jobDetail, String introduce, Integer likes, Integer dislikes, String educationLevel, JobPostCategoryEntity category, MemberEntity member) {
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

    @Id
    @GeneratedValue
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "page_view")
    public String getPageView() {
        return pageView;
    }

    public void setPageView(String pageView) {
        this.pageView = pageView;
    }

    @Basic
    @Column(name = "expired_time")
    public Date getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Date expiredTime) {
        this.expiredTime = expiredTime;
    }

    @Column(name = "complaint")
    public Integer getComplaint() {
        return complaint;
    }

    public void setComplaint(Integer complaint) {
        this.complaint = complaint;
    }

    @Column(name = "work_time")
    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    @Column(name = "salary_unit")
    public String getSalaryUnit() {
        return salaryUnit;
    }

    public void setSalaryUnit(String salaryUnit) {
        this.salaryUnit = salaryUnit;
    }

    @Basic
    @Column(name = "post_time")
    public Date getPostTime() {
        return postTime;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }

    @Basic
    @Column(name = "work_place")
    public String getWorkPlace() {
        return workPlace;
    }

    public void setWorkPlace(String workPlace) {
        this.workPlace = workPlace;
    }

    @Basic
    @Column(name = "wage")
    public Double getWage() {
        return wage;
    }

    public void setWage(Double wage) {
        this.wage = wage;
    }

    @Basic
    @Column(name = "time_to_pay")
    public String getTimeToPay() {
        return timeToPay;
    }

    public void setTimeToPay(String timeToPay) {
        this.timeToPay = timeToPay;
    }

    @Basic
    @Column(name = "job_description")
    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    @Basic
    @Column(name = "contact")
    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Basic
    @Column(name = "contact_phone")
    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    @Basic
    @Column(name = "contact_email")
    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    @Basic
    @Column(name = "contact_qq")
    public String getContactQq() {
        return contactQq;
    }

    public void setContactQq(String contactQq) {
        this.contactQq = contactQq;
    }

    @Basic
    @Column(name = "job_detail")
    public String getJobDetail() {
        return jobDetail;
    }

    public void setJobDetail(String jobDetail) {
        this.jobDetail = jobDetail;
    }

    @Basic
    @Column(name = "introduce")
    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    @Basic
    @Column(name = "likes")
    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    @Basic
    @Column(name = "dislikes")
    public Integer getDislikes() {
        return dislikes;
    }

    public void setDislikes(Integer dislikes) {
        this.dislikes = dislikes;
    }

    @Basic
    @Column(name = "education_level")
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

        JobPostEntity that = (JobPostEntity) o;

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

    @ManyToOne
    @JoinColumn(name = "job_post_category_id", referencedColumnName = "id")
    public JobPostCategoryEntity getCategory() {
        return category;
    }

    public void setCategory(JobPostCategoryEntity category) {
        this.category = category;
    }

    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    public MemberEntity getMember() {
        return member;
    }

    public void setMember(MemberEntity member) {
        this.member = member;
    }

    @OneToMany(mappedBy = "jobPost")
    public Collection<ReviewEntity> getReviewCollection() {
        return reviewCollection;
    }

    public void setReviewCollection(Collection<ReviewEntity> reviewCollection) {
        this.reviewCollection = reviewCollection;
    }
}

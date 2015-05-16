package com.fh.taolijie.domain;

import com.fh.taolijie.service.PageViewAware;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by wanghongfei on 15-3-4.
 */
@NamedQueries({
        @NamedQuery(name = "resumeEntity.findByMember",
            query = "SELECT r FROM ResumeEntity r WHERE r.member = :member"),

        // 查找所有简历，最新的简历在最前
        @NamedQuery(name = "ResumeEntity.findAll",
            query = "SELECT r FROM ResumeEntity r ORDER BY r.createdTime DESC"),

        @NamedQuery(name = "ResumeEntity.findByAuthority",
            query = "SELECT r FROM ResumeEntity r WHERE " +
                    "r.accessAuthority = :authority " +
                    "ORDER BY r.createdTime DESC"),

        @NamedQuery(name = "ResumeEntity.findByMemberAndAuthority",
            query = "SELECT r FROM ResumeEntity r WHERE " +
                    "r.member = :member AND " +
                    "r.accessAuthority = :authority"),

        @NamedQuery(name = "ResumeEntity.findByIds",
            query = "SELECT r FROM ResumeEntity r WHERE " +
                    "r.id IN :ids")
})
@Entity
@Table(name = "resume")
public class ResumeEntity implements PageViewAware {
    private Integer id;
    private String name;
    private String gender;
    private Integer age;
    private Integer height;
    private String photoPath;
    private String email;
    private String qq;
    private String experience;
    private String introduce;
    private MemberEntity member;

    private Date createdTime;
    private String accessAuthority;
    private Integer pageView;
    private String verified;

    private String phoneNumber;
    private String wechatAccount;

    private Integer photoId;

    // 求职意向
    private List<JobPostCategoryEntity> categoryList;

    public ResumeEntity() {}
    public ResumeEntity(String name, String gender, Integer age, Integer height, String photoPath, String email, String qq, String experience, String introduce, MemberEntity member) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.height = height;
        this.photoPath = photoPath;
        this.email = email;
        this.qq = qq;
        this.experience = experience;
        this.introduce = introduce;
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

    @Column(name = "photo_id")
    public Integer getPhotoId() {
        return photoId;
    }

    public void setPhotoId(Integer photoId) {
        this.photoId = photoId;
    }

    @Column(name = "phone_number")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Column(name = "wechat_account")
    public String getWechatAccount() {
        return wechatAccount;
    }

    public void setWechatAccount(String wechatAccount) {
        this.wechatAccount = wechatAccount;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "gender")
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Column(name = "verified")
    public String getVerified() {
        return verified;
    }

    public void setVerified(String verified) {
        this.verified = verified;
    }

    @Basic
    @Column(name = "age")
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Column(name = "access_authority")
    public String getAccessAuthority() {
        return accessAuthority;
    }

    public void setAccessAuthority(String accessAuthority) {
        this.accessAuthority = accessAuthority;
    }

    @Basic
    @Column(name = "height")
    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    @Column(name = "page_view")
    public Integer getPageView() {
        return pageView;
    }

    public void setPageView(Integer pageView) {
        this.pageView = pageView;
    }

    @Column(name = "created_time")
    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    @Basic
    @Column(name = "phone_path")
    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "qq")
    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    @Column(name = "experience", columnDefinition = "TEXT")
    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    @Column(name = "introduce", columnDefinition = "TEXT")
    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ResumeEntity that = (ResumeEntity) o;

        if (age != null ? !age.equals(that.age) : that.age != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (experience != null ? !experience.equals(that.experience) : that.experience != null) return false;
        if (gender != null ? !gender.equals(that.gender) : that.gender != null) return false;
        if (height != null ? !height.equals(that.height) : that.height != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (introduce != null ? !introduce.equals(that.introduce) : that.introduce != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (photoPath != null ? !photoPath.equals(that.photoPath) : that.photoPath != null) return false;
        if (qq != null ? !qq.equals(that.qq) : that.qq != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + (age != null ? age.hashCode() : 0);
        result = 31 * result + (height != null ? height.hashCode() : 0);
        result = 31 * result + (photoPath != null ? photoPath.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (qq != null ? qq.hashCode() : 0);
        result = 31 * result + (experience != null ? experience.hashCode() : 0);
        result = 31 * result + (introduce != null ? introduce.hashCode() : 0);
        return result;
    }

    @ManyToMany
    @JoinTable(
            name = "resume_job_post_category",
            joinColumns = @JoinColumn(name = "resume_id"),
            inverseJoinColumns = @JoinColumn(name = "job_post_category_id")

    )
    public List<JobPostCategoryEntity> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<JobPostCategoryEntity> categoryList) {
        this.categoryList = categoryList;
    }

    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    public MemberEntity getMember() {
        return member;
    }

    public void setMember(MemberEntity member) {
        this.member = member;
    }
}

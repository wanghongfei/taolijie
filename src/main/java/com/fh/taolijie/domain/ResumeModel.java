package com.fh.taolijie.domain;

import com.fh.taolijie.service.PageViewAware;

import java.util.Date;
import java.util.List;

/**
 * Created by wanghongfei on 15-3-4.
 */
public class ResumeModel implements PageViewAware {
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
    private MemberModel member;

    private Date createdTime;
    private String accessAuthority;
    private Integer pageView;
    private String verified;

    private String phoneNumber;
    private String wechatAccount;

    private Integer photoId;

    // 求职意向
    private List<JobPostCategoryModel> categoryList;

    public ResumeModel() {}
    public ResumeModel(String name, String gender, Integer age, Integer height, String photoPath, String email, String qq, String experience, String introduce, MemberModel member) {
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPhotoId() {
        return photoId;
    }

    public void setPhotoId(Integer photoId) {
        this.photoId = photoId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getWechatAccount() {
        return wechatAccount;
    }

    public void setWechatAccount(String wechatAccount) {
        this.wechatAccount = wechatAccount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getVerified() {
        return verified;
    }

    public void setVerified(String verified) {
        this.verified = verified;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAccessAuthority() {
        return accessAuthority;
    }

    public void setAccessAuthority(String accessAuthority) {
        this.accessAuthority = accessAuthority;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getPageView() {
        return pageView;
    }

    public void setPageView(Integer pageView) {
        this.pageView = pageView;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

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

        ResumeModel that = (ResumeModel) o;

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

    public List<JobPostCategoryModel> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<JobPostCategoryModel> categoryList) {
        this.categoryList = categoryList;
    }

    public MemberModel getMember() {
        return member;
    }

    public void setMember(MemberModel member) {
        this.member = member;
    }
}

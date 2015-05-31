package com.fh.taolijie.domain;

import java.util.Date;

/**
 * Created by wanghongfei on 15-3-4.
 */
public class MemberModel extends Pageable {
    private Integer id;
    private String username;
    private String password;

    private String email;
    private String name;
    private String studentId;
    private String gender;
    private String verified;
    private String profilePhotoPath;
    private String phone;
    private String qq;
    private Integer age;
    private String companyName;
    private String blockList;

    private Date createdTime;
    private Boolean valid;
    private String appliedJobIds;

    private Integer complaint;
    private String likedJobIds;
    private String likedShIds;
    private String favoriteJobIds;
    private String favoriteShIds;
    private String favoriteResumeIds;
    private String autoLoginIdentifier;
    private Date lastPostTime;

/*    private String likedIds;
    private String dislikedIds;*/

    private Integer profilePhotoId;

    private String resetPasswordToken;
    private Date lastTokenDate;

/*    private Collection<EducationExperienceModel> educationExperienceCollection;
    private Collection<JobPostModel> jobPostCollection;
    private Collection<MemberRoleModel> memberRoleCollection;
    private Collection<NewsModel> newsCollection;
    private Collection<NotificationModel> notificationCollection;
    private Collection<ResumeModel> resumeCollection;
    private Collection<ReviewModel> reviewCollection;
    private Collection<SecondHandPostModel> secondHandPostCollection;

    private List<ReviewModel> replyList;*/

    public MemberModel() {

    }

    public MemberModel(String username, String password, String email, String name, String studentId,
                       String gender, String verified, String profilePhotoPath, String phone, String qq,
                       Integer age, String companyName, String blockList, Boolean valid, Date createdTime) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
        this.studentId = studentId;
        this.gender = gender;
        this.verified = verified;
        this.profilePhotoPath = profilePhotoPath;
        this.phone = phone;
        this.qq = qq;
        this.age = age;
        this.companyName = companyName;
        this.blockList = blockList;

        this.valid = valid;
        this.createdTime = createdTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAutoLoginIdentifier() {
        return autoLoginIdentifier;
    }

    public void setAutoLoginIdentifier(String autoLoginIdentifier) {
        this.autoLoginIdentifier = autoLoginIdentifier;
    }

    public Date getLastTokenDate() {
        return lastTokenDate;
    }

    public void setLastTokenDate(Date lastTokenDate) {
        this.lastTokenDate = lastTokenDate;
    }

    public Date getLastPostTime() {
        return lastPostTime;
    }

    public void setLastPostTime(Date lastPostTime) {
        this.lastPostTime = lastPostTime;
    }

    public String getResetPasswordToken() {
        return resetPasswordToken;
    }

    public void setResetPasswordToken(String resetPasswordToken) {
        this.resetPasswordToken = resetPasswordToken;
    }

    public String getAppliedJobIds() {
        return appliedJobIds;
    }

    public void setAppliedJobIds(String appliedJobIds) {
        this.appliedJobIds = appliedJobIds;
    }

    public String getFavoriteResumeIds() {
        return favoriteResumeIds;
    }

    public void setFavoriteResumeIds(String favoriteResumeIds) {
        this.favoriteResumeIds = favoriteResumeIds;
    }

    public String getFavoriteShIds() {
        return favoriteShIds;
    }

    public void setFavoriteShIds(String favoriteShIds) {
        this.favoriteShIds = favoriteShIds;
    }

    public Integer getProfilePhotoId() {
        return profilePhotoId;
    }

    public void setProfilePhotoId(Integer profilePhotoId) {
        this.profilePhotoId = profilePhotoId;
    }

    public String getFavoriteJobIds() {
        return favoriteJobIds;
    }

    public void setFavoriteJobIds(String favoriteJobIds) {
        this.favoriteJobIds = favoriteJobIds;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLikedJobIds() {
        return likedJobIds;
    }

    public void setLikedJobIds(String likedJobIds) {
        this.likedJobIds = likedJobIds;
    }

    public String getLikedShIds() {
        return likedShIds;
    }

    public void setLikedShIds(String likedShIds) {
        this.likedShIds = likedShIds;
    }

    /* @Column(name = "disliked_ids", columnDefinition = "TEXT")
    public String getDislikedIds() {
        return dislikedIds;
    }

    public void setDislikedIds(String dislikedIds) {
        this.dislikedIds = dislikedIds;
    }

    @Column(name = "liked_ids", columnDefinition = "TEXT")
    public String getLikedIds() {
        return likedIds;
    }

    public void setLikedIds(String likedIds) {
        this.likedIds = likedIds;
    }*/

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getComplaint() {
        return complaint;
    }

    public void setComplaint(Integer complaint) {
        this.complaint = complaint;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
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

    public String getProfilePhotoPath() {
        return profilePhotoPath;
    }

    public void setProfilePhotoPath(String profilePhotoPath) {
        this.profilePhotoPath = profilePhotoPath;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getBlockList() {
        return blockList;
    }

    public void setBlockList(String blockList) {
        this.blockList = blockList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MemberModel that = (MemberModel) o;

        if (age != null ? !age.equals(that.age) : that.age != null) return false;
        if (blockList != null ? !blockList.equals(that.blockList) : that.blockList != null) return false;
        if (companyName != null ? !companyName.equals(that.companyName) : that.companyName != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (gender != null ? !gender.equals(that.gender) : that.gender != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (phone != null ? !phone.equals(that.phone) : that.phone != null) return false;
        if (profilePhotoPath != null ? !profilePhotoPath.equals(that.profilePhotoPath) : that.profilePhotoPath != null)
            return false;
        if (qq != null ? !qq.equals(that.qq) : that.qq != null) return false;
        if (studentId != null ? !studentId.equals(that.studentId) : that.studentId != null) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (verified != null ? !verified.equals(that.verified) : that.verified != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (studentId != null ? studentId.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + (verified != null ? verified.hashCode() : 0);
        result = 31 * result + (profilePhotoPath != null ? profilePhotoPath.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (qq != null ? qq.hashCode() : 0);
        result = 31 * result + (age != null ? age.hashCode() : 0);
        result = 31 * result + (companyName != null ? companyName.hashCode() : 0);
        result = 31 * result + (blockList != null ? blockList.hashCode() : 0);
        return result;
    }

}

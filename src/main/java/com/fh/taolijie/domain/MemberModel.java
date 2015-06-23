package com.fh.taolijie.domain;

import java.util.Date;
import java.util.List;

public class MemberModel extends Pageable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column member.id
     *
     * @mbggenerated
     */
    private Integer id;
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column member.student_id
     *
     * @mbggenerated
     */
    private String studentId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column member.block_list
     *
     * @mbggenerated
     */
    private String blockList;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column member.applied_job_ids
     *
     * @mbggenerated
     */
    private String appliedJobIds;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column member.liked_job_ids
     *
     * @mbggenerated
     */
    private String likedJobIds;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column member.liked_sh_ids
     *
     * @mbggenerated
     */
    private String likedShIds;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column member.favorite_job_ids
     *
     * @mbggenerated
     */
    private String favoriteJobIds;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column member.favorite_sh_ids
     *
     * @mbggenerated
     */
    private String favoriteShIds;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column member.favorite_resume_ids
     *
     * @mbggenerated
     */
    private String favoriteResumeIds;


    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column member.username
     *
     * @mbggenerated
     */
    private String username;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column member.password
     *
     * @mbggenerated
     */
    private String password;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column member.email
     *
     * @mbggenerated
     */
    private String email;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column member.name
     *
     * @mbggenerated
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column member.gender
     *
     * @mbggenerated
     */
    private String gender;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column member.verified
     *
     * @mbggenerated
     */
    private String verified;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column member.profile_photo_path
     *
     * @mbggenerated
     */
    private String profilePhotoPath;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column member.phone
     *
     * @mbggenerated
     */
    private String phone;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column member.qq
     *
     * @mbggenerated
     */
    private String qq;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column member.age
     *
     * @mbggenerated
     */
    private Integer age;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column member.company_name
     *
     * @mbggenerated
     */
    private String companyName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column member.created_time
     *
     * @mbggenerated
     */
    private Date createdTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column member.valid
     *
     * @mbggenerated
     */
    private Boolean valid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column member.complaint
     *
     * @mbggenerated
     */
    private Integer complaint;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column member.profile_photo_id
     *
     * @mbggenerated
     */
    private Integer profilePhotoId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column member.auto_login_identifier
     *
     * @mbggenerated
     */
    private String autoLoginIdentifier;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column member.last_post_time
     *
     * @mbggenerated
     */
    private Date lastPostTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column member.reset_password_token
     *
     * @mbggenerated
     */
    private String resetPasswordToken;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column member.last_token_date
     *
     * @mbggenerated
     */
    private Date lastTokenDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column member.school_organization
     *
     * @mbggenerated
     */
    private String schoolOrganization;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column member.creadits
     *
     * @mbggenerated
     */
    private Integer creadits;

    private String readSysNotificationIds;

    private List<RoleModel> roleList;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column member.id
     *
     * @return the value of member.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column member.id
     *
     * @param id the value for member.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    public String getReadSysNotificationIds() {
        return readSysNotificationIds;
    }

    public void setReadSysNotificationIds(String readSysNotificationIds) {
        this.readSysNotificationIds = readSysNotificationIds;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column member.username
     *
     * @return the value of member.username
     *
     * @mbggenerated
     */
    public String getUsername() {
        return username;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column member.username
     *
     * @param username the value for member.username
     *
     * @mbggenerated
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column member.password
     *
     * @return the value of member.password
     *
     * @mbggenerated
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column member.password
     *
     * @param password the value for member.password
     *
     * @mbggenerated
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column member.email
     *
     * @return the value of member.email
     *
     * @mbggenerated
     */
    public String getEmail() {
        return email;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column member.email
     *
     * @param email the value for member.email
     *
     * @mbggenerated
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column member.name
     *
     * @return the value of member.name
     *
     * @mbggenerated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column member.name
     *
     * @param name the value for member.name
     *
     * @mbggenerated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column member.gender
     *
     * @return the value of member.gender
     *
     * @mbggenerated
     */
    public String getGender() {
        return gender;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column member.gender
     *
     * @param gender the value for member.gender
     *
     * @mbggenerated
     */
    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column member.verified
     *
     * @return the value of member.verified
     *
     * @mbggenerated
     */
    public String getVerified() {
        return verified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column member.verified
     *
     * @param verified the value for member.verified
     *
     * @mbggenerated
     */
    public void setVerified(String verified) {
        this.verified = verified == null ? null : verified.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column member.profile_photo_path
     *
     * @return the value of member.profile_photo_path
     *
     * @mbggenerated
     */
    public String getProfilePhotoPath() {
        return profilePhotoPath;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column member.profile_photo_path
     *
     * @param profilePhotoPath the value for member.profile_photo_path
     *
     * @mbggenerated
     */
    public void setProfilePhotoPath(String profilePhotoPath) {
        this.profilePhotoPath = profilePhotoPath == null ? null : profilePhotoPath.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column member.phone
     *
     * @return the value of member.phone
     *
     * @mbggenerated
     */
    public String getPhone() {
        return phone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column member.phone
     *
     * @param phone the value for member.phone
     *
     * @mbggenerated
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column member.qq
     *
     * @return the value of member.qq
     *
     * @mbggenerated
     */
    public String getQq() {
        return qq;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column member.qq
     *
     * @param qq the value for member.qq
     *
     * @mbggenerated
     */
    public void setQq(String qq) {
        this.qq = qq == null ? null : qq.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column member.age
     *
     * @return the value of member.age
     *
     * @mbggenerated
     */
    public Integer getAge() {
        return age;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column member.age
     *
     * @param age the value for member.age
     *
     * @mbggenerated
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column member.company_name
     *
     * @return the value of member.company_name
     *
     * @mbggenerated
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column member.company_name
     *
     * @param companyName the value for member.company_name
     *
     * @mbggenerated
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column member.created_time
     *
     * @return the value of member.created_time
     *
     * @mbggenerated
     */
    public Date getCreatedTime() {
        return createdTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column member.created_time
     *
     * @param createdTime the value for member.created_time
     *
     * @mbggenerated
     */
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column member.valid
     *
     * @return the value of member.valid
     *
     * @mbggenerated
     */
    public Boolean getValid() {
        return valid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column member.valid
     *
     * @param valid the value for member.valid
     *
     * @mbggenerated
     */
    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column member.complaint
     *
     * @return the value of member.complaint
     *
     * @mbggenerated
     */
    public Integer getComplaint() {
        return complaint;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column member.complaint
     *
     * @param complaint the value for member.complaint
     *
     * @mbggenerated
     */
    public void setComplaint(Integer complaint) {
        this.complaint = complaint;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column member.profile_photo_id
     *
     * @return the value of member.profile_photo_id
     *
     * @mbggenerated
     */
    public Integer getProfilePhotoId() {
        return profilePhotoId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column member.profile_photo_id
     *
     * @param profilePhotoId the value for member.profile_photo_id
     *
     * @mbggenerated
     */
    public void setProfilePhotoId(Integer profilePhotoId) {
        this.profilePhotoId = profilePhotoId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column member.auto_login_identifier
     *
     * @return the value of member.auto_login_identifier
     *
     * @mbggenerated
     */
    public String getAutoLoginIdentifier() {
        return autoLoginIdentifier;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getBlockList() {
        return blockList;
    }

    public void setBlockList(String blockList) {
        this.blockList = blockList;
    }

    public String getAppliedJobIds() {
        return appliedJobIds;
    }

    public void setAppliedJobIds(String appliedJobIds) {
        this.appliedJobIds = appliedJobIds;
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

    public String getFavoriteJobIds() {
        return favoriteJobIds;
    }

    public void setFavoriteJobIds(String favoriteJobIds) {
        this.favoriteJobIds = favoriteJobIds;
    }

    public String getFavoriteShIds() {
        return favoriteShIds;
    }

    public void setFavoriteShIds(String favoriteShIds) {
        this.favoriteShIds = favoriteShIds;
    }

    public String getFavoriteResumeIds() {
        return favoriteResumeIds;
    }

    public void setFavoriteResumeIds(String favoriteResumeIds) {
        this.favoriteResumeIds = favoriteResumeIds;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column member.auto_login_identifier
     *
     * @param autoLoginIdentifier the value for member.auto_login_identifier
     *
     * @mbggenerated
     */
    public void setAutoLoginIdentifier(String autoLoginIdentifier) {
        this.autoLoginIdentifier = autoLoginIdentifier == null ? null : autoLoginIdentifier.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column member.last_post_time
     *
     * @return the value of member.last_post_time
     *
     * @mbggenerated
     */
    public Date getLastPostTime() {
        return lastPostTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column member.last_post_time
     *
     * @param lastPostTime the value for member.last_post_time
     *
     * @mbggenerated
     */
    public void setLastPostTime(Date lastPostTime) {
        this.lastPostTime = lastPostTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column member.reset_password_token
     *
     * @return the value of member.reset_password_token
     *
     * @mbggenerated
     */
    public String getResetPasswordToken() {
        return resetPasswordToken;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column member.reset_password_token
     *
     * @param resetPasswordToken the value for member.reset_password_token
     *
     * @mbggenerated
     */
    public void setResetPasswordToken(String resetPasswordToken) {
        this.resetPasswordToken = resetPasswordToken == null ? null : resetPasswordToken.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column member.last_token_date
     *
     * @return the value of member.last_token_date
     *
     * @mbggenerated
     */
    public Date getLastTokenDate() {
        return lastTokenDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column member.last_token_date
     *
     * @param lastTokenDate the value for member.last_token_date
     *
     * @mbggenerated
     */
    public void setLastTokenDate(Date lastTokenDate) {
        this.lastTokenDate = lastTokenDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column member.school_organization
     *
     * @return the value of member.school_organization
     *
     * @mbggenerated
     */
    public String getSchoolOrganization() {
        return schoolOrganization;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column member.school_organization
     *
     * @param schoolOrganization the value for member.school_organization
     *
     * @mbggenerated
     */
    public void setSchoolOrganization(String schoolOrganization) {
        this.schoolOrganization = schoolOrganization == null ? null : schoolOrganization.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column member.creadits
     *
     * @return the value of member.creadits
     *
     * @mbggenerated
     */
    public Integer getCreadits() {
        return creadits;
    }

    public List<RoleModel> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<RoleModel> roleList) {
        this.roleList = roleList;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column member.creadits
     *
     * @param creadits the value for member.creadits
     *
     * @mbggenerated
     */
    public void setCreadits(Integer creadits) {
        this.creadits = creadits;
    }
}
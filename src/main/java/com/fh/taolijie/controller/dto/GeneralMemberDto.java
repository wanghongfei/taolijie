package com.fh.taolijie.controller.dto;

import java.util.List;

/**
 * 此类为所有用户Dto类的父类，包含了所有用户都应该具有的field
 */
public class GeneralMemberDto {
    private String username;
    private String password;

    private String email;
    private String name;

    //private String studentId;
    private String gender;
    private String verified;
    private String profilePhotoPath;
    private String phone;
    private String qq;
    private Integer age;
    //private String companyName;
    //private String blockList;
    /**
     * 保存了{@link com.sun.javafx.scene.accessibility.Role}实体的主键.
     * <p> 这些{@code Role}实体都会被关联到{@code MemberEntity}中
     */
    private List<Integer> roleIdList;

    public List<Integer> getRoleIdList() {
        return roleIdList;
    }

    public void setRoleIdList(List<Integer> roleIdList) {
        this.roleIdList = roleIdList;
    }


    public String getVerified() {
        return verified;
    }

    public void setVerified(String verified) {
        this.verified = verified;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

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

}

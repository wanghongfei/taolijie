package com.fh.taolijie.controller.dto;

/**
 * 此类为所有用户Dto类的父类，包含了所有用户都应该具有的field
 */
public class GeneralMemberDto {
    protected String username;
    protected String password;

    protected String email;
    protected String name;

    //private String studentId;
    protected String gender;
    //private String verified;
    protected String profilePhotoPath;
    protected String phone;
    protected String qq;
    protected Integer age;
    //private String companyName;
    //private String blockList;

    protected String getUsername() {
        return username;
    }

    protected void setUsername(String username) {
        this.username = username;
    }

    protected String getPassword() {
        return password;
    }

    protected void setPassword(String password) {
        this.password = password;
    }

    protected String getEmail() {
        return email;
    }

    protected void setEmail(String email) {
        this.email = email;
    }

    protected String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }

    protected String getGender() {
        return gender;
    }

    protected void setGender(String gender) {
        this.gender = gender;
    }

    protected String getProfilePhotoPath() {
        return profilePhotoPath;
    }

    protected void setProfilePhotoPath(String profilePhotoPath) {
        this.profilePhotoPath = profilePhotoPath;
    }

    protected String getPhone() {
        return phone;
    }

    protected void setPhone(String phone) {
        this.phone = phone;
    }

    protected String getQq() {
        return qq;
    }

    protected void setQq(String qq) {
        this.qq = qq;
    }

    protected Integer getAge() {
        return age;
    }

    protected void setAge(Integer age) {
        this.age = age;
    }

}

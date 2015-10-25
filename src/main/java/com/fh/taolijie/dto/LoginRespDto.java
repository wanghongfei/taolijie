package com.fh.taolijie.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Created by whf on 10/3/15.
 */

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class LoginRespDto {
    private Integer id;

    private String appToken;

    private String sid;

    private String photoPath;

    private String nickname;

    private String gender;

    private String username;

    private String role;

    public LoginRespDto() {}

    public LoginRespDto(Integer id, String appToken) {
        this.id = id;
        this.appToken = appToken;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppToken() {
        return appToken;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setAppToken(String appToken) {
        this.appToken = appToken;
    }
}

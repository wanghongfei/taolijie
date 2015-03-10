package com.fh.taolijie.controller.dto;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by wynfrith on 15-3-10.
 */
public class LoginDto {
    @NotEmpty(message = "用户名不能为空")
    private String username;
    @NotEmpty(message = "密码不能为空")
    private String password;

    private Boolean rememberMe;

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
    public Boolean getRememberMe() {
        return rememberMe;
    }
    public void setRememberMe(Boolean rememberMe) {
        this.rememberMe = rememberMe;
    }
}

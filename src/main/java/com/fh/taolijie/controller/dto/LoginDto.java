package com.fh.taolijie.controller.dto;

import com.fh.taolijie.utils.Constants;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by wynfrith on 15-3-10.
 */
public class LoginDto {
    @NotEmpty(message = Constants.ErrorType.USERNAME_ILLEGAL)
    private String username;
    @NotEmpty(message = Constants.ErrorType.PASSWORD_ILLEGAL)
    private String password;


    private String rememberMe = "false";

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

    public String getRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(String rememberMe) {
        this.rememberMe = rememberMe;
    }
}

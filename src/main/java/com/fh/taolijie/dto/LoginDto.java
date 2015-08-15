package com.fh.taolijie.dto;

import com.fh.taolijie.constant.ErrorCode;
import com.fh.taolijie.utils.Constants;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by wynfrith on 15-6-11.
 */
public class LoginDto {

    @NotEmpty()
    private String username;
    @NotEmpty()
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

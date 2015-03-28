package com.fh.taolijie.controller.dto;

import com.fh.taolijie.utils.Constants;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by wynfrith on 15-3-28.
 */
public class ChangePasswordDto {
    private String oldPassword;
    private String newPassword;
    private String rePassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String password) {
        this.oldPassword = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getRePassword() {
        return rePassword;
    }

    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
    }
}

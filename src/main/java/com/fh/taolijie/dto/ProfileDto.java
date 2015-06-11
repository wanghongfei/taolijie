package com.fh.taolijie.dto;

import com.fh.taolijie.utils.Constants;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by wynfrith on 15-6-11.
 */
public class ProfileDto {

    @NotEmpty(message = Constants.ErrorType.PARAM_ILLEGAL)
    @Length(min=3,max=15, message = Constants.ErrorType.PARAM_ILLEGAL)
    String name;

    @NotEmpty(message = Constants.ErrorType.PARAM_ILLEGAL)
    @Length(min=3,max=15, message = Constants.ErrorType.PARAM_ILLEGAL)
    String gender;

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
}

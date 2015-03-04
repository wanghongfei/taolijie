package com.fh.taolijie.controller.dto;

import java.util.Date;

/**
 * Created by wanghongfei on 15-3-4.
 */
public class EducationExperienceDto {
    private Date admissionTime;
    private Integer lengthOfSchooling;
    private String major;

    private Integer schoolId;
    private Integer memberId;

    public Date getAdmissionTime() {
        return admissionTime;
    }

    public void setAdmissionTime(Date admissionTime) {
        this.admissionTime = admissionTime;
    }

    public Integer getLengthOfSchooling() {
        return lengthOfSchooling;
    }

    public void setLengthOfSchooling(Integer lengthOfSchooling) {
        this.lengthOfSchooling = lengthOfSchooling;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }
}

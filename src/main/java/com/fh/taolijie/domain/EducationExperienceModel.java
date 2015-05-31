package com.fh.taolijie.domain;

import java.util.Date;

/**
 * Created by wanghongfei on 15-3-4.
 */
public class EducationExperienceModel {
    private Integer id;
    private Date admissionTime;
    private Integer lengthOfSchooling;
    private String major;

    private Integer memberId;
    private MemberModel member;
    //private SchoolEntity school;
    private Integer academyId;
    private AcademyModel academy;

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getAcademyId() {
        return academyId;
    }

    public void setAcademyId(Integer academyId) {
        this.academyId = academyId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AcademyModel getAcademy() {
        return academy;
    }

    public void setAcademy(AcademyModel academy) {
        this.academy = academy;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EducationExperienceModel that = (EducationExperienceModel) o;

        if (admissionTime != null ? !admissionTime.equals(that.admissionTime) : that.admissionTime != null)
            return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (lengthOfSchooling != null ? !lengthOfSchooling.equals(that.lengthOfSchooling) : that.lengthOfSchooling != null)
            return false;
        if (major != null ? !major.equals(that.major) : that.major != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (admissionTime != null ? admissionTime.hashCode() : 0);
        result = 31 * result + (lengthOfSchooling != null ? lengthOfSchooling.hashCode() : 0);
        result = 31 * result + (major != null ? major.hashCode() : 0);
        return result;
    }

    public MemberModel getMember() {
        return member;
    }

    public void setMember(MemberModel member) {
        this.member = member;
    }

}

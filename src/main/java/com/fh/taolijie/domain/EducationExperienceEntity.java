package com.fh.taolijie.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by wanghongfei on 15-3-4.
 */
@Entity
@Table(name = "education_experience")
public class EducationExperienceEntity {
    private Integer id;
    private Date admissionTime;
    private Integer lengthOfSchooling;
    private String major;

    private MemberEntity member;
    private SchoolEntity school;

    @Id
    @GeneratedValue
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "admission_time")
    public Date getAdmissionTime() {
        return admissionTime;
    }

    public void setAdmissionTime(Date admissionTime) {
        this.admissionTime = admissionTime;
    }

    @Basic
    @Column(name = "length_of_schooling")
    public Integer getLengthOfSchooling() {
        return lengthOfSchooling;
    }

    public void setLengthOfSchooling(Integer lengthOfSchooling) {
        this.lengthOfSchooling = lengthOfSchooling;
    }

    @Basic
    @Column(name = "major")
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

        EducationExperienceEntity that = (EducationExperienceEntity) o;

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

    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    public MemberEntity getMember() {
        return member;
    }

    public void setMember(MemberEntity member) {
        this.member = member;
    }

    @ManyToOne
    @JoinColumn(name = "college_id", referencedColumnName = "id")
    public SchoolEntity getSchool() {
        return school;
    }

    public void setSchool(SchoolEntity school) {
        this.school = school;
    }
}

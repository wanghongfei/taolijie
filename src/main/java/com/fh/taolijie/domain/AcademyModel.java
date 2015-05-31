package com.fh.taolijie.domain;

/**
 * Created by wanghongfei on 15-3-4.
 */


public class AcademyModel {
    private Integer id;
    private String shortName;
    private String fullName;

    /**
     * 学院所在的学校
     */
    private SchoolModel school;
    private Integer schoolId;

    public AcademyModel() {}

    public AcademyModel(String shortName, String fullName, SchoolModel school) {
        this.shortName = shortName;
        this.fullName = fullName;
        this.school = school;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AcademyModel that = (AcademyModel) o;

        if (fullName != null ? !fullName.equals(that.fullName) : that.fullName != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (shortName != null ? !shortName.equals(that.shortName) : that.shortName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (shortName != null ? shortName.hashCode() : 0);
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        return result;
    }

    public SchoolModel getSchool() {
        return school;
    }

    public void setSchool(SchoolModel school) {
        this.school = school;
    }
}

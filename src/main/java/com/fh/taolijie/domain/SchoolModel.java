package com.fh.taolijie.domain;

import java.util.Collection;

/**
 * Created by wanghongfei on 15-3-4.
 */
public class SchoolModel {
    private Integer id;
    private String shortName;
    private String fullName;
    private String province;
    private String type;
    private Collection<AcademyModel> academyCollection;
    //private Collection<EducationExperienceEntity> educationExperienceCollection;

    public SchoolModel() {}
    public SchoolModel(String shortName, String fullName, String province, String type) {
        this.shortName = shortName;
        this.fullName = fullName;
        this.province = province;
        this.type = type;
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

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SchoolModel that = (SchoolModel) o;

        if (fullName != null ? !fullName.equals(that.fullName) : that.fullName != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (province != null ? !province.equals(that.province) : that.province != null) return false;
        if (shortName != null ? !shortName.equals(that.shortName) : that.shortName != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (shortName != null ? shortName.hashCode() : 0);
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        result = 31 * result + (province != null ? province.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    public Collection<AcademyModel> getAcademyCollection() {
        return academyCollection;
    }

    public void setAcademyCollection(Collection<AcademyModel> academyCollection) {
        this.academyCollection = academyCollection;
    }

    /*@OneToMany(mappedBy = "school")
    public Collection<EducationExperienceEntity> getEducationExperienceCollection() {
        return educationExperienceCollection;
    }

    public void setEducationExperienceCollection(Collection<EducationExperienceEntity> educationExperienceCollection) {
        this.educationExperienceCollection = educationExperienceCollection;
    }*/
}

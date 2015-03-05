package com.fh.taolijie.domain;

import javax.persistence.*;

/**
 * Created by wanghongfei on 15-3-4.
 */
@Entity
@Table(name = "academy")
public class AcademyEntity {
    private Integer id;
    private String shortName;
    private String fullName;

    /**
     * 学院所在的学校
     */
    private SchoolEntity school;

    public AcademyEntity() {}

    public AcademyEntity(String shortName, String fullName, SchoolEntity school) {
        this.shortName = shortName;
        this.fullName = fullName;
        this.school = school;
    }

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
    @Column(name = "short_name")
    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @Basic
    @Column(name = "full_name")
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AcademyEntity that = (AcademyEntity) o;

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

    @ManyToOne
    @JoinColumn(name = "college_id", referencedColumnName = "id")
    public SchoolEntity getSchool() {
        return school;
    }

    public void setSchool(SchoolEntity school) {
        this.school = school;
    }
}

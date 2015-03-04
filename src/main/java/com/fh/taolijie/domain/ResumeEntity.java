package com.fh.taolijie.domain;

import javax.persistence.*;

/**
 * Created by wanghongfei on 15-3-4.
 */
@Entity
@Table(name = "resume", schema = "", catalog = "taolijie")
public class ResumeEntity {
    private Integer id;
    private String name;
    private String gender;
    private Integer age;
    private Integer height;
    private String phonePath;
    private String email;
    private String qq;
    private String experience;
    private String introduce;
    private MemberEntity member;

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
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "gender")
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Basic
    @Column(name = "age")
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Basic
    @Column(name = "height")
    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    @Basic
    @Column(name = "phone_path")
    public String getPhonePath() {
        return phonePath;
    }

    public void setPhonePath(String phonePath) {
        this.phonePath = phonePath;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "qq")
    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    @Basic
    @Column(name = "experience")
    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    @Basic
    @Column(name = "introduce")
    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ResumeEntity that = (ResumeEntity) o;

        if (age != null ? !age.equals(that.age) : that.age != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (experience != null ? !experience.equals(that.experience) : that.experience != null) return false;
        if (gender != null ? !gender.equals(that.gender) : that.gender != null) return false;
        if (height != null ? !height.equals(that.height) : that.height != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (introduce != null ? !introduce.equals(that.introduce) : that.introduce != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (phonePath != null ? !phonePath.equals(that.phonePath) : that.phonePath != null) return false;
        if (qq != null ? !qq.equals(that.qq) : that.qq != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + (age != null ? age.hashCode() : 0);
        result = 31 * result + (height != null ? height.hashCode() : 0);
        result = 31 * result + (phonePath != null ? phonePath.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (qq != null ? qq.hashCode() : 0);
        result = 31 * result + (experience != null ? experience.hashCode() : 0);
        result = 31 * result + (introduce != null ? introduce.hashCode() : 0);
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
}

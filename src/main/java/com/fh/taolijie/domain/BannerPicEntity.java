package com.fh.taolijie.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by wanghongfei on 15-3-4.
 */
@Entity
@Table(name = "banner_pic")
public class BannerPicEntity {
    private Integer id;
    private String picturePath;
    private Date time;

    public BannerPicEntity() {}
    public BannerPicEntity(String picturePath, Date time) {
        this.picturePath = picturePath;
        this.time = time;
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
    @Column(name = "picture_path")
    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    @Basic
    @Column(name = "time")
    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BannerPicEntity that = (BannerPicEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (picturePath != null ? !picturePath.equals(that.picturePath) : that.picturePath != null) return false;
        if (time != null ? !time.equals(that.time) : that.time != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (picturePath != null ? picturePath.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        return result;
    }
}

package com.fh.taolijie.domain;

import java.util.Date;

/**
 * Created by wanghongfei on 15-3-4.
 */
public class BannerPicModel {
    private Integer id;
    private String picturePath;
    private Date time;

    private Integer pictureId;

    public BannerPicModel() {}
    public BannerPicModel(String picturePath, Date time) {
        this.picturePath = picturePath;
        this.time = time;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPictureId() {
        return pictureId;
    }

    public void setPictureId(Integer pictureId) {
        this.pictureId = pictureId;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

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

        BannerPicModel that = (BannerPicModel) o;

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

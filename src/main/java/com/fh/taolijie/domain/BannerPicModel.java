package com.fh.taolijie.domain;

import java.util.Date;

public class BannerPicModel extends Pageable {
    private Integer id;

    private Date createdTime;

    private Integer pictureId;

    private Integer orderIndex;

    private String url;
    private String memo;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrder(Integer order) {
        this.orderIndex = order;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date time) {
        this.createdTime = time;
    }

    public Integer getPictureId() {
        return pictureId;
    }

    public void setPictureId(Integer pictureId) {
        this.pictureId = pictureId;
    }
}
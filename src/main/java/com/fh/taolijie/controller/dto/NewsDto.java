package com.fh.taolijie.controller.dto;

import java.util.Date;

/**
 * Created by wanghongfei on 15-3-4.
 */
public class NewsDto {
    private String title;
    private String content;
    private String picturePath;
    private Date time;
    private String headPicturePath;

    private Integer memberId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getHeadPicturePath() {
        return headPicturePath;
    }

    public void setHeadPicturePath(String headPicturePath) {
        this.headPicturePath = headPicturePath;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }
}

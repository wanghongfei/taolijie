package com.fh.taolijie.domain;

import java.util.Date;

/**
 * Created by wanghongfei on 15-3-4.
 */
public class NewsModel {
    private Integer id;
    private String title;
    private String content;
    private String picturePath;
    private Date time;
    private String headPicturePath;
    private MemberModel member;

    public NewsModel() {}
    public NewsModel(String title, String content, String picturePath, Date time, String headPicturePath, MemberModel member) {
        this.title = title;
        this.content = content;
        this.picturePath = picturePath;
        this.time = time;
        this.headPicturePath = headPicturePath;
        this.member = member;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NewsModel that = (NewsModel) o;

        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        if (headPicturePath != null ? !headPicturePath.equals(that.headPicturePath) : that.headPicturePath != null)
            return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (picturePath != null ? !picturePath.equals(that.picturePath) : that.picturePath != null) return false;
        if (time != null ? !time.equals(that.time) : that.time != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (picturePath != null ? picturePath.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + (headPicturePath != null ? headPicturePath.hashCode() : 0);
        return result;
    }

    public MemberModel getMember() {
        return member;
    }

    public void setMember(MemberModel member) {
        this.member = member;
    }
}

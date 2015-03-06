package com.fh.taolijie.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by wanghongfei on 15-3-4.
 */
@NamedQueries( {
        @NamedQuery(name = "newsEntity.findAll",
            query = "SELECT news FROM NewsEntity news"),
        @NamedQuery(name = "newsEntity.findByDate",
            query = "SELECT news FROM NewsEntity news WHERE news.date > :date")
})

@Entity
@Table(name = "news")
public class NewsEntity {
    private Integer id;
    private String title;
    private String content;
    private String picturePath;
    private Date time;
    private String headPicturePath;
    private MemberEntity member;

    public NewsEntity() {}
    public NewsEntity(String title, String content, String picturePath, Date time, String headPicturePath, MemberEntity member) {
        this.title = title;
        this.content = content;
        this.picturePath = picturePath;
        this.time = time;
        this.headPicturePath = headPicturePath;
        this.member = member;
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
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    @Basic
    @Column(name = "head_picture_path")
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

        NewsEntity that = (NewsEntity) o;

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

    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    public MemberEntity getMember() {
        return member;
    }

    public void setMember(MemberEntity member) {
        this.member = member;
    }
}

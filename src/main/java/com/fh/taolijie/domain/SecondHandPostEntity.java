package com.fh.taolijie.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by wanghongfei on 15-3-4.
 */
@Entity
@Table(name = "second_hand_post")
public class SecondHandPostEntity {
    private Integer id;
    private String title;
    private Date expiredTime;
    private Date postTime;
    private String depreciationRate;
    private Double originalPrice;
    private Double sellPrice;
    private String picturePath;
    private String description;
    private Integer likes;
    private Integer dislikes;
    private MemberEntity member;
    private SecondHandPostCategoryEntity category;

    public SecondHandPostEntity() {}
    public SecondHandPostEntity(String title, Date expiredTime, Date postTime, String depreciationRate, Double originalPrice, Double sellPrice, String picturePath, String description, Integer likes, Integer dislikes, MemberEntity member, SecondHandPostCategoryEntity category) {
        this.title = title;
        this.expiredTime = expiredTime;
        this.postTime = postTime;
        this.depreciationRate = depreciationRate;
        this.originalPrice = originalPrice;
        this.sellPrice = sellPrice;
        this.picturePath = picturePath;
        this.description = description;
        this.likes = likes;
        this.dislikes = dislikes;
        this.member = member;
        this.category = category;
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
    @Column(name = "expired_time")
    public Date getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Date expiredTime) {
        this.expiredTime = expiredTime;
    }

    @Basic
    @Column(name = "post_time")
    public Date getPostTime() {
        return postTime;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }

    @Basic
    @Column(name = "depreciation_rate")
    public String getDepreciationRate() {
        return depreciationRate;
    }

    public void setDepreciationRate(String depreciationRate) {
        this.depreciationRate = depreciationRate;
    }

    @Basic
    @Column(name = "original_price")
    public Double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Double originalPrice) {
        this.originalPrice = originalPrice;
    }

    @Basic
    @Column(name = "sell_price")
    public Double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(Double sellPrice) {
        this.sellPrice = sellPrice;
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
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "likes")
    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    @Basic
    @Column(name = "dislikes")
    public Integer getDislikes() {
        return dislikes;
    }

    public void setDislikes(Integer dislikes) {
        this.dislikes = dislikes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SecondHandPostEntity that = (SecondHandPostEntity) o;

        if (depreciationRate != null ? !depreciationRate.equals(that.depreciationRate) : that.depreciationRate != null)
            return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (dislikes != null ? !dislikes.equals(that.dislikes) : that.dislikes != null) return false;
        if (expiredTime != null ? !expiredTime.equals(that.expiredTime) : that.expiredTime != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (likes != null ? !likes.equals(that.likes) : that.likes != null) return false;
        if (originalPrice != null ? !originalPrice.equals(that.originalPrice) : that.originalPrice != null)
            return false;
        if (picturePath != null ? !picturePath.equals(that.picturePath) : that.picturePath != null) return false;
        if (postTime != null ? !postTime.equals(that.postTime) : that.postTime != null) return false;
        if (sellPrice != null ? !sellPrice.equals(that.sellPrice) : that.sellPrice != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (expiredTime != null ? expiredTime.hashCode() : 0);
        result = 31 * result + (postTime != null ? postTime.hashCode() : 0);
        result = 31 * result + (depreciationRate != null ? depreciationRate.hashCode() : 0);
        result = 31 * result + (originalPrice != null ? originalPrice.hashCode() : 0);
        result = 31 * result + (sellPrice != null ? sellPrice.hashCode() : 0);
        result = 31 * result + (picturePath != null ? picturePath.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (likes != null ? likes.hashCode() : 0);
        result = 31 * result + (dislikes != null ? dislikes.hashCode() : 0);
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

    @ManyToOne
    @JoinColumn(name = "second_hand_post_category_id", referencedColumnName = "id")
    public SecondHandPostCategoryEntity getCategory() {
        return category;
    }

    public void setCategory(SecondHandPostCategoryEntity category) {
        this.category = category;
    }
}

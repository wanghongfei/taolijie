package com.fh.taolijie.domain;

import com.fh.taolijie.service.PageViewAware;

import java.util.Date;

/**
 * Created by wanghongfei on 15-3-4.
 */
public class SecondHandPostModel implements PageViewAware {
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


    private Integer complaint;
    private Integer pageView;
    private String verified;

    private MemberModel member;
    private SecondHandPostCategoryModel category;

    public SecondHandPostModel() {}
    public SecondHandPostModel(String title, Date expiredTime, Date postTime, String depreciationRate, Double originalPrice, Double sellPrice, String picturePath, String description, Integer likes, Integer dislikes, MemberModel member, SecondHandPostCategoryModel category) {
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

    public Date getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Date expiredTime) {
        this.expiredTime = expiredTime;
    }

    public String getVerified() {
        return verified;
    }

    public void setVerified(String verified) {
        this.verified = verified;
    }

    public Integer getPageView() {
        return pageView;
    }

    public void setPageView(Integer pageView) {
        this.pageView = pageView;
    }

    public Date getPostTime() {
        return postTime;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }

    public Integer getComplaint() {
        return complaint;
    }

    public void setComplaint(Integer complaint) {
        this.complaint = complaint;
    }

    public String getDepreciationRate() {
        return depreciationRate;
    }

    public void setDepreciationRate(String depreciationRate) {
        this.depreciationRate = depreciationRate;
    }

    public Double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(Double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

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

        SecondHandPostModel that = (SecondHandPostModel) o;

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

    public MemberModel getMember() {
        return member;
    }

    public void setMember(MemberModel member) {
        this.member = member;
    }

    public SecondHandPostCategoryModel getCategory() {
        return category;
    }

    public void setCategory(SecondHandPostCategoryModel category) {
        this.category = category;
    }
}

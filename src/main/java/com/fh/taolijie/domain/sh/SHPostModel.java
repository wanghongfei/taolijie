package com.fh.taolijie.domain.sh;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fh.taolijie.domain.acc.MemberModel;
import com.fh.taolijie.domain.Pageable;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class SHPostModel extends Pageable {
    private Integer id;
    private String picturePath;

    @NotNull
    @Length(min = 0, max = 500)
    private String description;


    @NotNull
    @Length(min = 0, max = 30)
    private String title;

    private boolean deleted = false;


    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date expiredTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date postTime;

    private String depreciationRate;

    /**
     * 售价
     */
    private BigDecimal originalPrice;

    @NotNull
    @Min(0) @Max(99999)
    private BigDecimal sellPrice;

    private Integer likes;

    private Integer dislikes;

    private Integer memberId;

    @NotNull
    private Integer secondHandPostCategoryId;

    private Integer complaint;

    private Integer pageView;

    /**
     * 是否下架
     */
    private Boolean expired;

    private String verified;

    @NotNull
    @Length(max = 40)
    private String tradePlace;

    @NotNull
    @Length(max = 20)
    private String contactName;

    //@NotNull
    //@Length(max = 20)
    private String contactQq;

    @NotNull
    @Length(max = 20)
    private String contactPhone;


    private MemberModel member;
    private SHPostCategoryModel category;


    // 以下参数仅查询用
    private Integer rangeQuery = 0; //是否根据价格范围查询. 0:否, 1:是
    private Integer minPrice;
    private Integer maxPrice;



    public SHPostCategoryModel getCategory() {
        return category;
    }

    public void setCategory(SHPostCategoryModel category) {
        this.category = category;
    }

    public MemberModel getMember() {
        return member;
    }

    public void setMember(MemberModel member) {
        this.member = member;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRangeQuery() {
        return rangeQuery;
    }

    public void setRangeQuery(Integer rangeQuery) {
        this.rangeQuery = rangeQuery;
    }

    public Integer getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Integer minPrice) {
        this.minPrice = minPrice;
    }

    public Integer getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Integer maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactQq() {
        return contactQq;
    }

    public void setContactQq(String contactQq) {
        this.contactQq = contactQq;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Date getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Date expiredTime) {
        this.expiredTime = expiredTime;
    }

    public Date getPostTime() {
        return postTime;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }

    public Boolean getExpired() {
        return expired;
    }

    public void setExpired(Boolean expired) {
        this.expired = expired;
    }

    public String getDepreciationRate() {
        return depreciationRate;
    }

    public void setDepreciationRate(String depreciationRate) {
        this.depreciationRate = depreciationRate == null ? null : depreciationRate.trim();
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
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

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    public BigDecimal getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(BigDecimal sellPrice) {
        this.sellPrice = sellPrice;
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

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getSecondHandPostCategoryId() {
        return secondHandPostCategoryId;
    }

    public void setSecondHandPostCategoryId(Integer secondHandPostCategoryId) {
        this.secondHandPostCategoryId = secondHandPostCategoryId;
    }

    public Integer getComplaint() {
        return complaint;
    }

    public void setComplaint(Integer complaint) {
        this.complaint = complaint;
    }

    public Integer getPageView() {
        return pageView;
    }

    public void setPageView(Integer pageView) {
        this.pageView = pageView;
    }

    public String getVerified() {
        return verified;
    }

    public void setVerified(String verified) {
        this.verified = verified == null ? null : verified.trim();
    }

    public String getTradePlace() {
        return tradePlace;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(300);
        sb.append(id == null ? "-" : id);
        sb.append(picturePath == null ? "-" : picturePath);
        sb.append(description == null ? "-" : description);
        sb.append(title == null ? "-" : title);
        sb.append(deleted);
        sb.append(expiredTime == null ? "-" : expiredTime);
        sb.append(postTime == null ? "-" : postTime);
        sb.append(depreciationRate == null ? "-" : depreciationRate);
        sb.append(originalPrice == null ? "-" : originalPrice);
        sb.append(sellPrice == null ? "-" : sellPrice);
        sb.append(likes == null ? "-" : likes);
        sb.append(dislikes == null ? "-" : dislikes);
        sb.append(memberId == null ? "-" : memberId);
        sb.append(secondHandPostCategoryId == null ? "-" : secondHandPostCategoryId);
        sb.append(complaint == null ? "-" : complaint);
        sb.append(pageView == null ? "-" : pageView);
        sb.append(expired == null ? "-" : expired);
        sb.append(verified == null ? "-" : verified);
        sb.append(tradePlace == null ? "-" : tradePlace);
        sb.append(contactName == null ? "-" : contactName);
        sb.append(contactQq == null ? "-" : contactQq);
        sb.append(contactPhone == null ? "-" : contactPhone);
        sb.append(rangeQuery == null ? "-" : rangeQuery);
        sb.append(minPrice == null ? "-" : minPrice);
        sb.append(maxPrice == null ? "-" : maxPrice);
        sb.append(pageNumber);
        sb.append(pageSize);

        return sb.toString();
    }

    public void setTradePlace(String tradePlace) {
        this.tradePlace = tradePlace == null ? null : tradePlace.trim();
    }
}

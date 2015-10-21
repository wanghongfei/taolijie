package com.fh.taolijie.domain.quest;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fh.taolijie.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class QuestModel extends Pageable {
    private Integer id;

    @NotNull
    private String title;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdTime;

    @NotNull
    private Integer questCateId;

    @NotNull
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @NotNull
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    private Integer limitTime;

    @NotNull
    private Integer totalAmt;

    private Integer leftAmt;

    @NotNull
    private BigDecimal award;

    private Integer memberId;

    private BigDecimal finalAward;

    @NotNull
    private String contactName;

    @NotNull
    private String contactPhone;

    @NotNull
    private String description;

    @NotNull
    private String questDetail;

    private String url;

    private String memo;

    private Boolean offline;

    /**
     * 商家在我的发布中看到的状态
     */
    private Integer empStatus;

    @NotNull
    private Integer provinceId;
    @NotNull
    private Integer cityId;
    @NotNull
    private Integer regionId;

    private Integer collegeId;
    private Integer schoolId;


    // ******** 仅作为请求参数使用 ********

    @JsonIgnore
    private Boolean awardRangeQuery = false;
    @JsonIgnore
    private BigDecimal minAward;
    @JsonIgnore
    private BigDecimal maxAward;


    @JsonIgnore
    private List<Integer> collegeIdList;
    @JsonIgnore
    private List<Integer> schoolIdList;

    // ******** 仅作为请求参数使用 ********


    // ******** 仅返回用 ********

    /**
     * 该任务对于当前用户的状态
     */
    private Integer status;

    // ******** 仅返回用 ********

    public QuestModel() {}

    public QuestModel(int pn, int ps) {
        super(pn, ps);
    }

    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column quest.id
     *
     * @param id the value for quest.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column quest.title
     *
     * @return the value of quest.title
     *
     * @mbggenerated
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column quest.title
     *
     * @param title the value for quest.title
     *
     * @mbggenerated
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column quest.created_time
     *
     * @return the value of quest.created_time
     *
     * @mbggenerated
     */
    public Date getCreatedTime() {
        return createdTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column quest.created_time
     *
     * @param createdTime the value for quest.created_time
     *
     * @mbggenerated
     */
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column quest.quest_cate_id
     *
     * @return the value of quest.quest_cate_id
     *
     * @mbggenerated
     */
    public Integer getQuestCateId() {
        return questCateId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column quest.quest_cate_id
     *
     * @param questCateId the value for quest.quest_cate_id
     *
     * @mbggenerated
     */
    public void setQuestCateId(Integer questCateId) {
        this.questCateId = questCateId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column quest.start_time
     *
     * @return the value of quest.start_time
     *
     * @mbggenerated
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column quest.start_time
     *
     * @param startTime the value for quest.start_time
     *
     * @mbggenerated
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column quest.end_time
     *
     * @return the value of quest.end_time
     *
     * @mbggenerated
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column quest.end_time
     *
     * @param endTime the value for quest.end_time
     *
     * @mbggenerated
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column quest.limit_time
     *
     * @return the value of quest.limit_time
     *
     * @mbggenerated
     */
    public Integer getLimitTime() {
        return limitTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column quest.limit_time
     *
     * @param limitTime the value for quest.limit_time
     *
     * @mbggenerated
     */
    public void setLimitTime(Integer limitTime) {
        this.limitTime = limitTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column quest.total_amt
     *
     * @return the value of quest.total_amt
     *
     * @mbggenerated
     */
    public Integer getTotalAmt() {
        return totalAmt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column quest.total_amt
     *
     * @param totalAmt the value for quest.total_amt
     *
     * @mbggenerated
     */
    public void setTotalAmt(Integer totalAmt) {
        this.totalAmt = totalAmt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column quest.left_amt
     *
     * @return the value of quest.left_amt
     *
     * @mbggenerated
     */
    public Integer getLeftAmt() {
        return leftAmt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column quest.left_amt
     *
     * @param leftAmt the value for quest.left_amt
     *
     * @mbggenerated
     */
    public void setLeftAmt(Integer leftAmt) {
        this.leftAmt = leftAmt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column quest.award
     *
     * @return the value of quest.award
     *
     * @mbggenerated
     */
    public BigDecimal getAward() {
        return award;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column quest.award
     *
     * @param award the value for quest.award
     *
     * @mbggenerated
     */
    public void setAward(BigDecimal award) {
        this.award = award;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column quest.contact_name
     *
     * @return the value of quest.contact_name
     *
     * @mbggenerated
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column quest.contact_name
     *
     * @param contactName the value for quest.contact_name
     *
     * @mbggenerated
     */
    public void setContactName(String contactName) {
        this.contactName = contactName == null ? null : contactName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column quest.contact_phone
     *
     * @return the value of quest.contact_phone
     *
     * @mbggenerated
     */
    public String getContactPhone() {
        return contactPhone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column quest.contact_phone
     *
     * @param contactPhone the value for quest.contact_phone
     *
     * @mbggenerated
     */
    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone == null ? null : contactPhone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column quest.description
     *
     * @return the value of quest.description
     *
     * @mbggenerated
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column quest.description
     *
     * @param description the value for quest.description
     *
     * @mbggenerated
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column quest.quest_detail
     *
     * @return the value of quest.quest_detail
     *
     * @mbggenerated
     */
    public String getQuestDetail() {
        return questDetail;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column quest.quest_detail
     *
     * @param questDetail the value for quest.quest_detail
     *
     * @mbggenerated
     */
    public void setQuestDetail(String questDetail) {
        this.questDetail = questDetail == null ? null : questDetail.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column quest.url
     *
     * @return the value of quest.url
     *
     * @mbggenerated
     */
    public String getUrl() {
        return url;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column quest.url
     *
     * @param url the value for quest.url
     *
     * @mbggenerated
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column quest.memo
     *
     * @return the value of quest.memo
     *
     * @mbggenerated
     */
    public String getMemo() {
        return memo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column quest.memo
     *
     * @param memo the value for quest.memo
     *
     * @mbggenerated
     */
    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column quest.offline
     *
     * @return the value of quest.offline
     *
     * @mbggenerated
     */
    public Boolean getOffline() {
        return offline;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    public Integer getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(Integer collegeId) {
        this.collegeId = collegeId;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public BigDecimal getFinalAward() {
        return finalAward;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public void setFinalAward(BigDecimal finalAward) {
        this.finalAward = finalAward;
    }

    public Boolean getAwardRangeQuery() {
        return awardRangeQuery;
    }

    public void setAwardRangeQuery(Boolean awardRangeQuery) {
        this.awardRangeQuery = awardRangeQuery;
    }

    public BigDecimal getMinAward() {
        return minAward;
    }

    public void setMinAward(BigDecimal minAward) {
        this.minAward = minAward;
    }

    public BigDecimal getMaxAward() {
        return maxAward;
    }

    public void setMaxAward(BigDecimal maxAward) {
        this.maxAward = maxAward;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<Integer> getCollegeIdList() {
        return collegeIdList;
    }

    public void setCollegeIdList(List<Integer> collegeIdList) {
        this.collegeIdList = collegeIdList;
    }

    public List<Integer> getSchoolIdList() {
        return schoolIdList;
    }

    public void setSchoolIdList(List<Integer> schoolIdList) {
        this.schoolIdList = schoolIdList;
    }

    public Integer getEmpStatus() {
        return empStatus;
    }

    public void setEmpStatus(Integer empStatus) {
        this.empStatus = empStatus;
    }

    public void setOffline(Boolean offline) {
        this.offline = offline;
    }
}
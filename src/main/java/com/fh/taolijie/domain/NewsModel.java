package com.fh.taolijie.domain;

import java.util.Date;

public class NewsModel extends Pageable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column news.id
     *
     * @mbggenerated
     */
    private Integer id;

    private MemberModel member;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column news.content
     *
     * @mbggenerated
     */
    private String content;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column news.picture_path
     *
     * @mbggenerated
     */
    private String picturePath;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column news.title
     *
     * @mbggenerated
     */
    private String title;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column news.time
     *
     * @mbggenerated
     */
    private Date time;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column news.member_id
     *
     * @mbggenerated
     */
    private Integer memberId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column news.head_picture_path
     *
     * @mbggenerated
     */
    private String headPicturePath;

    public NewsModel() {}
    public NewsModel(int number, int size) {
        super(number, size);
    }

    public MemberModel getMember() {
        return member;
    }

    public void setMember(MemberModel member) {
        this.member = member;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column news.id
     *
     * @return the value of news.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column news.id
     *
     * @param id the value for news.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column news.title
     *
     * @return the value of news.title
     *
     * @mbggenerated
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column news.title
     *
     * @param title the value for news.title
     *
     * @mbggenerated
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column news.time
     *
     * @return the value of news.time
     *
     * @mbggenerated
     */
    public Date getTime() {
        return time;
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

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column news.time
     *
     * @param time the value for news.time
     *
     * @mbggenerated
     */
    public void setTime(Date time) {
        this.time = time;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column news.member_id
     *
     * @return the value of news.member_id
     *
     * @mbggenerated
     */
    public Integer getMemberId() {
        return memberId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column news.member_id
     *
     * @param memberId the value for news.member_id
     *
     * @mbggenerated
     */
    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column news.head_picture_path
     *
     * @return the value of news.head_picture_path
     *
     * @mbggenerated
     */
    public String getHeadPicturePath() {
        return headPicturePath;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column news.head_picture_path
     *
     * @param headPicturePath the value for news.head_picture_path
     *
     * @mbggenerated
     */
    public void setHeadPicturePath(String headPicturePath) {
        this.headPicturePath = headPicturePath == null ? null : headPicturePath.trim();
    }
}
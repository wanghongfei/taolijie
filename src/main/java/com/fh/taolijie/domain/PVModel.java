package com.fh.taolijie.domain;

import java.util.Date;

public class PVModel {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pv.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pv.created_time
     *
     * @mbggenerated
     */
    private Date createdTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pv.data
     *
     * @mbggenerated
     */
    private String data;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pv.id
     *
     * @return the value of pv.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pv.id
     *
     * @param id the value for pv.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pv.created_time
     *
     * @return the value of pv.created_time
     *
     * @mbggenerated
     */
    public Date getCreatedTime() {
        return createdTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pv.created_time
     *
     * @param createdTime the value for pv.created_time
     *
     * @mbggenerated
     */
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pv.data
     *
     * @return the value of pv.data
     *
     * @mbggenerated
     */
    public String getData() {
        return data;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pv.data
     *
     * @param data the value for pv.data
     *
     * @mbggenerated
     */
    public void setData(String data) {
        this.data = data == null ? null : data.trim();
    }
}
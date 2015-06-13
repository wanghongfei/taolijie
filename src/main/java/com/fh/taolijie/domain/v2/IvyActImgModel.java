package com.fh.taolijie.domain.v2;

public class IvyActImgModel {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ivy_activity_image_resource.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ivy_activity_image_resource.file_name
     *
     * @mbggenerated
     */
    private String fileName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ivy_activity_image_resource.extension
     *
     * @mbggenerated
     */
    private String extension;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ivy_activity_image_resource.ivy_id
     *
     * @mbggenerated
     */
    private Integer ivyId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ivy_activity_image_resource.activity_id
     *
     * @mbggenerated
     */
    private Integer activityId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ivy_activity_image_resource.type
     *
     * @mbggenerated
     */
    private String type;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ivy_activity_image_resource.bin_data
     *
     * @mbggenerated
     */
    private byte[] binData;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ivy_activity_image_resource.id
     *
     * @return the value of ivy_activity_image_resource.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ivy_activity_image_resource.id
     *
     * @param id the value for ivy_activity_image_resource.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ivy_activity_image_resource.file_name
     *
     * @return the value of ivy_activity_image_resource.file_name
     *
     * @mbggenerated
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ivy_activity_image_resource.file_name
     *
     * @param fileName the value for ivy_activity_image_resource.file_name
     *
     * @mbggenerated
     */
    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ivy_activity_image_resource.extension
     *
     * @return the value of ivy_activity_image_resource.extension
     *
     * @mbggenerated
     */
    public String getExtension() {
        return extension;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ivy_activity_image_resource.extension
     *
     * @param extension the value for ivy_activity_image_resource.extension
     *
     * @mbggenerated
     */
    public void setExtension(String extension) {
        this.extension = extension == null ? null : extension.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ivy_activity_image_resource.ivy_id
     *
     * @return the value of ivy_activity_image_resource.ivy_id
     *
     * @mbggenerated
     */
    public Integer getIvyId() {
        return ivyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ivy_activity_image_resource.ivy_id
     *
     * @param ivyId the value for ivy_activity_image_resource.ivy_id
     *
     * @mbggenerated
     */
    public void setIvyId(Integer ivyId) {
        this.ivyId = ivyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ivy_activity_image_resource.activity_id
     *
     * @return the value of ivy_activity_image_resource.activity_id
     *
     * @mbggenerated
     */
    public Integer getActivityId() {
        return activityId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ivy_activity_image_resource.activity_id
     *
     * @param activityId the value for ivy_activity_image_resource.activity_id
     *
     * @mbggenerated
     */
    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ivy_activity_image_resource.type
     *
     * @return the value of ivy_activity_image_resource.type
     *
     * @mbggenerated
     */
    public String getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ivy_activity_image_resource.type
     *
     * @param type the value for ivy_activity_image_resource.type
     *
     * @mbggenerated
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ivy_activity_image_resource.bin_data
     *
     * @return the value of ivy_activity_image_resource.bin_data
     *
     * @mbggenerated
     */
    public byte[] getBinData() {
        return binData;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ivy_activity_image_resource.bin_data
     *
     * @param binData the value for ivy_activity_image_resource.bin_data
     *
     * @mbggenerated
     */
    public void setBinData(byte[] binData) {
        this.binData = binData;
    }
}
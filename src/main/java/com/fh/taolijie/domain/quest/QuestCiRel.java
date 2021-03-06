package com.fh.taolijie.domain.quest;

public class QuestCiRel {
    private Integer id;

    private Integer questId;
    private Integer cityId;
    private String cityName;

    public QuestCiRel() {}

    public QuestCiRel(Integer questId, Integer cityId) {
        this.questId = questId;
        this.cityId = cityId;
    }

    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column quest_city_rel.id
     *
     * @param id the value for quest_city_rel.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column quest_city_rel.quest_id
     *
     * @return the value of quest_city_rel.quest_id
     *
     * @mbggenerated
     */
    public Integer getQuestId() {
        return questId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column quest_city_rel.quest_id
     *
     * @param questId the value for quest_city_rel.quest_id
     *
     * @mbggenerated
     */
    public void setQuestId(Integer questId) {
        this.questId = questId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column quest_city_rel.city_id
     *
     * @return the value of quest_city_rel.city_id
     *
     * @mbggenerated
     */
    public Integer getCityId() {
        return cityId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column quest_city_rel.city_id
     *
     * @param cityId the value for quest_city_rel.city_id
     *
     * @mbggenerated
     */
    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }
}
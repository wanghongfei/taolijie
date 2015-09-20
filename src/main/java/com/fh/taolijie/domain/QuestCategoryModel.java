package com.fh.taolijie.domain;

public class QuestCategoryModel extends Pageable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column quest_cate.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column quest_cate.name
     *
     * @mbggenerated
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column quest_cate.memo
     *
     * @mbggenerated
     */
    private String memo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column quest_cate.theme_color
     *
     * @mbggenerated
     */
    private String themeColor;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column quest_cate.level
     *
     * @mbggenerated
     */
    private Integer level;

    public QuestCategoryModel() {}

    public QuestCategoryModel(int pn, int ps) {
        super(pn, ps);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column quest_cate.id
     *
     * @return the value of quest_cate.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column quest_cate.id
     *
     * @param id the value for quest_cate.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column quest_cate.name
     *
     * @return the value of quest_cate.name
     *
     * @mbggenerated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column quest_cate.name
     *
     * @param name the value for quest_cate.name
     *
     * @mbggenerated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column quest_cate.memo
     *
     * @return the value of quest_cate.memo
     *
     * @mbggenerated
     */
    public String getMemo() {
        return memo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column quest_cate.memo
     *
     * @param memo the value for quest_cate.memo
     *
     * @mbggenerated
     */
    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column quest_cate.theme_color
     *
     * @return the value of quest_cate.theme_color
     *
     * @mbggenerated
     */
    public String getThemeColor() {
        return themeColor;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column quest_cate.theme_color
     *
     * @param themeColor the value for quest_cate.theme_color
     *
     * @mbggenerated
     */
    public void setThemeColor(String themeColor) {
        this.themeColor = themeColor == null ? null : themeColor.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column quest_cate.level
     *
     * @return the value of quest_cate.level
     *
     * @mbggenerated
     */
    public Integer getLevel() {
        return level;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column quest_cate.level
     *
     * @param level the value for quest_cate.level
     *
     * @mbggenerated
     */
    public void setLevel(Integer level) {
        this.level = level;
    }
}
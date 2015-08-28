package com.fh.taolijie.domain.middle;

/**
 * Created by whf on 8/28/15.
 */
public class ResumeWithIntend {
    private Integer resumeId;
    private Integer cateId;
    private String cateName;
    private String cateMemo;

    private String cateLevel;

    private String cateThemeColor;

    public Integer getResumeId() {
        return resumeId;
    }

    public void setResumeId(Integer resumeId) {
        this.resumeId = resumeId;
    }

    public Integer getCateId() {
        return cateId;
    }

    public void setCateId(Integer cateId) {
        this.cateId = cateId;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public String getCateMemo() {
        return cateMemo;
    }

    public void setCateMemo(String cateMemo) {
        this.cateMemo = cateMemo;
    }

    public String getCateLevel() {
        return cateLevel;
    }

    public void setCateLevel(String cateLevel) {
        this.cateLevel = cateLevel;
    }

    public String getCateThemeColor() {
        return cateThemeColor;
    }

    public void setCateThemeColor(String cateThemeColor) {
        this.cateThemeColor = cateThemeColor;
    }
}

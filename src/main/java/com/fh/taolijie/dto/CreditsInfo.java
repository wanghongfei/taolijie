package com.fh.taolijie.dto;

/**
 * Created by whf on 8/8/15.
 */
public class CreditsInfo {
    private Integer credits;
    private String level;

    public CreditsInfo() {}

    public CreditsInfo(Integer credits, String level) {
        this.credits = credits;
        this.level = level;
    }

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}

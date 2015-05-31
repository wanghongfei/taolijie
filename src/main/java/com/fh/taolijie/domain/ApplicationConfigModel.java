package com.fh.taolijie.domain;

/**
 * Created by wanghongfei on 15-3-4.
 */
public class ApplicationConfigModel {
    private Integer id;
    private String applicationConfig;

    public ApplicationConfigModel(String applicationConfig) {
        this.applicationConfig = applicationConfig;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getApplicationConfig() {
        return applicationConfig;
    }

    public void setApplicationConfig(String applicationConfig) {
        this.applicationConfig = applicationConfig;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ApplicationConfigModel that = (ApplicationConfigModel) o;

        if (applicationConfig != null ? !applicationConfig.equals(that.applicationConfig) : that.applicationConfig != null)
            return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (applicationConfig != null ? applicationConfig.hashCode() : 0);
        return result;
    }
}

package com.fh.taolijie.domain;

import javax.persistence.*;

/**
 * Created by wanghongfei on 15-3-4.
 */
@Entity
@Table(name = "application_config")
public class ApplicationConfigEntity {
    private Integer id;
    private String applicationConfig;

    public ApplicationConfigEntity(String applicationConfig) {
        this.applicationConfig = applicationConfig;
    }

    @Id
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "application_config")
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

        ApplicationConfigEntity that = (ApplicationConfigEntity) o;

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

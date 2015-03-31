package com.fh.taolijie.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by wanghongfei on 15-3-31.
 */
@Entity
@Table(name = "bulletin")
public class BulletinEntity {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "created_time")
    private Date createdTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

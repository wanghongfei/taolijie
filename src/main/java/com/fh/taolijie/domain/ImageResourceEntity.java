package com.fh.taolijie.domain;

import javax.persistence.*;

/**
 * Created by wanghongfei on 15-3-31.
 */
@Entity
@Table(name = "image_resource")
public class ImageResourceEntity {
    @Id @GeneratedValue
    private Integer id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "extension")
    private String extension;

    @Lob
    @Column(name = "bin_data")
    private byte[] binData;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public byte[] getBinData() {
        return binData;
    }

    public void setBinData(byte[] binData) {
        this.binData = binData;
    }
}

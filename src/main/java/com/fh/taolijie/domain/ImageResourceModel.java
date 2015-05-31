package com.fh.taolijie.domain;

/**
 * Created by wanghongfei on 15-3-31.
 */
public class ImageResourceModel {
    private Integer id;

    private String fileName;

    private String extension;

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

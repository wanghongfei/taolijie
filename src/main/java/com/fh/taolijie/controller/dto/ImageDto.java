package com.fh.taolijie.controller.dto;

/**
 * Created by wanghongfei on 15-3-31.
 */
public class ImageDto {
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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public byte[] getBinData() {
        return binData;
    }

    public void setBinData(byte[] binData) {
        this.binData = binData;
    }
}

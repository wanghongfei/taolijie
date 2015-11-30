package com.fh.taolijie.dto;

/**
 * 认证状态
 * Created by whf on 11/30/15.
 */
public class CertiInfoDto {
    private String idCerti;
    private String empCerti;
    private String stuCerti;

    public CertiInfoDto() {}

    public CertiInfoDto(String idCerti, String empCerti, String stuCerti) {
        this.idCerti = idCerti;
        this.empCerti = empCerti;
        this.stuCerti = stuCerti;
    }

    public String getIdCerti() {
        return idCerti;
    }

    public void setIdCerti(String idCerti) {
        this.idCerti = idCerti;
    }

    public String getEmpCerti() {
        return empCerti;
    }

    public void setEmpCerti(String empCerti) {
        this.empCerti = empCerti;
    }

    public String getStuCerti() {
        return stuCerti;
    }

    public void setStuCerti(String stuCerti) {
        this.stuCerti = stuCerti;
    }
}

package com.fh.taolijie.dto;

import com.fh.taolijie.domain.IdCertiModel;
import com.fh.taolijie.domain.certi.EmpCertiModel;
import com.fh.taolijie.domain.certi.StuCertiModel;

/**
 * 认证状态
 * Created by whf on 11/30/15.
 */
public class CertiInfoDto {
    private String idCerti;
    private IdCertiModel idInfo;

    private String empCerti;
    private EmpCertiModel empInfo;

    private String stuCerti;
    private StuCertiModel stuInfo;

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

    public IdCertiModel getIdInfo() {
        return idInfo;
    }

    public void setIdInfo(IdCertiModel idInfo) {
        this.idInfo = idInfo;
    }

    public EmpCertiModel getEmpInfo() {
        return empInfo;
    }

    public void setEmpInfo(EmpCertiModel empInfo) {
        this.empInfo = empInfo;
    }

    public StuCertiModel getStuInfo() {
        return stuInfo;
    }

    public void setStuInfo(StuCertiModel stuInfo) {
        this.stuInfo = stuInfo;
    }

    public void setStuCerti(String stuCerti) {
        this.stuCerti = stuCerti;
    }
}

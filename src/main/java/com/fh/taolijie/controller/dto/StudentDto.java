package com.fh.taolijie.controller.dto;

/**
 * 与学生相关的数据封装在此类中
 * Created by wanghongfei on 15-3-4.
 */
public class StudentDto extends GeneralMemberDto {
    protected String studentId;
    protected Integer schoolId;
    protected Integer academyId;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public Integer getAcademyId() {
        return academyId;
    }

    public void setAcademyId(Integer academyId) {
        this.academyId = academyId;
    }
}

package com.fh.taolijie.controller.dto;

import java.util.List;

/**
 * 与学生相关的数据封装在此类中
 * Created by wanghongfei on 15-3-4.
 */
public class StudentDto extends GeneralMemberDto {
    protected String studentId;
    protected String blockList;

    public String getBlockList() {
        return blockList;
    }

    public void setBlockList(String blockList) {
        this.blockList = blockList;
    }

    //protected List<Integer> schoolIdList;
    protected List<Integer> academyIdList;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

   /* public List<Integer> getSchoolIdList() {
        return schoolIdList;
    }

    public void setSchoolIdList(List<Integer> schoolIdList) {
        this.schoolIdList = schoolIdList;
    }*/

    public List<Integer> getAcademyIdList() {
        return academyIdList;
    }

    public void setAcademyIdList(List<Integer> academyIdList) {
        this.academyIdList = academyIdList;
    }
}

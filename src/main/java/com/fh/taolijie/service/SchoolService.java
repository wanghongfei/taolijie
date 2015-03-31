package com.fh.taolijie.service;

import com.fh.taolijie.controller.dto.AcademyDto;
import com.fh.taolijie.controller.dto.SchoolDto;
import com.fh.taolijie.exception.checked.CascadeDeleteException;
import com.fh.taolijie.utils.ObjWrapper;

import java.util.List;

/**
 * 规定与school(学校), academy(学院)有关的CRUD操作.
 * Created by wanghongfei on 15-3-4.
 */
public interface SchoolService {
    /**
     * 得到所有的学校信息
     * @return
     */
    List<SchoolDto> getSchoolList(int firstResult, int capacity, ObjWrapper wrapper);

    /**
     * 得到某一省份的所有学校
     * @param province 省名
     * @return
     */
    List<SchoolDto> getSchoolListByProvince(String province, int firstResult, int capacity, ObjWrapper wrapper);

    /**
     * 根据id查找学校
     * @param schoolId {@link com.fh.taolijie.domain.SchoolEntity}实体的主键值.
     * @return 查找成功返回找到的对象，查找失败返回{@code null}.
     */
    SchoolDto findSchool(Integer schoolId);

    /**
     * 添加一所新学校
     * @param schoolDto 封装了学校信息的dto对象
     * @return 添加成功返回true, 失败返回false
     */
    boolean addSchool(SchoolDto schoolDto);

    void addAcademy(Integer schoolId, AcademyDto dto);

    /**
     * 更新学校信息
     * @param schoolId {@link com.fh.taolijie.domain.SchoolEntity}实体的主键值.
     * @param schoolDto 封装了学校信息的dto对象
     * @return 更新成功返回true, 失败返回false
     */
    boolean updateSchoolInfo(Integer schoolId, SchoolDto schoolDto);

    /**
     * 删除一所学校. 只有当对应的学院为空时才允许删除
     * @param schoolId {@link com.fh.taolijie.domain.SchoolEntity}实体的主键值.
     * @return 删除成功返回true, 失败返回false
     */
    boolean deleteSchool(Integer schoolId) throws CascadeDeleteException;



    
    /**
     * 查询指定学校所有的学院信息
     * @return
     */
    List<AcademyDto> getAcademyList(Integer schoolId);
    /**
     * 根据id查找学院
     * @param academyId {@link com.fh.taolijie.domain.AcademyEntity}实体的主键值
     * @return 查找成功返回找到的对象，查找失败返回{@code null}.
     */
    AcademyDto findAcademy(Integer academyId);

    /**
     * 更新学院信息
     * @param academyId {@link com.fh.taolijie.domain.AcademyEntity}实体的主键值
     * @param academyDto 封装了学院信息的dto对象
     * @return 更新成功返回true, 失败返回false
     */
    boolean updateAcademy(Integer academyId, AcademyDto academyDto);

    /**
     * 删除一个学院
     * @param academyId {@link com.fh.taolijie.domain.AcademyEntity}实体的主键值
     * @return 删除成功返回true, 失败返回false
     */
    boolean deleteAcademy(Integer academyId);
}

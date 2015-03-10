package com.fh.taolijie.service.impl;

import com.fh.taolijie.controller.dto.AcademyDto;
import com.fh.taolijie.controller.dto.SchoolDto;
import com.fh.taolijie.domain.AcademyEntity;
import com.fh.taolijie.domain.SchoolEntity;
import com.fh.taolijie.exception.checked.CascadeDeleteException;
import com.fh.taolijie.service.SchoolService;
import com.fh.taolijie.service.repository.AcademyRepo;
import com.fh.taolijie.service.repository.SchoolRepo;
import com.fh.taolijie.utils.CollectionUtils;
import com.fh.taolijie.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanghongfei on 15-3-7.
 */
@Repository
public class DefaultSchoolService implements SchoolService {
    @Autowired
    SchoolRepo schoolRepo;

    @Autowired
    AcademyRepo academyRepo;

    @Override
    @Transactional(readOnly = true)
    public List<SchoolDto> getSchoolList(int firstResult, int capacity) {
        int cap = capacity;
        if (cap <= 0) {
            cap = Constants.PAGE_CAPACITY;
        }

        /*List<SchoolEntity> sList = em.createNamedQuery("schoolEntity.findAll", SchoolEntity.class)
                .setFirstResult(firstResult)
                .setMaxResults(cap)
                .getResultList();*/
        Page<SchoolEntity> sList = schoolRepo.findAll(new PageRequest(firstResult, cap));

        List<SchoolDto> dtoList = new ArrayList<>();
        for (SchoolEntity s : sList) {
            dtoList.add(makeSchoolDto(s));
        }

        return dtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<SchoolDto> getSchoolListByProvince(String province, int firstResult, int capacity) {
        int cap = capacity;
        if (cap <= 0) {
            cap = Constants.PAGE_CAPACITY;
        }

        /*List<SchoolEntity> sList = em.createNamedQuery("schoolEntity.findByProvince", SchoolEntity.class)
                .setParameter("province", province)
                .setFirstResult(firstResult)
                .setMaxResults(cap)
                .getResultList();*/

        Page<SchoolEntity> sList = schoolRepo.findByProvince(province, new PageRequest(firstResult, cap));

        List<SchoolDto> dtoList = new ArrayList<>();
        for (SchoolEntity s : sList) {
            dtoList.add(makeSchoolDto(s));
        }

        return dtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public SchoolDto findSchool(Integer schoolId) {
        return makeSchoolDto(schoolRepo.findOne(schoolId));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    public boolean addSchool(SchoolDto schoolDto) {
        SchoolEntity school = new SchoolEntity(schoolDto.getShortName(), schoolDto.getFullName(),
                schoolDto.getProvince(), schoolDto.getType());

        schoolRepo.save(school);

        return true;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public boolean updateSchoolInfo(Integer schoolId, SchoolDto schoolDto) {
        SchoolEntity school = schoolRepo.findOne(schoolId);

        school.setFullName(schoolDto.getFullName());
        school.setShortName(schoolDto.getShortName());
        school.setProvince(schoolDto.getProvince());
        school.setType(schoolDto.getType());

        return true;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public boolean deleteSchool(Integer schoolId) throws CascadeDeleteException {
        SchoolEntity school = schoolRepo.findOne(schoolId);
        // 检查对应的学院是否为空
        if (school.getAcademyCollection() != null && false == school.getAcademyCollection().isEmpty()) {
            throw new CascadeDeleteException("学校下的学院不为空");
        }

        // 学院为空,执行删除操作
        schoolRepo.delete(schoolId);

        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AcademyDto> getAcademyList(Integer schoolId) {
        SchoolEntity school = schoolRepo.getOne(schoolId);
        List<AcademyEntity> aList = academyRepo.findBySchool(school);

        List<AcademyDto> dtoList = new ArrayList<>();
        for (AcademyEntity academy : aList) {
            dtoList.add(makeAcademyDto(academy));
        }

        return dtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public AcademyDto findAcademy(Integer academyId) {
        return makeAcademyDto(academyRepo.findOne(academyId));
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public boolean updateAcademy(Integer academyId, AcademyDto academyDto) {
        AcademyEntity academy = academyRepo.getOne(academyId);
        academy.setFullName(academyDto.getFullName());
        academy.setShortName(academyDto.getShortName());

        return true;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public boolean deleteAcademy(Integer academyId) {
        // 从school中解除关系
        AcademyEntity academy = academyRepo.findOne(academyId);
        CollectionUtils.removeFromCollection(academy.getSchool().getAcademyCollection(), (aca) -> {
            return aca.getId().equals(academyId);
        });

        // 删除实体本身
        academyRepo.delete(academyId);

        return true;
    }

    private AcademyDto makeAcademyDto(AcademyEntity academy) {
        AcademyDto dto = new AcademyDto();
        dto.setShortName(academy.getShortName());
        dto.setFullName(academy.getFullName());

        return dto;
    }
    private SchoolDto makeSchoolDto(SchoolEntity school) {
        SchoolDto dto = new SchoolDto();
        dto.setId(school.getId());
        dto.setShortName(school.getShortName());
        dto.setFullName(school.getFullName());
        dto.setProvince(school.getProvince());
        dto.setType(school.getType());

        return dto;
    }
}

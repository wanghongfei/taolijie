package com.fh.taolijie.service.impl;

import com.fh.taolijie.controller.dto.AcademyDto;
import com.fh.taolijie.controller.dto.SchoolDto;
import com.fh.taolijie.domain.SchoolEntity;
import com.fh.taolijie.service.SchoolService;
import com.fh.taolijie.utils.Constants;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanghongfei on 15-3-7.
 */
@Repository
public class DefaultSchoolService implements SchoolService {
    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional(readOnly = true)
    public List<SchoolDto> getSchoolList(int firstResult, int capacity) {
        int cap = capacity;
        if (cap <= 0) {
            cap = Constants.PAGE_CAPACITY;
        }

        List<SchoolEntity> sList = em.createNamedQuery("schoolEntity.findAll", SchoolEntity.class)
                .setFirstResult(firstResult)
                .setMaxResults(cap)
                .getResultList();

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

        List<SchoolEntity> sList = em.createNamedQuery("schoolEntity.findByProvince", SchoolEntity.class)
                .setParameter("province", province)
                .setFirstResult(firstResult)
                .setMaxResults(cap)
                .getResultList();

        List<SchoolDto> dtoList = new ArrayList<>();
        for (SchoolEntity s : sList) {
            dtoList.add(makeSchoolDto(s));
        }

        return dtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public SchoolDto findSchool(Integer schoolId) {
        return makeSchoolDto(em.find(SchoolEntity.class, schoolId));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    public boolean addSchool(SchoolDto schoolDto) {


        return true;
    }

    @Override
    public boolean updateSchoolInfo(Integer schoolId, SchoolDto schoolDto) {
        return false;
    }

    @Override
    public boolean deleteSchool(Integer schoolId) {
        return false;
    }

    @Override
    public List<AcademyDto> getAcademyList(Integer schoolId, int firstResult, int capacity) {
        return null;
    }

    @Override
    public AcademyDto findAcademy(Integer academyId) {
        return null;
    }

    @Override
    public boolean updateAcademy(Integer academyId, AcademyDto academyDto) {
        return false;
    }

    @Override
    public boolean deleteAcademy(Integer academyId) {
        return false;
    }

    private SchoolDto makeSchoolDto(SchoolEntity school) {
        SchoolDto dto = new SchoolDto();
        dto.setShortName(school.getShortName());
        dto.setFullName(school.getFullName());
        dto.setProvince(school.getProvince());
        dto.setType(school.getType());

        return dto;
    }
}

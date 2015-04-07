package com.fh.taolijie.service.impl;

import com.fh.taolijie.controller.dto.EducationExperienceDto;
import com.fh.taolijie.domain.AcademyEntity;
import com.fh.taolijie.domain.EducationExperienceEntity;
import com.fh.taolijie.domain.MemberEntity;
import com.fh.taolijie.service.EduExpService;
import com.fh.taolijie.service.repository.AcademyRepo;
import com.fh.taolijie.service.repository.EduExpRepo;
import com.fh.taolijie.service.repository.MemberRepo;
import com.fh.taolijie.utils.CheckUtils;
import com.fh.taolijie.utils.CollectionUtils;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.ObjWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by wanghongfei on 15-3-8.
 */
@Service
public class DefaultEduExpService implements EduExpService {
    @Autowired
    EduExpRepo eduRepo;
    @Autowired
    MemberRepo memberRepo;
    @Autowired
    AcademyRepo academyRepo;

    @Override
    @Transactional(readOnly = true)
    public List<EducationExperienceDto> getEduExpList(Integer memberId, int firstResult, int capacity, ObjWrapper wrapper) {
        int cap = capacity;
        if (cap <= 0) {
            cap = Constants.PAGE_CAPACITY;
        }

        MemberEntity member = memberRepo.getOne(memberId);
        CheckUtils.nullCheck(member);
        //List<EducationExperienceEntity> eduList = eduRepo.findByMember(member);
        Page<EducationExperienceEntity> eduList = eduRepo.findByMember(member, new PageRequest(firstResult, cap));
        //wrapper.setObj(eduLis);

        return CollectionUtils.transformCollection(eduList, EducationExperienceDto.class, (entity) -> {
            return makeEduExpDto(entity);
        });
    }




    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public boolean addEduExp(EducationExperienceDto eduDto) {
        MemberEntity mem = memberRepo.getOne(eduDto.getMemberId());
        AcademyEntity aca = academyRepo.getOne(eduDto.getAcademyId());
        CheckUtils.nullCheck(mem, aca);

        EducationExperienceEntity ee = new EducationExperienceEntity();
        ee.setAdmissionTime(eduDto.getAdmissionTime());
        ee.setLengthOfSchooling(eduDto.getLengthOfSchooling());
        ee.setMajor(eduDto.getMajor());

        ee.setMember(mem);
        ee.setAcademy(aca);

        // add experience to member
        List<EducationExperienceEntity> list = CollectionUtils.addToCollection(mem.getEducationExperienceCollection(), ee);
        if (null != list) {
            mem.setEducationExperienceCollection(list);
        }

        eduRepo.save(ee);

        return true;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public boolean updateEduExp(Integer eduId, EducationExperienceDto eduDto) {
        EducationExperienceEntity ee = eduRepo.getOne(eduId);
        CheckUtils.nullCheck(ee);

        // change state
        ee.setAdmissionTime(eduDto.getAdmissionTime());
        ee.setLengthOfSchooling(eduDto.getLengthOfSchooling());
        ee.setMajor(eduDto.getMajor());


        return true;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public boolean deleteEduExp(Integer id) {
        EducationExperienceEntity ee = eduRepo.getOne(id);
        CheckUtils.nullCheck(ee);

        // remove connection from Member
        CollectionUtils.removeFromCollection(ee.getMember().getEducationExperienceCollection(), (entity) -> {
            return entity.getId().equals(id);
        });

        // delete entity
        eduRepo.delete(ee);

        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public EducationExperienceDto findEduExp(Integer id) {
        EducationExperienceEntity edu = eduRepo.findOne(id);
        CheckUtils.nullCheck(edu);

        return makeEduExpDto(edu);
    }

    private EducationExperienceDto makeEduExpDto(EducationExperienceEntity edu) {
        EducationExperienceDto dto = new EducationExperienceDto();
        dto.setId(edu.getId());
        dto.setAdmissionTime(edu.getAdmissionTime());
        dto.setLengthOfSchooling(edu.getLengthOfSchooling());
        dto.setMajor(edu.getMajor());

        dto.setAcademyId(edu.getAcademy().getId());
        dto.setMemberId(edu.getMember().getId());

        return dto;
    }
}

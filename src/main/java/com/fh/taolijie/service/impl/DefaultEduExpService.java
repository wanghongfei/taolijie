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
import com.fh.taolijie.utils.ObjWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Consumer;

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

    /**
     * 用来设置DTO对象中与对应Domain对象变量名不匹配的域(field).
     * 此内部类存在的原因是为了消除重复代码。
     * <p> 用于{@link CollectionUtils#entity2Dto(Object, Class, Consumer)}方法的第三个参数
     * @param <ENTITY>
     */
    protected class SetupEduDto<ENTITY extends EducationExperienceEntity> implements Consumer<EducationExperienceDto> {
        private ENTITY entity;

        public SetupEduDto(ENTITY entity) {
            this.entity = entity;
        }

        @Override
        public void accept(EducationExperienceDto dto) {
            dto.setMemberId(entity.getMember().getId());
            dto.setAcademyId(entity.getAcademy().getId());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<EducationExperienceDto> getEduExpList(Integer memberId, int firstResult, int capacity, ObjWrapper wrapper) {
        int cap = CollectionUtils.determineCapacity(capacity);

        MemberEntity member = memberRepo.getOne(memberId);
        CheckUtils.nullCheck(member);
        Page<EducationExperienceEntity> eduList = eduRepo.findByMember(member, new PageRequest(firstResult, cap));
        // no need to paging
        //wrapper.setObj(eduLis);

        return CollectionUtils.transformCollection(eduList, EducationExperienceDto.class, entity -> {
            return CollectionUtils.entity2Dto(entity, EducationExperienceDto.class, new SetupEduDto(entity));
        });
    }




    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public boolean addEduExp(EducationExperienceDto eduDto) {
        MemberEntity mem = memberRepo.getOne(eduDto.getMemberId());
        AcademyEntity aca = academyRepo.getOne(eduDto.getAcademyId());
        CheckUtils.nullCheck(mem, aca);

        EducationExperienceEntity ee = CollectionUtils.dto2Entity(eduDto, EducationExperienceEntity.class, entity -> {
            entity.setMember(mem);
            entity.setAcademy(aca);
        });

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
        CollectionUtils.updateEntity(ee, eduDto, null);

        return true;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public boolean deleteEduExp(Integer id) {
        EducationExperienceEntity ee = eduRepo.getOne(id);
        CheckUtils.nullCheck(ee);

        // remove connection from Member
        CollectionUtils.removeFromCollection(ee.getMember().getEducationExperienceCollection(), entity -> {
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

        return CollectionUtils.entity2Dto(edu, EducationExperienceDto.class,  new SetupEduDto(edu));
    }
}

package com.fh.taolijie.service.impl;

import com.fh.taolijie.controller.dto.ResumeDto;
import com.fh.taolijie.domain.MemberEntity;
import com.fh.taolijie.domain.ResumeEntity;
import com.fh.taolijie.service.ResumeService;
import com.fh.taolijie.utils.CollectionUtils;
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
public class DefaultResumeService implements ResumeService {
    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional(readOnly = true)
    public List<ResumeDto> getResumeList(Integer memId, int firstResult, int capacity) {
        int cap = capacity;
        if (cap <= 0) {
            cap = Constants.PAGE_CAPACITY;
        }

        MemberEntity mem = em.getReference(MemberEntity.class, memId);

        List<ResumeEntity> rList = em.createNamedQuery("resumeEntity.findByMember", ResumeEntity.class)
                .setParameter("member", mem)
                .setFirstResult(firstResult)
                .setMaxResults(cap)
                .getResultList();

        List<ResumeDto> dtoList = new ArrayList<>();
        for (ResumeEntity r : rList) {
            dtoList.add(makeResumeDto(r));
        }

        return dtoList;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public boolean updateResume(Integer resumeId, ResumeDto resumeDto) {
        ResumeEntity r = em.getReference(ResumeEntity.class, resumeId);
        updateResume(r, resumeDto);

        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public ResumeDto findResume(Integer resumeId) {
        return makeResumeDto(em.find(ResumeEntity.class, resumeId));
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public boolean deleteResume(Integer resumeId) {
        ResumeEntity r = em.getReference(ResumeEntity.class, resumeId);

        // 从member中删除关联
        CollectionUtils.removeFromCollection(r.getMember().getResumeCollection(), (resume) -> {
            return resume.getId().equals(resumeId);
        });

        // 删除resume本身
        em.remove(r);

        return true;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void addResume(ResumeDto dto) {
        ResumeEntity r = makeResume(dto);
        em.persist(r);
    }

    private ResumeEntity makeResume(ResumeDto dto) {
        ResumeEntity r = new ResumeEntity(dto.getName(), dto.getGender(), dto.getAge(), dto.getHeight(),
                dto.getPhonePath(), dto.getEmail(), dto.getQq(), dto.getExperience(), dto.getIntroduce(),
                null);

        r.setMember(em.getReference(MemberEntity.class, dto.getMemberId()));

        return r;
    }

    /**
     * 不更新关联信息
     * @param resume
     * @param dto
     */
    private void updateResume(ResumeEntity resume, ResumeDto dto) {
        resume.setName(dto.getName());
        resume.setGender(dto.getGender());
        resume.setAge(dto.getAge());
        resume.setHeight(dto.getHeight());
        resume.setPhonePath(dto.getPhonePath());
        resume.setEmail(dto.getEmail());
        resume.setQq(dto.getQq());
        resume.setIntroduce(dto.getIntroduce());
    }
    private ResumeDto makeResumeDto(ResumeEntity resume) {
        ResumeDto dto = new ResumeDto();
        dto.setName(resume.getName());
        dto.setGender(resume.getGender());
        dto.setAge(resume.getAge());
        dto.setHeight(resume.getHeight());
        dto.setPhonePath(resume.getPhonePath());
        dto.setEmail(resume.getEmail());
        dto.setQq(resume.getQq());
        dto.setExperience(resume.getExperience());
        dto.setIntroduce(resume.getIntroduce());

        dto.setMemberId(resume.getMember().getId());

        return dto;
    }
}

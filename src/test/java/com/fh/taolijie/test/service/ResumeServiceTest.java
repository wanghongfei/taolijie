package com.fh.taolijie.test.service;

import com.fh.taolijie.controller.dto.ResumeDto;
import com.fh.taolijie.domain.MemberEntity;
import com.fh.taolijie.domain.ResumeEntity;
import com.fh.taolijie.service.ResumeService;
import com.fh.taolijie.service.impl.DefaultResumeService;
import com.fh.taolijie.test.BaseDatabaseTestClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by wanghongfei on 15-3-7.
 */
@ContextConfiguration(classes = {
        DefaultResumeService.class
})
public class ResumeServiceTest extends BaseDatabaseTestClass {
    MemberEntity member;
    ResumeEntity resume;

    @Autowired
    ResumeService rService;

    @PersistenceContext
    EntityManager em;

    @Before
    public void initData() {
        // 创建用户
        // password is 111111
        member = new MemberEntity("Bruce", "3d4f2bf07dc1be38b20cd6e46949a1071f9d0e3d", "", "Neo", "", "", "", "", "", "", 20, "", "");
        em.persist(member);

        // 创建简历
        resume = new ResumeEntity();
        resume.setName("resume");
        resume.setMember(member);
        em.persist(resume);

        // build connection
        member.setResumeCollection(new ArrayList<>());
        member.getResumeCollection().add(resume);
    }

    @Test
    @Transactional(readOnly = true)
    public void testGet() {
        List<ResumeDto> dtoList = rService.getResumeList(member.getId(), 0, 0);
        Assert.assertNotNull(dtoList);
        Assert.assertFalse(dtoList.isEmpty());
        Assert.assertTrue(containsName(dtoList, "resume"));
    }

    @Test
    @Transactional(readOnly = false)
    public void testUpdate() {
        ResumeDto dto = new ResumeDto();
        dto.setName("name");
        dto.setQq("1");

        rService.updateResume(resume.getId(), dto);

        ResumeEntity r = em.createQuery("SELECT r FROM ResumeEntity r WHERE r.name = 'name'", ResumeEntity.class)
                .getSingleResult();
        Assert.assertEquals("1", r.getQq());
    }

    @Test
    @Transactional(readOnly = true)
    public void testFind() {
        ResumeDto dto = rService.findResume(resume.getId());
        Assert.assertNotNull(dto);
        Assert.assertEquals("resume", dto.getName());
    }

    @Test
    @Transactional(readOnly = false)
    public void testDeleteResume() {
        rService.deleteResume(resume.getId());

        try {
            ResumeEntity r = em.createQuery("SELECT r FROM ResumeEntity r WHERE r.name = 'resume'", ResumeEntity.class)
                    .getSingleResult();
        } catch (NoResultException e) {
            return;
        }

        Assert.assertTrue(false);
    }

    private boolean containsName(Collection<ResumeDto> co, String name) {
        for (ResumeDto dto : co) {
            if (dto.getName().equals(name)) {
                return true;
            }
        }

        return false;
    }
}

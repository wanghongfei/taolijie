package com.fh.taolijie.test.service;

import com.fh.taolijie.controller.dto.ResumeDto;
import com.fh.taolijie.domain.MemberEntity;
import com.fh.taolijie.domain.ResumeEntity;
import com.fh.taolijie.service.ResumeService;
import com.fh.taolijie.service.impl.DefaultResumeService;
import com.fh.taolijie.test.service.repository.BaseSpringDataTestClass;
import com.fh.taolijie.utils.Constants;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by wanghongfei on 15-3-7.
 */
@ContextConfiguration(classes = {
        DefaultResumeService.class
})
public class ResumeServiceTest extends BaseSpringDataTestClass {
    MemberEntity member;
    ResumeEntity resume;
    ResumeEntity resumeBefore;

    @Autowired
    ResumeService rService;

    @PersistenceContext
    EntityManager em;

    @Before
    public void initData() throws Exception {
        // 创建用户
        // password is 111111
        member = new MemberEntity("Bruce", "3d4f2bf07dc1be38b20cd6e46949a1071f9d0e3d", "", "Neo", "", "", "", "", "", "", 20, "", "", true, new Date());
        em.persist(member);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        // 创建简历
        resume = new ResumeEntity();
        resume.setName("resume");
        resume.setAccessAuthority(Constants.AccessAuthority.ME_ONLY.toString());
        resume.setCreatedTime(new Date()); // now
        resume.setMember(member);
        em.persist(resume);

        resumeBefore = new ResumeEntity();
        resumeBefore.setName("resumeBefore");
        resumeBefore.setAccessAuthority(Constants.AccessAuthority.ALL.toString());
        resumeBefore.setCreatedTime(sdf.parse("2011/1/1"));
        resumeBefore.setMember(member);
        em.persist(resumeBefore);


        // build connection
        member.setResumeCollection(new ArrayList<>());
        member.getResumeCollection().add(resume);
        member.getResumeCollection().add(resumeBefore);

        em.flush();
        em.clear();
    }

    @Test
    @Transactional(readOnly = true)
    public void testGetAll() {
        List<ResumeDto> dtoList = rService.getAllResumeList(0, 0);
        Assert.assertNotNull(dtoList);
        Assert.assertFalse(dtoList.isEmpty());
        Assert.assertTrue(containsName(dtoList, "resume"));

        Assert.assertTrue(isRecentFront(dtoList));
    }

    @Test
    @Transactional(readOnly = true)
    public void testGetAllByAuthority() {
        List<ResumeDto> dtoList = rService.getAllResumeList(Constants.AccessAuthority.ALL, 0, 0);
        Assert.assertNotNull(dtoList);
        Assert.assertEquals(1, dtoList.size());
        Assert.assertTrue(containsName(dtoList, "resumeBefore"));

        Assert.assertTrue(isRecentFront(dtoList));

    }
    @Test
    @Transactional(readOnly = true)
    public void testGetByAuthority() {
        List<ResumeDto> dtoList = rService.getResumeList(this.member.getId(), Constants.AccessAuthority.ALL, 0, 0);
        Assert.assertNotNull(dtoList);
        Assert.assertFalse(dtoList.isEmpty());
        Assert.assertEquals(1, dtoList.size());
        Assert.assertTrue(containsName(dtoList, "resumeBefore"));

        Assert.assertTrue(isRecentFront(dtoList));


        dtoList = rService.getResumeList(this.member.getId(), Constants.AccessAuthority.ME_ONLY, 0, 0);
        Assert.assertNotNull(dtoList);
        Assert.assertFalse(dtoList.isEmpty());
        Assert.assertEquals(1, dtoList.size());
        Assert.assertTrue(containsName(dtoList, "resume"));

        Assert.assertTrue(isRecentFront(dtoList));
    }

    private boolean isRecentFront(List<ResumeDto> list) {
        int len = list.size();
        if (len <= 1) {
            return true;
        }

        for (int ix = 1 ; ix < len ; ++ix) {
            ResumeDto after = list.get(ix);
            ResumeDto before = list.get(ix - 1);

            if (before.getCreatedTime().compareTo(after.getCreatedTime()) < 0) {
                return false;
            }
        }

        return true;
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

    @Test
    @Transactional(readOnly = false)
    public void testAddResume() {
        ResumeDto dto = new ResumeDto();
        dto.setMemberId(member.getId());
        dto.setQq("1");
        rService.addResume(dto);

        em.createQuery("SELECT r FROM ResumeEntity r WHERE r.qq = '1'", ResumeEntity.class)
                .getSingleResult();
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

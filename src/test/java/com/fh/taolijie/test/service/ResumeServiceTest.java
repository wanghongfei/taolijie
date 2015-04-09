package com.fh.taolijie.test.service;

import com.fh.taolijie.controller.dto.ResumeDto;
import com.fh.taolijie.domain.JobPostCategoryEntity;
import com.fh.taolijie.domain.MemberEntity;
import com.fh.taolijie.domain.ResumeEntity;
import com.fh.taolijie.service.ResumeService;
import com.fh.taolijie.service.impl.DefaultResumeService;
import com.fh.taolijie.test.service.repository.BaseSpringDataTestClass;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.ObjWrapper;
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
import java.util.*;

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
    ResumeEntity resumeIntend;
    JobPostCategoryEntity cate1;
    JobPostCategoryEntity cate2;

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

        resumeIntend = new ResumeEntity();
        resumeIntend.setName("resumeIntend");
        resumeIntend.setAccessAuthority(Constants.AccessAuthority.ALL.toString());
        resumeIntend.setCreatedTime(sdf.parse("2011/1/1"));
        resumeIntend.setMember(member);
        em.persist(resumeIntend);

        // 创建工作分类
        cate1 = new JobPostCategoryEntity();
        cate1.setName("category");
        em.persist(cate1);
        cate2 = new JobPostCategoryEntity();
        cate2.setName("category2");
        em.persist(cate2);


        // build connection
        member.setResumeCollection(new ArrayList<>());
        member.getResumeCollection().add(resume);
        member.getResumeCollection().add(resumeBefore);
        // 添加意向
        resumeIntend.setCategoryList(Arrays.asList(cate1, cate2));


        em.flush();
        em.clear();
    }

    @Test
    public void testGetAll() {
        List<ResumeDto> dtoList = rService.getAllResumeList(0, 0, new ObjWrapper());
        Assert.assertNotNull(dtoList);
        Assert.assertFalse(dtoList.isEmpty());
        Assert.assertTrue(containsName(dtoList, "resume"));

        Assert.assertTrue(isRecentFront(dtoList));
    }

    @Test
    public void testGetAllByAuthority() {
        List<ResumeDto> dtoList = rService.getAllResumeList(Constants.AccessAuthority.ALL, 0, 0, new ObjWrapper());
        Assert.assertNotNull(dtoList);
        Assert.assertEquals(2, dtoList.size());
        Assert.assertTrue(containsName(dtoList, "resumeBefore"));

        Assert.assertTrue(isRecentFront(dtoList));

    }
    @Test
    public void testGetByAuthority() {
        List<ResumeDto> dtoList = rService.getResumeList(this.member.getId(), Constants.AccessAuthority.ALL, 0, 0, new ObjWrapper());
        Assert.assertNotNull(dtoList);
        Assert.assertFalse(dtoList.isEmpty());
        Assert.assertEquals(2, dtoList.size());
        Assert.assertTrue(containsName(dtoList, "resumeBefore"));

        Assert.assertTrue(isRecentFront(dtoList));


        dtoList = rService.getResumeList(this.member.getId(), Constants.AccessAuthority.ME_ONLY, 0, 0, new ObjWrapper());
        Assert.assertNotNull(dtoList);
        Assert.assertFalse(dtoList.isEmpty());
        Assert.assertEquals(1, dtoList.size());
        Assert.assertTrue(containsName(dtoList, "resume"));

        Assert.assertTrue(isRecentFront(dtoList));
    }

    @Test
    public void testGetByIds() {
        List<ResumeDto> dtoList = rService.getResumesByIds(0, 0, new ObjWrapper(), this.resume.getId(), this.resumeBefore.getId());
        Assert.assertNotNull(dtoList);
        Assert.assertEquals(2, dtoList.size());
        
        Assert.assertTrue(dtoList.stream().anyMatch( dto -> dto.getName().equals("resume") ));
        Assert.assertTrue(dtoList.stream().anyMatch( dto -> dto.getName().equals("resumeBefore") ));
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
    public void testGet() {
        List<ResumeDto> dtoList = rService.getResumeList(member.getId(), 0, 0, new ObjWrapper());
        Assert.assertNotNull(dtoList);
        Assert.assertFalse(dtoList.isEmpty());
        Assert.assertTrue(containsName(dtoList, "resume"));
    }

    @Test
    public void testUpdate() {
        ResumeDto dto = new ResumeDto();
        dto.setName("name");
        dto.setQq("1");
        // 修改求职意向
        dto.setIntendCategoryId(Arrays.asList(this.cate2.getId()));

        rService.updateResume(resume.getId(), dto);

        ResumeEntity r = em.createQuery("SELECT r FROM ResumeEntity r WHERE r.name = 'name'", ResumeEntity.class)
                .getSingleResult();
        Assert.assertEquals("1", r.getQq());
        Assert.assertEquals(1, r.getCategoryList().size());
        Assert.assertEquals("category2", r.getCategoryList().get(0).getName());
    }

    @Test
    public void testGetByIntend() {
        // TODO
        List<ResumeDto> dtoList = rService.getResumeListByIntend(cate1.getId());
        Assert.assertNotNull(dtoList);
        Assert.assertFalse(dtoList.isEmpty());

        Assert.assertTrue(dtoList.stream().anyMatch( (dto) -> {
            return dto.getName().equals("resumeIntend");
        }));
    }

    @Test
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
        dto.setIntendCategoryId(Arrays.asList(this.cate1.getId()));
        rService.addResume(dto);

        ResumeEntity entity = em.createQuery("SELECT r FROM ResumeEntity r WHERE r.qq = '1'", ResumeEntity.class)
                .getSingleResult();
        Assert.assertEquals("category", entity.getCategoryList().get(0).getName());
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

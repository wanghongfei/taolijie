package com.fh.taolijie.test.service.repository;

import com.fh.taolijie.controller.dto.EducationExperienceDto;
import com.fh.taolijie.domain.AcademyEntity;
import com.fh.taolijie.domain.EducationExperienceEntity;
import com.fh.taolijie.domain.MemberEntity;
import com.fh.taolijie.domain.SchoolEntity;
import com.fh.taolijie.service.EduExpService;
import com.fh.taolijie.service.impl.DefaultEduExpService;
import com.fh.taolijie.service.repository.AcademyRepo;
import com.fh.taolijie.service.repository.EduExpRepo;
import com.fh.taolijie.service.repository.MemberRepo;
import com.fh.taolijie.service.repository.SchoolRepo;
import com.fh.taolijie.utils.Constants;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wanghongfei on 15-3-9.
 */
@ContextConfiguration(classes = {
        DefaultEduExpService.class
})
public class EduExpServiceTest extends BaseSpringDataTestClass {
    MemberEntity member;
    SchoolEntity school;
    AcademyEntity academy;
    EducationExperienceEntity exp;


    @Autowired
    EduExpRepo eduRepo;
    @Autowired
    SchoolRepo schoolRepo;
    @Autowired
    AcademyRepo academyRepo;
    @Autowired
    MemberRepo memberRepo;

    @Autowired
    EduExpService eduService;

    @Before
    public void initData() {
        // 创建用户
        // password is 111111
        member = new MemberEntity("Bruce", "3d4f2bf07dc1be38b20cd6e46949a1071f9d0e3d", "", "Neo", "", "", "", "", "", "", 20, "", "",true, new Date());
        memberRepo.save(member);

        // 创建学校
        school = new SchoolEntity("school", "a school", "shandong", Constants.SchoolType.HIGH_SCHOOL.toString());
        schoolRepo.save(school);

        // 创建学院
        academy = new AcademyEntity("academy", "an academy", school);
        academyRepo.save(academy);
        school.setAcademyCollection(new ArrayList<>());
        school.getAcademyCollection().add(academy);

        // create experience
        exp = new EducationExperienceEntity();
        exp.setMember(member);
        exp.setAcademy(academy);
        exp.setMajor("math");
        eduRepo.save(exp);

    }

    @Test
    @Transactional(readOnly = true)
    public void testGetList() {
        List<EducationExperienceDto> dtoList = eduService.getEduExpList(member.getId(), 0, 0);
        boolean contains = dtoList.stream().anyMatch( (dto) -> dto.getMajor().equals("math") );
        Assert.assertTrue(contains);
    }

    @Test
    @Transactional
    public void testAddExp() {
        EducationExperienceDto dto = new EducationExperienceDto();
        dto.setMemberId(member.getId());
        dto.setAcademyId(academy.getId());
        dto.setMajor("chinese");
        eduService.addEduExp(dto);

        boolean contains = memberRepo.getOne(member.getId()).getEducationExperienceCollection()
                .stream()
                .anyMatch( (exp) -> exp.getMajor().equals("chinese") );
        Assert.assertTrue(contains);
    }

    @Test
    @Transactional
    public void testUpdateExp() {
        EducationExperienceDto dto = new EducationExperienceDto();
        dto.setMajor("writing");

        eduService.updateEduExp(exp.getId(), dto);

        Assert.assertEquals("writing", eduRepo.getOne(exp.getId()).getMajor());
    }

    @Test
    @Transactional
    public void testDeleteExp() {
        eduService.deleteEduExp(exp.getId());

        EducationExperienceEntity dto = eduRepo.findOne(exp.getId());
        Assert.assertNull(dto);

    }
}

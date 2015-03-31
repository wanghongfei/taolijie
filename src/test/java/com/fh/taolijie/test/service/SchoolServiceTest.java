package com.fh.taolijie.test.service;

import com.fh.taolijie.controller.dto.AcademyDto;
import com.fh.taolijie.controller.dto.SchoolDto;
import com.fh.taolijie.domain.AcademyEntity;
import com.fh.taolijie.domain.SchoolEntity;
import com.fh.taolijie.exception.checked.CascadeDeleteException;
import com.fh.taolijie.service.SchoolService;
import com.fh.taolijie.service.impl.DefaultSchoolService;
import com.fh.taolijie.service.repository.AcademyRepo;
import com.fh.taolijie.service.repository.SchoolRepo;
import com.fh.taolijie.test.service.repository.BaseSpringDataTestClass;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.ObjWrapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by wanghongfei on 15-3-8.
 */
@ContextConfiguration(classes = {
        DefaultSchoolService.class
})
public class SchoolServiceTest extends BaseSpringDataTestClass {
    SchoolEntity school;
    AcademyEntity academy;

    @Autowired
    SchoolService sService;

    @Autowired
    SchoolRepo schoolRepo;
    @Autowired
    AcademyRepo academyRepo;

    @Before
    @Transactional(readOnly = false)
    public void initData() {
        // 创建学校
        school = new SchoolEntity("school", "a school", "shandong", Constants.SchoolType.HIGH_SCHOOL.toString());
        schoolRepo.save(school);

        // 创建学院
        academy = new AcademyEntity("academy", "an academy", school);
        academyRepo.save(academy);
        school.setAcademyCollection(new ArrayList<>());
        school.getAcademyCollection().add(academy);

    }

    @Test
    @Transactional(readOnly = true)
    public void test() {
        Assert.assertNotNull(sService);
        Assert.assertNotNull(school.getId());
        Assert.assertNotNull(academy.getId());

        SchoolEntity se = schoolRepo.findOne(school.getId());
        Assert.assertNotNull(se.getAcademyCollection());
    }

    @Test
    @Transactional(readOnly = true)
    public void testGetSchoolList() {
        List<SchoolDto> dtoList = sService.getSchoolList(0, 0, new ObjWrapper());
        boolean contains = containsSchoolName(dtoList, "school");
    }

    @Test
    @Transactional(readOnly = true)
    public void testGetSchoolByProvince() {
        List<SchoolDto> dtoList = sService.getSchoolListByProvince("shandong", 0, 0, new ObjWrapper());
        boolean contains = containsSchoolName(dtoList, "school");
        Assert.assertTrue(contains);

        dtoList = sService.getSchoolListByProvince("guangzhou", 0, 0, new ObjWrapper());
        contains = containsSchoolName(dtoList, "school");
        Assert.assertFalse(contains);
    }

    @Test
    public void testFindSchool() {
        SchoolDto dto = sService.findSchool(school.getId());
        Assert.assertNotNull(dto);
        Assert.assertEquals("school", dto.getShortName());
    }

    @Test
    @Transactional(readOnly = false)
    public void testAddSchool() {
        SchoolDto dto = new SchoolDto();
        dto.setShortName("new school");
        dto.setProvince("province");

        sService.addSchool(dto);

        Page<SchoolEntity> schoolPage = schoolRepo.findByProvince("province", null);
        boolean contains = containsSchoolName(schoolPage, "new school");
        Assert.assertTrue(contains);
    }

    @Test
    @Transactional(readOnly = false)
    public void testUpdateSchoolInfo() {
        SchoolDto dto = new SchoolDto();
        dto.setShortName("new school");
        dto.setProvince("province");

        sService.updateSchoolInfo(school.getId(), dto);

        SchoolEntity s = schoolRepo.findOne(school.getId());
        Assert.assertNotNull(s);
        Assert.assertEquals("province", s.getProvince());
    }

    @Test
    @Transactional(readOnly = false)
    public void testDeleteSchool() {
        try {
            sService.deleteSchool(school.getId());
        } catch (CascadeDeleteException e) {
            return;
        }

        Assert.assertTrue(false);
    }
    @Test
    @Transactional(readOnly = false)
    public void testDeleteSchool2() {
       /* // delete academy first
        AcademyEntity ae = academyRepo.findOne(academy.getId());
        CollectionUtils.removeFromCollection(ae.getSchool().getAcademyCollection(), (acedemy) -> {
            return acedemy.getId().equals(ae.getId());
        });
        academyRepo.delete(academy.getId());

        // delete school
        try {
            sService.deleteSchool(school.getId());
        } catch (CascadeDeleteException e) {
            Assert.assertTrue(false);
        }*/
    }

    @Test
    @Transactional(readOnly = false)
    public void testGetAcademyList() {
        List<AcademyDto> dtoList = sService.getAcademyList(school.getId());
        Assert.assertTrue(containsAcademyName(dtoList, "academy"));

    }

    @Test
    @Transactional(readOnly = true)
    public void testFindAcademy() {
        AcademyDto dto = sService.findAcademy(academy.getId());
        Assert.assertNotNull(dto);
        Assert.assertEquals("academy", dto.getShortName());
    }

    @Test
    @Transactional(readOnly = false)
    public void updateAcademy() {
        AcademyDto dto = new AcademyDto();
        dto.setFullName("a full name");
        sService.updateAcademy(academy.getId(), dto);

        AcademyEntity ae = academyRepo.findOne(academy.getId());
        Assert.assertNotNull(ae);
        Assert.assertEquals("a full name", ae.getFullName());
    }

    @Test
    @Transactional(readOnly = false)
    public void testDeleteAcademy() {
        sService.deleteAcademy(academy.getId());

        AcademyEntity ae = academyRepo.findOne(academy.getId());
        Assert.assertNull(ae);
    }
    private boolean containsAcademyName(Collection<AcademyDto> collection, String name) {
        for (AcademyDto dto : collection) {
            if (dto.getShortName().equals(name)) {
                return true;
            }
        }

        return false;
    }
    private boolean containsSchoolName(Page<SchoolEntity> collection, String name) {
        for (SchoolEntity s : collection) {
            if (s.getShortName().equals(name)) {
                return true;
            }
        }

        return false;
    }
    private boolean containsSchoolName(Iterable<SchoolDto> dtoCollection, String name) {
        for (SchoolDto dto : dtoCollection) {
            if (dto.getShortName().equals(name)) {
                return true;
            }
        }

        return false;
    }
}

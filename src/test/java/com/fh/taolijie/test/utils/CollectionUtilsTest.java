package com.fh.taolijie.test.utils;

import com.fh.taolijie.controller.dto.JobPostDto;
import com.fh.taolijie.controller.dto.StudentDto;
import com.fh.taolijie.domain.JobPostModel;
import com.fh.taolijie.domain.MemberModel;
import com.fh.taolijie.utils.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by wanghongfei on 15-3-26.
 */
public class CollectionUtilsTest {
    @Test
    public void testDto2Entity() throws Exception {
        StudentDto dto = new StudentDto();
        dto.setUsername("Neo");
        dto.setAge(20);

        MemberModel entity = CollectionUtils.dto2Entity(dto, MemberModel.class, null);

        Assert.assertEquals("Neo", entity.getUsername());
    }

    @Test
    public void testEntity2Dto() throws Exception {
        MemberModel m = new MemberModel();
        m.setUsername("Bruce");
        m.setAge(100);
        m.setStudentId("121105");

        StudentDto dto = CollectionUtils.entity2Dto(m, StudentDto.class, null);
        Assert.assertEquals("121105", dto.getStudentId());


        JobPostModel jobPostEntity = new JobPostModel();
        jobPostEntity.setTitle("title");

        JobPostDto jobPostDto = CollectionUtils.entity2Dto(jobPostEntity, JobPostDto.class, null);
        Assert.assertEquals("title", jobPostDto.getTitle());
    }

    @Test
    public void testUpdateEntity() {

        MemberModel m = new MemberModel();
        m.setUsername("Bruce");
        m.setAge(100);
        m.setStudentId("121105");

        StudentDto dto = new StudentDto();
        //dto.setUsername("Neo");
        dto.setAge(20);
        dto.setStudentId("2222");

        CollectionUtils.updateEntity(m, dto, null);

        //Assert.assertEquals("Neo", m.getUsername());
        Assert.assertEquals(20, m.getAge().intValue());
        Assert.assertEquals("2222", m.getStudentId());
    }
}

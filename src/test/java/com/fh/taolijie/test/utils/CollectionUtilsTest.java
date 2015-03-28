package com.fh.taolijie.test.utils;

import com.fh.taolijie.controller.dto.JobPostDto;
import com.fh.taolijie.controller.dto.StudentDto;
import com.fh.taolijie.domain.JobPostEntity;
import com.fh.taolijie.domain.MemberEntity;
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

        MemberEntity entity = CollectionUtils.dto2Entity(dto, MemberEntity.class);

        Assert.assertEquals("Neo", entity.getUsername());
    }

    @Test
    public void testEntity2Dto() throws Exception {
        MemberEntity m = new MemberEntity();
        m.setUsername("Bruce");
        m.setAge(100);
        m.setStudentId("121105");

        StudentDto dto = CollectionUtils.entity2Dto(m, StudentDto.class, null);
        Assert.assertEquals("121105", dto.getStudentId());


        JobPostEntity jobPostEntity = new JobPostEntity();
        jobPostEntity.setTitle("title");

        JobPostDto jobPostDto = CollectionUtils.entity2Dto(jobPostEntity, JobPostDto.class, null);
        Assert.assertEquals("title", jobPostDto.getTitle());
    }
}

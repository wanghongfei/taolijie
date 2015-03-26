package com.fh.taolijie.test.utils;

import com.fh.taolijie.controller.dto.StudentDto;
import com.fh.taolijie.domain.MemberEntity;
import com.fh.taolijie.utils.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by wanghongfei on 15-3-26.
 */
public class CollectionUtilsTest {
    @Test
    public void testDto2Entity() {
        StudentDto dto = new StudentDto();
        dto.setUsername("Neo");
        dto.setAge(20);

        MemberEntity entity = CollectionUtils.dto2Entity(dto, MemberEntity.class);

        Assert.assertEquals("Neo", entity.getUsername());
    }
}

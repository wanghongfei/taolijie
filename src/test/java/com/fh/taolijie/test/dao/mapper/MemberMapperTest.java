package com.fh.taolijie.test.dao.mapper;

import com.fh.taolijie.dao.mapper.MemberMapper;
import com.fh.taolijie.test.BaseSpringDataTestClass;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by wanghongfei on 15-5-31.
 */
public class MemberMapperTest extends BaseSpringDataTestClass {
    @Autowired
    MemberMapper memMapper;

    @Test
    public void test() {
        Assert.assertNotNull(memMapper);
    }
}

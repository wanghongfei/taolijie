package com.fh.taolijie.test.dao.mapper;

import com.fh.taolijie.dao.mapper.JobPostMapper;
import com.fh.taolijie.domain.JobPostModel;
import com.fh.taolijie.test.BaseSpringDataTestClass;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by wanghongfei on 15-5-31.
 */
public class JobPostMapperTest extends BaseSpringDataTestClass {
    @Autowired
    JobPostMapper jobMapper;

    @Test
    public void testGetWithoutReview() {
        JobPostModel model = jobMapper.getPostByIdWithoutReview(1);
        Assert.assertNotNull(model);
    }
}

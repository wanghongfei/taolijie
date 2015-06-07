package com.fh.taolijie.test.dao.mapper;

import com.fh.taolijie.domain.JobPostModel;
import com.fh.taolijie.service.JobPostService;
import com.fh.taolijie.service.impl.DefaultJobPostService;
import com.fh.taolijie.test.BaseSpringDataTestClass;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

/**
 * Created by wanghongfei on 15-6-7.
 */
@ContextConfiguration(classes = {
        DefaultJobPostService.class
})
public class JobPostServiceTest extends BaseSpringDataTestClass {
    @Autowired
    JobPostService postService;

    @Test
    public void testGet() {
        List<JobPostModel> list = postService.getAllJobPostList(0, 100, null);
        Assert.assertFalse(list.isEmpty());

        Assert.assertFalse(postService.getJobPostListByMember(1, 0, 100, null).isEmpty() );
    }

}

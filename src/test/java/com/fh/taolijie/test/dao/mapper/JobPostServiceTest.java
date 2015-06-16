package com.fh.taolijie.test.dao.mapper;

import com.fh.taolijie.dao.mapper.JobPostModelMapper;
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

    @Autowired
    JobPostModelMapper jobMapper;

    @Test
    public void testGet() {
        List<JobPostModel> list = postService.getAllJobPostList(0, 100, null);
        Assert.assertFalse(list.isEmpty());

        Assert.assertFalse(postService.getJobPostListByMember(1, 0, 100, null).isEmpty() );

        long tot = jobMapper.countGetAll();
        Assert.assertNotEquals(0, tot);
    }

    @Test
    public void testFindBy() {
        JobPostModel job = new JobPostModel();
        job.setIntroduce("998");

        List<JobPostModel> list = jobMapper.findBy(job);
        long tot = jobMapper.countFindBy(job);
        Assert.assertEquals(tot, list.size());
    }

    @Test
    public void testSearch() {
        JobPostModel job = new JobPostModel();
        job.setTitle("");
        List<JobPostModel> list = postService.runSearch(job, 0, 100, null);
        long tot = jobMapper.countSearchBy(job);

        Assert.assertFalse(list.isEmpty());
        Assert.assertEquals(list.size(), tot);
    }

}

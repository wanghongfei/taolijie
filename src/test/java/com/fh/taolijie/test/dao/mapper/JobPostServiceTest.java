package com.fh.taolijie.test.dao.mapper;

import com.fh.taolijie.dao.mapper.JobPostModelMapper;
import com.fh.taolijie.domain.JobPostModel;
import com.fh.taolijie.service.JobPostService;
import com.fh.taolijie.service.impl.DefaultJobPostService;
import com.fh.taolijie.test.BaseSpringDataTestClass;
import com.fh.taolijie.utils.Constants;
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

        list = postService.getJobPostListByCategory(1, 0, 100, null);
        Assert.assertFalse(list.isEmpty());

        list = postService.getUnverifiedPostList(0, 100, null);
        Assert.assertFalse(list.isEmpty());

        list = postService.getPostListByIds(1, 2, 3);
        Assert.assertFalse(list.isEmpty());

        list = postService.getByComplaint(0, 100, null);
        Assert.assertFalse(list.isEmpty());
    }

    @Test
    public void testIncreaseComplaint() {
        postService.complaint(1);
        postService.complaint(1);
    }

    @Test
    public void testFavoritePost() {
        postService.favoritePost(1, 1);
        postService.unfavoritePost(1, 1);
        boolean fa = postService.isPostFavorite(1, 1);
        Assert.assertEquals(false, fa);
    }

    @Test
    public void testPostResume() {
        postService.postResume(1, 1, 1);
    }

    @Test
    public void testAddPost() {
        JobPostModel job = new JobPostModel();
        job.setTitle("job");
        postService.addJobPost(job);
    }

    @Test
    public void testUpdatePOst() {
        JobPostModel job = new JobPostModel();
        job.setId(1);
        job.setTitle("job");
        postService.updateJobPost(1, job);

    }

    @Test
    public void testDeletePost() {
        //postService.deleteJobPost(1);
    }

    @Test
    public void testGetByFilter() {
        List<JobPostModel> list = postService.getAndFilter(1, Constants.WayToPay.DAY, true, false, null, 0, 100 ,null);
        list = postService.getAndFilter(1, null, false, true, null, 0, 100 ,null);
    }

    @Test
    public void testFindBy() {
        JobPostModel job = new JobPostModel();
        job.setIntroduce("998");

        List<JobPostModel> list = jobMapper.findBy(job);
        long tot = jobMapper.countFindBy(job);
        Assert.assertEquals(tot, list.size());

        job = postService.findJobPost(1);
        Assert.assertNotNull(job);
        Assert.assertEquals("post1", job.getTitle());
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

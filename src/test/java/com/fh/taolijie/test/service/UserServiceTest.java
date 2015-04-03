package com.fh.taolijie.test.service;

import com.fh.taolijie.domain.JobPostEntity;
import com.fh.taolijie.domain.MemberEntity;
import com.fh.taolijie.domain.SecondHandPostEntity;
import com.fh.taolijie.service.UserService;
import com.fh.taolijie.service.impl.DefaultUserService;
import com.fh.taolijie.test.service.repository.BaseSpringDataTestClass;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.Print;
import com.fh.taolijie.utils.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wanghongfei on 15-4-3.
 */
@ContextConfiguration(classes = {
        DefaultUserService.class
})
public class UserServiceTest extends BaseSpringDataTestClass {
    @PersistenceContext
    EntityManager em;

    @Autowired
    UserService userService;

    MemberEntity member;
    JobPostEntity jobPost;
    SecondHandPostEntity shPost;

    @Before
    public void initData() {
        Print.print("准备数据");

        // 创建用户
        // password is 111111
        member = new MemberEntity("Bruce", "3d4f2bf07dc1be38b20cd6e46949a1071f9d0e3d", "", "Neo", "", "", "", "", "", "", 20, "", "", true, new Date());
        em.persist(member);


        // 创建兼职帖子
        jobPost = new JobPostEntity();
        jobPost.setTitle("a post");
        jobPost.setVerified(Constants.VerifyStatus.NONE.toString());
        jobPost.setComplaint(10);
        jobPost.setMember(member);
        em.persist(jobPost);

        // 创建二手帖子
        shPost = new SecondHandPostEntity();
        shPost.setTitle("a post");
        shPost.setVerified(Constants.VerifyStatus.NONE.toString());
        em.persist(shPost);

        List<JobPostEntity> jobList = new ArrayList<>();
        member.setJobPostCollection(jobList);
        List<SecondHandPostEntity> shList = new ArrayList<>();
        member.setSecondHandPostCollection(shList);

        em.flush();
        em.clear();
    }

    @Test
    public void testLikeJobPost() {
        userService.likeJobPost(this.member.getId(), this.jobPost.getId());

        Integer likes = em.find(JobPostEntity.class, this.jobPost.getId()).getLikes();
        Assert.assertNotNull(likes);
        Assert.assertEquals(1, likes.intValue());


        String ids = em.find(MemberEntity.class, this.member.getId()).getLikedJobIds();
        Assert.assertNotNull(ids);
        Assert.assertTrue(StringUtils.checkIdExists(ids, this.jobPost.getId().toString()));


        // 测试重复点赞
        boolean result = userService.likeJobPost(this.member.getId(), this.jobPost.getId());
        Assert.assertFalse(result);
    }

    @Test
    public void testLikeShPost() {
        userService.likeSHPost(this.member.getId(), this.shPost.getId());

        Integer likes = em.find(SecondHandPostEntity.class, this.shPost.getId()).getLikes();
        Assert.assertNotNull(likes);
        Assert.assertEquals(1, likes.intValue());

        String ids = em.find(MemberEntity.class, this.member.getId()).getLikedShIds();
        Assert.assertNotNull(ids);
        Assert.assertTrue(StringUtils.checkIdExists(ids, this.shPost.getId().toString()));

        // 测试重复点
        boolean result = userService.likeSHPost(this.member.getId(), this.shPost.getId());
        Assert.assertFalse(result);
    }
}

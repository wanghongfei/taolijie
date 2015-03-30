package com.fh.taolijie.test.service;

import com.fh.taolijie.controller.dto.JobPostDto;
import com.fh.taolijie.domain.JobPostCategoryEntity;
import com.fh.taolijie.domain.JobPostEntity;
import com.fh.taolijie.domain.MemberEntity;
import com.fh.taolijie.domain.ReviewEntity;
import com.fh.taolijie.service.JobPostService;
import com.fh.taolijie.service.impl.DefaultJobPostService;
import com.fh.taolijie.service.impl.DefaultReviewService;
import com.fh.taolijie.test.service.repository.BaseSpringDataTestClass;
import com.fh.taolijie.utils.Print;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by wanghongfei on 15-3-7.
 */
@ContextConfiguration(classes = {
        DefaultJobPostService.class,
        DefaultReviewService.class
})
public class JobPostServiceTest extends BaseSpringDataTestClass {
    MemberEntity member;
    JobPostCategoryEntity cate1;
    JobPostCategoryEntity cate2;
    JobPostEntity post;
    ReviewEntity review;

    @Autowired
    JobPostService postService;


    @PersistenceContext
    EntityManager em;

    @Before
    @Transactional(readOnly = false)
    public void initDate() {
        // 创建用户
        // password is 111111
        member = new MemberEntity("Bruce", "3d4f2bf07dc1be38b20cd6e46949a1071f9d0e3d", "", "Neo", "", "", "", "", "", "", 20, "", "", true, new Date());
        em.persist(member);

        // 创建2个分类
        cate1 = new JobPostCategoryEntity();
        cate1.setName("dog");
        em.persist(cate1);

        cate2 = new JobPostCategoryEntity();
        cate2.setName("cat");
        em.persist(cate2);

        // 创建帖子
        post = new JobPostEntity();
        post.setTitle("a post");
        post.setMember(member);
        // 创建关联
        post.setCategory(cate1);
        cate1.setJobPostCollection(new ArrayList<>());
        cate1.getJobPostCollection().add(post);
        em.persist(post);

        // 创建评论
        review = new ReviewEntity();
        review.setTime(new Date());
        review.setContent("OK");
        review.setMember(member);
        review.setJobPost(post);
        post.setReviewCollection(new ArrayList<>());
        post.getReviewCollection().add(review);
        member.setReviewCollection(new ArrayList<>());
        member.getReviewCollection().add(review);
        em.persist(review);


        em.flush();
        em.clear();
    }

    @Test
    @Transactional(readOnly = true)
    public void testFindAll() {
        List<JobPostDto> dtoList = postService.getAllJobPostList(0 ,0);
        Assert.assertNotNull(dtoList);
        Assert.assertFalse(dtoList.isEmpty());
        boolean contains = containsPostTitle(dtoList, "a post");
        Assert.assertTrue(contains);
    }

    @Test
    @Transactional(readOnly = true)
    public void testGetByMember() {
        List<JobPostDto> dtoList = postService.getJobPostListByMember(member.getId(), 0, 0);
        Assert.assertNotNull(dtoList);
        Assert.assertFalse(dtoList.isEmpty());
        boolean contains = containsPostTitle(dtoList, "a post");
        Assert.assertTrue(contains);


        dtoList = postService.getJobPostListByCategory(cate1.getId(), 0, 0);
        Assert.assertNotNull(dtoList);
        Assert.assertFalse(dtoList.isEmpty());
        contains = containsPostTitle(dtoList, "a post");
        Assert.assertTrue(contains);
    }

    @Test
    @Transactional(readOnly = true)
    public void testFindPost() {
        JobPostDto dto = postService.findJobPost(post.getId());
        Assert.assertNotNull(dto);
        Assert.assertEquals(post.getTitle(), dto.getTitle());
    }

    @Test
    @Transactional(readOnly = false)
    public void testComplaint() {
        postService.complaint(post.getId());
        JobPostDto dto = postService.findJobPost(post.getId());
        Assert.assertEquals(1, dto.getComplaint().intValue());


        MemberEntity mem = em.find(MemberEntity.class, member.getId());
        Assert.assertEquals(1, mem.getComplaint().intValue());

        em.flush();
        em.clear();

        postService.complaint(post.getId());
        dto = postService.findJobPost(post.getId());
        Assert.assertEquals(2, dto.getComplaint().intValue());


        mem = em.find(MemberEntity.class, member.getId());
        Assert.assertEquals(2, mem.getComplaint().intValue());
        Print.print("~~~~" + mem.getComplaint().intValue());
    }

    @Test
    @Transactional(readOnly = false)
    public void testUpdatePost() {
        JobPostDto dto = new JobPostDto();
        dto.setTitle("no post");
        dto.setLikes(100);

        postService.updateJobPost(this.post.getId(), dto);

        JobPostEntity p = em.createQuery("SELECT j FROM JobPostEntity j WHERE j.title = 'no post'", JobPostEntity.class)
                .getSingleResult();
        Assert.assertEquals(100, p.getLikes().intValue());
    }

    @Test
    @Transactional(readOnly = false)
    public void testAddPost() {
        JobPostDto dto = new JobPostDto();
        dto.setMemberId(member.getId());
        dto.setCategoryId(cate1.getId());
        dto.setTitle("new post");

        postService.addJobPost(dto);

        JobPostEntity p = em.createQuery("SELECT j FROM JobPostEntity j WHERE j.title = 'new post'", JobPostEntity.class)
                .getSingleResult();
        Assert.assertNotNull(p.getMember());
        Assert.assertNotNull(p.getCategory());
    }
    @Test
    @Transactional(readOnly = false)
    public void testDeletePost() {
        postService.deleteJobPost(this.post.getId());

        try {
            JobPostEntity p = em.createQuery("SELECT j FROM JobPostEntity j WHERE j.title = 'no post'", JobPostEntity.class)
                    .getSingleResult();
        } catch (NoResultException ex) {
           return;
        }

        Assert.assertTrue(false);
    }

    @Test
    @Transactional(readOnly = false)
    public void testDeleteWithReviews() {
        Print.print("测试完全删除");
        postService.deleteJobPost(this.post.getId());

        try {
            JobPostEntity p = em.createQuery("SELECT j FROM JobPostEntity j WHERE j.title = 'no post'", JobPostEntity.class)
                    .getSingleResult();
        } catch (NoResultException ex) {
            // 测试评论是否删除
            try {
                em.createQuery("SELECT r FROM ReviewEntity r WHERE r.content = 'OK'", ReviewEntity.class)
                        .getSingleResult();
            } catch (NoResultException e) {
                return;
            }

            Assert.assertTrue(false);
        }

        Assert.assertTrue(false);
    }

    private boolean containsPostTitle(Collection<JobPostDto> dtoCollection, String title) {
        for (JobPostDto dto : dtoCollection) {
            if (title.equals(dto.getTitle())) {
                return true;
            }
        }

        return false;
    }
}

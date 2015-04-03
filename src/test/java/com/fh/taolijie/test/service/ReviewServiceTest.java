package com.fh.taolijie.test.service;

import com.fh.taolijie.controller.dto.ReviewDto;
import com.fh.taolijie.domain.JobPostCategoryEntity;
import com.fh.taolijie.domain.JobPostEntity;
import com.fh.taolijie.domain.MemberEntity;
import com.fh.taolijie.domain.ReviewEntity;
import com.fh.taolijie.service.ReviewService;
import com.fh.taolijie.service.impl.DefaultReviewService;
import com.fh.taolijie.test.service.repository.BaseSpringDataTestClass;
import com.fh.taolijie.utils.ObjWrapper;
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
@ContextConfiguration(classes = {DefaultReviewService.class})
public class ReviewServiceTest extends BaseSpringDataTestClass {
    private JobPostEntity post;
    private MemberEntity member;
    private JobPostCategoryEntity cate;
    private ReviewEntity review;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    ReviewService rService;

    @Before
    @Transactional(readOnly = false)
    public void initData() {
        Print.print("准备数据");
        // 创建用户
        // password is 111111
        member = new MemberEntity("Bruce", "3d4f2bf07dc1be38b20cd6e46949a1071f9d0e3d", "", "Neo", "", "", "", "", "", "", 20, "", "", true, new Date());
        //em.persist(member);
        Print.print("创建用户");

        // 创建帖子分类
        cate = new JobPostCategoryEntity();
        cate.setName("category");
        //em.persist(cate);
        Print.print("创建帖子");


        // 创建帖子
        post = new JobPostEntity();
        post.setTitle("a post");
        post.setMember(member);
        //em.persist(post);

        // 创建评论
        review = new ReviewEntity();
        review.setTime(new Date());
        review.setContent("OK");
        //em.persist(review);

        // 创建关联
        cate.setJobPostCollection(new ArrayList<>());
        cate.getJobPostCollection().add(post);
        post.setMember(member);
        post.setCategory(cate);
        post.setReviewCollection(new ArrayList<>());
        post.getReviewCollection().add(review);
        review.setJobPost(post);
        review.setMember(member);
        member.setJobPostCollection(new ArrayList<>());
        member.getJobPostCollection().add(post);
        member.setReviewCollection(new ArrayList<>());
        member.getReviewCollection().add(review);

        em.persist(cate);
        em.persist(member);
        em.persist(post);
        em.persist(review);
        //em.flush();
    }
    @Test
    public void testGetList() {
        List<ReviewDto> dtoList = rService.getReviewList(this.post.getId(), 0, 0, new ObjWrapper());
        Assert.assertNotNull(dtoList);
        Assert.assertFalse(dtoList.isEmpty());
        boolean contains = containsReviewContent(dtoList, "OK");
        Assert.assertTrue(contains);
    }

    @Test
    @Transactional(readOnly = false)
    public void testAddComment() {
        ReviewDto dto = new ReviewDto();
        dto.setContent("comment");
        //dto.setJobPostId(post.getId());
        dto.setMemberId(member.getId());

        rService.addComment(member.getId(), this.review.getId(), dto);


        ReviewEntity r = em.createQuery("SELECT r FROM ReviewEntity r WHERE r.content = 'OK'", ReviewEntity.class)
                .getSingleResult();

        List<ReviewEntity> replyList = r.getReplyList();
        Assert.assertNotNull(replyList);
        boolean contains = replyList.stream()
                .anyMatch( (entity) -> {
                    return entity.getContent().equals("comment");
                });

        Assert.assertTrue(contains);
    }

    @Test
    @Transactional(readOnly = false)
    public void testAddReview() {
        ReviewDto dto = new ReviewDto();
        dto.setContent("review");
        dto.setJobPostId(post.getId());
        dto.setMemberId(member.getId());

        rService.addReview(dto);

        ReviewEntity r = em.createQuery("SELECT r FROM ReviewEntity r WHERE r.content = 'review'", ReviewEntity.class)
                .getSingleResult();
        Assert.assertNotNull(r.getMember());
        Assert.assertNotNull(r.getJobPost());
    }

    @Test
    @Transactional(readOnly = false)
    public void testDeleteReview() {
        rService.deleteReview(review.getId());

        try {
            ReviewEntity r = em.createQuery("SELECT r FROM ReviewEntity r WHERE r.content = 'OK'", ReviewEntity.class)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return;
        }

        Assert.assertTrue(false);
    }

    @Test
    @Transactional(readOnly = false)
    public void testUpdateReview() {
        ReviewDto dto = new ReviewDto();
        dto.setContent("NO");

        rService.updateReview(review.getId(), dto);

        em.createQuery("SELECT r FROM ReviewEntity r WHERE r.content = 'NO'", ReviewEntity.class)
                .getSingleResult();
    }

    private boolean containsReviewContent(Collection<ReviewDto> dtoCollection, String content) {
        for (ReviewDto dto : dtoCollection) {
            if (dto.getContent().equals(content)) {
                return true;
            }
        }

        return false;
    }
}

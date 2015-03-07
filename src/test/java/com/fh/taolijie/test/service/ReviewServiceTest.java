package com.fh.taolijie.test.service;

import com.fh.taolijie.domain.JobPostCategoryEntity;
import com.fh.taolijie.domain.JobPostEntity;
import com.fh.taolijie.domain.MemberEntity;
import com.fh.taolijie.service.ReviewService;
import com.fh.taolijie.service.impl.DefaultReviewService;
import com.fh.taolijie.test.BaseDatabaseTestClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;

/**
 * Created by wanghongfei on 15-3-7.
 */
@ContextConfiguration(classes = {DefaultReviewService.class})
public class ReviewServiceTest extends BaseDatabaseTestClass {
    private JobPostEntity post;
    private MemberEntity member;
    private JobPostCategoryEntity cate;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    ReviewService rService;

    @Before
    public void initData() {
        // 创建用户
        // password is 111111
        member = new MemberEntity("Bruce", "3d4f2bf07dc1be38b20cd6e46949a1071f9d0e3d", "", "Neo", "", "", "", "", "", "", 20, "", "");
        em.persist(member);

        // 创建帖子分类
        cate = new JobPostCategoryEntity();
        cate.setName("category");
        em.persist(cate);


        // 创建帖子
        post = new JobPostEntity();
        post.setTitle("a post");
        post.setMember(member);
        em.persist(post);

        // 创建关联
        cate.setJobPostCollection(new ArrayList<>());
        cate.getJobPostCollection().add(post);
        post.setMember(member);
        post.setCategory(cate);

    }
    @Test
    public void test() {
        Assert.assertNotNull(rService);
    }
}

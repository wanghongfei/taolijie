package com.fh.taolijie.test.service.repository;

import com.fh.taolijie.domain.JobPostCategoryEntity;
import com.fh.taolijie.domain.JobPostEntity;
import com.fh.taolijie.domain.MemberEntity;
import com.fh.taolijie.domain.ReviewEntity;
import com.fh.taolijie.service.repository.JobPostRepo;
import com.fh.taolijie.service.repository.MemberRepo;
import com.fh.taolijie.service.repository.NewsRepo;
import com.fh.taolijie.service.repository.ReviewRepo;
import com.fh.taolijie.utils.Print;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by wanghongfei on 15-3-8.
 */

public class NewsRepoTest extends BaseSpringDataTestClass{
    MemberEntity member;
    JobPostCategoryEntity cate1;
    JobPostCategoryEntity cate2;
    JobPostEntity post;
    ReviewEntity review;

    @Autowired
    NewsRepo repo;

    @Autowired
    MemberRepo memberRepo;

    @Autowired
    ReviewRepo reviewRepo;

    @Autowired
    JobPostRepo postRepo;

    @PersistenceContext
    EntityManager em;

    @Before
    public void initData() {
        //repo.save(new NewsEntity("title", "content", "", new Date(), "", null));
        Print.print("准备数据");
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


    }

    @Test
    @Transactional(readOnly = false)
    public void test() {
        /*MemberEntity m = mService.findMember(member.getId());
        int size = m.getMemberRoleCollection().size();

        for (MemberRoleEntity mr : m.getMemberRoleCollection()) {
            Print.print(mr.getRole().getRolename());
        }

        Print.print("---------- size:" + size);*/

        reviewRepo.delete(review.getId());
        postRepo.delete(post.getId());

        //em.createQuery("SELECT post FROM JobPostEntity post WHERE post.title = 'a post'", JobPostEntity.class)
                //.getSingleResult();

        //em.createQuery("SELECT r FROM ReviewEntity r WHERE r.content = 'OK'", ReviewEntity.class)
                //.getSingleResult();
    }

}

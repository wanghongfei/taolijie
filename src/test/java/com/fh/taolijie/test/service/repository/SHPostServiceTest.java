package com.fh.taolijie.test.service.repository;

import com.fh.taolijie.controller.dto.SecondHandPostDto;
import com.fh.taolijie.domain.MemberEntity;
import com.fh.taolijie.domain.SecondHandPostCategoryEntity;
import com.fh.taolijie.domain.SecondHandPostEntity;
import com.fh.taolijie.service.SHPostService;
import com.fh.taolijie.service.impl.DefaultSHPostService;
import com.fh.taolijie.service.impl.DefaultSearchService;
import com.fh.taolijie.service.repository.MemberRepo;
import com.fh.taolijie.service.repository.SHPostCategoryRepo;
import com.fh.taolijie.service.repository.SHPostRepo;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.ObjWrapper;
import com.fh.taolijie.utils.Print;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wanghongfei on 15-3-8.
 */
@ContextConfiguration(classes = {
        DefaultSHPostService.class,
        DefaultSearchService.class
})
public class SHPostServiceTest extends BaseSpringDataTestClass {
    MemberEntity member;
    SecondHandPostCategoryEntity cate1;
    SecondHandPostCategoryEntity cate2;
    SecondHandPostEntity post1;
    SecondHandPostEntity post2;

    @Autowired
    SHPostRepo postRepo;
    @Autowired
    SHPostCategoryRepo cateRepo;
    @Autowired
    MemberRepo memberRepo;
    @Autowired
    SHPostService postService;

    @PersistenceContext
    EntityManager em;

    @Before
    public void initData() throws Exception{
        // 创建用户
        // password is 111111
        member = new MemberEntity("Bruce", "3d4f2bf07dc1be38b20cd6e46949a1071f9d0e3d", "", "Neo", "", "", "", "", "", "", 20, "", "", true, new Date());
        memberRepo.save(member);

        // create category
        cate1 = new SecondHandPostCategoryEntity(1, "cate1", "memo");
        cate2 = new SecondHandPostCategoryEntity(2, "cate2", "memo");
        cateRepo.save(cate1);
        cateRepo.save(cate2);


        // create post
        post1 = new SecondHandPostEntity();
        post1.setTitle("a post");
        post1.setVerified(Constants.VerifyStatus.NONE.toString());
        // set category for this post
        post1.setCategory(cate1);
        post1.setMember(member);
        post1.setComplaint(10);
        // set expired time for this post
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        post1.setExpiredTime(sdf.parse("2014/1/1"));
        // persist post
        postRepo.save(post1);

        post2 = new SecondHandPostEntity();
        post2.setTitle("another post");
        post2.setCategory(cate1);
        post2.setMember(member);
        post2.setExpiredTime(sdf.parse("2016/1/1"));
        post2.setComplaint(0);
        postRepo.save(post2);
        // set post for category-1
        cate1.setPostCollection(new ArrayList<>());
        cate1.getPostCollection().add(post1);
        cate1.getPostCollection().add(post2);
    }

    @Test
    @Transactional(readOnly = true)
    public void tsetGetAllPost() {
        List<SecondHandPostDto> dtoList = postService.getAllPostList(0, 0, new ObjWrapper());
        Assert.assertNotNull(dtoList);
        boolean contains = dtoList.stream().anyMatch( (dto) -> {
            return dto.getTitle().equals("a post");
        });
        Assert.assertTrue(contains);
    }

    @Test
    @Transactional(readOnly = true)
    public void testGetAndFilter() {
        List<SecondHandPostDto> dtoList = postService.getAndFilter(null, true, 0, 0, new ObjWrapper());
        Assert.assertNotNull(dtoList);
        Assert.assertFalse(dtoList.isEmpty());

        Assert.assertTrue(dtoList.stream().anyMatch((dto) -> {
            return dto.getTitle().equals("a post");
        }));

        Assert.assertTrue(dtoList.stream().anyMatch( (dto) -> {
            return dto.getTitle().equals("another post");
        }));
    }

    @Test
    @Transactional(readOnly = true)
    public void testGetByCategory() {
        List<SecondHandPostDto> dtoList = postService.getPostList(cate1.getId(), 0, 0, new ObjWrapper());
        boolean contains = dtoList.stream().anyMatch( (dto) -> {
            return dto.getTitle().equals("a post");
        });
        Assert.assertTrue(contains);
    }

    @Test
    @Transactional(readOnly = true)
    public void testSearch() {
        List<SecondHandPostDto> dtoList = postService.runSearch("title", "post", 0, 0, new ObjWrapper());
        Assert.assertNotNull(dtoList);

        Assert.assertTrue(dtoList.stream().anyMatch((dto) -> {
            return dto.getTitle().equals("another post");
        }));

        Assert.assertTrue(dtoList.stream().anyMatch((dto) -> {
            return dto.getTitle().equals("a post");
        }));
    }

    @Test
    @Transactional(readOnly = true)
    public void testGetSuedPost() {
        List<SecondHandPostDto> dtoList = postService.getSuedPost(0, 0, new ObjWrapper());
        Assert.assertNotNull(dtoList);

        Assert.assertTrue(dtoList.stream().anyMatch( (dto) -> dto.getTitle().equals("a post") ));
        Assert.assertTrue(dtoList.stream().noneMatch( (dto) -> dto.getTitle().equals("another post") ));
    }

    @Test
    @Transactional(readOnly = true)
    public void testGetUnverified() {
        List<SecondHandPostDto> dtoList = postService.getUnverifiedPostList(0, 0, new ObjWrapper());
        Assert.assertNotNull(dtoList);
        Assert.assertEquals(1, dtoList.size());
        Assert.assertTrue(dtoList.stream().anyMatch( (dto) -> dto.getTitle().equals("a post") ));
    }

    @Test
    @Transactional(readOnly = true)
    public void testGetByFilter() {
        List<SecondHandPostDto> dtoList = postService.getPostList(member.getId(), true, 0, 0, new ObjWrapper());
        Assert.assertFalse(dtoList.isEmpty());
        boolean contains = dtoList.stream().anyMatch( (dto) -> {
            return dto.getTitle().equals("another post");
        });
        Assert.assertTrue(contains);

        dtoList = postService.getPostList(member.getId(), false, 0, 0, new ObjWrapper());
        Assert.assertFalse(dtoList.isEmpty());
        contains = dtoList.stream().anyMatch( (dto) -> {
            return dto.getTitle().equals("another post");
        });
        Assert.assertTrue(contains);
    }


    @Test
    @Transactional(readOnly = false)
    public void testComplaint() {
        postService.complaint(post1.getId());
        SecondHandPostDto dto = postService.findPost(post1.getId());
        Assert.assertEquals(1, dto.getComplaint().intValue());


        MemberEntity mem = em.find(MemberEntity.class, member.getId());
        Assert.assertEquals(1, mem.getComplaint().intValue());

        em.flush();
        em.clear();

        postService.complaint(post1.getId());
        dto = postService.findPost(post1.getId());
        Assert.assertEquals(2, dto.getComplaint().intValue());


        mem = em.find(MemberEntity.class, member.getId());
        Assert.assertEquals(2, mem.getComplaint().intValue());
        Print.print("~~~~" + mem.getComplaint().intValue());
    }

    @Test
    @Transactional(readOnly = false)
    public void testAddPost() {
        SecondHandPostDto dto = new SecondHandPostDto();
        dto.setTitle("new post");
        dto.setCategoryId(this.cate1.getId());
        dto.setMemberId(this.member.getId());

        postService.addPost(dto);

        SecondHandPostCategoryEntity cate = cateRepo.findOne(cate1.getId());
        boolean contains = cate.getPostCollection().stream().anyMatch( (d) -> {
            return d.getTitle().equals("new post");
        });
        Assert.assertTrue(contains);
    }

    @Test
    @Transactional(readOnly = false)
    public void testDeletePost() {
        postService.deletePost(post1.getId());

        SecondHandPostEntity pe = postRepo.findOne(post1.getId());
        Assert.assertNull(pe);
    }

    @Test
    @Transactional(readOnly = false)
    public void testUpdatePost() {
        SecondHandPostDto dto = new SecondHandPostDto();
        dto.setTitle("changed post");
        postService.updatePost(this.post1.getId(), dto);

        SecondHandPostEntity pe = postRepo.findOne(post1.getId());
        Assert.assertEquals("changed post", pe.getTitle());
    }

    @Test
    @Transactional(readOnly = false)
    public void testChangeCategory() {
        postService.changeCategory(this.post1.getId(), cate2.getId());

        SecondHandPostCategoryEntity cate = cateRepo.findOne(cate2.getId());
        Assert.assertFalse(cate.getPostCollection().isEmpty());
        boolean contains = cate.getPostCollection().stream().anyMatch( (d) -> {
            return d.getTitle().equals("a post");
        });
        Assert.assertTrue(contains);
    }
}

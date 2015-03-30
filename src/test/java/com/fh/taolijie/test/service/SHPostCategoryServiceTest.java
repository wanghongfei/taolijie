package com.fh.taolijie.test.service;

import com.fh.taolijie.controller.dto.SecondHandPostCategoryDto;
import com.fh.taolijie.domain.MemberEntity;
import com.fh.taolijie.domain.SecondHandPostCategoryEntity;
import com.fh.taolijie.domain.SecondHandPostEntity;
import com.fh.taolijie.exception.checked.CascadeDeleteException;
import com.fh.taolijie.service.SHPostCategoryService;
import com.fh.taolijie.service.impl.DefaultSHPostCategoryService;
import com.fh.taolijie.service.repository.MemberRepo;
import com.fh.taolijie.service.repository.SHPostCategoryRepo;
import com.fh.taolijie.service.repository.SHPostRepo;
import com.fh.taolijie.test.service.repository.BaseSpringDataTestClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by wanghongfei on 15-3-8.
 */
@ContextConfiguration(classes = {
        DefaultSHPostCategoryService.class
})
public class SHPostCategoryServiceTest extends BaseSpringDataTestClass {
    MemberEntity member;
    SecondHandPostCategoryEntity cate;
    SecondHandPostEntity post;


    @Autowired
    SHPostCategoryService cateService;

    @Autowired
    SHPostCategoryRepo cateRepo;

    @Autowired
    SHPostRepo postRepo;
    @Autowired
    MemberRepo memberRepo;

    @Before
    public void initData() {
        // create user
        // password is 111111
        member = new MemberEntity("Bruce", "3d4f2bf07dc1be38b20cd6e46949a1071f9d0e3d", "", "Neo", "", "", "", "", "", "", 20, "", "", true, new Date());
        memberRepo.save(member);


        // create category
        cate = new SecondHandPostCategoryEntity();
        cate.setName("category");
        cateRepo.save(cate);

        // create post
        post = new SecondHandPostEntity();
        post.setTitle("post");
        post.setMember(member);
        post.setCategory(cate);
        postRepo.save(post);

        // create connection
        cate.setPostCollection(new ArrayList<>());
        cate.getPostCollection().add(post);

    }

    @Test
    public void test() {
        Assert.assertNotNull(post.getId());
    }

    @Test
    @Transactional(readOnly = true)
    public void testGetCategory() {
        List<SecondHandPostCategoryDto> dtoList = cateService.getCategoryList(0, 0);
        Assert.assertTrue(containsCategoryName(dtoList, "category"));
    }

    @Test
    @Transactional(readOnly = true)
    public void testFind() {
        SecondHandPostCategoryDto dto = cateService.findCategory(cate.getId());
        Assert.assertEquals("category", dto.getName());
    }

    @Test
    @Transactional(readOnly = false)
    public void testUpdateCategory() {
        SecondHandPostCategoryDto dto = new SecondHandPostCategoryDto();
        dto.setName("new category");
        cateService.updateCategory(cate.getId(), dto);

        SecondHandPostCategoryEntity ce = cateRepo.findOne(cate.getId());
        Assert.assertNotNull(ce);
        Assert.assertEquals("new category", ce.getName());
    }

    @Test
    @Transactional(readOnly = false)
    public void testDeleteCate() {
        try {
            cateService.deleteCategory(cate.getId());
        } catch (CascadeDeleteException e) {
            return;
        }

        Assert.assertTrue(false);
    }

    private boolean containsCategoryName(Collection<SecondHandPostCategoryDto> dtoCollection, String name) {
        for (SecondHandPostCategoryDto dto : dtoCollection) {
            if (dto.getName().equals(name)) {
                return true;
            }
        }

        return false;
    }
}

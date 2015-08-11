package com.fh.taolijie.test.dao.mapper;

import com.fh.taolijie.dao.mapper.ShPostModelMapper;
import com.fh.taolijie.domain.JobPostModel;
import com.fh.taolijie.domain.SHPostModel;
import com.fh.taolijie.domain.SHPostModel;
import com.fh.taolijie.service.ShPostService;
import com.fh.taolijie.service.impl.DefaultShPostService;
import com.fh.taolijie.test.BaseSpringDataTestClass;
import com.fh.taolijie.utils.Constants;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

/**
 * Created by wanghongfei on 15-6-6.
 */
@ContextConfiguration(classes = {
        DefaultShPostService.class
})
public class ShPostServiceTest extends BaseSpringDataTestClass {
    @Autowired
    ShPostModelMapper postMapper;

    @Autowired
    ShPostService postService;

    @Test
    public void testGetGetAll() {
    }

    /*@Test
    public void testGetBy() {
        List<SHPostModel> list = postService.getPostList(1, 0 ,100, null);
        Assert.assertFalse(list.isEmpty());

        list = postService.getPostList(1, false, 0, 100, null);
        Assert.assertFalse(list.isEmpty());

        list = postService.getAndFilter(1, true, 0,100 ,null);

        SHPostModel model = new SHPostModel();
        model.setMemberId(1);

        list = postService.runSearch(model, null);
        Assert.assertFalse(list.isEmpty());

        model.setMemberId(null);
        model.setVerified(Constants.VerifyStatus.NONE.toString());
        list = postService.getUnverifiedPostList(model, null);
        //Assert.assertTrue(list.isEmpty());

        list = postService.getSuedPost(0, 100, null);
        Assert.assertTrue(list.isEmpty());
    }

    @Test
    public void testAdd() {
        SHPostModel post = new SHPostModel();
        post.setMemberId(1);
        post.setSecondHandPostCategoryId(1);
        post.setComplaint(100);
        postService.addPost(post);
    }

    @Test
    public void testFavorite() {
        postService.favoritePost(1, 1);
        Assert.assertTrue(postService.isPostAlreadyFavorite(1, 1));
        postService.unfavoritePost(1, 1);

        List<SHPostModel> list = postService.getFavoritePost(1);
    }

    @Test
    public void testUPdatePost() {
        SHPostModel model = new SHPostModel();
        model.setId(1);
        model.setTitle("hello");
        postService.updatePost(1, model);
    }

    @Test
    public void testComplaint() {
        postService.complaint(1);

        SHPostModel post = postMapper.selectByPrimaryKey(1);
        //Assert.assertEquals(1, post.getComplaint().intValue());
    }

    @Test
    public void testChangeCategory() {
        postService.changeCategory(1, 2);

        SHPostModel post = postMapper.selectByPrimaryKey(1);
        Assert.assertEquals(2, post.getSecondHandPostCategoryId().intValue());
    }

    @Test
    public void testDelete() {
        postService.deletePost(1);
    }

    @Test
    public void testRunSearch() {
        SHPostModel model = new SHPostModel();
        model.setPageNumber(0);
        model.setPageSize(100);
        model.setTitle("二");
        //model.setTitle("i");
        List<SHPostModel> list = postService.runSearch(model, null);

        //Assert.assertFalse(list.isEmpty());
    }*/
}

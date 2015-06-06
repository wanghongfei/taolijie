package com.fh.taolijie.test.dao.mapper;

import com.fh.taolijie.dao.mapper.ShPostModelMapper;
import com.fh.taolijie.domain.SHPostModel;
import com.fh.taolijie.service.ShPostService;
import com.fh.taolijie.service.impl.DefaultShPostService;
import com.fh.taolijie.test.BaseSpringDataTestClass;
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
        List<SHPostModel>  list = postService.getAllPostList(0, Integer.MAX_VALUE, null);
        Assert.assertFalse(list.isEmpty());
    }
}

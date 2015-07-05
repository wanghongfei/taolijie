package com.fh.taolijie.test.dao.mapper;

import com.fh.taolijie.dao.mapper.ReviewModelMapper;
import com.fh.taolijie.domain.ReviewModel;
import com.fh.taolijie.service.ReviewService;
import com.fh.taolijie.service.impl.DefaultReviewService;
import com.fh.taolijie.test.BaseSpringDataTestClass;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

/**
 * Created by wanghongfei on 15-6-17.
 */
@ContextConfiguration(classes = {
        DefaultReviewService.class
})
public class ReviewServiceTest extends BaseSpringDataTestClass {
    @Autowired
    ReviewModelMapper revMapper;
    @Autowired
    ReviewService service;

    @Test
    public void testGet() {
/*        List<ReviewModel> list = service.getReviewList(1, 0, 100).getList();
        Assert.assertFalse(list.isEmpty());
        boolean hasMember = list.stream().allMatch(rev -> rev.getMember() != null && rev.getMember().getName().isEmpty() == false);
        Assert.assertTrue(hasMember);*/
    }
}

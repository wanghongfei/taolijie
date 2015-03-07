package com.fh.taolijie.test.service.repository;

import com.fh.taolijie.service.repository.NewsRepo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by wanghongfei on 15-3-8.
 */

//@ContextConfiguration(classes = {
//})
public class NewsRepoTest extends BaseSpringDataTestClass{
    @Autowired
    NewsRepo repo;

    @Test
    public void test() {
        Assert.assertNotNull(repo);
    }
}

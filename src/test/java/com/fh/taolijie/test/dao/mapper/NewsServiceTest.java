package com.fh.taolijie.test.dao.mapper;

import com.fh.taolijie.dao.mapper.NewsModelMapper;
import com.fh.taolijie.domain.NewsModel;
import com.fh.taolijie.service.NewsService;
import com.fh.taolijie.service.impl.DefaultNewsService;
import com.fh.taolijie.test.BaseSpringDataTestClass;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.Date;
import java.util.List;

/**
 * Created by wanghongfei on 15-6-17.
 */
@ContextConfiguration(classes = {
        DefaultNewsService.class
})
public class NewsServiceTest extends BaseSpringDataTestClass {
    @Autowired
    NewsModelMapper newsMapper;
    @Autowired
    NewsService service;

    @Test
    public void testGet() {
/*        List<NewsModel> list = service.getNewsList(0, 100, null);
        Assert.assertFalse(list.isEmpty());

        list = service.getNewsList(new Date(), 0, 100, null);
        Assert.assertTrue(list.isEmpty());

        NewsModel news = service.findNews(1);
        Assert.assertNotNull(news);
        Assert.assertEquals("title1", news.getTitle());
        Assert.assertEquals("huangmian", news.getMember().getUsername());*/
    }

/*    @Test
    public void testAdd() {
        NewsModel news = new NewsModel();
        news.setTitle("news");
        service.addNews(news);
    }

    @Test
    public void testUpdate() {
        NewsModel news = new NewsModel();
        news.setId(1);
        news.setTitle("new s");
        service.updateNews(1, news);
    }*/

/*    @Test
    public void testDelete() {
        service.deleteNews(1);
    }*/
}

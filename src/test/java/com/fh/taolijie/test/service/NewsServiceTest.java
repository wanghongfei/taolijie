package com.fh.taolijie.test.service;

import com.fh.taolijie.domain.MemberEntity;
import com.fh.taolijie.domain.NewsEntity;
import com.fh.taolijie.service.NewsService;
import com.fh.taolijie.service.impl.DefaultNewsService;
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
 * Created by wanghongfei on 15-3-6.
 */
@ContextConfiguration(classes = {DefaultNewsService.class})
public class NewsServiceTest extends BaseDatabaseTestClass {
    private NewsEntity news;
    private MemberEntity member;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private NewsService newsService;

    @Before
    public void initData() {
        // 创建用户
        // password is 111111
        member = new MemberEntity("Bruce", "3d4f2bf07dc1be38b20cd6e46949a1071f9d0e3d", "", "Neo", "", "", "", "", "", "", 20, "", "");
        em.persist(member);

        // 创建新闻
        news = new NewsEntity();
        news.setTitle("news");
        em.persist(news);

        // 设置关联
        news.setMember(member);
        member.setNewsCollection(new ArrayList<>());
        member.getNewsCollection().add(news);

    }

    @Test
    public void testGet() {
        Assert.assertNotNull(newsService);
    }
}

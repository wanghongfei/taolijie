package com.fh.taolijie.test.service;

import com.fh.taolijie.controller.dto.NewsDto;
import com.fh.taolijie.domain.MemberEntity;
import com.fh.taolijie.domain.NewsEntity;
import com.fh.taolijie.service.NewsService;
import com.fh.taolijie.service.impl.DefaultNewsService;
import com.fh.taolijie.test.service.repository.BaseSpringDataTestClass;
import com.fh.taolijie.utils.ObjWrapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by wanghongfei on 15-3-6.
 */
@ContextConfiguration(classes = {DefaultNewsService.class})
public class NewsServiceTest extends BaseSpringDataTestClass {
    private NewsEntity newsBefore;
    private NewsEntity newsLater;
    private MemberEntity member;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private NewsService newsService;

    @Before
    public void initData() throws Exception{
        // 创建用户
        // password is 111111
        member = new MemberEntity("Bruce", "3d4f2bf07dc1be38b20cd6e46949a1071f9d0e3d", "", "Neo", "", "", "", "", "", "", 20, "", "", true, new Date());
        em.persist(member);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        // 创建新闻
        newsBefore = new NewsEntity();
        newsBefore.setTitle("newsBefore");
        newsBefore.setTime(sdf.parse("2001/1/1"));
        em.persist(newsBefore);
        newsLater = new NewsEntity();
        newsLater.setTitle("newsLater");
        newsLater.setTime(sdf.parse("2015/1/1"));
        em.persist(newsLater);

        // 设置关联
        newsBefore.setMember(member);
        newsLater.setMember(member);
        member.setNewsCollection(new ArrayList<>());
        member.getNewsCollection().add(newsBefore);
        member.getNewsCollection().add(newsLater);

        em.flush();
        em.clear();
    }

    @Test
    @Transactional(readOnly = true)
    public void testGet() throws Exception {
        List<NewsDto> dtoList = newsService.getNewsList(0, 0, new ObjWrapper());

        // 测试是否包含
        boolean contains = containsNews(dtoList, "newsBefore");
        Assert.assertTrue(contains);
        contains = containsNews(dtoList, "newsLater");
        Assert.assertTrue(contains);

       /*dtoList.stream()
                .forEach( (dto) -> {
                    Print.print(dto.getTitle() + ", " + dto.getTime());
                });*/
       /* for (NewsDto dto : dtoList) {
            Print.print(dto.getTitle() + ", " + dto.getTime());
        }*/

        // 测试是否最新的新闻在前
        boolean sorted = isSortedByTime(dtoList);
        Assert.assertTrue(sorted);


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        dtoList = newsService.getNewsList(sdf.parse("2010/1/1"), 0, 0, new ObjWrapper());
        Assert.assertEquals(1, dtoList.size());
        contains = containsNews(dtoList, "newsLater");
        Assert.assertTrue(contains);

    }

    @Test
    @Transactional(readOnly = false)
    public void testUpdateNews() {
        NewsDto dto = new NewsDto();
        dto.setTitle("new news");
        dto.setTime(new Date());

        newsService.updateNews(this.newsBefore.getId(), dto);
        em.createQuery("SELECT news FROM NewsEntity news WHERE news.title = :title", NewsEntity.class)
                .setParameter("title", "new news")
                .getSingleResult();
    }

    @Test
    public void testFindNews() {
        NewsDto dto = newsService.findNews(this.newsBefore.getId());
        Assert.assertNotNull(dto);
        Assert.assertEquals("newsBefore", dto.getTitle());
    }

    @Test
    @Transactional(readOnly = false)
    public void testAddNews() {
        NewsDto dto = new NewsDto();
        dto.setTitle("one news");
        dto.setMemberId(member.getId());
        newsService.addNews(dto);

        em.createQuery("SELECT news FROM NewsEntity news WHERE news.title = :title", NewsEntity.class)
                .setParameter("title", "one news")
                .getSingleResult();

        int size = em.find(MemberEntity.class, this.member.getId())
                .getNewsCollection()
                .size();

        Assert.assertEquals(3, size);
    }

    @Test
    @Transactional(readOnly = false)
    public void testDeleteNews() {
        newsService.deleteNews(this.newsBefore.getId());

        int size = em.find(MemberEntity.class, this.member.getId())
                .getNewsCollection()
                .size();
        Assert.assertEquals(1, size);

        try {
            em.createQuery("SELECT news FROM NewsEntity news WHERE news.title = :title", NewsEntity.class)
                    .setParameter("title", "newsBefore")
                    .getSingleResult();
        } catch (NoResultException ex) {
            return;
        }

        Assert.assertTrue(false);
    }

    private boolean containsNews(Collection<NewsDto> dtoCollection, String newsTitle) {
        for (NewsDto dto : dtoCollection) {
            if (null != dto.getTitle() && dto.getTitle().equals(newsTitle)) {
                return true;
            }
        }

        return false;
    }

    private boolean isSortedByTime(List<NewsDto> dtoList) {
        int len = dtoList.size();
        if (1 == len) {
            return true;
        }

        for (int ix = 1 ; ix < len ; ++ix) {
            if (dtoList.get(ix).getTime().getTime() > dtoList.get(ix - 1).getTime().getTime()) {
                return false;
            }
        }

        return true;
    }
}

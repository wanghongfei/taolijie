package com.fh.taolijie.service.impl;

import com.fh.taolijie.controller.dto.NewsDto;
import com.fh.taolijie.domain.MemberEntity;
import com.fh.taolijie.domain.NewsEntity;
import com.fh.taolijie.service.NewsService;
import com.fh.taolijie.utils.CollectionUtils;
import com.fh.taolijie.utils.Constants;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wanghongfei on 15-3-6.
 */
@Repository
public class DefaultNewsService implements NewsService {
    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional(readOnly = true)
    public List<NewsDto> getNewsList(int firstResult, int capacity) {
        int cap = capacity;
        if (0 == capacity) {
            cap = Constants.PAGE_CAPACITY;
        }

        List<NewsEntity> newsList = em.createNamedQuery("newsEntity.findAll")
                .setFirstResult(firstResult)
                .setMaxResults(cap)
                .getResultList();

        List<NewsDto> dtoList = new ArrayList<>();
        for (NewsEntity news : newsList) {
            //dtoList.add(makeNewsDto(news));
            dtoList.add(CollectionUtils.entity2Dto(news, NewsDto.class, (dto) -> {
                dto.setMemberId(news.getMember().getId());
            }));
        }

        return dtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<NewsDto> getNewsList(Date uptime, int firstResult, int capacity) {
        int cap = capacity;
        if (0 == cap) {
            cap = Constants.PAGE_CAPACITY;
        }

        List<NewsEntity> newsList = em.createNamedQuery("newsEntity.findByDate", NewsEntity.class)
                .setParameter("date", uptime)
                .setFirstResult(firstResult)
                .setMaxResults(cap)
                .getResultList();

        List<NewsDto> dtoList = new ArrayList<>();
        for (NewsEntity news : newsList) {
            //dtoList.add(CollectionUtils.entity2Dto(news, NewsDto.class, null));
            dtoList.add(CollectionUtils.entity2Dto(news, NewsDto.class, (dto) -> {
                dto.setMemberId(news.getMember().getId());
            }));
        }

        return dtoList;
    }


    @Override
    @Transactional(readOnly = true)
    public NewsDto findNews(Integer newsId) {
        NewsEntity news = em.find(NewsEntity.class, newsId);
        return CollectionUtils.entity2Dto(news, NewsDto.class, (dto) -> dto.setMemberId(news.getMember().getId()) );
        //return makeNewsDto(news);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public boolean updateNews(Integer newsId, NewsDto newsDto) {
        NewsEntity news = em.find(NewsEntity.class, newsId);
        CollectionUtils.updateEntity(news, newsDto, null);
        //updateNewsDto(news, newsDto);

        return true;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void addNews(NewsDto dto) {
        NewsEntity entity = CollectionUtils.dto2Entity(dto, NewsEntity.class, (newsEntity) -> {
            MemberEntity mem = em.getReference(MemberEntity.class, dto.getMemberId());
            newsEntity.setMember(mem);

            // 设置关联
            List<NewsEntity> newsList = CollectionUtils.addToCollection(mem.getNewsCollection(), newsEntity);
            if (null != newsList) {
                mem.setNewsCollection(newsList);
            }
        });

        //em.persist(makeNews(dto));
        em.persist(entity);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public boolean deleteNews(Integer newsId) {
        NewsEntity news = em.getReference(NewsEntity.class, newsId);

        CollectionUtils.removeFromCollection(news.getMember().getNewsCollection(), (newsEntity) -> {
            return newsEntity.getId().equals(newsId);
        });

        em.remove(news);

        return true;
    }

    /*private NewsEntity makeNews(NewsDto dto) {
        NewsEntity news = new NewsEntity(dto.getTitle(), dto.getContent(), dto.getPicturePath(),
                dto.getTime(), dto.getHeadPicturePath(), null);

        news.setMember(em.getReference(MemberEntity.class, dto.getMemberId()));

        return news;
    }*/

    /*private NewsDto makeNewsDto(NewsEntity news) {
        NewsDto dto = new NewsDto(news.getTitle(), news.getContent(), news.getPicturePath(),
                news.getTime(), news.getHeadPicturePath(), null);

        dto.setId(news.getId());
        dto.setMemberId(news.getMember().getId());

        return dto;
    }*/

    /**
     * 不更新time和memberId
     * @param news
     * @param dto
     */
    /*private void updateNewsDto(NewsEntity news, NewsDto dto) {
        news.setTitle(dto.getTitle());
        news.setTime(dto.getTime());
        news.setContent(dto.getContent());
        news.setPicturePath(dto.getPicturePath());
        news.setHeadPicturePath(dto.getHeadPicturePath());
    }*/
}

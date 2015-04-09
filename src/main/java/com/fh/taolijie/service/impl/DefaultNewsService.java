package com.fh.taolijie.service.impl;

import com.fh.taolijie.controller.dto.NewsDto;
import com.fh.taolijie.domain.MemberEntity;
import com.fh.taolijie.domain.NewsEntity;
import com.fh.taolijie.service.NewsService;
import com.fh.taolijie.service.repository.NewsRepo;
import com.fh.taolijie.utils.CheckUtils;
import com.fh.taolijie.utils.CollectionUtils;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.ObjWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    @Autowired
    NewsRepo newsRepo;

    @Override
    @Transactional(readOnly = true)
    public List<NewsDto> getNewsList(int firstResult, int capacity, ObjWrapper wrapper) {
        int cap = capacity;
        if (0 == capacity) {
            cap = Constants.PAGE_CAPACITY;
        }

        Page<NewsEntity> newsList = newsRepo.findAll(new PageRequest(firstResult, cap));
        wrapper.setObj(newsList.getTotalPages());

        return CollectionUtils.transformCollection(newsList, NewsDto.class, entity -> {
            return CollectionUtils.entity2Dto(entity, NewsDto.class, dto -> {
                dto.setMemberId(entity.getMember().getId());
            });
        });

    }

    @Override
    @Transactional(readOnly = true)
    public List<NewsDto> getNewsList(Date uptime, int firstResult, int capacity, ObjWrapper wrapper) {
        int cap = CollectionUtils.determineCapacity(capacity);

        Page<NewsEntity> newsList = newsRepo.findByDate(uptime, new PageRequest(firstResult, cap));
        wrapper.setObj(newsList.getTotalPages());

        List<NewsDto> dtoList = new ArrayList<>();
        for (NewsEntity news : newsList) {
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
        CheckUtils.nullCheck(news);

        return CollectionUtils.entity2Dto(news, NewsDto.class, (dto) -> dto.setMemberId(news.getMember().getId()) );
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public boolean updateNews(Integer newsId, NewsDto newsDto) {
        NewsEntity news = em.find(NewsEntity.class, newsId);
        CheckUtils.nullCheck(news);

        CollectionUtils.updateEntity(news, newsDto, null);

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

        em.persist(entity);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public boolean deleteNews(Integer newsId) {
        NewsEntity news = em.getReference(NewsEntity.class, newsId);
        CheckUtils.nullCheck(news);

        CollectionUtils.removeFromCollection(news.getMember().getNewsCollection(), (newsEntity) -> {
            return newsEntity.getId().equals(newsId);
        });

        em.remove(news);

        return true;
    }

}

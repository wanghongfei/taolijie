package com.fh.taolijie.service.impl;

import com.fh.taolijie.dao.mapper.NewsModelMapper;
import com.fh.taolijie.domain.NewsModel;
import com.fh.taolijie.service.NewsService;
import com.fh.taolijie.utils.CollectionUtils;
import com.fh.taolijie.utils.ObjWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by wanghongfei on 15-6-6.
 */
@Service
@Transactional(readOnly = true)
public class DefaultNewsService implements NewsService {
    @Autowired
    NewsModelMapper newsMapper;

    @Override
    public List<NewsModel> getNewsList(int firstResult, int capacity, ObjWrapper wrapper) {
        return newsMapper.getAll(firstResult, CollectionUtils.determineCapacity(capacity));
    }

    @Override
    public List<NewsModel> getNewsList(Date uptime, int firstResult, int capacity, ObjWrapper wrapper) {
        return newsMapper.getByInterval(uptime, new Date(), firstResult, CollectionUtils.determineCapacity(capacity));
    }

    @Override
    public NewsModel findNews(Integer newsId) {
        return newsMapper.selectByPrimaryKey(newsId);
    }

    @Override
    @Transactional(readOnly = false)
    public void addNews(NewsModel model) {
        newsMapper.insert(model);
    }

    @Override
    @Transactional(readOnly = false)
    public boolean updateNews(Integer newsId, NewsModel model) {
        model.setId(newsId);
        return newsMapper.updateByPrimaryKeySelective(model) <= 0 ? false : true;
    }

    @Override
    @Transactional(readOnly = false)
    public boolean deleteNews(Integer newsId) {
        return newsMapper.deleteByPrimaryKey(newsId) <= 0 ? false : true;
    }
}

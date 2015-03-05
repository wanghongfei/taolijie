package com.fh.taolijie.service;

import com.fh.taolijie.controller.dto.NewsDto;

import java.util.Date;
import java.util.List;

/**
 * Created by wanghongfei on 15-3-5.
 */
public interface NewsService {
    /**
     * 得到所有新闻
     * @param amount 一次获取的数量. 传递0为获取全部.
     * @return
     */
    List<NewsDto> getNewsList(int amount);

    /**
     * 获取从<code>uptime</code>到现在的所有新闻
     * @param uptime
     * @return
     */
    List<NewsDto> getNewsList(Date uptime);

    /**
     * 根据id查找新闻
     * @param newsId
     * @return
     */
    NewsDto findNews(Integer newsId);

    /**
     * 修改新闻信息
     * @param newsId
     * @param newsDto
     * @return
     */
    boolean updateNews(Integer newsId, NewsDto newsDto);

    /**
     * 删除一条新闻
     * @param newsId
     * @return
     */
    boolean deleteNews(Integer newsId);

    /**
     * 将新闻在发布时间的基础上按优先级排序
     * @param newsList 要排序的新闻
     */
    void sortByPriority(List<NewsDto> newsList);
}

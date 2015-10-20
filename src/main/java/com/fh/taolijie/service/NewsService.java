package com.fh.taolijie.service;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.domain.NewsModel;

import java.util.Date;

/**
 * Created by wanghongfei on 15-3-5.
 */
public interface NewsService {
    /**
     * 得到所有新闻
     * @return
     */
    ListResult<NewsModel> getNewsList(int firstResult, int capacity);

    /**
     * 获取从<code>uptime</code>到现在的所有新闻
     * @param uptime
     * @return
     */
    ListResult<NewsModel> getNewsList(Date uptime, int firstResult, int capacity);

    /**
     * 根据id查找新闻
     * @param newsId
     * @return
     */
    NewsModel findNews(Integer newsId);

    /**
     * 创建新闻
     * @param dto
     */
    void addNews(NewsModel model);

    /**
     * 修改新闻信息. 只修改新闻本身,无法修改所属用户和创建时间
     * @return
     */
    boolean updateNews(NewsModel model);

    /**
     * 删除一条新闻
     * @param newsId
     * @return
     */
    boolean deleteNews(Integer newsId);

    //void sortByPriority(List<NewsDto> newsList);
}

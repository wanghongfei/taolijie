package com.fh.taolijie.service;

import com.fh.taolijie.domain.NewsModel;
import com.fh.taolijie.domain.NewsModelWithBLOBs;
import com.fh.taolijie.utils.ObjWrapper;

import java.util.Date;
import java.util.List;

/**
 * Created by wanghongfei on 15-3-5.
 */
public interface NewsService {
    /**
     * 得到所有新闻
     * @return
     */
    List<NewsModelWithBLOBs> getNewsList(int firstResult, int capacity, ObjWrapper wrapper);

    /**
     * 获取从<code>uptime</code>到现在的所有新闻
     * @param uptime
     * @return
     */
    List<NewsModelWithBLOBs> getNewsList(Date uptime, int firstResult, int capacity, ObjWrapper wrapper);

    /**
     * 根据id查找新闻
     * @param newsId
     * @return
     */
    NewsModelWithBLOBs findNews(Integer newsId);

    /**
     * 创建新闻
     * @param dto
     */
    void addNews(NewsModelWithBLOBs model);

    /**
     * 修改新闻信息. 只修改新闻本身,无法修改所属用户和创建时间
     * @param newsId
     * @param newsDto
     * @return
     */
    boolean updateNews(Integer newsId, NewsModelWithBLOBs model);

    /**
     * 删除一条新闻
     * @param newsId
     * @return
     */
    boolean deleteNews(Integer newsId);

    //void sortByPriority(List<NewsDto> newsList);
}

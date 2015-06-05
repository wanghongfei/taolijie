package com.fh.taolijie.dao.mapper;

import com.fh.taolijie.domain.NewsModel;

import java.util.List;

/**
 * Created by wanghongfei on 15-6-5.
 */
public interface NewsMapper {
    NewsModel getNewsById(Integer newsId);

    /**
     * Used fields: timePoint
     * @return
     */
    List<NewsModel> getNewsAfterTime(NewsModel model);


    /**
     * Used fields: startTime, endTime
     * @return
     */
    List<NewsModel> getNewsByTimeRange(NewsModel model);

    /**
     * Used fields: memberId
     * @return
     */
    List<NewsModel> getNewsByMember(NewsModel model);

    void addNews(NewsModel model);

    void updateBySelective(NewsModel model);

    void deleteInBatch(List<Integer> idList);
}

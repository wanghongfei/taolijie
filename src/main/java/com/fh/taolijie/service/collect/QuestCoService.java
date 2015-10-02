package com.fh.taolijie.service.collect;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.domain.QuestCoModel;

/**
 * Created by whf on 10/3/15.
 */
public interface QuestCoService {
    /**
     * 添加收藏
     */
    void collect(Integer memId, Integer questId);

    /**
     * 条件查询
     * @param example
     * @return
     */
    ListResult<QuestCoModel> findBy(QuestCoModel example);

    /**
     * 判断是否已经收藏
     * @param memId
     * @param questId
     * @return
     */
    boolean collected(Integer memId, Integer questId);

    /**
     * 取消收藏
     * @param memId
     * @param questId
     */
    void unCollect(Integer memId, Integer questId);
}

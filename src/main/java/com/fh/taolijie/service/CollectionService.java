package com.fh.taolijie.service;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.constant.PostType;
import com.fh.taolijie.domain.acc.CollectionModel;
import com.fh.taolijie.domain.acc.CollectionModelExample;

/**
 * 与我的收藏相关的操作
 * Created by whf on 8/22/15.
 */
public interface CollectionService {
    /**
     * 条件查询
     * @param example
     * @return
     */
    ListResult<CollectionModel> findBy(CollectionModelExample example);

    /**
     * 判断是否已经收藏了
     * @return
     */
    boolean alreadyCollected(Integer memberId, Integer postId, PostType type);

    /**
     * 收藏
     * @param memberId
     * @param postId
     * @param type
     */
    void collect(Integer memberId, Integer postId, PostType type);

    /**
     * 取消收藏
     * @param memId
     * @param postId
     * @param type
     */
    void cancelCollect(Integer memId, Integer postId, PostType type);
}

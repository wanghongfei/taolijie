package com.fh.taolijie.service.impl;

import com.fh.taolijie.dao.mapper.ReviewModelMapper;
import com.fh.taolijie.domain.ReviewModel;
import com.fh.taolijie.service.ReviewService;
import com.fh.taolijie.utils.CollectionUtils;
import com.fh.taolijie.utils.ObjWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by wanghongfei on 15-6-6.
 */
@Service
@Transactional(readOnly = true)
public class DefaultReviewService implements ReviewService {
    @Autowired
    ReviewModelMapper reMapper;

    @Override
    public List<ReviewModel> getReviewList(Integer postId, int firstResult, int capacity, ObjWrapper wrapper) {
        ReviewModel model = new ReviewModel(firstResult, CollectionUtils.determineCapacity(capacity));
        model.setPostId(postId);

        return reMapper.findBy(model);
    }

    @Override
    public ReviewModel getById(Integer reviewId) {
        return reMapper.selectByPrimaryKey(reviewId);
    }

    @Override
    @Transactional(readOnly = false)
    public Integer addReview(ReviewModel model) {
        return reMapper.insert(model);
    }

    @Override
    @Transactional(readOnly = false)
    public void addComment(Integer memId, Integer reviewId, ReviewModel model) {
        model.setMemberId(memId);
        model.setRepliedReviewId(reviewId);

        reMapper.insert(model);
    }

    @Override
    @Transactional(readOnly = false)
    public boolean deleteReview(Integer reviewId) {
        int row = reMapper.deleteByPrimaryKey(reviewId);

        return row <= 0 ? false : true;
    }

    @Override
    @Transactional(readOnly = false)
    public boolean updateReview(Integer reviewId, ReviewModel model) {
        int row = reMapper.updateByPrimaryKeySelective(model);

        return row <= 0 ? false : true;
    }
}

package com.fh.taolijie.service.impl;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.dao.mapper.ReviewModelMapper;
import com.fh.taolijie.domain.ReviewModel;
import com.fh.taolijie.service.ReviewService;
import com.fh.taolijie.utils.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by wanghongfei on 15-6-6.
 */
@Service
public class DefaultReviewService implements ReviewService {
    @Autowired
    ReviewModelMapper reMapper;

    @Override
    @Transactional(readOnly = true)
    public ListResult<ReviewModel> getReviewList(ReviewModel model) {
        List<ReviewModel> list = reMapper.findBy(model);
        int count = reMapper.countFindBy(model);
        return new ListResult<>(list, count);
    }

    @Override
    @Transactional(readOnly = true)
    public ListResult<ReviewModel> getReply(Integer reviewId, int pageNumber, int pageSize) {
        ReviewModel cmd = new ReviewModel(pageNumber, pageSize);
        cmd.setRepliedReviewId(reviewId);

        List<ReviewModel> list = reMapper.findBy(cmd);
        int count = reMapper.countFindBy(cmd);

        return new ListResult<>(list, count);
    }

    @Override
    @Transactional(readOnly = false)
    public int deleteReplyByReview(Integer resumeId) {
        return reMapper.deleteReplyByReviewId(resumeId);
    }

    @Override
    @Transactional(readOnly = true)
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
    public void addComment(ReviewModel model) {
        reMapper.insertSelective(model);
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

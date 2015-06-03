package com.fh.taolijie.dao.mapper;

import com.fh.taolijie.domain.ReviewModel;

import java.util.List;

/**
 * Created by wanghongfei on 15-5-31.
 */
public interface ReviewMapper {
    List<ReviewModel> getReviewByIdInBatch(List<Integer> idList);
    List<ReviewModel> getReviewByJobPost(ReviewModel model);

    void addReview(ReviewModel model);

    /**
     * Used filed: id, content
     */
    void updateContent(ReviewModel model);

    void deleteReviewByIdInBatch(List<Integer> idList);
}

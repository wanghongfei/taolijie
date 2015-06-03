package com.fh.taolijie.dao.mapper;

import com.fh.taolijie.domain.ReviewModel;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by wanghongfei on 15-5-31.
 */
@Repository
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

package com.fh.taolijie.service.impl;

import com.fh.taolijie.controller.dto.ReviewDto;
import com.fh.taolijie.domain.JobPostEntity;
import com.fh.taolijie.domain.MemberEntity;
import com.fh.taolijie.domain.ReviewEntity;
import com.fh.taolijie.service.ReviewService;
import com.fh.taolijie.utils.Constants;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by wanghongfei on 15-3-7.
 */
@Repository
public class DefaultReviewService implements ReviewService {
    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional(readOnly = true)
    public List<ReviewDto> getReviewList(Integer postId, int firstResult, int capacity) {
        JobPostEntity jobPost = em.getReference(JobPostEntity.class, postId);

        int cap = capacity;
        if (cap <= 0) {
            cap = Constants.PAGE_CAPACITY;
        }

        List<ReviewEntity> reviewList = em.createNamedQuery("reviewEntity.findByPost", ReviewEntity.class)
                .setParameter("jobPost", jobPost)
                .setFirstResult(firstResult)
                .setMaxResults(cap)
                .getResultList();

        List<ReviewDto> dtoList = new ArrayList<>();
        for (ReviewEntity r : reviewList) {
            dtoList.add(makeReviewDto(r));
        }

        return dtoList;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public boolean addReview(ReviewDto reviewDto) {
        ReviewEntity review = new ReviewEntity();
        review.setContent(reviewDto.getContent());
        review.setTime(reviewDto.getTime());

        MemberEntity mem = em.getReference(MemberEntity.class, reviewDto.getMemberId());
        JobPostEntity post = em.getReference(JobPostEntity.class, reviewDto.getJobPostId());
        review.setMember(mem);
        review.setJobPost(post);

        em.persist(review);

        return true;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public boolean deleteReview(Integer reviewId) {
        // 删除与member的关联
        MemberEntity mem = em.getReference(MemberEntity.class, em.getReference(ReviewEntity.class, reviewId).getMember().getId());
        Collection<ReviewEntity> reCollection = mem.getReviewCollection();
        reCollection.size();

        Iterator<ReviewEntity> it = reCollection.iterator();
        ReviewEntity targetEntity = null;
        while (it.hasNext()) {
            ReviewEntity r = it.next();
            if (reviewId.equals(r.getId())) {
                targetEntity = r;
                it.remove();
                break;
            }

        }

        // 删除评论实体
        em.remove(targetEntity);

        return true;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public boolean updateReview(Integer reviewId, ReviewDto reviewDto) {
        ReviewEntity r = em.getReference(ReviewEntity.class, reviewId);
        updateReview(r, reviewDto);

        return true;
    }

    private ReviewDto makeReviewDto(ReviewEntity review) {
        ReviewDto dto = new ReviewDto();
        dto.setContent(review.getContent());
        dto.setTime(review.getTime());

        dto.setMemberId(review.getMember().getId());
        dto.setJobPostId(review.getJobPost().getId());
        return dto;
    }

    /**
     * 只修改content
     * @param r
     * @param dto
     */
    private void updateReview(ReviewEntity r, ReviewDto dto) {
        //r.setTime(dto.getTime());
        r.setContent(dto.getContent());
    }
}

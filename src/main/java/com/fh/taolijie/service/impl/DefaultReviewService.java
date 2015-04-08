package com.fh.taolijie.service.impl;

import com.fh.taolijie.controller.dto.ReviewDto;
import com.fh.taolijie.domain.JobPostEntity;
import com.fh.taolijie.domain.MemberEntity;
import com.fh.taolijie.domain.ReviewEntity;
import com.fh.taolijie.service.ReviewService;
import com.fh.taolijie.service.repository.ReviewRepo;
import com.fh.taolijie.utils.CheckUtils;
import com.fh.taolijie.utils.CollectionUtils;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.ObjWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    @Autowired
    ReviewRepo reviewRepo;

    @Override
    @Transactional(readOnly = true)
    public List<ReviewDto> getReviewList(Integer postId, int firstResult, int capacity, ObjWrapper wrapper) {
        JobPostEntity jobPost = em.getReference(JobPostEntity.class, postId);
        CheckUtils.nullCheck(jobPost);

        int cap = capacity;
        if (cap <= 0) {
            cap = Constants.PAGE_CAPACITY;
        }

        Page<ReviewEntity> reviewList = reviewRepo.findByJobPost(jobPost, new PageRequest(firstResult, cap));
        wrapper.setObj(reviewList.getTotalPages());
/*        List<ReviewEntity> reviewList = em.createNamedQuery("reviewEntity.findByPost", ReviewEntity.class)
                .setParameter("jobPost", jobPost)
                .setFirstResult(firstResult)
                .setMaxResults(cap)
                .getResultList();*/

        // 把Entity List转成DTO List
        List<ReviewDto> dtoList = new ArrayList<>();
        for (ReviewEntity r : reviewList) {
            dtoList.add(CollectionUtils.entity2Dto(r, ReviewDto.class, (dto) -> {
                // 设置DTO的关联信息
                dto.setMemberId(r.getMember().getId());
                dto.setJobPostId(r.getJobPost().getId());

                // 如果有评论回复，则设置回复
                List<ReviewEntity> replyList = r.getReplyList();
                if (null != replyList) {
                    // 把Entity转成DTO
                    CollectionUtils.transformCollection(replyList, ReviewDto.class, (entity) -> {
                        return CollectionUtils.entity2Dto(entity, ReviewDto.class, (reviewDto) -> {
                            reviewDto.setMemberId(entity.getMember().getId());
                        });
                    });
                }
            }));
        }

        return dtoList;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void addComment(Integer memId, Integer reviewId, ReviewDto dto) {
        // TODO untested!!
        MemberEntity mem = em.getReference(MemberEntity.class, memId);
        ReviewEntity review = em.getReference(ReviewEntity.class, reviewId);
        CheckUtils.nullCheck(mem, review);

        // 创建回复Entity对象
        ReviewEntity reply = CollectionUtils.dto2Entity(dto, ReviewEntity.class, (replyEntity) -> {
            // 创建回复与用户的关联关系
            // 设置从回复到用户的关联
            replyEntity.setMember(mem);
            // 设置从用户到回复的关联
            List<ReviewEntity> repList = CollectionUtils.addToCollection(mem.getReplyList(), replyEntity);
            if (null != repList) {
                mem.setReplyList(repList);
            }

            // 创建回复与被回复Review实体的关系
            // 设置从回复到被回复的关联
            replyEntity.setBaseReview(review);
            // 设置从被回复到回复的关联
            List<ReviewEntity> replyList = CollectionUtils.addToCollection(review.getReplyList(), replyEntity);
            if (null != replyList) {
                review.setReplyList(replyList);
            }
        });

        // 保存回复
        em.persist(reply);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public boolean addReview(ReviewDto reviewDto) {
/*        ReviewEntity review = new ReviewEntity();
        review.setContent(reviewDto.getContent());
        review.setTime(reviewDto.getTime());*/

        ReviewEntity review = CollectionUtils.dto2Entity(reviewDto, ReviewEntity.class, (entity) -> {
            MemberEntity mem = em.getReference(MemberEntity.class, reviewDto.getMemberId());
            JobPostEntity post = em.getReference(JobPostEntity.class, reviewDto.getJobPostId());
            CheckUtils.nullCheck(mem ,post);

            entity.setMember(mem);
            entity.setJobPost(post);
        });

/*        MemberEntity mem = em.getReference(MemberEntity.class, reviewDto.getMemberId());
        JobPostEntity post = em.getReference(JobPostEntity.class, reviewDto.getJobPostId());
        CheckUtils.nullCheck(mem ,post);

        review.setMember(mem);
        review.setJobPost(post);*/

        em.persist(review);

        return true;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public boolean deleteReview(Integer reviewId) {
        ReviewEntity review = em.getReference(ReviewEntity.class, reviewId);
        CheckUtils.nullCheck(review);
        MemberEntity mem = review.getMember();

        // 删除与member的关联
        //MemberEntity mem = em.getReference(MemberEntity.class, em.getReference(ReviewEntity.class, reviewId).getMember().getId());
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
        CheckUtils.nullCheck(r);

        CollectionUtils.updateEntity(r, reviewDto, null);
        //updateReview(r, reviewDto);

        return true;
    }

    /*private ReviewDto makeReviewDto(ReviewEntity review) {
        ReviewDto dto = new ReviewDto();
        dto.setId(review.getId());
        dto.setContent(review.getContent());
        dto.setTime(review.getTime());

        dto.setMemberId(review.getMember().getId());
        dto.setJobPostId(review.getJobPost().getId());
        return dto;
    }*/

   /* private void updateReview(ReviewEntity r, ReviewDto dto) {
        //r.setTime(dto.getTime());
        r.setContent(dto.getContent());
    }*/
}

package com.fh.taolijie.service.impl;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.constant.PostType;
import com.fh.taolijie.dao.mapper.CollectionModelMapper;
import com.fh.taolijie.domain.CollectionModel;
import com.fh.taolijie.domain.CollectionModelExample;
import com.fh.taolijie.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by whf on 8/22/15.
 */
@Service
@Transactional(readOnly = true)
public class DefaultCollectionService implements CollectionService {
    @Autowired
    private CollectionModelMapper coMapper;

    @Override
    public ListResult<CollectionModel> findBy(CollectionModelExample example) {
        List<CollectionModel> list = coMapper.selectByExample(example);
        long tot = coMapper.countByExample(example);

        return new ListResult<>(list, tot);
    }

    @Override
    public boolean alreadyCollected(Integer memberId, Integer postId, PostType type) {
        switch (type) {
            case JOB:
                return coMapper.isMemberAndJobExists(memberId, postId);

            case SH:
                return coMapper.isMemberAndShExists(memberId, postId);

            case RESUME:
                return coMapper.isMemberAndResumeExists(memberId, postId);

            default:
                throw new IllegalArgumentException("没有该类型");
        }
    }

    @Override
    @Transactional(readOnly = false)
    public void collect(Integer memberId, Integer postId, PostType type) {
        CollectionModel model = new CollectionModel();
        model.setCreatedTime(new Date());
        model.setMemberId(memberId);

        switch (type) {
            case JOB:
                model.setJobPostId(postId);
                break;

            case SH:
                model.setShPostId(postId);
                break;

            case RESUME:
                model.setResumeId(postId);
                break;

            default:
                throw new IllegalArgumentException("没有该类型");
        }

        coMapper.insertSelective(model);
    }

    @Override
    @Transactional(readOnly = false)
    public void cancelCollect(Integer memId, Integer postId, PostType type) {
        switch (type) {
            case JOB:
                coMapper.deleteByMemberAndJob(memId, postId);
                break;

            case SH:
                coMapper.deleteByMemberAndSh(memId, postId);
                break;

            case RESUME:
                coMapper.deleteByMemberAndResume(memId, postId);
                break;

            default:
                throw new IllegalArgumentException("没有该类型");
        }

    }
}

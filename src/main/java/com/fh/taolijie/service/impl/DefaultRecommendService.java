package com.fh.taolijie.service.impl;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.dao.mapper.RecommendedPostModelMapper;
import com.fh.taolijie.domain.RecommendedPostModel;
import com.fh.taolijie.service.RecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by whf on 7/11/15.
 */
@Service
@Transactional(readOnly = true)
public class DefaultRecommendService implements RecommendService {
    @Autowired
    RecommendedPostModelMapper recoMapper;

    @Override
    public RecommendedPostModel findById(Integer recommendId) {
        return recoMapper.selectByPrimaryKey(recommendId);
    }

    @Override
    @Transactional(readOnly = false)
    public int add(RecommendedPostModel model) {
        model.setApplyTime(new Date());

        return recoMapper.insert(model);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteById(Integer recommendId) {
        recoMapper.deleteByPrimaryKey(recommendId);
    }

    @Override
    @Transactional(readOnly = false)
    public void updateByIdSelective(RecommendedPostModel model) {
        recoMapper.updateByPrimaryKeySelective(model);
    }

    @Override
    public ListResult<RecommendedPostModel> getJobList(int pageNumber, int pageSize) {
        RecommendedPostModel cmd = new RecommendedPostModel(pageNumber, pageSize);
        cmd.setIsJob(true);
        cmd.setValidation(true);

        List<RecommendedPostModel> list = recoMapper.findRecommendList(cmd);
        int tot = recoMapper.countFindRecommendList(cmd);

        return new ListResult<>(list, tot);
    }

    @Override
    public ListResult<RecommendedPostModel> getShList(int pageNumber, int pageSize) {
        RecommendedPostModel cmd = new RecommendedPostModel(pageNumber, pageSize);
        cmd.setIsSh(true);
        cmd.setValidation(true);

        List<RecommendedPostModel> list = recoMapper.findRecommendList(cmd);
        int tot = recoMapper.countFindRecommendList(cmd);

        return new ListResult<>(list, tot);
    }

    @Override
    public ListResult<RecommendedPostModel> getResumeList(int pageNumber, int pageSize) {
        RecommendedPostModel cmd = new RecommendedPostModel(pageNumber, pageSize);
        cmd.setIsResume(true);
        cmd.setValidation(true);

        List<RecommendedPostModel> list = recoMapper.findRecommendList(cmd);
        int tot = recoMapper.countFindRecommendList(cmd);

        return new ListResult<>(list, tot);
    }

    @Override
    public ListResult<RecommendedPostModel> findNewAppliedRequest(boolean validatoin, int pageNumber, int pageSize) {
        RecommendedPostModel cmd = new RecommendedPostModel(pageNumber, pageSize);
        cmd.setValidation(validatoin);

        List<RecommendedPostModel> list = recoMapper.findBy(cmd);
        int tot = recoMapper.countFindBy(cmd);

        return new ListResult<>(list, tot);
    }
}

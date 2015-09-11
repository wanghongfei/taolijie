package com.fh.taolijie.service.impl;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.dao.mapper.JobPostCategoryModelMapper;
import com.fh.taolijie.dao.mapper.RecommendedPostModelMapper;
import com.fh.taolijie.dao.mapper.ShPostCategoryModelMapper;
import com.fh.taolijie.domain.JobPostCategoryModel;
import com.fh.taolijie.domain.RecommendedPostModel;
import com.fh.taolijie.domain.SHPostCategoryModel;
import com.fh.taolijie.domain.middle.CategoryWithPost;
import com.fh.taolijie.service.JobPostCateService;
import com.fh.taolijie.service.RecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.awt.image.IntegerInterleavedRaster;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by whf on 7/11/15.
 */
@Service
public class DefaultRecommendService implements RecommendService {
    @Autowired
    RecommendedPostModelMapper recoMapper;

    @Autowired
    ShPostCategoryModelMapper shCateMapper;

    @Autowired
    JobPostCateService jobCateService;

    @Autowired
    JobPostCategoryModelMapper jobCateMapper;

    @Override
    @Transactional(readOnly = true)
    public RecommendedPostModel findById(Integer recommendId) {
        return recoMapper.selectByPrimaryKey(recommendId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean checkRecommendExist(RecommendedPostModel model) {
        return recoMapper.checkRecommendExist(model);
    }

    @Override
    @Transactional(readOnly = false)
    public int add(RecommendedPostModel model) {
        model.setApplyTime(new Date());
        recoMapper.insertSelective(model);

        return model.getId();
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
    @Transactional(readOnly = true)
    public ListResult<RecommendedPostModel> getJobList(int pageNumber, int pageSize) {
        RecommendedPostModel cmd = new RecommendedPostModel(pageNumber, pageSize);
        cmd.setIsJob(true);
        cmd.setValidation(true);

        List<RecommendedPostModel> list = recoMapper.findRecommendList(cmd);
        int tot = recoMapper.countFindRecommendList(cmd);

        if (0 != tot) {
            // 查询对应的分类
            List<Integer> idList = list.stream()
                    .map(RecommendedPostModel::getId)
                    .collect(Collectors.toList());
            List<CategoryWithPost> jobAndCateList = jobCateMapper.getByJobInBatch(idList);

            doMatch(list, jobAndCateList);
        }

        return new ListResult<>(list, tot);
    }

    /**
     * 设置推荐信息对应的分类
     * @param recoList
     * @param jobAndCateList
     */
    private void doMatch(List<RecommendedPostModel> recoList, List<CategoryWithPost> jobAndCateList) {
        Map<Integer, JobPostCategoryModel> map = jobAndCateList.stream()
                .collect(Collectors.toMap(CategoryWithPost::getCateId, with -> new JobPostCategoryModel(with)));

        recoList.forEach(reco -> {
            reco.getJobPost().setCategory(map.get(reco.getJobId()));
        });
    }


    @Override
    @Transactional(readOnly = true)
    public ListResult<RecommendedPostModel> getShList(int pageNumber, int pageSize) {
        RecommendedPostModel cmd = new RecommendedPostModel(pageNumber, pageSize);
        cmd.setIsSh(true);
        cmd.setValidation(true);

        List<RecommendedPostModel> list = recoMapper.findRecommendList(cmd);
        int tot = recoMapper.countFindRecommendList(cmd);

        // 查询对应的分类
        if (0 != tot) {
            List<Integer> idList = list.stream()
                    .map(RecommendedPostModel::getShId)
                    .collect(Collectors.toList());
            List<CategoryWithPost> withPostList = shCateMapper.findByShInBatch(idList);

            doMatchForSh(list, withPostList);
        }

        return new ListResult<>(list, tot);
    }

    private void doMatchForSh(List<RecommendedPostModel> recoList, List<CategoryWithPost> withList) {
        Map<Integer, SHPostCategoryModel> map = withList.stream()
                .collect(Collectors.toMap(CategoryWithPost::getCateId, with -> new SHPostCategoryModel(with)));

        recoList.forEach(reco -> {
            reco.getShPost().setCategory(map.get(reco.getShId()));
        });

    }

    @Override
    @Transactional(readOnly = true)
    public ListResult<RecommendedPostModel> getResumeList(int pageNumber, int pageSize) {
        RecommendedPostModel cmd = new RecommendedPostModel(pageNumber, pageSize);
        cmd.setIsResume(true);
        cmd.setValidation(true);

        List<RecommendedPostModel> list = recoMapper.findRecommendList(cmd);
        int tot = recoMapper.countFindRecommendList(cmd);

        return new ListResult<>(list, tot);
    }

    @Override
    @Transactional(readOnly = true)
    public ListResult<RecommendedPostModel> findNewAppliedRequest(boolean validation, int pageNumber, int pageSize) {
        RecommendedPostModel cmd = new RecommendedPostModel(pageNumber, pageSize);
        cmd.setValidation(validation);

        List<RecommendedPostModel> list = recoMapper.findBy(cmd);
        int tot = recoMapper.countFindBy(cmd);

        return new ListResult<>(list, tot);
    }
}

package com.fh.taolijie.service.impl;

import com.fh.taolijie.cache.annotation.RedisCache;
import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.dao.mapper.JobPostModelMapper;
import com.fh.taolijie.dao.mapper.MemberModelMapper;
import com.fh.taolijie.dao.mapper.ReviewModelMapper;
import com.fh.taolijie.domain.JobPostModel;
import com.fh.taolijie.domain.MemberModel;
import com.fh.taolijie.service.JobPostService;
import com.fh.taolijie.utils.CollectionUtils;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.ObjWrapper;
import com.fh.taolijie.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by wanghongfei on 15-6-6.
 */
@Service
@Transactional(readOnly = true)
public class DefaultJobPostService implements JobPostService {
    @Autowired
    JobPostModelMapper postMapper;

    @Autowired
    MemberModelMapper memMapper;

    @Autowired
    ReviewModelMapper revMapper;

    @Override
    public ListResult<JobPostModel> getAllJobPostList(int firstResult, int capacity, ObjWrapper wrapper) {
        List<JobPostModel> list =  postMapper.getAll(firstResult, CollectionUtils.determineCapacity(capacity));
        long tot = postMapper.countGetAll();

        return new ListResult<>(list, tot);
    }

    @Override
    public ListResult<JobPostModel> getJobPostListByMember(Integer memId, int firstResult, int capacity, ObjWrapper wrapper) {
        JobPostModel model = new JobPostModel(firstResult, CollectionUtils.determineCapacity(capacity));
        model.setMemberId(memId);
        model.setFilterExpiredPost(false);

        List<JobPostModel> list = postMapper.findBy(model);
        long tot = postMapper.countFindBy(model);

        return new ListResult<>(list, tot);
    }

    @Override
    public ListResult<JobPostModel> getJobPostListByCategory(Integer cateId, int firstResult, int capacity, ObjWrapper wrapper) {
        JobPostModel model = new JobPostModel(firstResult, CollectionUtils.determineCapacity(capacity));
        model.setJobPostCategoryId(cateId);

        List<JobPostModel> list = postMapper.findBy(model);
        long tot = postMapper.countFindBy(model);

        return new ListResult<>(list, tot);
    }

    @Override
    public List<JobPostModel> getUnverifiedPostList(int firstResult, int capacity, ObjWrapper wrapper) {
        JobPostModel model = new JobPostModel(firstResult, CollectionUtils.determineCapacity(capacity));
        model.setVerified(Constants.VerifyStatus.NONE.toString());

        return postMapper.findBy(model);
    }

    @Override
    public ListResult<JobPostModel> getPostListByIds(Integer... ids) {
        List<JobPostModel> list = postMapper.getInBatch(Arrays.asList(ids));

        return new ListResult<>(list, list.size());
    }

    @Override
    public ListResult<JobPostModel> getByComplaint(int firstResult, int capacity, ObjWrapper wrapper) {
        List<JobPostModel> list = postMapper.getByComplaint(firstResult, CollectionUtils.determineCapacity(capacity));
        long tot = postMapper.countGetByComplaint();

        return new ListResult<>(list, tot);
    }

    @Override
    public List<JobPostModel> getAndFilter(Integer categoryId, Constants.WayToPay wayToPay, boolean orderByDate, boolean orderByPageVisit, Integer schoolId, int firstResult, int capacity, ObjWrapper wrapper) {
        JobPostModel model = new JobPostModel(firstResult, CollectionUtils.determineCapacity(capacity));
        model.setJobPostCategoryId(categoryId);
        if (null != wayToPay) {
            model.setTimeToPay(wayToPay.toString());
        }
        model.setOrderByDate(orderByDate);
        model.setOrderByVisit(orderByPageVisit);

        return postMapper.findBy(model);
    }

    @Override
    public ListResult<JobPostModel> runSearch(JobPostModel model, int firstResult, int capacity, ObjWrapper wrapper) {
        model.setPageNumber(firstResult);
        model.setPageSize(CollectionUtils.determineCapacity(capacity));

        List<JobPostModel> list = postMapper.searchBy(model);
        long tot = postMapper.countSearchBy(model);

        return new ListResult<>(list, tot);
    }

    @Override
    public ListResult<JobPostModel> findByExample(JobPostModel example) {
        List<JobPostModel> list = postMapper.findBy(example);
        long tot = postMapper.countFindBy(example);

        return new ListResult<>(list, tot);
    }

    @Override
    public JobPostModel findJobPost(Integer postId) {
        return postMapper.selectByPrimaryKey(postId);
    }

    @Override
    @Transactional(readOnly = false)
    public void complaint(Integer postId) {
        postMapper.complaint(postId);
    }

    @Override
    @Transactional(readOnly = false)
    public void favoritePost(Integer memId, Integer postId) {
        MemberModel mem = memMapper.selectByPrimaryKey(memId);
        String oldIds = mem.getFavoriteJobIds();
        String newIds = StringUtils.addToString(oldIds, postId.toString());
        mem.setFavoriteJobIds(newIds);

        memMapper.updateByPrimaryKeySelective(mem);
    }

    @Override
    @Transactional(readOnly = false)
    public void unfavoritePost(Integer memId, Integer postId) {
        MemberModel mem = memMapper.selectByPrimaryKey(memId);
        String oldIds = mem.getFavoriteJobIds();
        String newIds = StringUtils.removeFromString(oldIds, postId.toString());
        mem.setFavoriteJobIds(newIds);

        memMapper.updateByPrimaryKeySelective(mem);

    }

    @Override
    @Transactional(readOnly = true)
    public boolean isPostFavorite(Integer memId, Integer postId) {
        MemberModel mem = memMapper.selectByPrimaryKey(memId);
        String ids = mem.getFavoriteJobIds();

        return StringUtils.checkIdExists(ids, postId.toString());
    }

    @Override
    @Transactional(readOnly = false)
    public ListResult<JobPostModel> getFavoritePost(Integer memberId) {
        MemberModel mem = memMapper.selectByPrimaryKey(memberId);
        String allIds = mem.getFavoriteJobIds();
        if (null == allIds || allIds.isEmpty()) {
            return new ListResult<>();
        }

        String[] ids = allIds.split(Constants.DELIMITER);

        List<Integer> idList = Arrays.stream(ids).map(id -> {
            return Integer.parseInt(id);
        }).collect(Collectors.toList());

        List<JobPostModel> list = postMapper.getInBatch(idList);
        return new ListResult<>(list, list.size());
    }

    @Deprecated
    @Override
    @Transactional(readOnly = false)
    public void postResume(Integer postId, Integer resumeId, Integer memberId) {
        postMapper.postResume(resumeId, postId, memberId);
    }

    @Override
    @Transactional(readOnly = false)
    public void addJobPost(JobPostModel model) {
        postMapper.insert(model);
    }

    @Override
    @Transactional(readOnly = false)
    public boolean updateJobPost(Integer postId, JobPostModel model) {
        model.setId(postId);
        int row = postMapper.updateByPrimaryKeySelective(model);

        return row <= 0 ? false : true;
    }

    @Override
    @Transactional(readOnly = false)
    public boolean deleteJobPost(Integer postId) {
        return postMapper.setDeleted(postId, true) <= 0 ? false : true;

/*        //得到所有评论
        ReviewModel revModel = new ReviewModel(0, Integer.MAX_VALUE);
        revModel.setPostId(postId);

        List<ReviewModel> revList = revMapper.findBy(revModel);

        List<Integer> idList = revList.stream()
                .map(ReviewModel::getId)
                .collect(Collectors.toList());

        // 批量删除评论
        revMapper.deleteInBatch(idList);

        // 删除兼职本身
        int rows = postMapper.deleteByPrimaryKey(postId);

        return rows <= 0 ? false : true;*/


    }

    @Override
    @Transactional(readOnly = false)
    public void increasePageView(Integer id) {
        postMapper.increasePageView(id);
    }
}

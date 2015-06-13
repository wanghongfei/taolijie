package com.fh.taolijie.service.impl;

import com.fh.taolijie.dao.mapper.JobPostModelMapper;
import com.fh.taolijie.dao.mapper.MemberModelMapper;
import com.fh.taolijie.dao.mapper.ReviewModelMapper;
import com.fh.taolijie.domain.JobPostModel;
import com.fh.taolijie.domain.MemberModel;
import com.fh.taolijie.domain.ReviewModel;
import com.fh.taolijie.service.JobPostService;
import com.fh.taolijie.utils.CollectionUtils;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.ObjWrapper;
import com.fh.taolijie.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public List<JobPostModel> getAllJobPostList(int firstResult, int capacity, ObjWrapper wrapper) {
        return postMapper.getAll(firstResult, CollectionUtils.determineCapacity(capacity));
    }

    @Override
    public List<JobPostModel> getJobPostListByMember(Integer memId, int firstResult, int capacity, ObjWrapper wrapper) {
        JobPostModel model = new JobPostModel(firstResult, CollectionUtils.determineCapacity(capacity));
        model.setMemberId(memId);

        return postMapper.findBy(model);
    }

    @Override
    public List<JobPostModel> getJobPostListByCategory(Integer cateId, int firstResult, int capacity, ObjWrapper wrapper) {
        JobPostModel model = new JobPostModel(firstResult, CollectionUtils.determineCapacity(capacity));
        model.setJobPostCategoryId(cateId);

        return postMapper.findBy(model);
    }

    @Override
    public List<JobPostModel> getUnverifiedPostList(int firstResult, int capacity, ObjWrapper wrapper) {
        JobPostModel model = new JobPostModel(firstResult, CollectionUtils.determineCapacity(capacity));
        model.setVerified(Constants.VerifyStatus.IN_PROCESS.toString());

        return postMapper.findBy(model);
    }

    @Override
    public List<JobPostModel> getPostListByIds(Integer... ids) {
        return postMapper.getInBatch(Arrays.asList(ids));
    }

    @Override
    public List<JobPostModel> getByComplaint(int firstResult, int capacity, ObjWrapper wrapper) {
        return postMapper.getByComplaint(firstResult, CollectionUtils.determineCapacity(capacity));
    }

    @Override
    public List<JobPostModel> getAndFilter(Integer categoryId, Constants.WayToPay wayToPay, boolean orderByDate, boolean orderByPageVisit, Integer schoolId, int firstResult, int capacity, ObjWrapper wrapper) {
        JobPostModel model = new JobPostModel(firstResult, CollectionUtils.determineCapacity(capacity));
        model.setJobPostCategoryId(categoryId);
        model.setTimeToPay(wayToPay.toString());
        model.setOrderByDate(orderByDate);
        model.setOrderByVisit(orderByPageVisit);

        return postMapper.findBy(model);
    }

    @Override
    public List<JobPostModel> runSearch(JobPostModel model, int firstResult, int capacity, ObjWrapper wrapper) {
        model.setPageNumber(firstResult);
        model.setPageSize(CollectionUtils.determineCapacity(capacity));

        return postMapper.searchBy(model);
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

        memMapper.updateByPrimaryKeySelective(mem);
    }

    @Override
    @Transactional(readOnly = false)
    public void unfavoritePost(Integer memId, Integer postId) {
        MemberModel mem = memMapper.selectByPrimaryKey(memId);
        String oldIds = mem.getFavoriteJobIds();
        String newIds = StringUtils.removeFromString(oldIds, postId.toString());

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
    public List<JobPostModel> getFavoritePost(Integer memberId) {
        MemberModel mem = memMapper.selectByPrimaryKey(memberId);
        String allIds = mem.getFavoriteJobIds();
        String[] ids = allIds.split(Constants.DELIMITER);

        List<Integer> idList = Arrays.stream(ids).map(id -> {
            return Integer.parseInt(id);
        }).collect(Collectors.toList());

        return postMapper.getInBatch(idList);
    }

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
        //得到所有评论
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

        return rows <= 0 ? false : true;
    }

    @Override
    @Transactional(readOnly = false)
    public void increasePageView(Integer id) {
        postMapper.increasePageView(id);
    }
}

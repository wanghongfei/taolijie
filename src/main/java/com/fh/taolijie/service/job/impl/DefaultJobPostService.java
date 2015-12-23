package com.fh.taolijie.service.job.impl;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.constant.PostType;
import com.fh.taolijie.dao.mapper.JobPostModelMapper;
import com.fh.taolijie.dao.mapper.MemberModelMapper;
import com.fh.taolijie.dao.mapper.ReviewModelMapper;
import com.fh.taolijie.domain.acc.CollectionModel;
import com.fh.taolijie.domain.acc.CollectionModelExample;
import com.fh.taolijie.domain.job.JobPostModel;
import com.fh.taolijie.domain.acc.MemberModel;
import com.fh.taolijie.exception.checked.JobNotFoundException;
import com.fh.taolijie.service.PVService;
import com.fh.taolijie.service.collect.CollectionService;
import com.fh.taolijie.service.job.JobPostService;
import com.fh.taolijie.utils.CollectionUtils;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by wanghongfei on 15-6-6.
 */
@Service
public class DefaultJobPostService implements JobPostService {
    @Autowired
    JobPostModelMapper postMapper;

    @Autowired
    MemberModelMapper memMapper;

    @Autowired
    ReviewModelMapper revMapper;

    @Autowired
    CollectionService coService;

    @Autowired
    private PVService pvService;

    @Override
    @Transactional(readOnly = true)
    public ListResult<JobPostModel> getAllJobPostList(int firstResult, int capacity) {
        List<JobPostModel> list =  postMapper.getAll(firstResult, CollectionUtils.determineCapacity(capacity));
        long tot = postMapper.countGetAll();

        return new ListResult<>(list, tot);
    }

    @Override
    @Transactional(readOnly = true)
    public ListResult<JobPostModel> getJobPostListByMember(Integer memId, int firstResult, int capacity) {
        JobPostModel model = new JobPostModel(firstResult, CollectionUtils.determineCapacity(capacity));
        model.setMemberId(memId);
        model.setFilterExpiredPost(false);

        List<JobPostModel> list = postMapper.findBy(model);
        long tot = postMapper.countFindBy(model);

        return new ListResult<>(list, tot);
    }

    @Override
    @Transactional(readOnly = true)
    public ListResult<JobPostModel> getJobPostListByCategory(Integer cateId, int firstResult, int capacity) {
        JobPostModel model = new JobPostModel(firstResult, CollectionUtils.determineCapacity(capacity));
        model.setJobPostCategoryId(cateId);

        List<JobPostModel> list = postMapper.findBy(model);
        long tot = postMapper.countFindBy(model);

        return new ListResult<>(list, tot);
    }

    /**
     * @deprecated
     */
    @Override
    @Transactional(readOnly = true)
    public List<JobPostModel> getUnverifiedPostList(int firstResult, int capacity) {
        JobPostModel model = new JobPostModel(firstResult, CollectionUtils.determineCapacity(capacity));
        model.setVerified(Constants.VerifyStatus.NONE.toString());

        return postMapper.findBy(model);
    }

    @Transactional(readOnly = true)
    @Override
    public ListResult<JobPostModel> getPostListByIds(Integer... ids) {
        List<JobPostModel> list = postMapper.getInBatch(Arrays.asList(ids));

        return new ListResult<>(list, list.size());
    }

    @Override
    @Transactional(readOnly = true)
    public ListResult<JobPostModel> getInBatch(List<Integer> idList) {
        if (idList.isEmpty()) {
            return new ListResult<>(new ArrayList<>(0), 0);
        }

        List<JobPostModel> list = postMapper.getInBatch(idList);

        return new ListResult<>(list, list.size());
    }

    @Override
    @Transactional(readOnly = true)
    public ListResult<JobPostModel> getByComplaint(int firstResult, int capacity) {
        List<JobPostModel> list = postMapper.getByComplaint(firstResult, CollectionUtils.determineCapacity(capacity));
        long tot = postMapper.countGetByComplaint();

        return new ListResult<>(list, tot);
    }

    @Override
    @Transactional(readOnly = true)
    public List<JobPostModel> getAndFilter(Integer categoryId,
                                           Constants.WayToPay wayToPay,
                                           boolean orderByDate,
                                           boolean orderByPageVisit,
                                           Integer schoolId,
                                           int firstResult,
                                           int capacity) {
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
    @Transactional(readOnly = true)
    public ListResult<JobPostModel> runSearch(JobPostModel model, int firstResult, int capacity) {
        model.setPageNumber(firstResult);
        model.setPageSize(CollectionUtils.determineCapacity(capacity));

        List<JobPostModel> list = postMapper.searchBy(model);
        long tot = postMapper.countSearchBy(model);

        return new ListResult<>(list, tot);
    }

    @Override
    @Transactional(readOnly = true)
    public ListResult<JobPostModel> findByExample(JobPostModel example) {
        List<JobPostModel> list = postMapper.findBy(example);
        long tot = postMapper.countFindBy(example);

        return new ListResult<>(list, tot);
    }

    @Override
    @Transactional(readOnly = true)
    public JobPostModel findJobPost(Integer postId) {
        return postMapper.selectByPrimaryKey(postId);
    }

    @Override
    @Transactional(readOnly = true)
    public JobPostModel findJobPostWithPV(Integer postId) {
        JobPostModel post = postMapper.selectByPrimaryKey(postId);

        if (null != post) {
            String pv = pvService.getJobPV(postId);
            post.setPv(pv);
        }

        return post;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public void checkExpired(List<JobPostModel> postList) {
        if (null == postList || postList.isEmpty()) {
            return;
        }

        List<JobPostModel> expiredList = selectExpiredPost(postList);
        flagAndUpdate(expiredList);
    }

    /**
     * 找出已经过期的帖子
     * @return
     */
    private List<JobPostModel> selectExpiredPost(List<JobPostModel> list) {
        Date now = new Date();
        Date nextDay = TimeUtil.calculateDate(now, Calendar.DAY_OF_MONTH, -1);

        List<JobPostModel> expiredList = new ArrayList<>(list.size() / 3);
        list.forEach( job -> {
            Date expTime = job.getExpiredTime();
            if (null != expTime) {
                // 判断
                // 已经过期但是标记还是未过期的帖子
                if (nextDay.compareTo(expTime) >= 0 && false == job.getExpired()) {
                    expiredList.add(job);
                }
            }
        });

        return expiredList;
    }

    /**
     * 标记为已过期并更新到数据库
     * @param list
     */
    private int flagAndUpdate(List<JobPostModel> list) {
        if (list.isEmpty()) {
            return 0;
        }

        List<Integer> idList = new ArrayList<>(list.size());

        // 标记过期的帖子
        list.forEach( job -> {
            job.setExpired(true);
            idList.add(job.getId());
        });

        // 更新到数据库
        return postMapper.setExpired(idList, true);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public void complaint(Integer postId) {
        postMapper.complaint(postId);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public void favoritePost(Integer memId, Integer postId) {
        coService.collect(memId, postId, PostType.JOB);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public void unfavoritePost(Integer memId, Integer postId) {
        coService.cancelCollect(memId, postId, PostType.JOB);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isPostFavorite(Integer memId, Integer postId) {
        return coService.alreadyCollected(memId, postId, PostType.JOB);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public ListResult<JobPostModel> getFavoritePost(Integer memberId, int pn, int ps) {
        CollectionModelExample example = new CollectionModelExample(pn, ps);
        example.createCriteria()
                .andMemberIdEqualTo(memberId)
                .andJobPostIdIsNotNull();

        // TODO 没分页
        ListResult<CollectionModel> coList = coService.findBy(example);
        if (0 == coList.getResultCount()) {
            return new ListResult<>(new ArrayList<>(0), 0);
        }

        // 转换成idList
        List<Integer> idList = coList.getList().stream()
                .map(CollectionModel::getJobPostId)
                .collect(Collectors.toList());

        List<JobPostModel> list = postMapper.getInBatch(idList);
        return new ListResult<>(list, coList.getResultCount());
    }

    @Deprecated
    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public void postResume(Integer postId, Integer resumeId, Integer memberId) {
        postMapper.postResume(resumeId, postId, memberId);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public void addJobPost(JobPostModel model) {
        // 更新作者的上次发布时间
        MemberModel example = new MemberModel();
        example.setId(model.getMemberId());
        example.setLastJobDate(model.getPostTime());
        memMapper.updateByPrimaryKeySelective(example);

        // 插入兼职表
        postMapper.insertSelective(model);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public boolean updateJobPost(Integer postId, JobPostModel model) {
        model.setId(postId);
        int row = postMapper.updateByPrimaryKeySelective(model);

        return row <= 0 ? false : true;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public boolean deleteJobPost(Integer postId) {
        Boolean deleted = postMapper.checkDeleted(postId);
        if (null == deleted) {
            return false;
        }

        return postMapper.setDeleted(postId, !deleted.booleanValue()) <= 0 ? false : true;

    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public boolean undeleteJobPost(Integer postId) {
        return postMapper.setDeleted(postId, false) <= 0 ? false : true;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean checkExist(Integer postId) {
        return postMapper.checkExist(postId);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public void increasePageView(Integer id) {
        postMapper.increasePageView(id);
    }
}

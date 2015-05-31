package com.fh.taolijie.dao.mapper;

import com.fh.taolijie.domain.JobPostModel;
import com.fh.taolijie.domain.MemberModel;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by wanghongfei on 15-5-31.
 */
@Repository
public interface JobPostMapper {
    JobPostModel getPostByIdWithoutReview(Integer postId);
    List<JobPostModel> getPostByMemberId(MemberModel model);

    void saveJobPost(JobPostModel model);
    void updateBySelective(JobPostModel model);
    void deleteByBatch(List<Integer> idList);
}

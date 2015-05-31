package com.fh.taolijie.dao.mapper;

import com.fh.taolijie.domain.JobPostModel;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by wanghongfei on 15-5-31.
 */
@Repository
public interface JobPostMapper {
    List<JobPostModel> getPostByIdWithoutReview(JobPostModel model);
}

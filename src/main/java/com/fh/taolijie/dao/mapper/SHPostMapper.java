package com.fh.taolijie.dao.mapper;

import com.fh.taolijie.domain.MemberModel;
import com.fh.taolijie.domain.SecondHandPostModel;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by wanghongfei on 15-6-3.
 */
@Repository
public interface SHPostMapper {
    SecondHandPostModel getPostByIdWithoutReview(Integer postId);
    List<SecondHandPostModel> getPostByMemberId(MemberModel model);

    void saveJobPost(SecondHandPostModel model);
    void updateBySelective(SecondHandPostModel model);
    void deleteByBatch(List<Integer> idList);
}

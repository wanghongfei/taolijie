package com.fh.taolijie.service.impl;

import com.fh.taolijie.domain.MemberEntity;
import com.fh.taolijie.service.UserService;
import com.fh.taolijie.service.repository.JobPostRepo;
import com.fh.taolijie.service.repository.MemberRepo;
import com.fh.taolijie.service.repository.SHPostRepo;
import com.fh.taolijie.utils.CheckUtils;
import com.fh.taolijie.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by wanghongfei on 15-4-3.
 */
@Service
public class DefaultUserService implements UserService {
    @Autowired
    MemberRepo memRepo;

    @Autowired
    JobPostRepo jobPostRepo;

    @Autowired
    SHPostRepo shPostRepo;

    @Override
    @Transactional(readOnly = false)
    public boolean likeJobPost(Integer memId, Integer postId) {
        MemberEntity mem = memRepo.findOne(memId);
        //JobPostEntity post = jobPostRepo.findOne(postId);
/*        if (false == CheckUtils.nullCheck(mem, post)) {
            return false;
        }*/
        //CheckUtils.nullCheck(mem, post);

        // 查检是否重复赞
        String oldIds = mem.getLikedJobIds();
        if (true == StringUtils.checkIdExists(oldIds, postId.toString())) {
            return false;
        }


        // 在member中记录这次点赞
        String newIds = StringUtils.addToString(oldIds, postId.toString());
        mem.setLikedJobIds(newIds);

        // post赞数+1
/*        Integer oldValue = post.getLikes();
        Integer newValue = oldValue == null ? 1 : oldValue.intValue() + 1;
        post.setLikes(newValue);*/
        jobPostRepo.likePost(postId);



        return true;
    }

    @Override
    @Transactional(readOnly = false)
    public boolean likeSHPost(Integer memId, Integer shId) {
        MemberEntity mem = memRepo.findOne(memId);
        //SecondHandPostEntity post = shPostRepo.findOne(shId);
/*        if (false == CheckUtils.nullCheck(mem, post)) {
            return false;
        }*/
        //CheckUtils.nullCheck(mem, post);

        // 检查是否重复
        String oldIds = mem.getLikedShIds();
        if (true == StringUtils.checkIdExists(oldIds, shId.toString())) {
            return false;
        }

        // 在member中记录这次点赞
        String newIds = StringUtils.addToString(oldIds, shId.toString());
        mem.setLikedShIds(newIds);

        // SHpost点赞数+1
/*        Integer oldValue = post.getLikes();
        Integer newValue = oldValue == null ? 1 : oldValue.intValue() + 1;
        post.setLikes(newValue);*/
        shPostRepo.likePost(shId);


        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isJobPostAlreadyLiked(Integer memId, Integer posId) {
        MemberEntity mem = memRepo.findOne(memId);
        CheckUtils.nullCheck(mem);

        String ids = mem.getLikedJobIds();
        return StringUtils.checkIdExists(ids, posId.toString());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isSHPostAlreadyLiked(Integer memId, Integer shId) {
        MemberEntity mem = memRepo.findOne(memId);
        CheckUtils.nullCheck(mem);

        String ids = mem.getLikedShIds();
        return StringUtils.checkIdExists(ids, shId.toString());

    }
}

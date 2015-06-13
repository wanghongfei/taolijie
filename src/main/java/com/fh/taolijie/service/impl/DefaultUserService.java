package com.fh.taolijie.service.impl;

import com.fh.taolijie.dao.mapper.JobPostModelMapper;
import com.fh.taolijie.dao.mapper.MemberModelMapper;
import com.fh.taolijie.dao.mapper.ShPostModelMapper;
import com.fh.taolijie.domain.MemberModel;
import com.fh.taolijie.service.UserService;
import com.fh.taolijie.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by wanghongfei on 15-6-7.
 */
@Service
@Transactional(readOnly = true)
public class DefaultUserService implements UserService {
    @Autowired
    MemberModelMapper memMapper;

    @Autowired
    JobPostModelMapper jobMapper;

    @Autowired
    ShPostModelMapper shMapper;


    @Override
    @Transactional(readOnly = false)
    public boolean likeJobPost(Integer memId, Integer postId) {
        MemberModel mem = memMapper.selectByPrimaryKey(memId);
        String oldIds = mem.getLikedJobIds();
        String newIds = StringUtils.addToString(oldIds, postId.toString());

        jobMapper.increaseLike(postId);
        memMapper.updateByPrimaryKeySelective(mem);

        return true;
    }

    @Override
    @Transactional(readOnly = false)
    public boolean likeSHPost(Integer memId, Integer shId) {
        MemberModel mem = memMapper.selectByPrimaryKey(memId);
        String oldIds = mem.getLikedShIds();
        String newIds = StringUtils.addToString(oldIds, shId.toString());

        shMapper.increaseLike(shId);
        memMapper.updateByPrimaryKeySelective(mem);

        return false;
    }

    @Override
    public boolean isJobPostAlreadyLiked(Integer memId, Integer posId) {
        MemberModel mem = memMapper.selectByPrimaryKey(memId);
        String oldIds = mem.getLikedJobIds();

        return StringUtils.checkIdExists(oldIds, posId.toString());
    }

    @Override
    public boolean isSHPostAlreadyLiked(Integer memId, Integer shId) {
        MemberModel mem = memMapper.selectByPrimaryKey(memId);
        String oldIds = mem.getLikedShIds();

        return StringUtils.checkIdExists(oldIds, shId.toString());
    }
}

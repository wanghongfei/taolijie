package com.fh.taolijie.service.impl;

import com.fh.taolijie.constant.OperationType;
import com.fh.taolijie.dto.CreditsInfo;
import com.fh.taolijie.dao.mapper.JobPostModelMapper;
import com.fh.taolijie.dao.mapper.MemberModelMapper;
import com.fh.taolijie.dao.mapper.ShPostModelMapper;
import com.fh.taolijie.domain.acc.MemberModel;
import com.fh.taolijie.service.UserService;
import com.fh.taolijie.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by wanghongfei on 15-6-7.
 */
@Service
public class DefaultUserService implements UserService {
    @Autowired
    MemberModelMapper memMapper;

    @Autowired
    JobPostModelMapper jobMapper;

    @Autowired
    ShPostModelMapper shMapper;


/*    @Qualifier("redisTemplateForString")
    @Autowired(required = false)
    StringRedisTemplate rt;*/


    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public boolean likeJobPost(Integer memId, Integer postId) {
        MemberModel mem = memMapper.selectByPrimaryKey(memId);
        String oldIds = mem.getLikedJobIds();
        String newIds = StringUtils.addToString(oldIds, postId.toString());

        jobMapper.increaseLike(postId);
        mem.setLikedJobIds(newIds);
        memMapper.updateByPrimaryKeySelective(mem);

        return true;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public boolean unLikeJobPost(Integer memId, Integer postId) {
        MemberModel mem = memMapper.selectByPrimaryKey(memId);
        String oldIds = mem.getLikedJobIds();

        // 如果本来就没有点赞，无法执行取消赞操作
        if (false == StringUtils.checkIdExists(oldIds, postId.toString())) {
            return false;
        }

        String newIds = StringUtils.removeFromString(oldIds, postId.toString());

        jobMapper.decreaseLike(postId);
        mem.setLikedJobIds(newIds);
        memMapper.updateByPrimaryKeySelective(mem);

        return true;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public boolean likeSHPost(Integer memId, Integer shId) {
        MemberModel mem = memMapper.selectByPrimaryKey(memId);
        String oldIds = mem.getLikedShIds();
        String newIds = StringUtils.addToString(oldIds, shId.toString());

        shMapper.increaseLike(shId);
        mem.setLikedShIds(newIds);
        memMapper.updateByPrimaryKeySelective(mem);

        return true;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public boolean unlikeShPost(Integer memId, Integer shId) {
        MemberModel mem = memMapper.selectByPrimaryKey(memId);
        String oldIds = mem.getLikedShIds();

        // 如果本来就没有点赞，无法执行取消赞操作
        if (false == StringUtils.checkIdExists(oldIds, shId.toString())) {
            return false;
        }

        String newIds = StringUtils.removeFromString(oldIds, shId.toString());

        shMapper.decreaseLike(shId);
        mem.setLikedShIds(newIds);
        memMapper.updateByPrimaryKeySelective(mem);
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isJobPostAlreadyLiked(Integer memId, Integer posId) {
        MemberModel mem = memMapper.selectByPrimaryKey(memId);
        String oldIds = mem.getLikedJobIds();

        return StringUtils.checkIdExists(oldIds, posId.toString());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isSHPostAlreadyLiked(Integer memId, Integer shId) {
        MemberModel mem = memMapper.selectByPrimaryKey(memId);
        String oldIds = mem.getLikedShIds();

        return StringUtils.checkIdExists(oldIds, shId.toString());
    }

    @Override
    @Transactional(readOnly = true)
    public CreditsInfo queryCreditsInfo(Integer memberId) {
        return memMapper.queryCreditsInfo(memberId);
    }

    @Override
    public int changeCredits(Integer memberId, OperationType type, int oldCredits) {
/*        String valueString = (String) rt.opsForHash().get(RedisKey.CREDITS_OPERATION.toString(), type.toString());
        int valueToAdd = Integer.valueOf(valueString);
        int newCredits = oldCredits + valueToAdd;

        String newLevel = queryLevel(newCredits);
        memMapper.addCredits(memberId, valueToAdd, newLevel);

        return newCredits;*/
        return 0;
    }

    @Override
    public String queryLevel(int credits) {
/*        Set<String> levelSet = rt.opsForZSet().rangeByScore(RedisKey.CREDITS_LEVEL.toString(), 0, credits);

        return CollectionUtils.findMax(levelSet);*/
        return "";
    }
}

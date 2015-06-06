package com.fh.taolijie.service.impl;

import com.fh.taolijie.dao.mapper.MemberModelMapper;
import com.fh.taolijie.dao.mapper.ShPostModelMapper;
import com.fh.taolijie.domain.MemberModelWithBLOBs;
import com.fh.taolijie.domain.Pagination;
import com.fh.taolijie.domain.SHPostModel;
import com.fh.taolijie.domain.SHPostModelWithBLOBs;
import com.fh.taolijie.service.ShPostService;
import com.fh.taolijie.utils.CollectionUtils;
import com.fh.taolijie.utils.ObjWrapper;
import com.fh.taolijie.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by wanghongfei on 15-6-6.
 */
@Service
@Transactional(readOnly = true)
public class DefaultShPostService implements ShPostService {
    @Autowired
    ShPostModelMapper postMapper;

    @Autowired
    MemberModelMapper memMapper;

    @Override
    public List<SHPostModel> getAllPostList(int firstResult, int capacity, ObjWrapper wrapper) {
        Pagination page = new Pagination(firstResult, CollectionUtils.determineCapacity(capacity));

        return postMapper.getAll(page.getMap());
    }

    @Override
    public List<SHPostModel> getPostList(Integer cateId, int firstResult, int capacity, ObjWrapper wrapper) {
        return postMapper.getByCategory(cateId, false, firstResult, CollectionUtils.determineCapacity(capacity));
    }

    @Override
    public List<SHPostModel> getPostList(Integer memId, boolean filtered, int firstResult, int capacity, ObjWrapper wrapper) {
        return postMapper.getByMember(memId, filtered, firstResult, CollectionUtils.determineCapacity(capacity));
    }

    @Override
    public List<SHPostModel> getAndFilter(Integer cateId, boolean pageView, int firstResult, int capacity, ObjWrapper wrapper) {
        return postMapper.getByCategory(cateId, pageView, firstResult, CollectionUtils.determineCapacity(capacity));
    }

    @Override
    public List<SHPostModel> runSearch(SHPostModel model, ObjWrapper wrapper) {
        return postMapper.searchBy(model);
    }

    @Override
    public List<SHPostModel> getUnverifiedPostList(SHPostModel model, ObjWrapper wrapper) {
        return postMapper.findBy(model);
    }

    @Override
    public List<SHPostModel> getSuedPost(int firstResult, int capacity, ObjWrapper wrapper) {
        return postMapper.getSuedPost(firstResult, CollectionUtils.determineCapacity(capacity));
    }

    @Override
    @Transactional(readOnly = false)
    public boolean addPost(SHPostModelWithBLOBs model) {
        postMapper.insert(model);

        return true;
    }

    @Override
    @Transactional(readOnly = false)
    public void favoritePost(Integer memId, Integer postId) {
        MemberModelWithBLOBs mem = memMapper.selectByPrimaryKey(memId);
        String oldIds = mem.getFavoriteShIds();

        String newIds = StringUtils.addToString(oldIds, postId.toString());

        mem.setFavoriteShIds(newIds);
        memMapper.updateByPrimaryKeySelective(mem);
    }

    @Override
    @Transactional(readOnly = false)
    public void unfavoritePost(Integer memId, Integer postId) {
        MemberModelWithBLOBs mem = memMapper.selectByPrimaryKey(memId);
        String oldIds = mem.getFavoriteShIds();

        String newIds = StringUtils.removeFromString(oldIds, postId.toString());

        mem.setFavoriteShIds(newIds);
        memMapper.updateByPrimaryKeySelective(mem);

    }

    @Override
    public boolean isPostAlreadyFavorite(Integer memId, Integer postId) {
        MemberModelWithBLOBs mem = memMapper.selectByPrimaryKey(memId);
        String oldIds = mem.getFavoriteShIds();

        return StringUtils.checkIdExists(oldIds, postId.toString());
    }

    @Override
    @Transactional(readOnly = false)
    public void complaint(Integer postId) {
        postMapper.increaseComplaint(postId);
    }

    @Override
    public SHPostModelWithBLOBs findPost(Integer postId) {
        return postMapper.selectByPrimaryKey(postId);
    }

    @Override
    @Transactional(readOnly = false)
    public boolean deletePost(Integer postId) {
        int row = postMapper.deleteByPrimaryKey(postId);

        return row <= 0 ? false : true;
    }

    @Override
    public boolean updatePost(Integer postId, SHPostModelWithBLOBs model) {
        model.setId(postId);
        int row = postMapper.updateByPrimaryKeySelective(model);

        return row <= 0 ? false : true;
    }

    @Override
    @Transactional(readOnly = false)
    public void changeCategory(Integer postId, Integer cateId) {
        SHPostModelWithBLOBs model = new SHPostModelWithBLOBs();
        model.setId(postId);
        model.setSecondHandPostCategoryId(cateId);


        postMapper.updateByPrimaryKeySelective(model);
    }

    @Override
    @Transactional(readOnly = false)
    public void increasePageView(Integer postId) {
        postMapper.increasePageView(postId);
    }
}

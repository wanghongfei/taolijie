package com.fh.taolijie.service.impl;

import com.fh.taolijie.controller.dto.GeneralMemberDto;
import com.fh.taolijie.controller.dto.SecondHandPostDto;
import com.fh.taolijie.domain.MemberEntity;
import com.fh.taolijie.domain.SecondHandPostCategoryEntity;
import com.fh.taolijie.domain.SecondHandPostEntity;
import com.fh.taolijie.service.SHPostService;
import com.fh.taolijie.service.SearchService;
import com.fh.taolijie.service.repository.MemberRepo;
import com.fh.taolijie.service.repository.SHPostCategoryRepo;
import com.fh.taolijie.service.repository.SHPostRepo;
import com.fh.taolijie.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.*;
import java.util.function.Consumer;

/**
 * Created by wanghongfei on 15-3-8.
 */
@Service
public class DefaultSHPostService extends DefaultPageService implements SHPostService {
    @Autowired
    SHPostRepo postRepo;

    @Autowired
    SHPostCategoryRepo cateRepo;

    @Autowired
    MemberRepo memberRepo;

    @Autowired
    SearchService search;

    @PersistenceContext
    EntityManager em;

    /**
     * 用来设置DTO对象中与对应Domain对象变量名不匹配的域(field).
     * 此内部类存在的原因是为了消除重复代码。
     * <p> 用于{@link CollectionUtils#entity2Dto(Object, Class, Consumer)}方法的第三个参数
     * @param <ENTITY>
     */
    protected class SetupDto<ENTITY extends SecondHandPostEntity> implements Consumer<SecondHandPostDto> {
        private ENTITY entity;

        public SetupDto(ENTITY entity) {
            this.entity = entity;
        }

        @Override
        public void accept(SecondHandPostDto dto) {
            dto.setMemberId(entity.getMember().getId());
            dto.setCategoryId(entity.getCategory().getId());
            dto.setCategoryName(entity.getCategory().getName());
            // 设置内嵌dto
            dto.setMemberDto(CollectionUtils.entity2Dto(entity.getMember(), GeneralMemberDto.class, null));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<SecondHandPostDto> getAllPostList(int firstResult, int capacity, ObjWrapper wrapper) {
        int cap = CollectionUtils.determineCapacity(capacity);

        Page<SecondHandPostEntity> entityList = postRepo.findAll(new PageRequest(firstResult, cap));
        wrapper.setObj(entityList.getTotalPages());

        return CollectionUtils.transformCollection(entityList, SecondHandPostDto.class, (entity) -> {
            return CollectionUtils.entity2Dto(entity, SecondHandPostDto.class, new SetupDto(entity));
        });
    }

    @Override
    @Transactional(readOnly = true)
    public List<SecondHandPostDto> getPostList(Integer cateId, int firstResult, int capacity, ObjWrapper wrapper) {
        int cap = capacity;
        if (cap <= 0) {
            cap = Constants.PAGE_CAPACITY;
        }

        SecondHandPostCategoryEntity cate = cateRepo.findOne(cateId);
        CheckUtils.nullCheck(cate);

        Page<SecondHandPostEntity> postPages = postRepo.findByCategory(cate, new PageRequest(firstResult, cap));
        wrapper.setObj(postPages.getTotalPages());

        return CollectionUtils.transformCollection(postPages, SecondHandPostDto.class, (entity) -> {
            return CollectionUtils.entity2Dto(entity, SecondHandPostDto.class, new SetupDto(entity));
        });
    }

    @Override
    public List<SecondHandPostDto> getPostList(Integer memId, boolean filtered, int firstResult, int capacity, ObjWrapper wrapper) {
        int cap = capacity;
        if (cap <= 0 ) {
            cap = Constants.PAGE_CAPACITY;
        }

        MemberEntity member = memberRepo.getOne(memId);
        CheckUtils.nullCheck(member);

        Page<SecondHandPostEntity> postList = null;

        if (filtered) {
            postList = postRepo.findByMemberAndNotExpired(member, new Date(), new PageRequest(firstResult, cap));
        } else {
            postList = postRepo.findByMember(member, new PageRequest(firstResult, cap));
        }

        wrapper.setObj(postList.getTotalPages());

        List<SecondHandPostDto> dtoList = new ArrayList<>();
        for (SecondHandPostEntity post : postList) {
            dtoList.add(CollectionUtils.entity2Dto(post, SecondHandPostDto.class, new SetupDto(post)));
        }

        return dtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<SecondHandPostDto> getAndFilter(Integer cateId, boolean pageView, int firstResult, int capacity, ObjWrapper wrapper) {
        Map<String, Object> parmMap = new HashMap<>();

        if (null != cateId) {
            parmMap.put("category", cateRepo.getOne(cateId));
        }


        Map.Entry<String, String> sort = null;
        if (pageView) {
            sort = new AbstractMap.SimpleEntry("postTime", "DESC");
        }

        // 构建Query对象
        String query = StringUtils.buildQuery("post", SecondHandPostEntity.class.getSimpleName(), parmMap, sort);
        TypedQuery<SecondHandPostEntity> queryObj = em.createQuery(query, SecondHandPostEntity.class);

        // 参数赋值
        Set<Map.Entry<String, Object>> entrySet = parmMap.entrySet();
        for (Map.Entry<String, Object> entry : entrySet) {
            queryObj.setParameter(entry.getKey(), entry.getValue());
        }

        // 执行查询
        List<SecondHandPostEntity> entityList = queryObj.getResultList();
        // 分页
        int cap = CollectionUtils.determineCapacity(capacity);
        Page<SecondHandPostEntity> entityPage = new PageImpl<SecondHandPostEntity>(entityList, new PageRequest(firstResult, cap), entityList.size());
        wrapper.setObj(entityPage.getTotalPages());

        return CollectionUtils.transformCollection(entityPage, SecondHandPostDto.class, (entity) -> {
            return CollectionUtils.entity2Dto(entity, SecondHandPostDto.class, (dto) -> new SetupDto(entity));
        });
    }

    @Override
    @Transactional(readOnly = true)
    public List<SecondHandPostDto> runSearch(String field, String includeString, int firstResult, int capacity, ObjWrapper wrapper) {
        Map<String, Object> parmMap = new HashMap<>();
        parmMap.put(field, includeString);

        List<SecondHandPostEntity> entityList = search.runLikeQuery(SecondHandPostEntity.class, parmMap, new AbstractMap.SimpleEntry<String, String>("postTime", "DESC"), em);
        int cap = CollectionUtils.determineCapacity(capacity);
        Page<SecondHandPostEntity> entityPage = new PageImpl<SecondHandPostEntity>(entityList, new PageRequest(firstResult, cap), entityList.size());
        wrapper.setObj(entityPage.getTotalPages());

        return CollectionUtils.transformCollection(entityList, SecondHandPostDto.class, (entity) -> {
            return CollectionUtils.entity2Dto(entity, SecondHandPostDto.class, (dto) -> new SetupDto(entity));
        });
    }

    @Override
    @Transactional(readOnly = true)
    public List<SecondHandPostDto> getUnverifiedPostList(int firstResult, int capacity, ObjWrapper wrapper) {
        int cap = CollectionUtils.determineCapacity(capacity);
        Page<SecondHandPostEntity> entityList = postRepo.findByVerified(Constants.VerifyStatus.NONE.toString(), new PageRequest(firstResult, cap));

        return CollectionUtils.transformCollection(entityList, SecondHandPostDto.class, (entity) -> {
            return CollectionUtils.entity2Dto(entity, SecondHandPostDto.class, (dto) -> new SetupDto(entity));
        });
    }

    @Override
    @Transactional(readOnly = true)
    public List<SecondHandPostDto> getSuedPost(int firstResult, int capacity, ObjWrapper wrapper) {
        int cap = CollectionUtils.determineCapacity(capacity);
        Page<SecondHandPostEntity> entityPage = postRepo.getSuedPost(new PageRequest(firstResult, cap));

        return CollectionUtils.transformCollection(entityPage, SecondHandPostDto.class, (entity) -> {
            return CollectionUtils.entity2Dto(entity, SecondHandPostDto.class, (dto) -> new SetupDto(entity));
        });
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void favoritePost(Integer memId, Integer postId) {
        MemberEntity mem = memberRepo.findOne(memId);
        SecondHandPostEntity post = postRepo.findOne(postId);
        CheckUtils.nullCheck(mem, post);


        String oldIds = mem.getFavoriteShIds();
        String newIds = StringUtils.addToString(oldIds, postId.toString());
        mem.setFavoriteShIds(newIds);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void unfavoritePost(Integer memId, Integer postId) {
        MemberEntity mem = memberRepo.findOne(memId);
        SecondHandPostEntity post = postRepo.findOne(postId);
        CheckUtils.nullCheck(mem, post);

        String oldIds = mem.getFavoriteShIds();
        String newIds = StringUtils.removeFromString(oldIds, postId.toString());
        mem.setFavoriteShIds(newIds);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public boolean addPost(SecondHandPostDto postDto) {
        // 创建帖子实体
        //SecondHandPostEntity post = makePost(postDto);
        SecondHandPostEntity post = CollectionUtils.dto2Entity(postDto, SecondHandPostEntity.class, (entity) -> {
            MemberEntity mem = memberRepo.getOne(postDto.getMemberId());
            SecondHandPostCategoryEntity cate = cateRepo.getOne(postDto.getCategoryId());
            CheckUtils.nullCheck(mem, cate);

            entity.setMember(mem);
            entity.setCategory(cate);
        });

        // 从分类中添加此帖子
        Integer cateId = postDto.getCategoryId();
        SecondHandPostCategoryEntity cate = cateRepo.getOne(cateId);
        CheckUtils.nullCheck(cate);

        Collection<SecondHandPostEntity> postCollection = cate.getPostCollection();
        if (postCollection == null) {
            cate.setPostCollection(new ArrayList<>());
            postCollection = cate.getPostCollection();
        }
        postCollection.add(post);

        // 保存帖子
        postRepo.save(post);

        return true;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void complaint(Integer postId) {
        SecondHandPostEntity post = postRepo.findOne(postId);
        CheckUtils.nullCheck(post);

        Integer original = post.getComplaint();

        // 帖子本身投诉数+1
        Integer newValue = original == null ? 1 : original.intValue() + 1;
        post.setComplaint(newValue);

        // 对应用户投诉数+1
        original = post.getMember().getComplaint();
        newValue = original == null ? 1 : original.intValue() + 1;
        post.getMember().setComplaint(newValue);
    }


    @Override
    @Transactional(readOnly = true)
    public SecondHandPostDto findPost(Integer postId) {
        SecondHandPostEntity entity = postRepo.getOne(postId);
        CheckUtils.nullCheck(entity);

        return CollectionUtils.entity2Dto(entity, SecondHandPostDto.class, new SetupDto(entity));
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public boolean deletePost(Integer postId) {
        // 从分类中删除关联
        SecondHandPostEntity post = postRepo.findOne(postId);
        CheckUtils.nullCheck(post);

        CollectionUtils.removeFromCollection(post.getCategory().getPostCollection(), (po) -> {
            return po.getId().equals(postId);
        });

        // 删除实体本身
        postRepo.delete(post);

        return true;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public boolean updatePost(Integer postId, SecondHandPostDto postDto) {
        SecondHandPostEntity post = postRepo.findOne(postId);
        CheckUtils.nullCheck(post);

        CollectionUtils.updateEntity(post, postDto, null);
        //updatePost(postRepo.findOne(postId), postDto);

        return true;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void changeCategory(Integer postId, Integer cateId) {
        SecondHandPostEntity post = postRepo.getOne(postId);
        SecondHandPostCategoryEntity cate = cateRepo.getOne(cateId);
        CheckUtils.nullCheck(post, cate);

        // 设置新分类
        post.setCategory(cate);

        // 从原分类中移除post
        CollectionUtils.removeFromCollection(post.getCategory().getPostCollection(), (po) -> {
            return po.getId().equals(postId);
        });
        // 将post添加到新分类中
        Collection<SecondHandPostEntity> postCollection = cate.getPostCollection();
        if (null == postCollection) {
            cate.setPostCollection(new ArrayList<>());
            postCollection = cate.getPostCollection();
        }
        postCollection.add(post);
    }

}

package com.fh.taolijie.service.impl;

import com.fh.taolijie.controller.dto.SecondHandPostDto;
import com.fh.taolijie.domain.MemberEntity;
import com.fh.taolijie.domain.SecondHandPostCategoryEntity;
import com.fh.taolijie.domain.SecondHandPostEntity;
import com.fh.taolijie.service.SHPostService;
import com.fh.taolijie.service.SearchService;
import com.fh.taolijie.service.repository.MemberRepo;
import com.fh.taolijie.service.repository.SHPostCategoryRepo;
import com.fh.taolijie.service.repository.SHPostRepo;
import com.fh.taolijie.utils.CollectionUtils;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.ObjWrapper;
import com.fh.taolijie.utils.StringUtils;
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

    @Override
    @Transactional(readOnly = true)
    public List<SecondHandPostDto> getAllPostList(int firstResult, int capacity, ObjWrapper wrapper) {
        int cap = CollectionUtils.determineCapacity(capacity);

        Page<SecondHandPostEntity> entityList = postRepo.findAll(new PageRequest(firstResult, cap));
        wrapper.setObj(entityList.getTotalPages());

        return CollectionUtils.transformCollection(entityList, SecondHandPostDto.class, (entity) -> {
            return CollectionUtils.entity2Dto(entity, SecondHandPostDto.class, (dto) -> {
                dto.setMemberId(entity.getMember().getId());
                dto.setCategoryId(entity.getCategory().getId());
                dto.setCategoryName(entity.getCategory().getName());
            });
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

        Page<SecondHandPostEntity> postPages = postRepo.findByCategory(cate, new PageRequest(firstResult, cap));
        wrapper.setObj(postPages.getTotalPages());

        return CollectionUtils.transformCollection(postPages, SecondHandPostDto.class, (entity) -> {
            return CollectionUtils.entity2Dto(entity, SecondHandPostDto.class, (dto) -> {
                dto.setMemberId(entity.getMember().getId());
                dto.setCategoryId(entity.getCategory().getId());
                dto.setCategoryName(entity.getCategory().getName());
            });
        });
    }

    @Override
    public List<SecondHandPostDto> getPostList(Integer memId, boolean filtered, int firstResult, int capacity, ObjWrapper wrapper) {
        int cap = capacity;
        if (cap <= 0 ) {
            cap = Constants.PAGE_CAPACITY;
        }

        MemberEntity member = memberRepo.getOne(memId);
        Page<SecondHandPostEntity> postList = null;

        if (filtered) {
            postList = postRepo.findByMemberAndNotExpired(member, new Date(), new PageRequest(firstResult, cap));
        } else {
            postList = postRepo.findByMember(member, new PageRequest(firstResult, cap));
        }

        wrapper.setObj(postList.getTotalPages());

        List<SecondHandPostDto> dtoList = new ArrayList<>();
        for (SecondHandPostEntity post : postList) {
            dtoList.add(CollectionUtils.entity2Dto(post, SecondHandPostDto.class, (dto) -> {
                dto.setMemberId(post.getMember().getId());
                dto.setCategoryId(post.getCategory().getId());
                dto.setCategoryName(post.getCategory().getName());
            }));
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
            return CollectionUtils.entity2Dto(entity, SecondHandPostDto.class, (dto) -> {
                dto.setCategoryId(entity.getCategory().getId());
                dto.setCategoryName(entity.getCategory().getName());
                dto.setMemberId(entity.getMember().getId());
            });
        });
    }

    @Override
    @Transactional(readOnly = true)
    public List<SecondHandPostDto> runSearch(String field, String includeString, int firstResult, int capacity, ObjWrapper wrapper) {
        Map<String, Object> parmMap = new HashMap<>();
        parmMap.put(field, includeString);

        List<SecondHandPostEntity> entityList = search.runLikeQuery(SecondHandPostEntity.class, parmMap, new AbstractMap.SimpleEntry<String, String>("postTime", "DESC"), em);

        return CollectionUtils.transformCollection(entityList, SecondHandPostDto.class, (entity) -> {
            return CollectionUtils.entity2Dto(entity, SecondHandPostDto.class, (dto) -> {
                dto.setMemberId(entity.getMember().getId());
                dto.setCategoryId(entity.getCategory().getId());
                dto.setCategoryName(entity.getCategory().getName());
            });
        });
    }

    @Override
    @Transactional(readOnly = true)
    public List<SecondHandPostDto> getUnverifiedPostList(int firstResult, int capacity, ObjWrapper wrapper) {
        int cap = CollectionUtils.determineCapacity(capacity);
        Page<SecondHandPostEntity> entityList = postRepo.findByVerified(Constants.VerifyStatus.NONE.toString(), new PageRequest(firstResult, cap));

        return CollectionUtils.transformCollection(entityList, SecondHandPostDto.class, (entity) -> {
            return CollectionUtils.entity2Dto(entity, SecondHandPostDto.class, (dto) -> {
                dto.setMemberId(entity.getMember().getId());
                dto.setCategoryId(entity.getCategory().getId());
                dto.setCategoryName(entity.getCategory().getName());
            });
        });
    }

    @Override
    @Transactional(readOnly = true)
    public List<SecondHandPostDto> getSuedPost(int firstResult, int capacity, ObjWrapper wrapper) {
        int cap = CollectionUtils.determineCapacity(capacity);
        Page<SecondHandPostEntity> entityPage = postRepo.getSuedPost(new PageRequest(firstResult, cap));

        return CollectionUtils.transformCollection(entityPage, SecondHandPostDto.class, (entity) -> {
            return CollectionUtils.entity2Dto(entity, SecondHandPostDto.class, (dto) -> {
                dto.setMemberId(entity.getMember().getId());
                dto.setCategoryId(entity.getCategory().getId());
                dto.setCategoryName(entity.getCategory().getName());
            });
        });
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public boolean addPost(SecondHandPostDto postDto) {
        // 创建帖子实体
        //SecondHandPostEntity post = makePost(postDto);
        SecondHandPostEntity post = CollectionUtils.dto2Entity(postDto, SecondHandPostEntity.class, (entity) -> {
            MemberEntity mem = memberRepo.getOne(postDto.getMemberId());
            SecondHandPostCategoryEntity cate = cateRepo.getOne(postDto.getCategoryId());

            entity.setMember(mem);
            entity.setCategory(cate);
        });

        // 从分类中添加此帖子
        Integer cateId = postDto.getCategoryId();
        SecondHandPostCategoryEntity cate = cateRepo.getOne(cateId);
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

        return CollectionUtils.entity2Dto(entity, SecondHandPostDto.class, (dto) -> {
            dto.setMemberId(entity.getMember().getId());
            dto.setCategoryId(entity.getCategory().getId());
            dto.setCategoryName(entity.getCategory().getName());
        });
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public boolean deletePost(Integer postId) {
        // 从分类中删除关联
        SecondHandPostEntity post = postRepo.findOne(postId);
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
        CollectionUtils.updateEntity(post, postDto, null);
        //updatePost(postRepo.findOne(postId), postDto);

        return true;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void changeCategory(Integer postId, Integer cateId) {
        SecondHandPostEntity post = postRepo.getOne(postId);
        // 得到新分类
        SecondHandPostCategoryEntity cate = cateRepo.getOne(cateId);
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

   /* private void updatePost(SecondHandPostEntity post, SecondHandPostDto dto) {
        post.setTitle(dto.getTitle());
        post.setExpiredTime(dto.getExpiredTime());
        post.setPostTime(dto.getPostTime());
        post.setDepreciationRate(dto.getDepreciationRate());
        post.setOriginalPrice(dto.getOriginalPrice());
        post.setSellPrice(dto.getSellPrice());
        post.setPicturePath(dto.getPicturePath());
        post.setDescription(dto.getDescription());
        post.setLikes(dto.getLikes());
        post.setDislikes(dto.getDislikes());
    }*/

    /*private SecondHandPostEntity makePost(SecondHandPostDto dto) {
        SecondHandPostEntity post = new SecondHandPostEntity(dto.getTitle(), dto.getExpiredTime(), dto.getPostTime(),
                dto.getDepreciationRate(), dto.getOriginalPrice(), dto.getSellPrice(), dto.getPicturePath(),
                dto.getDescription(), dto.getLikes(), dto.getDislikes(), null, null);

        MemberEntity mem = memberRepo.getOne(dto.getMemberId());
        SecondHandPostCategoryEntity cate = cateRepo.getOne(dto.getCategoryId());
        post.setMember(mem);
        post.setCategory(cate);

        return post;
    }*/

   /* private SecondHandPostDto makePostDto(SecondHandPostEntity post) {
        SecondHandPostDto dto = new SecondHandPostDto();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setExpiredTime(post.getExpiredTime());
        dto.setPostTime(post.getPostTime());
        dto.setDepreciationRate(post.getDepreciationRate());
        dto.setOriginalPrice(post.getOriginalPrice());
        dto.setSellPrice(post.getSellPrice());
        dto.setPicturePath(post.getPicturePath());
        dto.setDescription(post.getDescription());
        dto.setLikes(post.getLikes());
        dto.setDislikes(post.getDislikes());

        dto.setMemberId(post.getMember().getId());
        dto.setCategoryName(post.getCategory().getName());
        dto.setCategoryId(post.getCategory().getId());


        return dto;
    }*/
}

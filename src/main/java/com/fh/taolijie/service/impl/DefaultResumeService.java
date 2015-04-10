package com.fh.taolijie.service.impl;

import com.fh.taolijie.controller.dto.PostRecordDto;
import com.fh.taolijie.controller.dto.ResumeDto;
import com.fh.taolijie.domain.JobPostCategoryEntity;
import com.fh.taolijie.domain.MemberEntity;
import com.fh.taolijie.domain.ResumeEntity;
import com.fh.taolijie.service.ResumeService;
import com.fh.taolijie.service.repository.JobPostCategoryRepo;
import com.fh.taolijie.service.repository.MemberRepo;
import com.fh.taolijie.service.repository.ResumeRepo;
import com.fh.taolijie.utils.*;
import com.fh.taolijie.utils.json.JsonWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Created by wanghongfei on 15-3-7.
 */
@Repository
public class DefaultResumeService extends DefaultPageService implements ResumeService {
    @PersistenceContext
    private EntityManager em;

    @Autowired
    private ResumeRepo resumeRepo;

    @Autowired
    private MemberRepo memRepo;

    @Autowired
    private JobPostCategoryRepo cateRepo;

    private static final String QUERY_INTEND = "SELECT resume_id AS resumeId, job_post_category_id AS categoryId FROM resume_job_post_category AS category WHERE category.job_post_category_id = :cateId";


    /**
     * 用来设置DTO对象中与对应Domain对象变量名不匹配的域(field).
     * @param <ENTITY>
     */
    private class SetupResumeDto<ENTITY extends ResumeEntity> implements Consumer<ResumeDto> {
        private ENTITY entity;

        public SetupResumeDto(ENTITY entity) {
            this.entity = entity;
        }

        @Override
        public void accept(ResumeDto dto) {
            // 设置member id
            dto.setMemberId(entity.getMember().getId());

            // 设置求职意向到DTO对象中
            if (null != entity.getCategoryList()) {
                List<Integer> intendList = entity.getCategoryList().stream()
                        .map( en -> en.getId() )
                        .collect(Collectors.toList());
                dto.setIntendCategoryId(intendList);
            }
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResumeDto> getAllResumeList(int firstResult, int capacity, ObjWrapper wrap) {
        int cap = CollectionUtils.determineCapacity(capacity);

        Page<ResumeEntity> entityList = resumeRepo.findAll(new PageRequest(firstResult, cap));

        wrap.setObj(entityList.getTotalPages());

        return CollectionUtils.transformCollection(entityList, ResumeDto.class, (ResumeEntity resumeEntity) -> {
            return  CollectionUtils.entity2Dto(resumeEntity, ResumeDto.class, new SetupResumeDto(resumeEntity));
        });
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResumeDto> getAllResumeList(Constants.AccessAuthority authority, int firstResult, int capacity, ObjWrapper wrap) {
        int cap = CollectionUtils.determineCapacity(capacity);

        Page<ResumeEntity> entityList = resumeRepo.findByAuthority(authority.toString(), new PageRequest(firstResult, cap));
        wrap.setObj(entityList.getTotalPages());

        return CollectionUtils.transformCollection(entityList, ResumeDto.class, (ResumeEntity resumeEntity) -> {
            return  CollectionUtils.entity2Dto(resumeEntity, ResumeDto.class, new SetupResumeDto(resumeEntity));
        });
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResumeDto> getResumeList(Integer memId, int firstResult, int capacity, ObjWrapper wrap) {
        int cap = CollectionUtils.determineCapacity(capacity);

        MemberEntity mem = em.getReference(MemberEntity.class, memId);
        CheckUtils.nullCheck(mem);

        Page<ResumeEntity> rList = resumeRepo.findByMember(mem, new PageRequest(firstResult, cap));
        wrap.setObj(rList.getTotalPages());

        return CollectionUtils.transformCollection(rList, ResumeDto.class, (ResumeEntity resumeEntity) -> {
            return  CollectionUtils.entity2Dto(resumeEntity, ResumeDto.class, new SetupResumeDto(resumeEntity));
        });
    }

    @Override
    public List<ResumeDto> getResumeList(Integer memId, Constants.AccessAuthority authority, int firstResult, int capacity, ObjWrapper wrap) {
        int cap = CollectionUtils.determineCapacity(capacity);

        MemberEntity mem = em.getReference(MemberEntity.class, memId);
        CheckUtils.nullCheck(mem);


        Page<ResumeEntity> rList = resumeRepo.findByMemberAndAuthority(mem, authority.toString(), new PageRequest(firstResult, cap));
        wrap.setObj(rList.getTotalPages());

        return CollectionUtils.transformCollection(rList, ResumeDto.class, (ResumeEntity resumeEntity) -> {
            return  CollectionUtils.entity2Dto(resumeEntity, ResumeDto.class, new SetupResumeDto(resumeEntity));
        });
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResumeDto> getResumeListByIntend(Integer cateId) {
        // 查询关联表
        // object[0]是简历id, object[1]是工作分类id
        List<Object[]> rajList =  em.createNativeQuery(QUERY_INTEND)
                .setParameter("cateId", cateId)
                .getResultList();

        // 构造ResumeList
        List<ResumeDto> dtoList = new ArrayList<>();
        for (Object[] obj : rajList) {
            Integer resumeId = (Integer) obj[0];
            //ResumeAndJobCategory raj = (ResumeAndJobCategory) obj;
            //Integer resumeId = raj.getResumeId();
            ResumeEntity entity = resumeRepo.findOne(resumeId);

            dtoList.add(CollectionUtils.entity2Dto(entity, ResumeDto.class, (dto) -> {
                dto.setMemberId(entity.getMember().getId());
            }));
        }

        // 根据简历创建时间排序
        dtoList.stream().sorted( (dto1, dto2) -> {
            return dto1.getCreatedTime().compareTo(dto2.getCreatedTime());
        });

        return dtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostRecordDto> getPostRecord(Integer memId, int page, int capacity, ObjWrapper wrap) {
        MemberEntity mem = memRepo.getOne(memId);
        CheckUtils.nullCheck(mem);

        String recordJson = mem.getAppliedJobIds();

        // 没有记录，返回空List
        if (null == recordJson) {
            return new ArrayList<>();
        }

        // 解析JSON
        JsonWrapper js = new JsonWrapper(recordJson);
        List<Map<String, String>> jsonList = js.getJsonList();


        // 取出记录信息
        List<PostRecordDto> dtoList = new ArrayList<>();
        for (Map<String, String> jsonObj : jsonList) {
            String postId = jsonObj.get(Constants.ApplicationRecord.KEY_ID);
            String timeString = jsonObj.get(Constants.ApplicationRecord.KEY_TIME);

            PostRecordDto dto = new PostRecordDto(Integer.valueOf(postId), new Date(Long.parseLong(timeString)) );
            dtoList.add(dto);
        }

        // 分页
        int cap = CollectionUtils.determineCapacity(capacity);
        Page<PostRecordDto> dtoPage = new PageImpl<>(dtoList, new PageRequest(page, cap), dtoList.size());

        return CollectionUtils.transformCollection(dtoPage, PostRecordDto.class, (dto) -> dto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResumeDto> getResumesByRecord(List<PostRecordDto> recordList) {
        List<Integer> ids = recordList.stream()
                .map(PostRecordDto::getJobPostId)
                .collect(Collectors.toList());

        List<ResumeEntity> entityList = resumeRepo.findByIds(ids);

        return CollectionUtils.transformCollection(entityList, ResumeDto.class, entity -> {
            return CollectionUtils.entity2Dto(entity, ResumeDto.class, new SetupResumeDto(entity));
        });
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResumeDto> getResumesByIds(int page, int capacity, ObjWrapper wrapper, Integer... ids) {
        int cap = CollectionUtils.determineCapacity(capacity);

        Page<ResumeEntity> entityList = resumeRepo.findByIds(Arrays.asList(ids), new PageRequest(page, cap));

        return CollectionUtils.transformCollection(entityList, ResumeDto.class, entity -> {
            return CollectionUtils.entity2Dto(entity, ResumeDto.class, new SetupResumeDto(entity));
        });
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public boolean refresh(Integer resumeId) {
        ResumeEntity r = em.find(ResumeEntity.class, resumeId);
        CheckUtils.nullCheck(r);

        Date original = r.getCreatedTime();
        Date now = new Date();


        if (now.getTime() - original.getTime() >= TimeUnit.DAYS.toSeconds(1)) {
            r.setCreatedTime(now);
            return true;
        }

        return false;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void favoriteResume(Integer memId, Integer resumeId) {
        MemberEntity mem = memRepo.findOne(memId);
        ResumeEntity resume = resumeRepo.findOne(resumeId);
        CheckUtils.nullCheck(mem ,resume);

        String oldIds = mem.getFavoriteResumeIds();
        String newIds = StringUtils.addToString(oldIds, resumeId.toString());
        mem.setFavoriteResumeIds(newIds);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public boolean updateResume(Integer resumeId, ResumeDto resumeDto) {
        ResumeEntity r = em.find(ResumeEntity.class, resumeId);
        CheckUtils.nullCheck(r);

        CollectionUtils.updateEntity(r, resumeDto, (entity) -> {
            // 设置求职意向
            entity.getCategoryList().clear();
            for (Integer cateId : resumeDto.getIntendCategoryId()) {
                JobPostCategoryEntity cate = cateRepo.getOne(cateId);
                entity.getCategoryList().add(cate);
            }
        });

        //updateResume(r, resumeDto);

        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public ResumeDto findResume(Integer resumeId) {
        ResumeEntity entity = em.find(ResumeEntity.class, resumeId);
        CheckUtils.nullCheck(entity);

        return CollectionUtils.entity2Dto(entity, ResumeDto.class, new SetupResumeDto(entity));
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public boolean deleteResume(Integer resumeId) {
        ResumeEntity r = em.getReference(ResumeEntity.class, resumeId);
        CheckUtils.nullCheck(r);

        // 从member中删除关联
        CollectionUtils.removeFromCollection(r.getMember().getResumeCollection(), (resume) -> {
            return resume.getId().equals(resumeId);
        });

        // 删除resume本身
        em.remove(r);

        return true;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void addResume(ResumeDto dto) {
        //ResumeEntity r = makeResume(dto);
        ResumeEntity r = CollectionUtils.dto2Entity(dto, ResumeEntity.class, (entity) -> {
            MemberEntity mem = memRepo.getOne(dto.getMemberId());
            CheckUtils.nullCheck(mem);

            // 设置简历主人
            entity.setMember(mem);

            // 设置求职意向
            List<JobPostCategoryEntity> cateList = new ArrayList<>();
            for (Integer cateId : dto.getIntendCategoryId()) {
                JobPostCategoryEntity cate = cateRepo.getOne(cateId);
                cateList.add(cate);
            }
            entity.setCategoryList(cateList);
        });

        resumeRepo.save(r);
    }

}

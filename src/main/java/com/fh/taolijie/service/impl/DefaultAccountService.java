package com.fh.taolijie.service.impl;

import cn.fh.security.utils.CredentialUtils;
import com.fh.taolijie.controller.dto.EmployerDto;
import com.fh.taolijie.controller.dto.GeneralMemberDto;
import com.fh.taolijie.controller.dto.RoleDto;
import com.fh.taolijie.controller.dto.StudentDto;
import com.fh.taolijie.domain.*;
import com.fh.taolijie.exception.checked.DuplicatedUsernameException;
import com.fh.taolijie.exception.checked.PasswordIncorrectException;
import com.fh.taolijie.exception.checked.UserInvalidException;
import com.fh.taolijie.exception.checked.UserNotExistsException;
import com.fh.taolijie.service.AccountService;
import com.fh.taolijie.service.repository.MemberRepo;
import com.fh.taolijie.service.repository.RoleRepo;
import com.fh.taolijie.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * {@link AccountService}接口的默认实现
 * Created by wanghongfei on 15-3-5.
 */
@Service
public class DefaultAccountService implements AccountService {
    private Logger logger = LoggerFactory.getLogger(DefaultAccountService.class);

    @PersistenceContext
    private EntityManager em;

    @Autowired
    MemberRepo memberRepo;

    @Autowired
    RoleRepo roleRepo;

    @Autowired
    Mail mail;

    /**
     * 用来设置DTO对象中与对应Domain对象变量名不匹配的域(field).
     * 此内部类存在的原因是为了消除重复代码。
     * <p> 用于{@link CollectionUtils#entity2Dto(Object, Class, Consumer)}方法的第三个参数
     * @param <ENTITY>
     */
    protected class SetupMemberDto<ENTITY extends MemberEntity> implements Consumer<GeneralMemberDto> {
        private ENTITY entity;
        private Constants.RoleType type;

        public SetupMemberDto(ENTITY entity, Constants.RoleType type) {
            this.entity = entity;
            this.type = type;
        }

        @Override
        public void accept(GeneralMemberDto dto) {
            if (type == Constants.RoleType.USER || type == Constants.RoleType.STUDENT || type == Constants.RoleType.EMPLOYER) {
                loadRoleField(entity, dto);
            }

            if (type == Constants.RoleType.STUDENT) {
                loadEducationField(entity, (StudentDto) dto);
            }
        }
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public boolean registerStudent(StudentDto stuDto) throws DuplicatedUsernameException {
        // 判断用户是否存在
        if (true == isUserExists(stuDto.getUsername())) {
            throw new DuplicatedUsernameException(Constants.ErrorType.USERNAME_EXISTS);
        }

        // 创建实体
        MemberEntity mem = CollectionUtils.dto2Entity(stuDto, MemberEntity.class, null);

        // 保存实体
        em.persist(mem);

        // 得到角色
        Collection<MemberRoleEntity> memRoleCollection = new ArrayList<>();
        List<Integer> idList = stuDto.getRoleIdList();
        for (Integer id : idList) {
            RoleEntity role = em.getReference(RoleEntity.class, id);
            // 创建关联实体
            MemberRoleEntity mr = new MemberRoleEntity();
            mr.setRole(role);
            mr.setMember(mem);
            em.persist(mr);

            // 将关联添加到member实体中
            memRoleCollection.add(mr);
        }
        mem.setMemberRoleCollection(memRoleCollection);

        return true;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public Integer register(GeneralMemberDto dto) throws DuplicatedUsernameException {
        // 检查用户是否存在
        if (true == isUserExists(dto.getUsername())) {
            throw new DuplicatedUsernameException(Constants.ErrorType.USERNAME_EXISTS);
        }

        // 创建实体
        MemberEntity mem = CollectionUtils.dto2Entity(dto, MemberEntity.class, null);

        // 保存实体
        em.persist(mem);

        // 得到角色
        Collection<MemberRoleEntity> memRoleCollection = new ArrayList<>();
        List<Integer> idList = dto.getRoleIdList();
        for (Integer id : idList) {
            RoleEntity role = em.getReference(RoleEntity.class, id);
            // 创建关联实体
            MemberRoleEntity mr = new MemberRoleEntity();
            mr.setRole(role);
            mr.setMember(mem);
            em.persist(mr);

            // 将关联添加到member实体中
            memRoleCollection.add(mr);
        }
        mem.setMemberRoleCollection(memRoleCollection);

        em.flush();
        return mem.getId();
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public boolean registerEmployer(EmployerDto empDto) throws DuplicatedUsernameException {
        if (true == isUserExists(empDto.getUsername())) {
            throw new DuplicatedUsernameException(Constants.ErrorType.USERNAME_EXISTS);
        }

        // create entity
        MemberEntity mem = CollectionUtils.dto2Entity(empDto, MemberEntity.class, null);

        // 保存用户实体
        em.persist(mem);

        // 得到角色
        Collection<MemberRoleEntity> memRoleCollection = new ArrayList<>();
        List<Integer> idList = empDto.getRoleIdList();
        for (Integer id : idList) {
            RoleEntity role = em.getReference(RoleEntity.class, id);
            // 创建关联实体
            MemberRoleEntity mr = new MemberRoleEntity();
            mr.setRole(role);
            mr.setMember(mem);
            em.persist(mr);

            // 将关联添加到member实体中
            memRoleCollection.add(mr);
        }
        mem.setMemberRoleCollection(memRoleCollection);

        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean login(String username, String password) throws UserNotExistsException, PasswordIncorrectException, UserInvalidException {
        try {
            MemberEntity mem = em.createNamedQuery("memberEntity.findMemberByUsername", MemberEntity.class)
                    .setParameter("username", username)
                    .getSingleResult();

            // check validity
            if (false == mem.getValid()) {
                throw new UserInvalidException(Constants.ErrorType.USER_INVALID_ERROR);
            }

            // check password
            if (false == mem.getPassword().equals(CredentialUtils.sha(password))) {
                throw new PasswordIncorrectException(Constants.ErrorType.PASSWORD_ERROR);
            }

        } catch (NoResultException ex) {
            throw new UserNotExistsException(Constants.ErrorType.USERNAME_NOT_EXISTS);
        }

        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public Integer login(String identifier) {
        MemberEntity mem = null;

        try {
            mem = em.createNamedQuery("memberEntity.findByIdentifier", MemberEntity.class)
                    .setParameter("identifier", identifier)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }

        return mem.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public <T extends GeneralMemberDto> T findMember(String username, T[] type, boolean isWired) {
        MemberEntity mem = null;

        try {
            mem = em.createNamedQuery("memberEntity.findMemberByUsername", MemberEntity.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }

        if (type instanceof StudentDto[]) {
            // 是StudentDto对象
            return (T) CollectionUtils.entity2Dto(mem, StudentDto.class, new SetupMemberDto(mem, Constants.RoleType.STUDENT));
        } else if (type instanceof EmployerDto[]) {
            // 是EmployerDto对象
            return (T) CollectionUtils.entity2Dto(mem ,EmployerDto.class, new SetupMemberDto(mem, Constants.RoleType.EMPLOYER));
        } else if (type instanceof GeneralMemberDto[]) {
            return (T) CollectionUtils.entity2Dto(mem, GeneralMemberDto.class, new SetupMemberDto(mem, Constants.RoleType.USER));
        }

        return null;
    }



    @Override
    @Transactional(readOnly = true)
    public List<GeneralMemberDto> getMemberList(int firstResult, int capacity, ObjWrapper wrap) {
        int cap = capacity;
        if (0 == capacity) {
            cap = Constants.PAGE_CAPACITY;
        }

        Page<MemberEntity> memList = memberRepo.findAll(new PageRequest(firstResult, cap));
        wrap.setObj(memList.getTotalPages());


        return CollectionUtils.transformCollection(memList, GeneralMemberDto.class, entity -> {
            return CollectionUtils.entity2Dto(entity, GeneralMemberDto.class, null);
        });
    }

    @Override
    @Transactional(readOnly = true)
    public GeneralMemberDto findMember(Integer memId) {
        MemberEntity mem = memberRepo.findOne(memId);

        return CollectionUtils.entity2Dto(mem, GeneralMemberDto.class, (dto) -> {
            // 设置role信息
            Collection<MemberRoleEntity> mrCollection = mem.getMemberRoleCollection();
            List<Integer> idList = mrCollection.stream()
                    .map((mr) -> mr.getRole().getRid())
                    .collect(Collectors.toList());

            dto.setRoleIdList(idList);
        });
    }

    @Override
    @Transactional(readOnly = true)
    public Long getMemberAmount() {
        return em.createNamedQuery("memberEntity.count", Long.class)
                .getSingleResult();
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public <T extends GeneralMemberDto> boolean updateMember(T memDto) {
        MemberEntity mem = getMemberByUsername(memDto.getUsername());

        if (memDto instanceof StudentDto) {
            StudentDto dto = (StudentDto) memDto;
            CollectionUtils.updateEntity(mem, dto, null);
        } else if (memDto instanceof EmployerDto) {
            EmployerDto dto = (EmployerDto) memDto;
            CollectionUtils.updateEntity(mem , dto, null);
        } else if (memDto instanceof GeneralMemberDto) {
            GeneralMemberDto dto = memDto;
            CollectionUtils.updateEntity(mem, dto, null);
        }

        em.merge(mem);

        return true;
    }

    @Deprecated
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public boolean deleteMember(Integer memberId) {
        MemberEntity me = memberRepo.getOne(memberId);
        CheckUtils.nullCheck(me);

        // 删除用户所有评论
        Collection<ReviewEntity> reviewCo = me.getReviewCollection();
        if (null != reviewCo) {
            reviewCo.stream().forEach( review -> {
                // 判断评论有没有回复
                List<ReviewEntity> replyCo = review.getReplyList();
                if (null != replyCo) {
                    // 先删除回复
                    replyCo.stream().forEach( reply -> {
                        em.remove(reply);
                    });
                }

                // 删除评论本身
                em.remove(review);
            });
        }

        // 删除用户所有二手信息
        Collection<SecondHandPostEntity> shCo = me.getSecondHandPostCollection();
        if (null != shCo) {
            shCo.stream().forEach( post -> {
                em.remove(post);
            });
        }

        // 删除用户所有兼职信息
        Collection<JobPostEntity> jobCo = me.getJobPostCollection();
        if (null != jobCo) {
            jobCo.stream().forEach( post -> {
                em.remove(post);
            });
        }




        // 删除用户所有简历
        // 在兼职信息中删除简历投递
        Collection<ResumeEntity> resumeCo = me.getResumeCollection();
        if (null != resumeCo) {
            // TODO
        }

        // 删除用户所有通知
        // 与教育信息取消关联
        // 删除用户本身

        return false;
    }

    @Deprecated
    @Override
    public boolean deleteMember(String username) {
        return false;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public boolean addRole(RoleDto roleDto) {
        RoleEntity role = CollectionUtils.dto2Entity(roleDto, RoleEntity.class, null);
        em.persist(role);

        return true;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void invalidAccount(Integer memId) {
        MemberEntity mem = memberRepo.getOne(memId);
        CheckUtils.nullCheck(mem);

        mem.setValid(false);
    }

    @Override
    public void validateAccount(Integer memId) {
        MemberEntity mem = memberRepo.getOne(memId);
        CheckUtils.nullCheck(mem);

        mem.setValid(true);

    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void saveLoginIdentifier(Integer memId, String identifier) {
        MemberEntity mem = em.getReference(MemberEntity.class, memId);
        CheckUtils.nullCheck(mem);

        mem.setAutoLoginIdentifier(identifier);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void assignRole(Integer roleId, String username) {
        RoleEntity role = em.getReference(RoleEntity.class, roleId);
        MemberEntity mem = getMemberByUsername(username);

        // 创建关联对象
        MemberRoleEntity mr = new MemberRoleEntity();
        mr.setRole(role);
        mr.setMember(mem);
        em.persist(mr);

        // 创建关联关系
        Collection<MemberRoleEntity> mrCollection = mem.getMemberRoleCollection();
        if (null == mrCollection) {
            mrCollection = new ArrayList<>();
            mem.setMemberRoleCollection(mrCollection);
        }
        mrCollection.add(mr);
    }

    @Override
    @Transactional(readOnly = false)
    public boolean deleteRole(Integer roleId) {
        RoleEntity role = em.getReference(RoleEntity.class, roleId);
        em.remove(role);

        return true;
    }

    @Override
    @Transactional(readOnly = false)
    public void deassignRole(Integer roleId, String username) {
        RoleEntity role = em.getReference(RoleEntity.class, roleId);
        MemberEntity mem = getMemberByUsername(username);

        // 删除关联对象
        MemberRoleEntity target = CollectionUtils.removeFromCollection(mem.getMemberRoleCollection(), (mre) -> mre.getRole().getRid().equals(roleId));

        em.remove(target);

    }

    @Override
    @Transactional(readOnly = true)
    public RoleDto findRole(Integer roleId) {
        RoleEntity role = em.find(RoleEntity.class, roleId);

        return CollectionUtils.entity2Dto(role, RoleDto.class, null);
        //return makeRoleDto(role);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleDto> getAllRole() {
        List<RoleEntity> entityList = roleRepo.findAll();

        return CollectionUtils.transformCollection(entityList, RoleDto.class, entity -> {
            return CollectionUtils.entity2Dto(entity, RoleDto.class, null);
        });
    }


    /**
     * 判断用户名在数据库是是否存在
     * @param username
     * @return
     */
    private boolean isUserExists(String username) {
        Long amount = em.createNamedQuery("memberEntity.isExisted", Long.class)
                .setParameter("username", username)
                .getSingleResult();

        return amount.intValue() == 0 ? false : true;
    }

    /**
     * 触发role信息的加载
     * @param mem
     * @param dto
     */
    @Transactional(propagation = Propagation.REQUIRED)
    private void loadRoleField(MemberEntity mem, GeneralMemberDto dto) {
        // 设置role信息
        Collection<MemberRoleEntity> mrCollection = mem.getMemberRoleCollection();
        if (null != mrCollection) {
            // 得到MemberEntity关联的Role对象的id
            // 并转换成List对象返回
            List<Integer> roleIdList = mrCollection.stream()
                    .map(mre -> mre.getRole().getRid())
                    .collect(Collectors.toList());

            dto.setRoleIdList(roleIdList);
        }
    }

    /**
     * 触发教育信息eager加载
     * @param mem
     * @param dto
     */
    private void loadEducationField(MemberEntity mem, StudentDto dto) {
        // 得到关联表实体
        Collection<EducationExperienceEntity> eduCollection = mem.getEducationExperienceCollection();
        if (null == eduCollection) {
            // 没有关联对象，直接返回
            return;
        }

        // 将关联表实体中学院id取出
        List<Integer> academyIdList = eduCollection.stream()
                .map( eduEntity -> eduEntity.getAcademy().getId() )
                .collect(Collectors.toList());

        // 放置到DTO对象中
        dto.setAcademyIdList(academyIdList);
    }

    @Transactional(readOnly = true)
    private MemberEntity getMemberByUsername(String username) {
        return em.createNamedQuery("memberEntity.findMemberByUsername", MemberEntity.class)
                .setParameter("username", username)
                .getSingleResult();
    }

    /**
     * For test purpose only
     * @param args
     */
    public static void main(String[] args) {
        String hashed = CredentialUtils.sha("111111");
        Print.print(hashed);

    }

    @Override
    @Transactional(readOnly = false)
    public boolean resetPassword(String username, String token, String newPassword) {
        List<MemberEntity> memList = em.createNamedQuery("memberEntity.findMemberByUsername", MemberEntity.class)
                .setParameter("username", username)
                .getResultList();


        MemberEntity mem = memList.get(0);
        CheckUtils.nullCheck(mem);

        String tk = mem.getResetPasswordToken();
        String email = mem.getEmail();
        Date lastTokenDate = mem.getLastTokenDate();

        // 验证用户表中的邮箱和token, token日期是否为空
        if (!StringUtils.checkNotEmpty(email)  || !StringUtils.checkNotEmpty(tk) || null == lastTokenDate) {
            return false;
        }

        // 验证token是否匹配，是否过期
        if (false == checkDate(lastTokenDate) || false == token.equals(tk)) {
            return false;
        }

        // 验证通过，修改密码
        mem.setPassword(CredentialUtils.sha(newPassword));

        return true;
    }

    private boolean checkDate(Date date) {
        return TimeUtil.intervalLessThan(new Date(), date, Constants.MAIL_VALID_MINUTES, TimeUnit.MINUTES);
    }

    @Override
    @Transactional(readOnly = false)
    public void sendResetPasswordEmail(String username) {
        MemberEntity mem = em.createNamedQuery("memberEntity.findMemberByUsername", MemberEntity.class)
                .setParameter("username", username)
                .getResultList()
                .get(0);

        CheckUtils.nullCheck(mem);


        // 向用户表中写入重置密码所需的token和日期
        String token = StringUtils.randomString(Constants.TOKEN_LENGTH);
        mem.setLastTokenDate(new Date());
        mem.setResetPasswordToken(token);

        // send email
        mail.sendMailAsync("token:\n" + token, Constants.MailType.RESET_PASSWORD, mem.getEmail());
    }
}

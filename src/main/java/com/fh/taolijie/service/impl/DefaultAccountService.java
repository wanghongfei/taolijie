package com.fh.taolijie.service.impl;

import cn.fh.security.utils.CredentialUtils;
import com.fh.taolijie.controller.dto.EmployerDto;
import com.fh.taolijie.controller.dto.GeneralMemberDto;
import com.fh.taolijie.controller.dto.RoleDto;
import com.fh.taolijie.controller.dto.StudentDto;
import com.fh.taolijie.domain.EducationExperienceEntity;
import com.fh.taolijie.domain.MemberEntity;
import com.fh.taolijie.domain.RoleEntity;
import com.fh.taolijie.exception.checked.DuplicatedUsernameException;
import com.fh.taolijie.exception.checked.PasswordIncorrectException;
import com.fh.taolijie.exception.checked.UserNotExistsException;
import com.fh.taolijie.service.AccountService;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.Print;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by wanghongfei on 15-3-5.
 */
@Repository
public class DefaultAccountService implements AccountService {
    private Logger logger = LoggerFactory.getLogger(DefaultAccountService.class);

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public boolean registerStudent(StudentDto stuDto) throws DuplicatedUsernameException {
        if (true == isUserExists(stuDto.getUsername())) {
            throw new DuplicatedUsernameException("用户名[" + stuDto.getUsername() + "]已存在");
        }

        // 创建实体
        MemberEntity mem = new MemberEntity(stuDto.getUsername(), CredentialUtils.sha(stuDto.getPassword()), stuDto.getEmail(),
                stuDto.getName(), stuDto.getStudentId(), stuDto.getGender(), Constants.VerifyStatus.NONE.toString(),
                stuDto.getProfilePhotoPath(), stuDto.getPhone(), stuDto.getQq(), stuDto.getAge(), "", "");

        // 保存实体
        em.persist(mem);

        return true;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public boolean registerEmployer(EmployerDto empDto) throws DuplicatedUsernameException {
        if (true == isUserExists(empDto.getUsername())) {
            throw new DuplicatedUsernameException("用户名[" + empDto.getUsername() + "]已存在");
        }

        // create entity
        MemberEntity mem = new MemberEntity(empDto.getUsername(), CredentialUtils.sha(empDto.getPassword()), empDto.getEmail(),
                empDto.getName(), "", empDto.getGender(), Constants.VerifyStatus.NONE.toString(),
                empDto.getProfilePhotoPath(), empDto.getPhone(), empDto.getQq(), empDto.getAge(), empDto.getCompanyName(), "");

        // persist entity
        em.persist(mem);

        return false;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean login(String username, String password) throws UserNotExistsException, PasswordIncorrectException {
        try {
            MemberEntity mem = em.createNamedQuery("memberEntity.findMemberByUsername", MemberEntity.class)
                    .setParameter("username", username)
                    .getSingleResult();

            // check password
            if (false == mem.getPassword().equals(CredentialUtils.sha(password))) {
                throw new PasswordIncorrectException("密码错误");
            }

        } catch (NoResultException ex) {
            throw new UserNotExistsException("用户[" + username + "]不存在");
        }

        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public <T extends GeneralMemberDto> T findMember(String username, Class<T> memberType) {
        MemberEntity mem = em.createNamedQuery("memberEntity.findMemberByUsername", MemberEntity.class)
                .getSingleResult();


        if (isStudentType(memberType)) {
            // 是StudentDto对象
            return (T) makeStudentDto(mem, true);
        } else if (isEmployerType(memberType)) {
            // 是EmployerDto对象
            return (T) makeEmployerDto(mem);
        }

        return null;
    }

    private boolean isStudentType(Class clazz) {
        return clazz.getName().equals(StudentDto.class.getName());
    }
    private boolean isEmployerType(Class clazz) {
        return clazz.getName().equals(EmployerDto.class.getName());
    }

    private EmployerDto makeEmployerDto(MemberEntity mem) {
        EmployerDto dto = new EmployerDto();
        dto.setUsername(mem.getUsername());
        dto.setEmail(mem.getEmail());
        dto.setName(mem.getName());
        dto.setGender(mem.getGender());
        dto.setVerified(mem.getVerified());
        dto.setProfilePhotoPath(mem.getProfilePhotoPath());
        dto.setPhone(mem.getPhone());
        dto.setAge(mem.getAge());
        dto.setQq(mem.getQq());

        dto.setCompanyName(mem.getCompanyName());

        return dto;
    }

    private StudentDto makeStudentDto(MemberEntity mem, boolean isWired) {
        StudentDto dto = new StudentDto();
        dto.setUsername(mem.getUsername());
        dto.setEmail(mem.getEmail());
        dto.setName(mem.getName());
        dto.setGender(mem.getGender());
        dto.setVerified(mem.getVerified());
        dto.setProfilePhotoPath(mem.getProfilePhotoPath());
        dto.setPhone(mem.getPhone());
        dto.setAge(mem.getAge());
        dto.setQq(mem.getQq());

        dto.setStudentId(mem.getStudentId());
        dto.setBlockList(mem.getBlockList());

        // 设置教育经历
        if (isWired) {
            Collection<EducationExperienceEntity> eduCollection = mem.getEducationExperienceCollection();
            List<Integer> schoolIdList = new ArrayList<>();
            List<Integer> academyIdList = new ArrayList<>();
            for (EducationExperienceEntity ee : eduCollection) {
                Integer schoolId = ee.getSchool().getId();
                Integer academyId = ee.getAcademy().getId();

                schoolIdList.add(schoolId);
                academyIdList.add(academyId);
            }
            dto.setSchoolIdList(schoolIdList);
            dto.setAcademyIdList(academyIdList);
        }

        return dto;
    }

    /**
     * 更新实体field，username除外
     * @param mem
     * @param dto
     */
    private void updateMemberEntity(MemberEntity mem, StudentDto dto) {
        mem.setPassword(CredentialUtils.sha(dto.getPassword()));
        mem.setEmail(dto.getEmail());
        mem.setName(dto.getName());
        mem.setStudentId(dto.getStudentId());
        mem.setGender(dto.getGender());
        mem.setVerified(dto.getVerified());
        mem.setProfilePhotoPath(dto.getProfilePhotoPath());
        mem.setPhone(dto.getPhone());
        mem.setQq(dto.getQq());
        mem.setAge(dto.getAge());
        mem.setBlockList(dto.getBlockList());
    }
    /**
     * 更新实体field，username除外
     * @param mem
     * @param dto
     */
    private void updateMemberEntity(MemberEntity mem, EmployerDto dto) {
        mem.setPassword(CredentialUtils.sha(dto.getPassword()));
        mem.setEmail(dto.getEmail());
        mem.setName(dto.getName());
        //mem.setStudentId(dto.getStudentId());
        mem.setGender(dto.getGender());
        mem.setVerified(dto.getVerified());
        mem.setProfilePhotoPath(dto.getProfilePhotoPath());
        mem.setPhone(dto.getPhone());
        mem.setQq(dto.getQq());
        mem.setAge(dto.getAge());
        mem.setCompanyName(dto.getCompanyName());
        //mem.setBlockList(dto.getBlockList());

    }

    @Override
    @Transactional(readOnly = true)
    public List<GeneralMemberDto> getMemberList(int firstResult, int capacity) {
        int cap = capacity;
        if (0 == capacity) {
            cap = Constants.PAGE_CAPACITY;
        }

        List<MemberEntity> memList = em.createNamedQuery("memberEntity.findAll", MemberEntity.class)
                .setFirstResult(firstResult)
                .setMaxResults(cap * firstResult)
                .getResultList();

        List<GeneralMemberDto> dtoList = new ArrayList<>();
        for (MemberEntity m : memList) {
            dtoList.add(makeEmployerDto(m));
        }

        return dtoList;
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
            updateMemberEntity(mem, dto);
        } else if (memDto instanceof EmployerDto) {
            EmployerDto dto = (EmployerDto) memDto;
            updateMemberEntity(mem, dto);
        }

        em.merge(mem);

        return true;
    }

    @Transactional(readOnly = true)
    private MemberEntity getMemberByUsername(String username) {
        return em.createNamedQuery("memberEntity.findMemberByUsername", MemberEntity.class)
                .setParameter("username", username)
                .getSingleResult();
    }

    @Override
    public boolean deleteMember(Integer memberId) throws UserNotExistsException {
        return false;
    }

    @Override
    public boolean deleteMember(String username) throws UserNotExistsException {
        return false;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public boolean addRole(RoleDto roleDto) {
        RoleEntity role = new RoleEntity(roleDto.getRolename(), roleDto.getMemo());
        em.persist(role);

        return true;
    }

    @Override
    public boolean deleteRole(Integer roleId) {
        RoleEntity role = em.getReference(RoleEntity.class, roleId);
        em.remove(role);

        return true;
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
     * For test purpose only
     * @param args
     */
    public static void main(String[] args) {
        String hashed = CredentialUtils.sha("111111");
        Print.print(hashed);
    }
}

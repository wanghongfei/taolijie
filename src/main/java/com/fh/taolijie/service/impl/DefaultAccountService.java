package com.fh.taolijie.service.impl;

import cn.fh.security.utils.CredentialUtils;
import com.fh.taolijie.controller.dto.EmployerDto;
import com.fh.taolijie.controller.dto.GeneralMemberDto;
import com.fh.taolijie.controller.dto.RoleDto;
import com.fh.taolijie.controller.dto.StudentDto;
import com.fh.taolijie.domain.MemberEntity;
import com.fh.taolijie.exception.checked.DuplicatedUsernameException;
import com.fh.taolijie.exception.checked.PasswordIncorrectException;
import com.fh.taolijie.exception.checked.UserNotExistsException;
import com.fh.taolijie.service.AccountService;
import com.fh.taolijie.utils.Constants;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by wanghongfei on 15-3-5.
 */
public class DefaultAccountService implements AccountService {
    @PersistenceContext
    private EntityManager em;

    @Override
    public boolean registerStudent(StudentDto stuDto) throws DuplicatedUsernameException {
        if (true == isUserExists(stuDto.getUsername())) {
            throw new DuplicatedUsernameException("用户名[" + stuDto.getUsername() + "]已存在");
        }

        // 创建实体
        MemberEntity mem = new MemberEntity(stuDto.getUsername(), CredentialUtils.sha(stuDto.password), stuDto.getEmail(),
                stuDto.getName(), stuDto.getStudentId(), stuDto.getGender(), Constants.VerifyStatus.NONE.toString(),
                stuDto.profilePhotoPath, stuDto.getPhone(), stuDto.getQq(), stuDto.getAge(), "", "");

        // 保存实体
        em.persist(mem);

        return true;
    }

    @Override
    public boolean registerEmployer(EmployerDto empDto) throws DuplicatedUsernameException {
        return false;
    }

    @Override
    public boolean login(String username, String password) throws UserNotExistsException, PasswordIncorrectException {
        return false;
    }

    @Override
    public <T extends GeneralMemberDto> T findMember(String username, Class<T> memberType) {
        return null;
    }

    @Override
    public List<GeneralMemberDto> getMemberList(int firstResult, int capacity) {
        return null;
    }

    @Override
    public Long getMemberAmount() {
        return null;
    }

    @Override
    public <T extends GeneralMemberDto> boolean updateMember(T memDto) {
        return false;
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
    public boolean addRole(RoleDto roleDto) {
        return false;
    }

    @Override
    public boolean deleteRole(Integer roleId) {
        return false;
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
}

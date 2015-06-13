package com.fh.taolijie.service.impl;

import cn.fh.security.utils.CredentialUtils;
import com.fh.taolijie.dao.mapper.MemberModelMapper;
import com.fh.taolijie.dao.mapper.MemberRoleModelMapper;
import com.fh.taolijie.dao.mapper.RoleModelMapper;
import com.fh.taolijie.domain.*;
import com.fh.taolijie.exception.checked.DuplicatedUsernameException;
import com.fh.taolijie.exception.checked.PasswordIncorrectException;
import com.fh.taolijie.exception.checked.UserInvalidException;
import com.fh.taolijie.exception.checked.UserNotExistsException;
import com.fh.taolijie.service.AccountService;
import com.fh.taolijie.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by wanghongfei on 15-6-5.
 */
@Service
public class DefaultAccountService implements AccountService {
    @Autowired
    MemberModelMapper memMapper;

    @Autowired
    MemberRoleModelMapper mrMapper;

    @Autowired
    RoleModelMapper roleMapper;

/*    @Autowired
    Mail mail;*/

    @Override
    @Transactional(readOnly = false)
    public Integer register(MemberModel model) throws DuplicatedUsernameException {
        // 检查用户是否存在
        if (true == isUserExisted(model.getUsername())) {
            throw new DuplicatedUsernameException(Constants.ErrorType.USERNAME_EXISTS);
        }

        memMapper.insert(model);

        // 授权
        List<RoleModel> roleList = model.getRoleList();
        MemberRoleModel mr = null;
        for (RoleModel role : roleList) {
            mr = new MemberRoleModel();
            mr.setMemberId(model.getId());
            mr.setRid(role.getRid());
            mrMapper.insert(mr);
        }

        return model.getId();
    }

    private boolean isUserExisted(String username) {
        return memMapper.checkUserExist(username);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean login(String username, String password) throws UserNotExistsException, PasswordIncorrectException, UserInvalidException {
        MemberModel mem = memMapper.selectByUsername(username);
        if (null == mem) {
            throw new UserNotExistsException(Constants.ErrorType.USERNAME_NOT_EXISTS);
        }

        // check validity
        if (null != mem.getValid() && false == mem.getValid()) {
            throw new UserInvalidException(Constants.ErrorType.USER_INVALID_ERROR);
        }

        // check password
        if (false == mem.getPassword().equals(CredentialUtils.sha(password))) {
            throw new PasswordIncorrectException(Constants.ErrorType.PASSWORD_ERROR);
        }

        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public Integer login(String identifier) {
        MemberModel mem = memMapper.selectByIdentifier(identifier);
        if (null == mem) {
            return null;
        }

        return mem.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public MemberModel findMember(String username, boolean isWired) {
        MemberModel mem = memMapper.selectByUsername(username);
        CheckUtils.nullCheck(mem);

        return mem;
    }

    @Override
    @Transactional(readOnly = true)
    public List<MemberModel> getMemberList(int firstResult, int capacity, ObjWrapper wrap) {
        Pagination page = new Pagination(firstResult, CollectionUtils.determineCapacity(capacity));
        return memMapper.getMemberList(page.getMap());
    }

    @Override
    @Transactional(readOnly = true)
    public MemberModel findMember(Integer memId) {
        return memMapper.selectByPrimaryKey(memId);
    }

    @Override
    @Transactional(readOnly = true)
    public Long getMemberAmount() {
        return memMapper.getMemberAmount();
    }

    @Override
    @Transactional(readOnly = false)
    public void updateMember(MemberModel model) {
        memMapper.updateByPrimaryKeySelective(model);
    }

    @Override
    @Transactional(readOnly = false)
    public boolean deleteMember(Integer memberId) {
        MemberModel mem = memMapper.selectByPrimaryKey(memberId);
        mem.setValid(false);
        memMapper.updateByPrimaryKeySelective(mem);

        return true;
    }

    @Override
    @Transactional(readOnly = false)
    public boolean deleteMember(String username) {
        MemberModel mem = memMapper.selectByUsername(username);
        mem.setValid(false);
        memMapper.updateByPrimaryKeySelective(mem);

        return true;
    }

    @Override
    @Transactional(readOnly = false)
    public boolean addRole(RoleModel model) {
        roleMapper.insert(model);

        return true;
    }

    @Override
    @Transactional(readOnly = false)
    public void invalidAccount(Integer memId) {
        memMapper.validMemberById(memId, false);
    }

    @Override
    @Transactional(readOnly = false)
    public void validateAccount(Integer memId) {
        memMapper.validMemberById(memId, true);
    }

    @Override
    @Transactional(readOnly = false)
    public void saveLoginIdentifier(Integer memId, String identifier) {
        MemberModel mem = new MemberModel();
        mem.setId(memId);
        mem.setAutoLoginIdentifier(identifier);

        memMapper.updateByPrimaryKeySelective(mem);
    }

    @Override
    @Transactional(readOnly = false)
    public boolean deleteRole(Integer roleId) {
        roleMapper.deleteByPrimaryKey(roleId);

        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public RoleModel findRole(Integer roleId) {
        RoleModel r = roleMapper.selectByPrimaryKey(roleId);
        CheckUtils.nullCheck(r);

        return r;
    }

    @Override
    @Transactional(readOnly = true)
    public RoleModel findRoleByName(String roleName) {
        return roleMapper.getByName(roleName);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleModel> getAllRole() {
        return roleMapper.getAllRole();
    }

    @Override
    @Transactional(readOnly = false)
    public void assignRole(Integer roleId, String username) {
        MemberModel mem = memMapper.selectByUsername(username);
        MemberRoleModel mr = new MemberRoleModel();
        mr.setMemberId(mem.getId());
        mr.setRid(roleId);

        mrMapper.insert(mr);
    }

    @Override
    @Transactional(readOnly = false)
    public void deassignRole(Integer roleId, String username) {
        MemberModel mem = memMapper.selectByUsername(username);
        MemberRoleModel mr = new MemberRoleModel();
        mr.setMemberId(mem.getId());
        mr.setRid(roleId);

        mrMapper.deleteRelation(mr);
    }

    @Override
    @Transactional(readOnly = false)
    public boolean resetPassword(String username, String token, String newPassword) {
        MemberModel mem = memMapper.selectByUsername(username);
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
        memMapper.updateByPrimaryKeySelective(mem);

        return true;
    }

    private boolean checkDate(Date date) {
        return TimeUtil.intervalLessThan(new Date(), date, Constants.MAIL_VALID_MINUTES, TimeUnit.MINUTES);
    }

    @Override
    @Transactional(readOnly = false)
    public void sendResetPasswordEmail(String username) {
        MemberModel mem = memMapper.selectByUsername(username);
        CheckUtils.nullCheck(mem);


        // 向用户表中写入重置密码所需的token和日期
        String token = StringUtils.randomString(Constants.TOKEN_LENGTH);
        mem.setLastTokenDate(new Date());
        mem.setResetPasswordToken(token);
        memMapper.updateByPrimaryKeySelective(mem);

        // send email
        //mail.sendMailAsync("token:\n" + token, Constants.MailType.RESET_PASSWORD, mem.getEmail());
    }
}

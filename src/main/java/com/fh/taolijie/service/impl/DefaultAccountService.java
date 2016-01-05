package com.fh.taolijie.service.impl;

import cn.fh.security.credential.AuthLogic;
import cn.fh.security.utils.CredentialUtils;
import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.constant.RedisKey;
import com.fh.taolijie.constant.RegType;
import com.fh.taolijie.constant.certi.CertiStatus;
import com.fh.taolijie.dao.mapper.MemberModelMapper;
import com.fh.taolijie.dao.mapper.MemberRoleModelMapper;
import com.fh.taolijie.dao.mapper.RoleModelMapper;
import com.fh.taolijie.domain.SeQuestionModel;
import com.fh.taolijie.domain.acc.MemberModel;
import com.fh.taolijie.domain.acc.MemberRoleModel;
import com.fh.taolijie.domain.Pagination;
import com.fh.taolijie.domain.acc.RoleModel;
import com.fh.taolijie.dto.CertiInfoDto;
import com.fh.taolijie.exception.checked.*;
import com.fh.taolijie.exception.checked.acc.*;
import com.fh.taolijie.exception.checked.code.SMSCodeMismatchException;
import com.fh.taolijie.service.AccountService;
import com.fh.taolijie.service.acc.SeQuestionService;
import com.fh.taolijie.service.acc.impl.CodeService;
import com.fh.taolijie.service.certi.EmpCertiService;
import com.fh.taolijie.service.certi.IdCertiService;
import com.fh.taolijie.service.certi.StuCertiService;
import com.fh.taolijie.utils.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by wanghongfei on 15-6-5.
 */
@Service
public class DefaultAccountService implements AccountService, AuthLogic {
    public static Logger logger = LoggerFactory.getLogger(DefaultAccountService.class);

    @Autowired
    MemberModelMapper memMapper;

    @Autowired
    MemberRoleModelMapper mrMapper;

    @Autowired
    RoleModelMapper roleMapper;

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private CodeService codeService;

    @Autowired
    private SeQuestionService seService;

    @Autowired
    private EmpCertiService empService;

    @Autowired
    private IdCertiService idService;

    @Autowired
    private StuCertiService stuService;

    @Autowired(required = false)
    Mail mail;

    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public Integer register(MemberModel model) throws DuplicatedUsernameException {
        // 检查用户是否存在
        if (true == isUserExisted(model.getUsername())) {
            throw new DuplicatedUsernameException();
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
            throw new UserNotExistsException();
        }

        // check validity
        if (null != mem.getValid() && false == mem.getValid()) {
            throw new UserInvalidException();
        }

        // check password
        if (false == mem.getPassword().equals(CredentialUtils.sha(password))) {
            throw new PasswordIncorrectException();
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
    //@CachePut(value = "memberCache", key = "#username")
    public MemberModel findMember(String username, boolean isWired) {
        MemberModel mem = memMapper.selectByUsername(username);

        return mem;
    }

    @Override
    @Transactional(readOnly = true)
    public ListResult<MemberModel> getMemberList(int firstResult, int capacity) {
        Pagination page = new Pagination(firstResult, CollectionUtils.determineCapacity(capacity));

        List<MemberModel> list = memMapper.getMemberList(page.getMap());
        long tot = memMapper.countGetMemberList();

        return new ListResult<>(list, tot);
    }

    @Override
    @Transactional(readOnly = true)
    public MemberModel findMember(Integer memId) {
        return memMapper.selectByPrimaryKey(memId);
    }

    @Override
    @Transactional(readOnly = true)
    public ListResult<MemberModel> searchMember(String name, int pn, int ps) {
        List<MemberModel> list = memMapper.searchMember(name, pn, ps);
        long tot = memMapper.countSearchMember(name);

        return new ListResult<>(list, tot);
    }

    @Override
    @Transactional(readOnly = true)
    public Long getMemberAmount() {
        return memMapper.getMemberAmount();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean checkUsernameExist(String username) {
        return memMapper.checkUserExist(username);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public int updateMember(MemberModel model) {
        return memMapper.updateByPrimaryKeySelective(model);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public void updateAppToken(Integer memId, String token) {
        memMapper.updateAppToken(memId, token);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public void changePwd(Integer memId, String oldPwd, String newPwd) throws GeneralCheckedException {
        // 验证老密码
        MemberModel mem = memMapper.selectByPrimaryKey(memId);
        if (false == oldPwd.equals(mem.getPassword())) {
            throw new PasswordIncorrectException("");
        }

        // 更新密码
        MemberModel cmd = new MemberModel();
        cmd.setId(memId);
        cmd.setPassword(newPwd);
        memMapper.updateByPrimaryKeySelective(cmd);
    }

    @Override
    public MemberModel selectByAppToken(String token) {
        return memMapper.selectByAppToken(token);
    }

    @Override
    public MemberModel selectByWechatToken(String wechat) {
        return memMapper.selectByWechatToken(wechat);
    }

    @Override
    @Transactional(readOnly = true)
    public CertiInfoDto selectCertiStatus(Integer memId) {
        CertiInfoDto dto = new CertiInfoDto();

        String idCerti = memMapper.selectIdVerified(memId);
        dto.setIdCerti(idCerti);
        // 如果是已经认证
        // 则查出认证信息, 下同
        if (CertiStatus.DONE.code().endsWith(idCerti)) {
            dto.setIdInfo(idService.findLastSuccess(memId));
        }

        String empCerti = memMapper.selectEmpVerified(memId);
        dto.setEmpCerti(empCerti);
        if (CertiStatus.DONE.code().equals(empCerti)) {
            dto.setEmpInfo(empService.findLastSuccessCerti(memId));
        }

        String stuCerti = memMapper.selectStuVerified(memId);
        dto.setStuCerti(stuCerti);
        if (CertiStatus.DONE.code().equals(stuCerti)) {
            dto.setStuInfo(stuService.findDoneByMember(memId));
        }

        return dto;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public boolean deleteMember(Integer memberId) {
        MemberModel mem = memMapper.selectByPrimaryKey(memberId);
        mem.setValid(false);
        memMapper.updateByPrimaryKeySelective(mem);

        return true;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public boolean deleteMember(String username) {
        MemberModel mem = memMapper.selectByUsername(username);
        mem.setValid(false);
        memMapper.updateByPrimaryKeySelective(mem);

        return true;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public boolean addRole(RoleModel model) {
        roleMapper.insert(model);

        return true;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public void invalidAccount(Integer memId) {
        memMapper.validMemberById(memId, false);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public void validateAccount(Integer memId) {
        memMapper.validMemberById(memId, true);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public void saveLoginIdentifier(Integer memId, String identifier) {
        MemberModel mem = new MemberModel();
        mem.setId(memId);
        mem.setAutoLoginIdentifier(identifier);

        memMapper.updateByPrimaryKeySelective(mem);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
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
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public void assignRole(Integer roleId, String username) {
        MemberModel mem = memMapper.selectByUsername(username);
        MemberRoleModel mr = new MemberRoleModel();
        mr.setMemberId(mem.getId());
        mr.setRid(roleId);

        mrMapper.insert(mr);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public void deassignRole(Integer roleId, String username) {
        MemberModel mem = memMapper.selectByUsername(username);
        MemberRoleModel mr = new MemberRoleModel();
        mr.setMemberId(mem.getId());
        mr.setRid(roleId);

        mrMapper.deleteRelation(mr);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
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
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public void sendResetPasswordEmail(String username) {
        MemberModel mem = memMapper.selectByUsername(username);
        CheckUtils.nullCheck(mem);


        // 向用户表中写入重置密码所需的token和日期
        String token = StringUtils.randomString(Constants.TOKEN_LENGTH);
        mem.setLastTokenDate(new Date());
        mem.setResetPasswordToken(token);
        memMapper.updateByPrimaryKeySelective(mem);

        // send email
        mail.sendMailAsync("token:\n" + token, Constants.MailType.RESET_PASSWORD, mem.getEmail());
    }

    @Override
    public String createRedisSession(MemberModel mem) {
        String sid = genSid();
        String key = RedisKey.SESSION.toString() + sid;

        Jedis jedis = JedisUtils.getClient(jedisPool);
        Pipeline pip = jedis.pipelined();
        pip.hset(key, "id", mem.getId().toString());
        pip.hset(key, "username", mem.getUsername());
        pip.hset(key, "role", mem.getRoleList().get(0).getRolename());
        pip.expire(key, (int)TimeUnit.DAYS.toSeconds(30));
        pip.sync();

        JedisUtils.returnJedis(jedisPool, jedis);

        return sid;
    }

    @Override
    public String genRedisKey4Session(String sid) {
        return StringUtils.concat(37, RedisKey.SESSION.toString(), sid);
    }

    @Override
    public String genSid() {
        String random = RandomStringUtils.randomAlphabetic(22);

        return StringUtils.concat(
                30,
                TimeUtil.date2String(new Date(), Constants.DATE_FORMAT_FOR_SESSION),
                random
        );
    }

    @Override
    public void deleteRedisSession(String sid) {
        String key = RedisKey.SESSION.toString() + sid;

        Jedis jedis = JedisUtils.getClient(jedisPool);
        jedis.del(key);
        JedisUtils.returnJedis(jedisPool, jedis);

    }

    @Override
    public Map<String, Object> loginByToken(String s) {
        MemberModel mem = memMapper.selectByIdentifier(s);
        if (null == mem) {
            return null;
        }

        Map<String, Object> map = new HashMap<>();
        map.put(AuthLogic.ROLE_NAME, mem.getRoleList().get(0).getRolename());
        map.put(AuthLogic.ROLE_LIST, mem.getRoleList().get(0));
        map.put(AuthLogic.USERNAME, mem.getUsername());
        map.put(AuthLogic.ID, mem.getId());
        map.put(AuthLogic.MODEL, mem);

        return map;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public int changePhoneByCode(Integer memId, String newPhone, String code) throws SMSCodeMismatchException, UsernameExistException, PermissionException {
        // 检查是否设置了密保问题
        // 如果设置了则不允许调用此方法

        SeQuestionModel question = seService.findByMember(memId, false);
        if (null != question) {
            throw new PermissionException();
        }

        // 查询用户信息
        // 判断注册类型
        MemberModel member = memMapper.selectByPrimaryKey(memId);
        RegType regType = RegType.fromCode(member.getRegType());


        if (RegType.MOBILE == regType) {
            // 手机注册

            // 验证验证码
            if ( false == codeService.validateSMSCode(newPhone, code) ) {
                // 验证失败
                throw new SMSCodeMismatchException();
            }

            // 更新手机号
            // 退出登陆
            // 清除appToken
            updatePhone(memId, newPhone, true);
            // 更新用户名
            updateUsername(memId, newPhone);


        } else {
            // 其它注册类型
            // 验证验证码
            if ( false == codeService.validateSMSCode(newPhone, code) ) {
                // 验证失败
                throw new SMSCodeMismatchException();
            }

            // 更新手机号
            // 退出登陆
            // 清除appToken
            updatePhone(memId, newPhone, false);
        }

        return 0;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public int changePhoneByQuestionAndCode(Integer memId, String answer, String newPhone, String code)
            throws SecretQuestionNotExistException, SecretQuestionWrongException, SMSCodeMismatchException, UsernameExistException {

        // 验证问题
        boolean result = seService.checkAnswer(memId, answer);
        if (!result) {
            throw new SecretQuestionWrongException();
        }

        // 验证验证码
        if ( false == codeService.validateSMSCode(newPhone, code) ) {
            // 验证失败
            throw new SMSCodeMismatchException();
        }

        // 判断注册类型
        MemberModel member = memMapper.selectByPrimaryKey(memId);
        RegType regType = RegType.fromCode(member.getRegType());

        if (RegType.MOBILE == regType) {
            // 手机注册

            // 更新手机号
            // 退出登陆
            // 清除appToken
            updatePhone(memId, newPhone, true);
            // 更新用户名
            updateUsername(memId, newPhone);

        } else {
            // 其它注册类型
            updatePhone(memId, newPhone, false);
        }

        return 0;
    }

    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    private int updatePhone(Integer memId, String phone, boolean cleanToken) {
        MemberModel mem = new MemberModel();
        mem.setId(memId);
        mem.setPhone(phone);
        if (cleanToken) {
            mem.setAppToken("");
        }

        return memMapper.updateByPrimaryKeySelective(mem);
    }

    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    private int updateUsername(Integer memId, String username) throws UsernameExistException {
        if (false == StringUtils.checkNotEmpty(username)) {
            throw new IllegalArgumentException("username cannot be null");
        }

        // 先检查用户名是否重复
        boolean exist = checkUsernameExist(username);
        if (exist) {
            throw new UsernameExistException();
        }

        // 更新用户名
        MemberModel mem = new MemberModel();
        mem.setId(memId);
        mem.setUsername(username);

        return memMapper.updateByPrimaryKeySelective(mem);
    }
}

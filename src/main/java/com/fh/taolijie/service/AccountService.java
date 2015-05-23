package com.fh.taolijie.service;

import com.fh.taolijie.controller.dto.EmployerDto;
import com.fh.taolijie.controller.dto.GeneralMemberDto;
import com.fh.taolijie.controller.dto.RoleDto;
import com.fh.taolijie.controller.dto.StudentDto;
import com.fh.taolijie.exception.checked.DuplicatedUsernameException;
import com.fh.taolijie.exception.checked.PasswordIncorrectException;
import com.fh.taolijie.exception.checked.UserInvalidException;
import com.fh.taolijie.exception.checked.UserNotExistsException;
import com.fh.taolijie.utils.ObjWrapper;

import java.util.List;

/**
 * 本接口规定了与用户帐户操作有关的业务逻辑。
 * Created by wanghongfei on 15-3-4.
 */
public interface AccountService {
    /**
     * 注册一个普通用户
     * @param dto
     * @throws DuplicatedUsernameException
     * @return 如果注册成功，返回用户主键
     */
    public Integer register(GeneralMemberDto dto) throws DuplicatedUsernameException;

    /**
     * @deprecated
     * 注册一个学生用户.
     *
     * @param stuDto 封装了学生信息的{@link StudentDto}对象
     * @return 注册成功返回true, 失败返回false
     * @throws UserNotExistsException 用户名重复
     */
    public boolean registerStudent(StudentDto stuDto) throws DuplicatedUsernameException;

    /**
     * @deprecated
     * 注册一个商家用户.
     *
     * @param empDto 封装了商家信息的dto对象
     * @return 注册成功返回true, 失败返回false
     * @throws DuplicatedUsernameException 用户名重复
     */
    public boolean registerEmployer(EmployerDto empDto) throws DuplicatedUsernameException;

    /**
     * 执行用户登陆操作
     * @param username 用户名
     * @param password 密码<b>明文</b>
     * @return 登陆验证成功返回true, 失败返回false.
     * @throws UserNotExistsException 用户名不存在
     * @throws PasswordIncorrectException 密码错误
     */
    public boolean login(String username, String password) throws UserNotExistsException, PasswordIncorrectException, UserInvalidException;

    /**
     * 根据Cookie信息中的identifier登陆.
     * 登陆失败返回null, 成功返回member实体的id值
     * @param identifier
     * @return
     */
    Integer login(String identifier);

    /**
     * 查询用户所有基本信息.
     * @param username 要查询的用户的用户名
     * @param type new StudentDto[0]
     * @param isWired 指定是否查询关联表内的信息
     * @param <T> 用户实体有3类，该方法会根据泛型参数的实际类型返回对应的对象。一定是{@link com.fh.taolijie.controller.dto.GeneralMemberDto}, {@link com.fh.taolijie.controller.dto.StudentDto}
     *             或{@link EmployerDto}中的一种。
     * @return 返回类型由泛型参数{@code T}决定. 一定是{@link com.fh.taolijie.controller.dto.GeneralMemberDto}, {@link com.fh.taolijie.controller.dto.StudentDto}
     *          或{@link EmployerDto}中的一种。
     */
    public <T extends GeneralMemberDto> T findMember(String username, T[] type, boolean isWired);

    /**
     * 得到所有用户信息. 不包含关联表内的信息
     * @param firstResult
     * @param capacity
     * @return
     */
    public List<GeneralMemberDto> getMemberList(int firstResult, int capacity, ObjWrapper wrap);

    GeneralMemberDto findMember(Integer memId);

    /**
     * 得到当前已注册用户的数量
     * @return
     */
    public Long getMemberAmount();

    //List<JobPostDto> getAppliedJobList(Integer memberId, );
    /**
     * 更新用户信息.
     * @param memDto 表示用户的dto对象.
     * @param <T>
     * @return 更新成功返回true, 失败返回false
     */
    public <T extends GeneralMemberDto> boolean updateMember(T memDto);

    //public boolean addEducation(Integer academyId, String username);

    //public boolean deleteEducation(Integer academyId, String username);

    /**
     * @deprecated 方法未实现
     * 删除一个用户
     * @param memberId {@link com.fh.taolijie.domain.MemberEntity}实体的主键值
     * @return 删除成功返回true, 失败返回false
     * @throws UserNotExistsException 该用户不存在
     */
    public boolean deleteMember(Integer memberId);

    /**
     * 封号
     * @param username 要删除用户的用户名
     * @return 删除成功返回true, 失败返回false
     */
    public boolean deleteMember(String username);

    /**
     * 向数据库role表中添加一个新角色
     * @param roleDto 封装了role信息的dto对象
     * @return 删除成功返回true, 失败返回false
     */
    public boolean addRole(RoleDto roleDto);

    /**
     * 封号
     * @param memId
     */
    void invalidAccount(Integer memId);

    /**
     * 解封
     * @param memId
     */
    void validateAccount(Integer memId);

    /**
     * 保存随机生成的字符串，用于自动登陆
     * @param memId
     * @param identifier
     */
    void saveLoginIdentifier(Integer memId, String identifier);

    /**
     * 从数据库role表中删除一个已存在的role
     * @param roleId {@link com.fh.taolijie.domain.RoleEntity}实体的主键值
     * @return 删除成功返回true, 失败返回false
     */
    public boolean deleteRole(Integer roleId);

    /**
     * 根据id查找Role实体
     * @param roleId
     * @return
     */
    public RoleDto findRole(Integer roleId);

    /**
     * 查询所有role
     * @return
     */
    List<RoleDto> getAllRole();

    /**
     * 为指定用户添加一个新role
     * @param roleId
     * @param username
     */
    public void assignRole(Integer roleId, String username);

    /**
     * 去年用户的一个已有role
     * @param roleId
     * @param username
     */
    public void deassignRole(Integer roleId, String username);


    /**
     * 找回密码并重置密码.
     * <p> 找回密码调用说明:
     * 1. 用户请求找回密码后，服务器向用户邮箱发送一封带有15位token的邮件, 通过调用{@link #sendResetPasswordEmail(String)}实现;
     * 2. 用户得到token后，点击邮件中的url, 服务端从请求中获取token调用{@link #resetPassword(String, String, String)}方法，
     * 然后与用户表中的token, last_token_time对比，只有当token相同且时间间隔在10分钟以内，才能成功修改密码。
     *
     *
     * @param username
     * @param token 发给用户邮件中的token值
     * @param newPassword
     * @return 修改成功返回true, 否则返回false
     */
    boolean resetPassword(String username, String token, String newPassword);

    /**
     * 向用户发送重置密码邮件
     * @param username
     */
    void sendResetPasswordEmail(String username);
}

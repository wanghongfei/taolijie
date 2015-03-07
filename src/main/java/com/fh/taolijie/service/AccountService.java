package com.fh.taolijie.service;

import com.fh.taolijie.controller.dto.EmployerDto;
import com.fh.taolijie.controller.dto.GeneralMemberDto;
import com.fh.taolijie.controller.dto.RoleDto;
import com.fh.taolijie.controller.dto.StudentDto;
import com.fh.taolijie.exception.checked.DuplicatedUsernameException;
import com.fh.taolijie.exception.checked.PasswordIncorrectException;
import com.fh.taolijie.exception.checked.UserNotExistsException;

import java.util.List;

/**
 * 本接口规定了与用户帐户操作有关的业务逻辑。
 * Created by wanghongfei on 15-3-4.
 */
public interface AccountService {
    /**
     * 注册一个学生用户.
     *
     * @param stuDto 封装了学生信息的{@link StudentDto}对象
     * @return 注册成功返回true, 失败返回false
     * @throws UserNotExistsException 用户名重复
     */
    public boolean registerStudent(StudentDto stuDto) throws DuplicatedUsernameException;

    /**
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
    public boolean login(String username, String password) throws UserNotExistsException, PasswordIncorrectException;


    /**
     * 查询用户所有基本信息.
     * @param username 要查询的用户的用户名
     * @param memberType 用户实体dto对象的{@code Class}对象. 如, {@link StudentDto}.class
     * @param isWired 指定是否查询关联表内的信息
     * @param <T> 用户实体有3类，该方法会根据泛型参数的实际类型返回对应的对象。一定是{@link com.fh.taolijie.controller.dto.GeneralMemberDto}, {@link com.fh.taolijie.controller.dto.StudentDto}
     *             或{@link EmployerDto}中的一种。
     * @return 返回类型由泛型参数{@code T}决定. 一定是{@link com.fh.taolijie.controller.dto.GeneralMemberDto}, {@link com.fh.taolijie.controller.dto.StudentDto}
     *          或{@link EmployerDto}中的一种。
     */
    public <T extends GeneralMemberDto> T findMember(String username, Class<T> memberType, boolean isWired);

    /**
     * 得到所有用户信息. 不包含关联表内的信息
     * @param firstResult
     * @param capacity
     * @return
     */
    public List<GeneralMemberDto> getMemberList(int firstResult, int capacity);

    /**
     * 得到当前已注册用户的数量
     * @return
     */
    public Long getMemberAmount();

    /**
     * 更新用户信息.
     * @param memDto 表示用户的dto对象.
     * @param <T>
     * @return 更新成功返回true, 失败返回false
     */
    public <T extends GeneralMemberDto> boolean updateMember(T memDto);

    /**
     * 添加一个教育经历
     * @param academyId 学院实体的id
     * @param username
     * @return
     */
    public boolean addEducation(Integer academyId, String username);

    /**
     * 删除一个教育经历
     * @param academyId 学院实体的id
     * @param username
     * @return
     */
    public boolean deleteEducation(Integer academyId, String username);

    /**
     * 删除一个用户
     * @param memberId {@link com.fh.taolijie.domain.MemberEntity}实体的主键值
     * @return 删除成功返回true, 失败返回false
     * @throws UserNotExistsException 该用户不存在
     */
    public boolean deleteMember(Integer memberId) throws UserNotExistsException;

    /**
     * 删除一个用户
     * @param username 要删除用户的用户名
     * @return 删除成功返回true, 失败返回false
     * @throws UserNotExistsException 该用户不存在
     */
    public boolean deleteMember(String username) throws UserNotExistsException;

    /**
     * 向数据库role表中添加一个新角色
     * @param roleDto 封装了role信息的dto对象
     * @return 删除成功返回true, 失败返回false
     */
    public boolean addRole(RoleDto roleDto);

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
}

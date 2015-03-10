package com.fh.taolijie.test.service;

import com.fh.taolijie.controller.dto.GeneralMemberDto;
import com.fh.taolijie.controller.dto.RoleDto;
import com.fh.taolijie.controller.dto.StudentDto;
import com.fh.taolijie.domain.*;
import com.fh.taolijie.exception.checked.DuplicatedUsernameException;
import com.fh.taolijie.exception.checked.PasswordIncorrectException;
import com.fh.taolijie.exception.checked.UserNotExistsException;
import com.fh.taolijie.service.AccountService;
import com.fh.taolijie.service.impl.DefaultAccountService;
import com.fh.taolijie.test.BaseDatabaseTestClass;
import com.fh.taolijie.utils.Print;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by wanghongfei on 15-3-5.
 */
@ContextConfiguration(classes = {DefaultAccountService.class})
public class AccountServiceTest extends BaseDatabaseTestClass {
    private MemberEntity member;
    private RoleEntity role;
    private AcademyEntity academy;

    @Autowired
    private AccountService accService;

    @PersistenceContext
    private EntityManager em;

    @Before
    @Transactional(readOnly = false)
    public void initData() {
        Print.print("准备数据");

        // 创建用户
        // password is 111111
        member = new MemberEntity("Bruce", "3d4f2bf07dc1be38b20cd6e46949a1071f9d0e3d", "", "Neo", "", "", "", "", "", "", 20, "", "");
        em.persist(member);

        // 创建role
        role = new RoleEntity("ADMIN", "");
        em.persist(role);

        // 创建关联关系
        MemberRoleEntity memRole = new MemberRoleEntity();
        memRole.setRole(role);
        memRole.setMember(member);
        em.persist(memRole);

        List<MemberRoleEntity> memRoleList = new ArrayList<>();
        memRoleList.add(memRole);
        member.setMemberRoleCollection(memRoleList);

        // 创建教育信息
        SchoolEntity school = new SchoolEntity();
        school.setShortName("school");
        school.setFullName("school");
        academy = new AcademyEntity();
        academy.setFullName("academy");
        academy.setShortName("academy");
        // 创建从school到academy的关联
        academy.setSchool(school);
        school.setAcademyCollection(new ArrayList<>());
        school.getAcademyCollection().add(academy);
        em.persist(academy);
        em.persist(school);
    }

    @Test
    @Transactional(readOnly = false)
    public void testRegisterStudent() {
        StudentDto stuDto = new StudentDto();
        stuDto.setUsername("Hello");
        stuDto.setPassword("222222");
        stuDto.setRoleIdList(Arrays.asList(this.role.getRid()));

        try {
            accService.registerStudent(stuDto);
        } catch (DuplicatedUsernameException e) {
            Print.print(e.getMessage());
            Assert.assertTrue(false);
        }

        // 验证entity是否存在
        MemberEntity mem = em.createQuery("SELECT m FROM MemberEntity m WHERE m.username = :username", MemberEntity.class)
                .setParameter("username", "Hello")
                .getSingleResult();

        // 验证role是否指定
        Collection<MemberRoleEntity> memRoleCollection = mem.getMemberRoleCollection();
        Assert.assertNotNull(memRoleCollection);
        //Assert.assertEquals(1, memRoleCollection.size());
        Assert.assertFalse(memRoleCollection.isEmpty());

        // 验证指定的role是否正确
        Print.print("~~~~~~~~~~~~~~~ id:" + role.getRid() + ", role:" + role.getRolename());
        boolean contains = containsRole(memRoleCollection, this.role);
        Assert.assertTrue(contains);
    }

    private boolean containsRole(Collection<MemberRoleEntity> mrCollection, RoleEntity role) {
        for (MemberRoleEntity mr : mrCollection) {
            if (role.equals(mr.getRole())) {
                return true;
            }
        }

        return false;
    }

    @Test
    @Transactional(readOnly = false)
    public void testRegisterStudentDuplicatesName() {
        StudentDto stuDto = new StudentDto();
        stuDto.setUsername("Bruce");
        stuDto.setPassword("222222");

        try {
            accService.registerStudent(stuDto);
        } catch (DuplicatedUsernameException e) {
            Print.print(e.getMessage());
            return;
        }

        Assert.assertTrue(false);
    }

    @Test
    @Transactional(readOnly = false)
    public void testLogin() {
        boolean res = false;
        try {
            res = accService.login("Bruce", "111111");
        } catch (UserNotExistsException e) {
            Assert.assertTrue(false);
        } catch (PasswordIncorrectException e) {
            Assert.assertTrue(false);
        }
        Assert.assertTrue(res);

        // 用户名错误
        res = false;
        try {
            accService.login("Neo", "111111");
        } catch (UserNotExistsException e) {
            res = true;
        } catch (PasswordIncorrectException e) {
            res = false;
        }
        Assert.assertTrue(res);

        // 密码错误
        res = false;
        try {
            accService.login("Bruce", "222");
        } catch (UserNotExistsException e) {
            res = false;
        } catch (PasswordIncorrectException e) {
            res = true;
        }
        Assert.assertTrue(res);
    }

    @Test
    @Transactional(readOnly = true)
    public void testFindMember() {
        Print.print("-------- testFindMember");
        StudentDto dto = accService.findMember("Bruce", StudentDto.class, true);
        Assert.assertNotNull(dto);

        // 测试是不是Bruce用户
        Assert.assertEquals("Bruce", dto.getUsername());

        // 测试role是否正确
        List<Integer> idList = dto.getRoleIdList();
        Assert.assertNotNull(idList);
        Assert.assertTrue(contains(idList, this.role.getRid()));
    }

    private <T> boolean contains(Collection<T> collection, T target) {
        for (T obj : collection) {
            if (target.equals(obj)) {
                return true;
            }
        }

        return false;
    }

    @Test
    @Transactional(readOnly = true)
    public void testGetMemberList() {
        List<GeneralMemberDto> dtoList = accService.getMemberList(0, 0);
        Assert.assertNotNull(dtoList);
        Assert.assertFalse(dtoList.isEmpty());
        Assert.assertTrue(containsUsername(dtoList, "Bruce"));
    }

    private boolean containsUsername(Collection<GeneralMemberDto> dtoCollection, String username) {
        for (GeneralMemberDto gm : dtoCollection) {
            if (gm.getUsername().equals(username)) {
                return true;
            }
        }

        return false;
    }

    @Test
    @Transactional(readOnly = false)
    public void testUpdateMember() {
        StudentDto dto = new StudentDto();
        dto.setUsername("Bruce");
        dto.setName("wanghongfei"); // change name field
        dto.setAge(22); // change age field
        //dto.set

        accService.updateMember(dto);

        MemberEntity mem = em.createQuery("SELECT m FROM MemberEntity m WHERE m.username = :username", MemberEntity.class)
                .setParameter("username", "Bruce")
                .getSingleResult();
        Assert.assertEquals("wanghongfei", mem.getName());
        Assert.assertEquals(22, mem.getAge().intValue());
    }

    @Test
    @Transactional(readOnly = false)
    public void testAddRole() {
        RoleDto dto = new RoleDto();
        dto.setRolename("USER");
        accService.addRole(dto);

        // 测试是否添加成功
        em.createQuery("SELECT r FROM RoleEntity r WHERE r.rolename = :rolename", RoleEntity.class)
                .setParameter("rolename", "USER")
                .getSingleResult();
    }

    @Test
    @Transactional(readOnly = false)
    public void testDeleteRole() {
        RoleDto dto = new RoleDto();
        dto.setRolename("USER");
        accService.addRole(dto);
        RoleEntity role = em.createQuery("SELECT r FROM RoleEntity r WHERE r.rolename = :rolename", RoleEntity.class)
                .setParameter("rolename", "USER")
                .getSingleResult();

        accService.deleteRole(role.getRid());
        // 测试是否删除
        try {
            role = em.createQuery("SELECT r FROM RoleEntity r WHERE r.rolename = :rolename", RoleEntity.class)
                    .setParameter("rolename", "USER")
                    .getSingleResult();
        } catch (NoResultException ex) {
            return;
        }

        Assert.assertTrue(false);

    }

    @Test
    @Transactional(readOnly = false)
    public void testAssignRole() {
        // 添加USER角色
        RoleDto dto = new RoleDto();
        dto.setRolename("USER");
        accService.addRole(dto);

        RoleEntity role = em.createQuery("SELECT r FROM RoleEntity r WHERE r.rolename = :rolename", RoleEntity.class)
                .setParameter("rolename", "USER")
                .getSingleResult();

        accService.assignRole(role.getRid(), this.member.getUsername());

        // 测试是否添加成功
        StudentDto stuDto = accService.findMember(this.member.getUsername(), StudentDto.class, true);
        List<Integer> idList = stuDto.getRoleIdList();
        boolean contains = contains(idList, role.getRid());
        Assert.assertTrue(contains);
    }

    @Test
    @Transactional(readOnly = false)
    public void testDeassignRole() {
        accService.deassignRole(this.role.getRid(), this.member.getUsername());

        // 测试是否删除了关联
        StudentDto stuDto = accService.findMember(this.member.getUsername(), StudentDto.class, true);
        List<Integer> idList = stuDto.getRoleIdList();
        boolean contains = contains(idList, role.getRid());
        Assert.assertFalse(contains);

        // 测试是否删除了Role实体(不应该删除)
        RoleEntity role = em.createQuery("SELECT r FROM RoleEntity r WHERE r.rolename = :rolename", RoleEntity.class)
                .setParameter("rolename", "ADMIN")
                .getSingleResult();

    }
}

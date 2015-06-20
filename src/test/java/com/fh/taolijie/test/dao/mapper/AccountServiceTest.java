package com.fh.taolijie.test.dao.mapper;

import com.fh.taolijie.dao.mapper.MemberModelMapper;
import com.fh.taolijie.domain.MemberModel;
import com.fh.taolijie.domain.RoleModel;
import com.fh.taolijie.exception.checked.DuplicatedUsernameException;
import com.fh.taolijie.exception.checked.PasswordIncorrectException;
import com.fh.taolijie.exception.checked.UserInvalidException;
import com.fh.taolijie.exception.checked.UserNotExistsException;
import com.fh.taolijie.service.AccountService;
import com.fh.taolijie.service.impl.DefaultAccountService;
import com.fh.taolijie.test.BaseSpringDataTestClass;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by hanxinxin on 15-5-31.
 */
@ContextConfiguration(classes = {
        DefaultAccountService.class

})
public class AccountServiceTest extends BaseSpringDataTestClass {
    @Autowired
    AccountService accountService;

    @Autowired
    MemberModelMapper memMapper;

    @Test
    public void testFind() {
        MemberModel mem = accountService.findMember("hanxinxin", false);
        Assert.assertNotNull(mem);
        mem = accountService.findMember("hanxinxin", false);
        Assert.assertNotNull(mem);

        List<RoleModel> rList = mem.getRoleList();
        Assert.assertNotNull(rList);
        Assert.assertTrue(rList.stream().anyMatch( role -> role.getRolename().equals("ADMIN") ));

        memMapper.checkUserExist("hanxinxin");
    }

    @Test
    public void testAdd() {
        MemberModel mem = new MemberModel();
        mem.setUsername("hanxinxin");
        mem.setPassword("111111");

        RoleModel r1 = new RoleModel();
        r1.setRid(1);
        RoleModel r2 = new RoleModel();
        r1.setRid(2);
        mem.setRoleList(Arrays.asList(r1, r2));

        // 测试用户名重复
        boolean duplicated = false;
        try {
            accountService.register(mem);
        } catch (DuplicatedUsernameException e) {
            duplicated = true;
        }
        Assert.assertTrue(duplicated);

        // 正常测试
        mem.setUsername("whf");
        mem.setPassword("111111");
        List<RoleModel> rList = new ArrayList<>(1);
        RoleModel r = new RoleModel();
        r.setRid(1);
        rList.add(r);
        mem.setRoleList(rList);
        Integer id = null;
        try {
            id = accountService.register(mem);
        } catch (DuplicatedUsernameException e) {
            Assert.assertTrue(false);
        }

        Assert.assertNotNull(id);
        mem = memMapper.selectByPrimaryKey(id);
        Assert.assertNotNull(mem);
    }

    @Test
    public void testLogin() {
        // normal case
        try {
            accountService.login("hanxinxin", "111111");
        } catch (UserNotExistsException e) {
            Assert.assertFalse(true);
        } catch (PasswordIncorrectException e) {
            Assert.assertFalse(true);
        } catch (UserInvalidException e) {
            Assert.assertFalse(true);
        }

        // wrong password
        try {
            accountService.login("hanxinxin", "wrong password");
        } catch (UserNotExistsException e) {
            Assert.assertFalse(true);
        } catch (PasswordIncorrectException e) {
        } catch (UserInvalidException e) {
            Assert.assertFalse(true);
        }

        // invalid username
        try {
            accountService.login("asdfasdf", "wrong password");
        } catch (UserNotExistsException e) {
        } catch (PasswordIncorrectException e) {
            Assert.assertFalse(true);
        } catch (UserInvalidException e) {
            Assert.assertFalse(true);
        }
    }

    @Test
    public void testGetAll() {
        List<MemberModel> mList = accountService.getMemberList(0, Integer.MAX_VALUE, null);
        Assert.assertFalse(mList.isEmpty());
    }

    @Test
    public void testGetAmount() {
        Long tot = accountService.getMemberAmount();
        Assert.assertEquals(2, tot.intValue());
    }

    @Test
    public void testUpdate() {
        MemberModel mem = new MemberModel();
        mem.setId(1); // hanxinxin
        mem.setValid(false);
        accountService.updateMember(mem);

        mem = memMapper.selectByPrimaryKey(1);
        Assert.assertEquals(false, mem.getValid().booleanValue());
    }

    @Test
    public void testInvalid() {
        accountService.invalidAccount(1);
        MemberModel mem = memMapper.selectByPrimaryKey(1);
        Assert.assertEquals(false, mem.getValid().booleanValue());

        accountService.validateAccount(1);
        mem = memMapper.selectByPrimaryKey(1);
        Assert.assertEquals(true, mem.getValid().booleanValue());
    }

    @Test
    public void testAddRole() {
        RoleModel role = new RoleModel();
        role.setRolename("TESTER");

        accountService.addRole(role);
    }

    @Test
    public void testAssignRole() {
        accountService.assignRole(2, "hanxinxin");

        MemberModel mem = memMapper.selectByUsername("hanxinxin");
        Assert.assertTrue(mem.getRoleList().stream().anyMatch(r -> r.getRolename().equals("ADMIN")));
    }

    @Test
    public void testDeassignRole() {
        accountService.deassignRole(1, "hanxinxin");

        MemberModel mem = memMapper.selectByUsername("hanxinxin");
        Assert.assertTrue(mem.getRoleList().stream().noneMatch(r -> r.getRolename().equals("STUDENT")));
    }
}

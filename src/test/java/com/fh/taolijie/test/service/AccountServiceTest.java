package com.fh.taolijie.test.service;

import com.fh.taolijie.controller.dto.StudentDto;
import com.fh.taolijie.domain.MemberEntity;
import com.fh.taolijie.exception.checked.DuplicatedUsernameException;
import com.fh.taolijie.exception.checked.PasswordIncorrectException;
import com.fh.taolijie.exception.checked.UserNotExistsException;
import com.fh.taolijie.service.AccountService;
import com.fh.taolijie.service.impl.DefaultAccountService;
import com.fh.taolijie.test.BaseDatabaseTestClass;
import com.fh.taolijie.utils.Print;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by wanghongfei on 15-3-5.
 */
@ContextConfiguration(classes = {DefaultAccountService.class})
public class AccountServiceTest extends BaseDatabaseTestClass {
    private MemberEntity member;

    @Autowired
    private AccountService accService;

    @PersistenceContext
    private EntityManager em;

    @Before
    public void initData() {
        Print.print("准备数据");

        // password is 111111
        member = new MemberEntity("Bruce", "3d4f2bf07dc1be38b20cd6e46949a1071f9d0e3d", "", "Neo", "", "", "", "", "", "", 20, "", "");
        em.persist(member);
    }

    @Test
    @Transactional(readOnly = false)
    public void testRegisterStudent() {
        StudentDto stuDto = new StudentDto();
        stuDto.setUsername("Hello");
        stuDto.setPassword("222222");

        try {
            accService.registerStudent(stuDto);
        } catch (DuplicatedUsernameException e) {
            Print.print(e.getMessage());
            Assert.assertTrue(false);
        }

        // test
        Long tot = em.createQuery("SELECT COUNT(m.username) FROM MemberEntity m WHERE m.username = :username", Long.class)
                .setParameter("username", "Hello")
                .getSingleResult();
        Assert.assertEquals(1, tot.intValue());
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
}

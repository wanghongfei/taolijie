package com.fh.taolijie.test.service;

import com.fh.taolijie.controller.dto.StudentDto;
import com.fh.taolijie.domain.MemberEntity;
import com.fh.taolijie.exception.checked.DuplicatedUsernameException;
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

        member = new MemberEntity("Bruce", "111111", "", "Neo", "", "", "", "", "", "", 20, "", "");
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
}

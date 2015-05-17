package com.fh.taolijie.test.utils;

import com.fh.taolijie.service.AccountService;
import com.fh.taolijie.service.impl.DefaultAccountService;
import com.fh.taolijie.service.impl.Mail;
import com.fh.taolijie.test.service.repository.SpringDataConfigBean;
import com.fh.taolijie.utils.ControllerHelper;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by wynfrith on 15-5-17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringDataConfigBean.class,
        DefaultAccountService.class,
        Mail.class,
        JavaMailSenderImpl.class})
public class ControllerHelperTest {
    @Autowired
    AccountService accountService;


    /**
     * ControllerHelper.getRoleId(roleName);
     * 通过RoleName得到RoleId方法
     */
    @Test
    //region testGetRoleId()
    public void testGetRoleId(){
        String roleName = "ADMIN";
        int roleId =ControllerHelper.getRoleId(roleName,accountService);
        System.out.println(roleId);
        Assert.assertNotSame(-1,roleId);

        roleName = "STUDENT";
        roleId =ControllerHelper.getRoleId(roleName,accountService);
        System.out.println(roleId);
        Assert.assertNotSame(-1,roleId);

        roleName = "HHHHHHHH";
        roleId =ControllerHelper.getRoleId(roleName,accountService);
        System.out.println(roleId);
        Assert.assertSame(-1,roleId);
    }
    //endregion


}

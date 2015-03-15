package com.fh.taolijie.test.controller;


import cn.fh.security.utils.CredentialUtils;
import com.fh.taolijie.controller.HomeController;
import com.fh.taolijie.controller.UserController;
import com.fh.taolijie.controller.dto.MemberRoleDto;
import com.fh.taolijie.domain.MemberEntity;
import com.fh.taolijie.domain.MemberRoleEntity;
import com.fh.taolijie.domain.RoleEntity;
import com.fh.taolijie.service.AccountService;
import com.fh.taolijie.service.impl.*;
import com.fh.taolijie.test.BaseDatabaseTestClass;
import com.fh.taolijie.test.service.repository.BaseSpringDataTestClass;
import com.fh.taolijie.utils.Print;
import com.fh.taolijie.utils.ResponseUtils;
import com.fh.taolijie.utils.json.JsonWrapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Persistent;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import sun.security.krb5.internal.CredentialsUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by wynfrith on 15-3-5.
 */

@WebAppConfiguration
@ContextConfiguration(classes = {UserController.class,
        DefaultAccountService.class,
        DefaultJobPostService.class,
        DefaultSHPostService.class,
        DefaultResumeService.class,
        DefaultReviewService.class
        })
//@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml")
public class UserControllerTest extends BaseSpringDataTestClass{
    /*
    先测试登陆与注册功能
     */
    @PersistenceContext
    private EntityManager em;


    @Autowired
    private UserController userController;

    private MockMvc mockMvc;

    @Before
    public void before(){
        Print.print("准备数据");
       // UserController userController = new UserController();
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        initData();
        Print.print("准备完成");
    }

    private void initData(){
        //创建用户
        MemberEntity mem = new MemberEntity();
        mem.setEmail("yazhou@126.com");
        mem.setUsername("yazhou");
        mem.setPassword(CredentialUtils.sha("yazhou110"));
        em.persist(mem);

        // 创建role
        RoleEntity role = new RoleEntity("ADMIN","超级管理员");
        em.persist(role);

        //创建mem-role映射
        MemberRoleEntity memRole = new MemberRoleEntity();
        memRole.setMember(mem);
        memRole.setRole(role);
        em.persist(memRole);

        List<MemberRoleEntity> memRoleList = new ArrayList<>();
        memRoleList.add(memRole);
        mem.setMemberRoleCollection(memRoleList);

    }

    /**
     * 测试登陆成功
     */
    @Test
    public void testLogin() throws Exception {
        String expected = new JsonWrapper(true,"登陆成功").getAjaxMessage();

        mockMvc.perform(post("/user/login")
                        .contentType("application/json;charset=utf-8")
                        .param("username","yazhou")
                        .param("password","yazhou110")
        ).andExpect(status().isOk())
                .andExpect(content().string(expected));

    }

}

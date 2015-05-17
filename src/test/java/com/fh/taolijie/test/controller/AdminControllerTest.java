package com.fh.taolijie.test.controller;

import cn.fh.security.credential.Credential;
import cn.fh.security.utils.CredentialUtils;
import com.fh.taolijie.controller.AdminController;
import com.fh.taolijie.controller.dto.GeneralMemberDto;
import com.fh.taolijie.controller.dto.RoleDto;
import com.fh.taolijie.service.AccountService;
import com.fh.taolijie.service.JobPostService;
import com.fh.taolijie.service.impl.*;
import com.fh.taolijie.test.service.repository.BaseSpringDataTestClass;
import com.fh.taolijie.utils.Print;
import com.fh.taolijie.utils.TaolijieCredential;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * Created by wynfrith on 15-3-30.
 */


@WebAppConfiguration
@ContextConfiguration(classes = {
        AdminController.class,
        DefaultAccountService.class,
        Mail.class,
        JavaMailSenderImpl.class,
        DefaultReviewService.class,
        DefaultSearchService.class,
        DefaultJobPostCategoryService.class,
        DefaultSHPostService.class,
        DefaultSHPostCategoryService.class,
        DefaultSchoolService.class,
        DefaultNewsService.class,
        DefaultJobPostService.class})
public class AdminControllerTest extends BaseSpringDataTestClass{
    @PersistenceContext
    private EntityManager em;

    @Autowired
    AdminController adminController;
    @Autowired
    AccountService accountService;
    @Autowired
    MockHttpSession session;
    GeneralMemberDto mem;

    private MockMvc mockMvc;


    public void getCredential(int id,String username,MockHttpSession session){
        Credential credential = new TaolijieCredential(id,username);
//        GeneralMemberDto memDto =accountService.findMember(username, new GeneralMemberDto[0], true);
        GeneralMemberDto memDto = accountService.findMember(1);
        for(Integer rid:memDto.getRoleIdList()){
            RoleDto role = accountService.findRole(rid);
            System.out.println(role.getRolename());
            credential.addRole(role.getRolename());
        }
        CredentialUtils.createCredential(session, credential);
    }

    @Before
    public void before(){
        Print.print("准备数据");
        // UserController userController = new UserController();
        if(mockMvc==null){
            this.mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
            ControllerTestUtils.initData(em);
        }

        mem = new GeneralMemberDto();
        mem.setId(1);
        mem.setUsername("wynfrith");

        Print.print("准备完成");
    }
    @After
    public void after(){
        session.invalidate();
    }

    @Test
    public void testDeleteUser() throws Exception {
        getCredential(mem.getId(),mem.getUsername(), session);
        mockMvc.perform(post("/manage/deleteUser/4")
                .contentType("application/json;charset=utf-8")
                .session(session))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }



}

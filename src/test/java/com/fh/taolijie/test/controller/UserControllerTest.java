package com.fh.taolijie.test.controller;


import cn.fh.security.credential.Credential;
import cn.fh.security.utils.CredentialUtils;
import com.fh.taolijie.controller.UserController;
import com.fh.taolijie.controller.dto.GeneralMemberDto;
import com.fh.taolijie.controller.dto.RoleDto;
import com.fh.taolijie.domain.*;
import com.fh.taolijie.service.AccountService;
import com.fh.taolijie.service.impl.*;
import com.fh.taolijie.test.service.repository.BaseSpringDataTestClass;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.Print;
import com.fh.taolijie.utils.TaolijieCredential;
import com.fh.taolijie.utils.json.JsonWrapper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.ArrayList;
import java.util.Date;
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
        DefaultReviewService.class,
        DefaultSearchService.class,
        DefaultNotificationService.class,
        Mail.class,
        JavaMailSenderImpl.class,
        DefaultJobPostCategoryService.class
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
    @Autowired
    private MockHttpSession session;
    @Autowired
    private AccountService accountService;
    private MockMvc mockMvc;

    @Before
    public void before(){
        Print.print("准备数据");
       // UserController userController = new UserController();
        if(mockMvc==null){
            this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
            initData();
        }



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

        //创建JobPostCategory
        JobPostCategoryEntity jobPostCategory = new JobPostCategoryEntity();
        jobPostCategory.setName("发传单");
        em.persist(jobPostCategory);

        //创建jobPost
        JobPostEntity jobPost= new JobPostEntity();
        jobPost.setMember(mem);
        jobPost.setCategory(jobPostCategory);
        jobPost.setTitle("静安驾校传单");
        jobPost.setContact("王富诚");
        jobPost.setContactEmail("wangfucheng56@gmail.com");
        jobPost.setContactPhone("18369905318");
        jobPost.setContactQq("1252264267");
        jobPost.setExpiredTime(new Date(Date.parse("Mon 6 Jan 2015 13:3:00")));
        jobPost.setIntroduce("我是软件1201王富诚");
        jobPost.setWage(40.0);
        jobPost.setTimeToPay("日结");
        jobPost.setJobDescription("为静安驾校发传单");
        jobPost.setJobDetail("时间为每周六周日早晨");
        em.persist(jobPost);


    }



    /**
     * 修改个人资料成功
     * @throws Exception
     */
    @Test
    public void testChangeProfile() throws Exception{

        /*创建session*/
        Credential credential = new TaolijieCredential("yazhou");
        GeneralMemberDto memDto = accountService.findMember("yazhou",new GeneralMemberDto[0],true);
        for(Integer rid:memDto.getRoleIdList()){
           RoleDto role = accountService.findRole(rid);
            credential.addRole(role.getRolename());
        }
        CredentialUtils.createCredential(session,credential);



        String expected = new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();
        mockMvc.perform(post("/user/setting/profile")
                        .contentType("application/json;charset=utf-8")
                        .session(session)
                        .param("age", "18")
                        .param("gender","男")
                        .param("name","小北")
                        .param("qq","1252264267")
                        .param("phone","18369905318")
                        .param("phone","18369905318")
                        //.param("profilePhotoPath","") //头像路径
        ).andExpect(status().isOk())
                .andExpect(content().json(expected));
    }

    /**
     * 修改密码成功
     */
    @Test
    public void testChangePassword() throws Exception{
        /*创建session*/
        Credential credential = new TaolijieCredential("yazhou");
        GeneralMemberDto memDto = accountService.findMember("yazhou",new GeneralMemberDto[0],true);
        for(Integer rid:memDto.getRoleIdList()){
            RoleDto role = accountService.findRole(rid);
            credential.addRole(role.getRolename());
        }
        CredentialUtils.createCredential(session,credential);

        String expected = new JsonWrapper(true,"success").getAjaxMessage();
        mockMvc.perform(post("/user/setting/security")
                        .contentType("application/json;charset=utf-8")
                        .session(session)
                        .param("password","12312312")
                //.param("profilePhotoPath","") //头像路径
        ).andExpect(status().isOk())
                .andExpect(content().json(expected));
    }

    /**
     * 修改密码不合法
     */
    @Test
    public void testChangePasswordError() throws Exception{
        /*创建session*/
        Credential credential = new TaolijieCredential("yazhou");
        GeneralMemberDto memDto = accountService.findMember("yazhou",new GeneralMemberDto[0],true);
        for(Integer rid:memDto.getRoleIdList()){
            RoleDto role = accountService.findRole(rid);
            credential.addRole(role.getRolename());
        }
        CredentialUtils.createCredential(session,credential);

        String expected = new JsonWrapper(false,"密码不合法").getAjaxMessage();
        mockMvc.perform(post("/user/setting/security")
                        .contentType("application/json;charset=utf-8")
                        .session(session)
                        .param("password","asdkfjslfjlsjflsjflkasjfljf")
                //.param("profilePhotoPath","") //头像路径
        ).andExpect(status().isOk())
                .andExpect(content().json(expected));
    }

}

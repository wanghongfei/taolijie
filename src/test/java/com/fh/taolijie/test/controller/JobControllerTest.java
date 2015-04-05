package com.fh.taolijie.test.controller;

import cn.fh.security.credential.Credential;
import cn.fh.security.utils.CredentialUtils;
import com.fh.taolijie.controller.JobController;
import com.fh.taolijie.controller.dto.GeneralMemberDto;
import com.fh.taolijie.controller.dto.JobPostDto;
import com.fh.taolijie.controller.dto.RoleDto;
import com.fh.taolijie.domain.*;
import com.fh.taolijie.service.AccountService;
import com.fh.taolijie.service.JobPostService;
import com.fh.taolijie.service.impl.*;
import com.fh.taolijie.test.service.repository.BaseSpringDataTestClass;
import com.fh.taolijie.test.service.repository.SpringDataConfigBean;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.ObjWrapper;
import com.fh.taolijie.utils.Print;
import com.fh.taolijie.utils.TaolijieCredential;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by wynfrith on 15-4-3.
 */
@WebAppConfiguration
@ContextConfiguration(classes = {JobController.class,
        DefaultAccountService.class,
        DefaultSearchService.class,
        DefaultJobPostService.class,
        DefaultJobPostCategoryService.class,
        DefaultReviewService.class,
        DefaultNotificationService.class})
public class JobControllerTest extends BaseSpringDataTestClass {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    JobController jobController;
    @Autowired
    JobPostService jobPostService;
    @Autowired
    AccountService accountService;
    @Autowired
    MockHttpSession session;

    private MockMvc mockMvc;


    MemberEntity mem1;
    MemberEntity mem2;
    /*学生*/
    RoleEntity role1;
    /*商户*/
    RoleEntity role2;
    JobPostEntity job1;
    JobPostCategoryEntity jobcate1;
    private void initData(){
        role1 = new RoleEntity();
        role1.setRolename(Constants.RoleType.STUDENT.toString());
        em.persist(role1);

        mem1 = new MemberEntity();
        mem1.setUsername("wynfrith");
        mem1.setPassword("helloo");
        mem1.setQq("123456");
        em.persist(mem1);

        MemberRoleEntity memRole = new MemberRoleEntity();
        memRole.setMember(mem1);
        memRole.setRole(role1);
        mem1.setMemberRoleCollection(Arrays.asList(memRole));
        em.persist(memRole);

        mem2 = new MemberEntity();
        mem2.setUsername("helloworld");
        mem2.setPassword("wfc5582563");
        em.persist(mem2);

        MemberRoleEntity memRole2 = new MemberRoleEntity();
        memRole2.setMember(mem2);
        memRole2.setRole(role1);
        mem2.setMemberRoleCollection(Arrays.asList(memRole));
        em.persist(memRole2);


        jobcate1 = new JobPostCategoryEntity();
        jobcate1.setLevel(1);
        jobcate1.setName("分类");
        em.persist(jobcate1);

        job1 = new JobPostEntity();
        job1.setTitle("兼职标题");
        job1.setMember(mem1);
        job1.setCategory(jobcate1);
        jobcate1.setJobPostCollection(Arrays.asList(job1));
        em.persist(job1);


    }

    public void getCredential(int id,String username,MockHttpSession session){
        Credential credential = new TaolijieCredential(id,username);
        GeneralMemberDto memDto =accountService.findMember(username, new GeneralMemberDto[0], true);
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
        if(mockMvc==null){
            this.mockMvc = MockMvcBuilders.standaloneSetup(jobController).build();
            initData();
        }
        Print.print("准备完成");
    }
    @After
    public void after(){
        session.invalidate();
    }

    /**
     * 测试发布兼职
     * @throws Exception
     */

    @Test
    public void testPostSuccess() throws Exception {
        getCredential(mem1.getId(),mem1.getUsername(), session);
        mockMvc.perform(post("/user/job/post")
                .session(session)
                .contentType("application/json;charset=utf-8")
                .param("categoryId",jobcate1.getId()+"")
                .param("jobDescription","我是一篇普通的兼职信息")
                .param("title", "兼职"))
        .andExpect(status().isOk())
                .andReturn();
        List<JobPostDto> list =jobPostService.getAllJobPostList(0,0,new ObjWrapper());
        System.out.println(list.toString());
    }

    /**
     * 获取发布兼职列表
     * @throws Exception
     */
    @Test
    public void testGetList() throws  Exception{
        getCredential(mem1.getId(), mem1.getUsername(), session);
        mockMvc.perform(get("/user/job/list/1")
                .contentType("application/json;charset=utf-8")
                .session(session))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn();
    }

    /**
     * 测试访问修改页面
     * @throws Exception
     */
    @Test
    public void testChangeGet() throws  Exception{
        getCredential(mem1.getId(), mem1.getUsername(), session);
        mockMvc.perform(get("/user/job/change/"+job1.getId())
                .session(session))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.model().attributeExists("job"))
                .andExpect(status().isOk())
                .andReturn();
    }

    /**
     * 测试修改一个不存在的兼职信息
     * @return
     * @throws Exception
     */
    @Test
    public void testChangeGetNotFound() throws Exception{
        getCredential(mem1.getId(),mem1.getUsername(), session);
        mockMvc.perform(get("/user/job/change/" + 9999999)
                .session(session))
                .andExpect(status().isFound())
                .andReturn();
    }

    /**
     * 删除一条兼职
     * @throws Exception
     */
    @Test
    public void testDeleteJob()  throws Exception{
        getCredential(mem1.getId(),mem1.getUsername(), session);
        mockMvc.perform(post("/user/job/del/" + job1.getId())
                .session(session))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    /**
     * 删除兼职失败,权限不够(不是自己创建的,管理员除外)
     */
    @Test
    public void testDeleteJobNoPermission() throws Exception{
        getCredential(mem2.getId(),mem2.getUsername(), session);
        mockMvc.perform(post("/user/job/del/" + job1.getId())
                .session(session))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    /**
     * 刷新兼职 ajax post
     */
    @Test
    public void testRefresh() throws Exception{
        getCredential(mem1.getId(),mem1.getUsername(), session);
        mockMvc.perform(post("/user/job/refresh/" + job1.getId())
                .session(session))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

}

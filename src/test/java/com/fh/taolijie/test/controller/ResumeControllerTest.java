package com.fh.taolijie.test.controller;

import cn.fh.security.credential.Credential;
import cn.fh.security.utils.CredentialUtils;
import com.fh.taolijie.controller.ResumeController;
import com.fh.taolijie.controller.SHController;
import com.fh.taolijie.controller.dto.GeneralMemberDto;
import com.fh.taolijie.controller.dto.RoleDto;
import com.fh.taolijie.domain.*;
import com.fh.taolijie.service.AccountService;
import com.fh.taolijie.service.ResumeService;
import com.fh.taolijie.service.SHPostService;
import com.fh.taolijie.service.impl.*;
import com.fh.taolijie.test.service.repository.BaseSpringDataTestClass;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.ObjWrapper;
import com.fh.taolijie.utils.Print;
import com.fh.taolijie.utils.TaolijieCredential;
import com.fh.taolijie.utils.json.JsonWrapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by wynfrith on 15-4-7.
 */
@WebAppConfiguration
@ContextConfiguration(classes = {ResumeController.class,
        DefaultAccountService.class,
        DefaultSearchService.class,
        DefaultResumeService.class,
        DefaultReviewService.class,
        DefaultNotificationService.class})
public class ResumeControllerTest extends BaseSpringDataTestClass {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    ResumeController resumeController;
    @Autowired
    ResumeService resumeService;
    @Autowired
    AccountService accountService;
    @Autowired
    MockHttpSession session;

    private MockMvc mockMvc;


    MemberEntity mem1;
    MemberEntity mem2;
    MemberEntity mem3;
    /*学生*/
    RoleEntity role1;
    /*商户*/
    RoleEntity role2;
    RoleEntity role3;
    ResumeEntity resume1;
    JobPostEntity job;
    JobPostCategoryEntity jobcate;

    private void initData() {
        role1 = new RoleEntity();
        role1.setRolename(Constants.RoleType.STUDENT.toString());
        em.persist(role1);

        role2 = new RoleEntity();
        role2.setRolename(Constants.RoleType.EMPLOYER.toString());
        em.persist(role2);

        role3 = new RoleEntity();
        role3.setRolename((Constants.RoleType.ADMIN).toString());
        em.persist(role3);

        /*创建学生用户*/
        mem1 = new MemberEntity();
        mem1.setUsername("wynfrith");
        mem1.setPassword("helloo");
        mem1.setQq("123456");
        em.persist(mem1);

        MemberRoleEntity memRole = new MemberRoleEntity();
        memRole.setMember(mem1);
        memRole.setRole(role1);
        List<MemberRoleEntity> memberRoleEntityList = new ArrayList<>();
        memberRoleEntityList.add(memRole);
        mem1.setMemberRoleCollection(memberRoleEntityList);
        em.persist(memRole);

        /*创建商家用户*/
        mem2 = new MemberEntity();
        mem2.setUsername("helloworld");
        mem2.setPassword("wfc5582563");
        em.persist(mem2);

        MemberRoleEntity memRole2 = new MemberRoleEntity();
        memRole2.setMember(mem2);
        memRole2.setRole(role2);
        List<MemberRoleEntity> memberRoleEntityList2 = new ArrayList<>();
        memberRoleEntityList.add(memRole2);
        mem2.setMemberRoleCollection(memberRoleEntityList2);
        em.persist(memRole2);

        /*创建一个管理员用户*/
        mem3 = new MemberEntity();
        mem3.setUsername("woshiguanliyuan");
        mem3.setPassword("guanliyuan");
        em.persist(mem3);

        MemberRoleEntity memRole3 = new MemberRoleEntity();
        memRole3.setMember(mem3);
        memRole3.setRole(role3);
        List<MemberRoleEntity> memberRoleEntityList3 = new ArrayList<>();
        memberRoleEntityList.add(memRole3);
        mem3.setMemberRoleCollection(memberRoleEntityList3);
        em.persist(memRole3);

        jobcate = new JobPostCategoryEntity();
        jobcate.setName("程序员");
        jobcate.setLevel(1);
        em.persist(jobcate);

        job = new JobPostEntity();
        job.setTitle("兼职标题");
        job.setCategory(jobcate);
        List<JobPostEntity> jobPostEntityList1 = new ArrayList<>();
        jobPostEntityList1.add(job);
        jobcate.setJobPostCollection(jobPostEntityList1);
        em.persist(job);

        resume1 = new ResumeEntity();
        resume1.setName("大副");
        resume1.setGender("男");
        resume1.setQq("1252264267");
        List<JobPostCategoryEntity> jobPostCategoryEntityList1 = new ArrayList<>();
        jobPostCategoryEntityList1.add(jobcate);
        resume1.setCategoryList(jobPostCategoryEntityList1);
        resume1.setMember(mem1);
        em.persist(resume1);


    }

    public void getCredential(int id, String username, MockHttpSession session) {
        Credential credential = new TaolijieCredential(id, username);
        GeneralMemberDto memDto = accountService.findMember(username, new GeneralMemberDto[0], true);
        for (Integer rid : memDto.getRoleIdList()) {
            RoleDto role = accountService.findRole(rid);
            System.out.println(role.getRolename());
            credential.addRole(role.getRolename());
        }
        CredentialUtils.createCredential(session, credential);
    }

    @Before
    public void before() {
        Print.print("准备数据");
        if (mockMvc == null) {
            this.mockMvc = MockMvcBuilders.standaloneSetup(resumeController).build();
            initData();
        }
        Print.print("准备完成");
    }

    @After
    public void after() {
        session.invalidate();
    }

    /**
     * 学生创建简历 post
     */
    @Test
    public void testCreateResume() throws Exception {
        String expect = new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();
        getCredential(mem1.getId(), mem1.getUsername(), session);
        mockMvc.perform(post("/user/resume/create")
                .session(session)
                .contentType("application/json;charset=utf-8")
                .param("name", "王富诚的简历")
                .param("intendCategoryId", jobcate.getId().toString())
                .param("gender", "男"))
                .andExpect(status().isOk())
                .andExpect(content().json(expect))
                .andReturn();
    }
    /**
     * 查看简历
     */
    @Test
    public void testViewResume() throws Exception {
        getCredential(mem1.getId(), mem1.getUsername(), session);
        mockMvc.perform(get("/user/resume/view")
                .session(session)
                .contentType("application/json;charset=utf-8"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    /**
     * 删除简历 post
     */
    @Test
    public void testDelResume() throws Exception {
        String expect = new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();
        getCredential(mem1.getId(), mem1.getUsername(), session);
        mockMvc.perform(post("/user/resume/del")
                .session(session)
                .contentType("application/json;charset=utf-8"))
                .andExpect(status().isOk())
                .andExpect(content().json(expect))
                .andReturn();
    }

    /**
     * 修改简历 GET
     * @throws Exception
     */
    @Test
    public void testChangeResumeGet() throws Exception {
        String expect = new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();
        getCredential(mem1.getId(), mem1.getUsername(), session);
        mockMvc.perform(get("/user/resume/change")
                .session(session)
                .contentType("application/json;charset=utf-8"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    /**
     * 修改简历 POST
     * @throws Exception
     */
    @Test
    public void testChangeResumePost() throws Exception {
        String expect = new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();
        getCredential(mem1.getId(), mem1.getUsername(), session);
        mockMvc.perform(post("/user/resume/change")
                .session(session)
                .param("id",resume1.getId().toString())
                .param("name", "小北简历12312313")
                .param("intendCategoryId", jobcate.getId().toString())
                .param("gender", "女")
                .contentType("application/json;charset=utf-8"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(content().json(expect))
                .andReturn();
    }

    /**
     * 刷新简历 POST
     * @throws Exception
     */
    @Test
    public void testRefreshResumePost() throws Exception {
        String expect = new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();
        getCredential(mem1.getId(), mem1.getUsername(), session);
        mockMvc.perform(post("/user/resume/refresh")
                .session(session)
                .contentType("application/json;charset=utf-8"))
                .andExpect(status().isOk())
                .andExpect(content().json(expect))
                .andReturn();
    }

}


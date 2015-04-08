package com.fh.taolijie.test.controller;

import cn.fh.security.credential.Credential;
import cn.fh.security.utils.CredentialUtils;
import com.fh.taolijie.controller.JobController;
import com.fh.taolijie.controller.SHController;
import com.fh.taolijie.controller.dto.GeneralMemberDto;
import com.fh.taolijie.controller.dto.RoleDto;
import com.fh.taolijie.controller.dto.SecondHandPostDto;
import com.fh.taolijie.domain.*;
import com.fh.taolijie.service.AccountService;
import com.fh.taolijie.service.JobPostService;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.http.converter.json.Jackson2ObjectMapperBuilder.json;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by wynfrith on 15-4-5.
 */
@WebAppConfiguration
@ContextConfiguration(classes = {SHController.class,
        DefaultAccountService.class,
        DefaultSearchService.class,
        DefaultSHPostService.class,
        DefaultSHPostCategoryService.class,
        DefaultReviewService.class,
        DefaultNotificationService.class})
public class SHControllerTest extends BaseSpringDataTestClass {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    SHController shController;
    @Autowired
    SHPostService shPostService;
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
    JobPostEntity job1;
    JobPostCategoryEntity jobcate1;
    SecondHandPostEntity sh1;
    SecondHandPostCategoryEntity shcate1;
    SecondHandPostEntity sh2;

    private void initData() {
        role1 = new RoleEntity();
        role1.setRolename(Constants.RoleType.STUDENT.toString());
        em.persist(role1);

        /*管理员*/
        role3 = new RoleEntity();
        role3.setRolename((Constants.RoleType.ADMIN).toString());
        em.persist(role3);

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
        mem2.setMemberRoleCollection(Arrays.asList(memRole2));
        em.persist(memRole2);

        /*创建一个管理员用户*/
        mem3 = new MemberEntity();
        mem3.setUsername("woshiguanliyuan");
        mem3.setPassword("guanliyuan");
        em.persist(mem3);

        MemberRoleEntity memRole3 = new MemberRoleEntity();
        memRole3.setMember(mem3);
        memRole3.setRole(role3);
        mem3.setMemberRoleCollection(Arrays.asList(memRole3));
        em.persist(memRole3);


        shcate1 = new SecondHandPostCategoryEntity();
        shcate1.setName("二手分类");
        shcate1.setLevel(1);
        em.persist(shcate1);

        sh1 = new SecondHandPostEntity();
        sh1.setTitle("这是一个二手帖子");
        sh1.setMember(mem1);
        sh1.setCategory(shcate1);
        shcate1.setPostCollection(Arrays.asList(sh1));
        em.persist(sh1);

        sh2 = new SecondHandPostEntity();
        sh2.setTitle("这是第二篇二手");
        sh2.setDescription("这是正文部分");
        sh2.setMember(mem1);
        sh2.setCategory(shcate1);
        List<SecondHandPostEntity> secondHandPostEntityList = new ArrayList<>();
        secondHandPostEntityList.add(sh2);
        shcate1.setPostCollection(secondHandPostEntityList);
        em.persist(sh2);


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
            this.mockMvc = MockMvcBuilders.standaloneSetup(shController).build();
            initData();
        }
        Print.print("准备完成");
    }

    @After
    public void after() {
        session.invalidate();
    }

    /**
     * 发布二手信息
     */
    @Test
    public void testPostSuccess() throws Exception {
        getCredential(mem1.getId(), mem1.getUsername(), session);
        mockMvc.perform(post("/user/sh/post")
                .session(session)
                .contentType("application/json;charset=utf-8")
                .param("categoryId", shcate1.getId() + "")
                .param("description", "二手描述,二手描述,二手描述")
                .param("title", "我是二手"))
                .andExpect(status().isOk())
                .andReturn();
        List<SecondHandPostDto> list = shPostService.getAllPostList(0, 0, new ObjWrapper());
        System.out.println(list.toString());
    }

    /**
     * 获取发布兼职列表
     * @throws Exception
     */
    @Test
    public void testGetList() throws  Exception{
        getCredential(mem1.getId(), mem1.getUsername(), session);
        mockMvc.perform(get("/user/sh/list/1")
                .contentType("application/json;charset=utf-8")
                .session(session))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn();
    }

    /**
     * 删除一篇二手
     */
    @Test
    public void testDel() throws Exception{
        String expect = new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();
        getCredential(mem1.getId(), mem1.getUsername(), session);
        mockMvc.perform(post("/user/sh/del/" + sh1.getId())
                .contentType("application/json;charset=utf-8")
                .session(session))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().json(expect))
                .andReturn();
    }


    /**
     * 删除二手 失败(非本人操作,用户权限不够)
     */
    @Test
    public void testDelNoPermission() throws Exception{
        String expect = new JsonWrapper(false, Constants.ErrorType.PERMISSION_ERROR).getAjaxMessage();
        getCredential(mem2.getId(), mem2.getUsername(), session);
        mockMvc.perform(post("/user/sh/del/" + sh1.getId())
                .contentType("application/json;charset=utf-8")
                .session(session))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().json(expect))
                .andReturn();
    }
    /**
     * 管理员老大删除二手 (可以删除任意二手帖子)
     */
    @Test
    public void testDelAdmin() throws Exception{
        String expect = new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();
        getCredential(mem3.getId(), mem3.getUsername(), session);
        mockMvc.perform(post("/user/sh/del/" + sh1.getId())
                .contentType("application/json;charset=utf-8")
                .session(session))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().json(expect))
                .andReturn();
    }

    /**
     * 二手修改页面
     * @throws Exception
     */
    @Test
    public  void testChangeGet() throws Exception{
        getCredential(mem1.getId(), mem1.getUsername(), session);
        mockMvc.perform(get("/user/sh/change")
                .contentType("application/json;charset=utf-8")
                .param("id", sh1.getId().toString())
                .session(session))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("sh"))
                .andReturn();
    }



    /**
     * 传入id不是本用户发布的打开二手修改页面
     */
    @Test
    public void testChangeGetNOPerssion() throws Exception{
        getCredential(mem2.getId(), mem2.getUsername(), session);
        mockMvc.perform(get("/user/sh/change")
                .contentType("application/json;charset=utf-8")
                .param("id", sh1.getId().toString())
                .session(session))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isFound())
                .andReturn();
    }

    /**
     * 修改二手post请求
     * @throws Exception
     */
    @Test
    public  void testChangePost() throws Exception{
        String expect = new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();
        getCredential(mem1.getId(), mem1.getUsername(), session);
        System.out.println("memid:"+mem1.getId());
        mockMvc.perform(post("/user/sh/change")
                .contentType("application/json;charset=utf-8")
                .param("id", sh1.getId().toString())
                .param("title", "哈哈哈,修改成功啦")
                .param("description", "这是新的描述!!")
                .param("categoryId", shcate1.getId().toString())
                .session(session))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(content().json(expect))
                .andReturn();
        List<SecondHandPostDto> list = shPostService.getAllPostList(0, 0, new ObjWrapper());
        for(SecondHandPostDto s:list){
            System.out.print("二手id: "+s.getId()+"   ");
            System.out.print("memid: " + s.getMemberId());
            System.out.println("二手标题: "+s.getTitle());
        }
    }

    /**
     * 刷新
     */
    @Test
    public  void testRefresh() throws Exception{
        String expect = new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();
        getCredential(mem1.getId(), mem1.getUsername(), session);
        mockMvc.perform(post("/user/sh/refresh")
                .contentType("application/json;charset=utf-8")
                .param("id", sh1.getId().toString())
                .session(session))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(content().json(expect))
                .andReturn();
    }
}


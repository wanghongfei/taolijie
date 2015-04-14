package com.fh.taolijie.test.controller;

import cn.fh.security.credential.Credential;
import cn.fh.security.utils.CredentialUtils;
import com.fh.taolijie.controller.HomeController;
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
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by wynfrith on 15-4-1.
 */

@WebAppConfiguration
@ContextConfiguration(classes = {HomeController.class,
        DefaultNewsService.class,
        DefaultJobPostService.class,
        DefaultSHPostService.class,
        DefaultResumeService.class,
        DefaultAccountService.class,
        DefaultReviewService.class,
        DefaultJobPostCategoryService.class,
        DefaultSearchService.class})
public class HomeControllerTest extends BaseSpringDataTestClass{
    @PersistenceContext
    private EntityManager em;

    @Autowired
    HomeController homeController;
    @Autowired
    AccountService accountService;
    @Autowired
    private MockHttpSession session;

    private MockMvc mockMvc;


    /**
     * 实体引用
     */
    RoleEntity role1;
    RoleEntity role2;
    RoleEntity role3;
    MemberEntity mem1;
    MemberEntity mem2;
    NewsEntity news1;
    ReviewEntity review1;
    JobPostEntity job1;
    ResumeEntity resume1;
    SecondHandPostEntity sh1;
    NotificationEntity notification1;
    SecondHandPostCategoryEntity shCate1;
    private JobPostCategoryEntity jobCate1;


    @Before
    public void before(){
        Print.print("准备数据");
        // UserController userController = new UserController();
        if(mockMvc==null){
            this.mockMvc = MockMvcBuilders.standaloneSetup(homeController).build();
            initData();
        }
        Print.print("准备完成");
    }
    public void initData(){
        /**
         * 创建role
         */
        role1 = new RoleEntity();
        role1.setRolename(Constants.RoleType.STUDENT.toString());
        role1.setMemo("学生");
        em.persist(role1);

        role2 = new RoleEntity();
        role2.setRolename(Constants.RoleType.EMPLOYER.toString());
        role2.setMemo("商家");
        em.persist(role2);


        role3 = new RoleEntity();
        role3.setRolename(Constants.RoleType.ADMIN.toString());
        role3.setMemo("管理员");
        em.persist(role3);

        /**
         *  创建用户
         *
         */
        /* mem1 学生用户 wynfrith*/
        mem1 = new MemberEntity();
        mem1.setUsername("wynfrith");
        mem1.setEmail("wangfucheng56@gmail.com");
        mem1.setPassword(CredentialUtils.sha("wfc5582563"));
        mem1.setQq("1252264267");
        mem1.setValid(false);
        mem1.setAge(21);
        /*加入三个工作id*/
        mem1.setAppliedJobIds("1" + Constants.DELIMITER + "2" + Constants.DELIMITER + "3");
        //mem1.setBlockList();
        mem1.setGender("男");
        //mem1.setCompanyName("");
        mem1.setName("王富诚");
        //  mem1.setComplaint();
        // mem1.setVerified();
        // mem1.setSecondHandPostCollection();
        //  mem1.setJobPostCollection();
        // mem1.setReviewCollection();
        //mem1.setResumeCollection();
        // mem1.setProfilePhotoId();
        //mem1.setReplyList();
        // mem1.setPhone();
        //mem1.setNotificationCollection();
        //mem1.setNewsCollection();
        em.persist(mem1);



        /* mem2 商家用户 wfc5582563*/
        mem2 = new MemberEntity();
        mem2.setUsername("wfc5582563");
        mem2.setEmail("wfc5582563@126.com");
        mem2.setPassword(CredentialUtils.sha("qq123456"));
        mem2.setQq("123456789");
        mem2.setAge(28);
        /*加入三个工作id*/
        mem2.setGender("男");
        mem2.setName("王先生");
        em.persist(mem2);



        /**
         * 创建member与role映射
         */
        MemberRoleEntity memRole1 = new MemberRoleEntity();
        memRole1.setRole(role1);
        memRole1.setMember(mem1);

        List<MemberRoleEntity> memRoleList = new ArrayList<>();
        memRoleList.add(memRole1);
        mem1.setMemberRoleCollection(memRoleList);
        em.persist(memRole1);

        MemberRoleEntity memRole2 = new MemberRoleEntity();
        memRole2.setRole(role2);
        memRole2.setMember(mem2);
        mem2.setMemberRoleCollection(Arrays.asList(memRole2));
        em.persist(memRole2);

        /**
         * 新闻聚焦点
         */
        news1 = new NewsEntity();
        news1.setMember(mem1);
        news1.setContent("新闻聚焦点正文1");
        news1.setTime(new Date());
        news1.setTitle("新闻聚焦点标题1");
        mem1.setNewsCollection(Arrays.asList(news1));
        em.persist(news1);

        /**
         * 用户发送的评论
         */
        review1 = new ReviewEntity();
        review1.setMember(mem1);
        review1.setTime(new Date());
        review1.setContent("评论正文1");
        //review1.setReplyList();

        /**
         * 用户收到的通知
         */
        notification1 = new NotificationEntity();
        notification1.setContent("通知正文");
        notification1.setTitle("通知标题");
        notification1.setTime(new Date());
        notification1.setMember(mem1);
        /*0未读 1已读*/
        notification1.setIsRead(0);


        /**
         * 兼职分类
         */
        jobCate1 = new JobPostCategoryEntity();
        jobCate1.setName("电脑维护人员");
        jobCate1.setLevel(1);
        jobCate1.setMemo("修电脑的");
        jobCate1.setThemeColor("#66ccff");
        em.persist(jobCate1);

        /**
         * 兼职
         */
        job1 = new JobPostEntity();
        job1.setMember(mem1);
        job1.setWorkTime("工作时间在周六周日");
        job1.setWorkPlace("工作地点在淄博");
        job1.setTitle("兼职标题");

        job1.setCategory(jobCate1);
        jobCate1.setJobPostCollection(new ArrayList<>());
        jobCate1.getJobPostCollection().add(job1);
        mem1.setJobPostCollection(new ArrayList<>());
        mem1.getJobPostCollection().add(job1);
        em.persist(job1);

        JobPostEntity job;
        for(int i=0;i<10;i++){
            job = new JobPostEntity();
            job.setMember(mem1);
            job.setWorkTime("工作时间在周六周日");
            job.setWorkPlace("工作地点在淄博");
            job.setTitle("兼职标题"+i);
            job.setCategory(jobCate1);
            jobCate1.setJobPostCollection(new ArrayList<>());
            jobCate1.getJobPostCollection().add(job);
            em.persist(job);
        }

        /**
         * 二手分类
         */
        shCate1 = new SecondHandPostCategoryEntity();
        shCate1.setName("废旧电子设备");
        shCate1.setLevel(1);
        shCate1.setMemo("电子设备");
        shCate1.setThemeColor("#66ccff");
        em.persist(shCate1);

        /**
         * 二手
         */
        sh1 = new SecondHandPostEntity();
        sh1.setMember(mem1);
        sh1.setTitle("二手标题");
        sh1.setDescription("二手物品的描述");

        sh1.setCategory(shCate1);
        shCate1.setPostCollection(new ArrayList<>());
        shCate1.getPostCollection().add(sh1);
        mem1.setSecondHandPostCollection(new ArrayList<>());
        mem1.getSecondHandPostCollection().add(sh1);
        em.persist(sh1);

        /**
         * 简历
         *
         */

        /*学生mem1 发的简历*/
        resume1= new ResumeEntity();
        resume1.setMember(mem1);
        resume1.setEmail("wangfucheng56@gmail.com");
        resume1.setGender("男");
        resume1.setQq("1252264267");
        resume1.setIntroduce("我叫王富诚");
        mem1.setResumeCollection(Arrays.asList(resume1));
        em.persist(resume1);


        em.flush();
        em.clear();
    }

    @After
    public void after(){
        /*销毁session*/
        session.invalidate();
    }

    public void getCredential(String username,MockHttpSession session){
        Credential credential = new TaolijieCredential(username);
        GeneralMemberDto memDto =accountService.findMember(username, new GeneralMemberDto[0], true);
        for(Integer rid:memDto.getRoleIdList()){
            RoleDto role = accountService.findRole(rid);
            System.out.println(role.getRolename());
            credential.addRole(role.getRolename());
        }
        CredentialUtils.createCredential(session, credential);
    }


    /**
     * 未登录显示 “个人中心”
     */
    @Test
    public void testHome() throws Exception {
        mockMvc.perform(get("/index")
                .contentType("text/html;chrset=utf-8"))
                .andExpect(MockMvcResultMatchers.model().attribute("username","个人中心"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("newsList"))
                //.andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    /**
     * 已登陆 显示 用户名
     */
    @Test
    public void testHomeVerified() throws Exception {
          /*创建session*/
        /*wynfrith 为 学生用户 role = STUDENT*/
        getCredential("wynfrith", session);

        mockMvc.perform(get("/index")
                .session(session)
                .contentType("text/html;chrset=utf-8"))
                .andExpect(MockMvcResultMatchers.model().attribute("username", "wynfrith"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("newsList"))
                .andReturn();

    }


    @Test
    public  void testJobDetail() throws Exception{
        mockMvc.perform(get("/detail/job/"+job1.getId())
                .contentType("text/html;chrset=utf-8"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("job"))
                .andReturn();
    }

    @Test
    public  void testSHDetail() throws Exception{
        mockMvc.perform(get("/detail/sh/"+sh1.getId())
                .contentType("text/html;chrset=utf-8"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("sh"))
                .andReturn();
    }

    /**
     * 简历详情页测试(显示所有信息)
     * @throws Exception
     */
    @Test
    public void testResumeDetail() throws Exception{
        /*
        * 创建一个用户
        * wfc5582563 为 商家用户 role = EMPLOYER
        * */
        getCredential("wfc5582563", session);

        MvcResult result = mockMvc.perform(get("/detail/resume/"+resume1.getId())
                .session(session)
                .contentType("text/html;chrset=utf-8"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();


        Boolean isDisplay = true;
        Map<String,Object>  map= result.getModelAndView().getModel();
        for(String key:map.keySet()){
            if(key.equals("contactDisplay")){
                isDisplay = Boolean.parseBoolean(map.get(key).toString());
            }
        }
        Assert.assertTrue(isDisplay);
    }


    /**
 * 简历详情页测试(不显示联系方式)
     * @throws Exception
     */
    @Test
    public void testResumeDetailNoContacts() throws Exception{
         /*
        * 创建一个用户
        * wynfrith 为 学生用户 role = STUDENT
        * */
        getCredential("wynfrith",session);

        MvcResult result = mockMvc.perform(get("/detail/resume/"+resume1.getId())
                .session(session)
                .contentType("text/html;chrset=utf-8"))
                .andReturn();

        Boolean isDisplay = false;
        Map<String,Object>  map= result.getModelAndView().getModel();
        for(String key:map.keySet()){
            if(key.equals("contactDisplay")){
                isDisplay = Boolean.parseBoolean(map.get(key).toString());
            }
        }
        Assert.assertFalse(isDisplay);
    }


    /**
     * 新闻
     */
    @Test
    public void testNewsDetail() throws  Exception{
        MvcResult result = mockMvc.perform(get("/detail/news/"+news1.getId())
                .contentType("text/html;chrset=utf-8"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("news"))
                .andReturn();
    }
    /**
     * 获得新闻列表
     */
    @Test
    public void testNewsList() throws  Exception{
        MvcResult result = mockMvc.perform(get("/list/news/1")
                .contentType("application/json;charset=utf-8"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }


    /**
     * 注册成功
     * @throws Exception
     */
    @Test
    @Transactional(readOnly = false)
    public void testRegisterSuccess() throws Exception{

        String expected = new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();

        mockMvc.perform(post("/register")
                        .contentType("application/json;charset=utf-8")
                        .param("username","wynfrith111")
                        .param("password","wfc5582563")
                        .param("repassword","wfc5582563")
        ).andExpect(status().isOk())
                .andExpect(content().json(expected));
    }

    /**
     * 注册失败(用户名以存在)
     * @throws Exception
     */
    @Test
    public void testRegisterErrorUserExist() throws Exception{
        mockMvc.perform(post("/register")
                        .contentType("application/json;charset=utf-8")
                        .param("username", "wynfrith")
                        .param("password", "wfc5582563")
                        .param("repassword","wfc5582563")
        ).andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    /**
     * 注册失败
     * @throws Exception
     */
    @Test
    public void testRegisterError() throws Exception{
        mockMvc.perform(post("/register")
                        .contentType("application/json;charset=utf-8")
                        .param("username", "")
                        .param("password", "")
                        .param("repassword","")
        ).andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }


    /**
     * 测试登陆成功
     * 需要测试cookie是否写入
     */
    @Test
    public void testLoginSuccess() throws Exception {
        String expected = new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();
        int cookieExpireTime = 1*24*60*60;
        mockMvc.perform(post("/login")
                .contentType("application/json;charset=utf-8")
                .param("username", "wynfrith")
                .param("password", "wfc5582563")
                .param("rememberMe", "true"))
                .andExpect(status().isOk())
                .andExpect(cookie().value("username", "wynfrith"))
                .andExpect(cookie().value("password","wfc5582563"))
                .andExpect(cookie().maxAge("username", cookieExpireTime))
                .andExpect(cookie().maxAge("password", cookieExpireTime))
                .andExpect(content().string(expected));
    }

    /**
     * 测试登陆用户名不存在
     * @throws Exception
     */
    @Test
    public void testLoginUsernameError() throws Exception {
        String expected = new JsonWrapper(false, Constants.ErrorType.USERNAME_NOT_EXISTS).getAjaxMessage();

        mockMvc.perform(post("/login")
                        .contentType("application/json;charset=utf-8")
                        .param("username", "hehehe")
                        .param("password", "yazhou110")
        ).andExpect(status().isOk())
                .andExpect(content().string(expected));

    }

    /**
     * 测试登陆密码错误
     * @throws Exception
     */
    @Test
    public void testLoginPasswordError() throws Exception {
        String expected = new JsonWrapper(false, Constants.ErrorType.USER_INVALID_ERROR).getAjaxMessage();

        mockMvc.perform(post("/login")
                        .contentType("application/json;charset=utf-8")
                        .param("username", "wynfrith")
                        .param("password", "9988979797779797")
        ).andExpect(status().isOk())
                .andExpect(content().string(expected));

    }


}

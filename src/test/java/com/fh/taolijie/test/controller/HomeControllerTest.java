package com.fh.taolijie.test.controller;

import cn.fh.security.credential.Credential;
import cn.fh.security.utils.CredentialUtils;
import com.fh.taolijie.controller.HomeController;
import com.fh.taolijie.controller.dto.EmployerDto;
import com.fh.taolijie.controller.dto.GeneralMemberDto;
import com.fh.taolijie.controller.dto.RoleDto;
import com.fh.taolijie.controller.dto.StudentDto;
import com.fh.taolijie.service.AccountService;
import com.fh.taolijie.service.impl.*;
import com.fh.taolijie.test.service.repository.BaseSpringDataTestClass;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.Print;
import com.fh.taolijie.utils.TaolijieCredential;
import com.fh.taolijie.utils.json.JsonWrapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.After;
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

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    @Before
    public void before(){
        Print.print("准备数据");
        // UserController userController = new UserController();
        if(mockMvc==null){
            this.mockMvc = MockMvcBuilders.standaloneSetup(homeController).build();
            ControllerTestUtils.initData(em);
        }
        Print.print("准备完成");
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
        mockMvc.perform(get("/detail/job/1")
                .contentType("text/html;chrset=utf-8"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("job"))
                .andReturn();
    }

    @Test
    public  void testSHDetail() throws Exception{
        mockMvc.perform(get("/detail/sh/1")
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

        MvcResult result = mockMvc.perform(get("/detail/resume/1")
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

        MvcResult result = mockMvc.perform(get("/detail/resume/1")
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
        MvcResult result = mockMvc.perform(get("/detail/news/1")
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
        String expected = new JsonWrapper(false, Constants.ErrorType.PASSWORD_ERROR).getAjaxMessage();

        mockMvc.perform(post("/login")
                        .contentType("application/json;charset=utf-8")
                        .param("username", "wynfrith")
                        .param("password", "9988979797779797")
        ).andExpect(status().isOk())
                .andExpect(content().string(expected));

    }


}

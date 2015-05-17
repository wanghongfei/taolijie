package com.fh.taolijie.test.controller;

import cn.fh.security.utils.CredentialUtils;
import com.fh.taolijie.controller.ApiController;
import com.fh.taolijie.controller.dto.JobPostDto;
import com.fh.taolijie.domain.*;
import com.fh.taolijie.service.impl.*;
import com.fh.taolijie.test.service.repository.BaseSpringDataTestClass;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.Print;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by wynfrith on 15-4-2.
 */
@WebAppConfiguration
@ContextConfiguration(classes = {ApiController.class,
                                 DefaultJobPostService.class,
                                 DefaultSHPostService.class,
                                 DefaultResumeService.class,
                                 DefaultAccountService.class,
                                 DefaultReviewService.class,
                                 DefaultJobPostCategoryService.class,
                                 DefaultSearchService.class,
                                 DefaultSHPostCategoryService.class})
public class ApiControllerTest extends BaseSpringDataTestClass {
    @PersistenceContext
    private EntityManager em;
    @Autowired
    ApiController apiController;
    private MockMvc mockMvc;



    RoleEntity role1;
    RoleEntity role2;
    RoleEntity role3;
    MemberEntity mem1;
    MemberEntity mem2;
    NewsEntity news1;
    ReviewEntity review1;
    ResumeEntity resume1;
    SecondHandPostEntity sh1;
    SecondHandPostCategoryEntity shCate1;
    JobPostCategoryEntity jobCate1;
    NotificationEntity notification1;


    JobPostDto job1;

    private void initData(){
        job1 = new JobPostDto();
        job1.setId(1);
    }


    @Before
    public void before(){
        Print.print("准备数据");
        // UserController userController = new UserController();
        if(mockMvc==null){
            this.mockMvc = MockMvcBuilders.standaloneSetup(apiController).build();
             initData();
        }

        Print.print("准备完成");
    }





    /*获得某一条兼职信息*/
    @Test
    public void testGetOneJobItem() throws Exception{
        String expect = "{\"applicantAmount\":0,\"categoryId\":1,\"categoryName\":\"程序员\",\"contact\":\"王先生\",\"contactEmail\":\"wynfrith@hotmail.com\",\"contactPhone\":\"18369905318\",\"contactQq\":\"1252264267\",\"dislikes\":0,\"educationLevel\":\"\",\"expiredTime\":1438963200000,\"id\":1,\"introduce\":\"求给力程序员一名名\",\"jobDescription\":\"1.基础扎实2.会唱歌\",\"jobDetail\":\"打杂\",\"likes\":0,\"memberId\":1,\"pageView\":0,\"postTime\":1429977600000,\"salaryUnit\":\"元\",\"timeToPay\":\"月\",\"title\":\"招程序员\",\"verified\":\"\",\"wage\":1000,\"workPlace\":\"北京中关村\",\"workTime\":\"周一至周六\"}";
        MvcResult result = mockMvc.perform(get("/api/item/job/"+job1.getId())
                .contentType("application/json;charset=utf-8"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expect))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void testJobcate() throws  Exception{
        MvcResult result = mockMvc.perform(get("/api/cate/job")
                .contentType("application/json;charset=utf-8"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void testJobList() throws  Exception{
        MvcResult result = mockMvc.perform(get("/api/list/job/1")
                .contentType("application/json;charset=utf-8"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void testResumeList() throws  Exception{
        MvcResult result = mockMvc.perform(get("/api/list/resume/2")
                .contentType("application/json;charset=utf-8"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }


    @Test
    public void testShCate() throws  Exception{
        MvcResult result = mockMvc.perform(get("/api/cate/sh")
                .contentType("application/json;charset=utf-8"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void testSHList() throws  Exception{
        MvcResult result = mockMvc.perform(get("/api/list/sh/1")
                .contentType("application/json;charset=utf-8"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }




}

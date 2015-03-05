package com.fh.taolijie.test.controller;


import com.fh.taolijie.controller.HomeController;
import com.fh.taolijie.utils.ResponseUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by wynfrith on 15-3-5.
 */
public class HomeControllerTest {

    private MockMvc mockMvc;
    @Before
    public void setUp() {
        HomeController homeController = new HomeController();
        mockMvc = MockMvcBuilders.standaloneSetup(homeController).build();
    }

    @Test
    public void testHome() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        Assert.assertEquals("pc/index", result.getModelAndView().getViewName());
    }


}

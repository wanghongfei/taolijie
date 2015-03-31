package com.fh.taolijie.test.controller;

import com.fh.taolijie.controller.AdminController;
import com.fh.taolijie.domain.MemberEntity;
import com.fh.taolijie.service.AccountService;
import com.fh.taolijie.test.service.repository.BaseSpringDataTestClass;
import com.fh.taolijie.utils.Print;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by wynfrith on 15-3-30.
 */


@WebAppConfiguration
@ContextConfiguration(classes = {AdminController.class})
public class AdminControllerTest extends BaseSpringDataTestClass{
    @PersistenceContext
    private EntityManager em;

    @Autowired
    AdminController adminController;

    private MockMvc mockMvc;

    @Before
    public void before(){
        Print.print("准备数据");
        // UserController userController = new UserController();
        if(mockMvc==null){
            this.mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
            initData();
        }
        Print.print("准备完成");
    }

    private void initData() {

        /*创建一个账户*/
        MemberEntity entity = new MemberEntity();

    }

}

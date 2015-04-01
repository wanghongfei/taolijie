package com.fh.taolijie.controller;

import cn.fh.security.credential.Credential;
import cn.fh.security.utils.CredentialUtils;
import com.fh.taolijie.controller.dto.GeneralMemberDto;
import com.fh.taolijie.controller.dto.JobPostDto;
import com.fh.taolijie.controller.dto.ResumeDto;
import com.fh.taolijie.service.AccountService;
import com.fh.taolijie.service.JobPostCateService;
import com.fh.taolijie.service.JobPostService;
import com.fh.taolijie.service.ResumeService;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.ResponseUtils;
import com.fh.taolijie.utils.json.JsonWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Date;


/**
 * 简历控制器
 * 存放用户操作简历的路由
 *
 * 权限：学生、管理员可以创建简历
 *
 * Created by wynfrith on 15-4-1.
 */

@Controller
@RequestMapping("/user/resume")
public class ResumeController{

    @Autowired
    ResumeService resumeService;
    @Autowired
    AccountService accountService;

    /**
     * 创建简历 get
     * @param session
     * @return
     */
    @RequestMapping(value = "/create" ,method = RequestMethod.GET)
    public String create(HttpSession session){
        return "";
    }

    /**
     * 创建简历 ajax
     * @param resumeDto 简历提交的表单
     * @param result 表单的错误信息
     * @param session
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public @ResponseBody String create(@Valid ResumeDto resumeDto,
               BindingResult result,
               HttpSession session){
        return "";
    }


    /**
     * 查看我的兼职 GET
     * @param session 用户的角色
     * @return
     */
    @RequestMapping(value = "view",method = RequestMethod.GET)
    public String view(HttpSession session){
        return "";
    }


    /**
     * 获取兼职投递的兼职列表 Ajax GET
     * 我都投给了谁
     * @param page  页码数
     * @param session 用户的信息
     * @return
     */
    @RequestMapping(value = "list/{page}",method = RequestMethod.GET,produces = "application/json;charset=utf-8")
    public @ResponseBody String list(@PathVariable int page,HttpSession session){
        return "";
    }



    /**
     * 删除简历
     * 先判断有木有简历
     * @param session  用户的信息
     * @return
     */
    @RequestMapping(value = "del",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public @ResponseBody String del(HttpSession session){
        return "";
    }


    /**
     * 修改简历
     * 与创建简历同一个页面，只不过修改简历会填充好之前的字段
     * @param resumeDto 传入一个简历所有信息
     * @param session 用户的信息
     * @return
     */
    @RequestMapping(value = "change",method = RequestMethod.GET)
    public String changeJob(ResumeDto resumeDto,HttpSession session){
        return  "";
    }

    /**
     * 修改简历 ajax
     * 之后考虑时候可以把创建简历和修改合并
     * @param session  用户的信息
     * @return
     */
    @RequestMapping(value = "change",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public @ResponseBody String change(HttpSession session){
        return  "";
    }

    /**
     * 刷新简历数据 ajax
     * 更新一下posttime
     * @param session  用户的信息
     */
    @RequestMapping(value = "refresh",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public @ResponseBody String refresh(HttpSession session){
        return  "";
    }
}


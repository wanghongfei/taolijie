package com.fh.taolijie.controller;

import cn.fh.security.credential.Credential;
import cn.fh.security.utils.CredentialUtils;
import com.alibaba.fastjson.JSON;
import com.fh.taolijie.controller.dto.*;
import com.fh.taolijie.domain.MemberModel;
import com.fh.taolijie.domain.MemberModelWithBLOBs;
import com.fh.taolijie.domain.RoleModel;
import com.fh.taolijie.exception.checked.*;
import com.fh.taolijie.service.*;
import com.fh.taolijie.service.repository.BannerPicRepo;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.ObjWrapper;
import com.fh.taolijie.utils.json.JsonWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wynfrith on 15-3-30.
 */
@Controller
@RequestMapping("/manage")
public class AdminController {

    @Autowired
    AccountService accountService;
    @Autowired
    JobPostService jobPostService;
    @Autowired
    JobPostCateService jobPostCateService;
    @Autowired
    ShPostService shPostService;
    @Autowired
    ShPostCategoryService shPostCategoryService;
/*    @Autowired
    SchoolService schoolService;*/
    @Autowired
    NewsService newsService;
/*    @Autowired
    BannerPicRepo bannerPicRepo;*/



    /**
     * 后台主页
     */
    @RequestMapping(value = {"/","index"},method = RequestMethod.GET)
    public String index(){
        return "pc/admin/index";
    }

    /**
     * 管理员注销
     */
    @RequestMapping(value = "logout",method = RequestMethod.GET)
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/login/admin";
    }


    /**
     * 兼职列表页面
     */
    @RequestMapping(value = "jobs",method = RequestMethod.GET)
    public String jobs(){
        return "pc/admin/jobs";
    }
    /**
     * 二手列表页面
     */
    @RequestMapping(value = "shs",method = RequestMethod.GET)
    public String shs(){
        return "pc/admin/shs";
    }
    /**
     * 简历页面
     */
    @RequestMapping(value = "resumes",method = RequestMethod.GET)
    public String resumes(){
        return "pc/admin/resumes";
    }




    /**
     * 用户列表页面
     * @return
     */
    @RequestMapping(value = "users",method = RequestMethod.GET)
    public String userlist(){
       return "pc/admin/users";
    }


    /**
     * 添加用户页面（填表单）
     */
    @RequestMapping(value = "adduser",method = RequestMethod.GET)
    public String addUser(Model model){
        model.addAttribute("isUpdate",false);
        return "pc/admin/adduser";
    }
    /**
     * 修改用户页面
     */
    @RequestMapping(value = "updateUser/{id}",method = RequestMethod.GET)
    public String updateUser(@PathVariable int id,Model model){

        //GeneralMemberDto member = accountService.findMember(id);
        MemberModelWithBLOBs member = accountService.findMember(id);
        if(member == null){
            return "redirect:pc/admin/adduser";
        }
        model.addAttribute("isUpdate",true);
        model.addAttribute("member",member);
        return "pc/admin/adduser";
    }

    /**
     * 添加用户
     * @return
     */
    @RequestMapping(value = "/adduser",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public @ResponseBody String  addUser(MemberModel dto,@RequestParam String role){
        RoleModel studentRole = null;
        RoleModel employerRole = null;
        RoleModel adminRole = null;

/*        if(null != accountService.findMember(dto.getUsername(),new GeneralMemberDto[0],false)){
            return new JsonWrapper(false, Constants.ErrorType.USERNAME_EXISTS).getAjaxMessage();
        }*/
        if(null != accountService.findMember(dto.getUsername())){
            return new JsonWrapper(false, Constants.ErrorType.USERNAME_EXISTS).getAjaxMessage();
        }


        System.out.println("添加用户的role:" + role);

           /*查询所有role*/
        for(RoleDto r : accountService.getAllRole()){
            if(r.getRolename().equals(Constants.RoleType.STUDENT.toString())) {
                studentRole = r;
            }else if(r.getRolename().equals(Constants.RoleType.EMPLOYER.toString())){
                employerRole = r;
            }else if(r.getRolename().equals(Constants.RoleType.ADMIN.toString())){
                adminRole = r;
            }
        }

         /*如果没有role,创建*/
        if(studentRole == null){
            RoleDto r = new RoleDto();
            r.setRolename(Constants.RoleType.STUDENT.toString());
            r.setMemo("学生");
            accountService.addRole(r);
            studentRole = r;
        }
        if(employerRole==null){
            RoleDto r = new RoleDto();
            r.setRolename(Constants.RoleType.EMPLOYER.toString());
            r.setMemo("商家");
            accountService.addRole(r);
            employerRole = r;
        }
        if(adminRole==null){
            RoleDto r = new RoleDto();
            r.setRolename(Constants.RoleType.ADMIN.toString());
            r.setMemo("管理员");
            accountService.addRole(r);
            employerRole = r;
        }


        List<Integer> roleList = new ArrayList<>();
        /*对role的判断*/
        if(role.equals(adminRole.getRolename())){
            roleList.add(adminRole.getRid());
        }else if(role.equals(employerRole.getRolename())){
            roleList.add(employerRole.getRid());
        }else if(role.equals(studentRole.getRolename())){
            roleList.add(studentRole.getRid());
        }else{
            return new JsonWrapper(false,Constants.ErrorType.FAILED).getAjaxMessage();
        }

        /*设置role*/
        dto.setRoleIdList(roleList);
        dto.setPassword(CredentialUtils.sha(dto.getPassword()));
        dto.setCreated_time(new Date());
        dto.setValid(true);


        try {
            accountService.register(dto);
        } catch (DuplicatedUsernameException e) {
            return new JsonWrapper(false,Constants.ErrorType.FAILED).getAjaxMessage();
        }
        return new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();
    }


    /***
     * 修改账户(权限)
     */
    @RequestMapping(value = "/updateuser",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public @ResponseBody String updateUser(GeneralMemberDto member,RoleDto role){

        /*这里还需要验证role是否存在*/
        accountService.assignRole(role.getRid(), member.getUsername());

        if(!accountService.updateMember(member)){
            return new JsonWrapper(true,Constants.ErrorType.FAILED).getAjaxMessage();
        }
        return new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();
    }


    /**
     * 封号
     */
    @RequestMapping(value = "/banuser",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public @ResponseBody String banUser(GeneralMemberDto member){
        member.setValid(true);
        if(!accountService.updateMember(member)){
            return new JsonWrapper(true,Constants.ErrorType.FAILED).getAjaxMessage();
        }
        return new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();
    }


    /**
     * 解封账户
     */
    @RequestMapping(value = "/resumeuser",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public @ResponseBody String resumeUser(GeneralMemberDto member){
        member.setValid(false);
        if(!accountService.updateMember(member)){
            return new JsonWrapper(true,Constants.ErrorType.FAILED).getAjaxMessage();
        }
        return new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();
    }

    /**
     *删除用户
     */
    @RequestMapping(value = "/deleteUser/{id}",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public @ResponseBody String deleterUser(@PathVariable int id, HttpSession session){
        Credential credential = CredentialUtils.getCredential(session);
        if(id == credential.getId()){
            return new JsonWrapper(true,Constants.ErrorType.CANT_DELETE_CURRENT_USER).getAjaxMessage();
        }
/*        try {
        } catch (UserNotExistsException e) {
            return new JsonWrapper(true,Constants.ErrorType.USERNAME_NOT_EXISTS).getAjaxMessage();
        }*/
        if(!accountService.deleteMember(id)){
            return new JsonWrapper(true,Constants.ErrorType.DELETE_FAILED).getAjaxMessage();
        }
        return new JsonWrapper(true,Constants.ErrorType.SUCCESS).getAjaxMessage();
    }


    /**
     * 获取所有账户
     * 获得一个包含所有账户的json数组
     */
    @RequestMapping(value = "/getuser",method = RequestMethod.GET,produces = "application/json;charset=utf-8")
    public @ResponseBody String getUser(){
        List<GeneralMemberDto> list = accountService.getMemberList(0,Integer.MAX_VALUE, new ObjWrapper());
        return JSON.toJSONString(list);
    }

    /**
     * 获取一个用户的信息
     */
    @RequestMapping(value="/findUser/{id}",method = RequestMethod.GET,produces = "application/json;charset=utf-8")
    public @ResponseBody String getOneUser(@PathVariable int id){
        GeneralMemberDto member = accountService.findMember(id);
        if(member == null){
            return new JsonWrapper(false, Constants.ErrorType.NOT_FOUND).getAjaxMessage();
        }

        return JSON.toJSONString(member);
    }

    /**
     * 审核用户的兼职或二手等发布信息
     * @param id 要审核的id
     * @param type 审核的类型 0代表兼职 1代表二手
     */
    @RequestMapping(value = "/checkinfo",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public @ResponseBody String checkInfo(@RequestParam int id,@RequestParam int type){
        /*修改审核的状态,并给用户返回一条审核成功的消息*/
        JobPostDto job;
        SecondHandPostDto sh;
        if(type == 0){
            job = jobPostService.findJobPost(id);
            job.setVerified(Constants.VerifyStatus.VERIFIED.toString());
            if(!jobPostService.updateJobPost(id,job)){
                return new JsonWrapper(false,Constants.ErrorType.ERROR).getAjaxMessage();
            }
        }else if(type == 1){
            sh = shPostService.findPost(id);
            sh.setVerified(Constants.VerifyStatus.VERIFIED.toString());
            if(!shPostService.updatePost(id,sh)){
                return new JsonWrapper(false,Constants.ErrorType.ERROR).getAjaxMessage();
            }
        }else{
            return new JsonWrapper(false,Constants.ErrorType.FAILED).getAjaxMessage();
        }

        return new JsonWrapper(true, Constants.ErrorType.VERFIEDSUCCESS).getAjaxMessage();
    }

    /**
     * 删除用户的兼职或者二手等发布信息
     */
    @RequestMapping(value = "/deleteinfo",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public @ResponseBody String deleteInfo(@RequestParam int id,@RequestParam int type){
        /*修改审核的状态,并给用户返回一条审核失败的消息*/
        JobPostDto job;
        SecondHandPostDto sh;
        if(type == 0){
            job = jobPostService.findJobPost(id);
            job.setVerified(Constants.VerifyStatus.FAILED.toString());
            if(!jobPostService.updateJobPost(id,job)){
                return new JsonWrapper(false,Constants.ErrorType.PARAM_ILLEGAL).getAjaxMessage();
            }
        }else if(type == 1){
            sh = shPostService.findPost(id);
            sh.setVerified(Constants.VerifyStatus.FAILED.toString());
            if(!shPostService.updatePost(id,sh)){
                return new JsonWrapper(false,Constants.ErrorType.PARAM_ILLEGAL).getAjaxMessage();
            }
        }else{
            return new JsonWrapper(false,Constants.ErrorType.FAILED).getAjaxMessage();
        }

        return new JsonWrapper(true, Constants.ErrorType.VERFIEDFAILED).getAjaxMessage();

    }



    /**
     * 添加新闻 页面
     */
    @RequestMapping(value = "/addnews", method = RequestMethod.GET)
    public String addNews(Model model){
        model.addAttribute("isUpdate",false);
        return "pc/admin/addnews";
    }
    /**
     * 修改新闻页面
     */
    @RequestMapping(value = "/updatenews", method = RequestMethod.GET)
    public String updateNews(@PathVariable int id, Model model){
        NewsDto news = newsService.findNews(id);
        if(news == null){
            return "redirect:pc/admin/addnews";
        }
        model.addAttribute("news",news);
        model.addAttribute("isUpdate", true);
        return "pc/admin/addnews";
    }

    /**
     * 添加新闻
     */
    @RequestMapping(value = "/addnews",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public @ResponseBody String addNews(@Valid NewsDto newsDto,BindingResult result,HttpSession session){

        System.out.println(newsDto.getContent());
        Credential credential = CredentialUtils.getCredential(session);
        newsDto.setMemberId(credential.getId());
        newsService.addNews(newsDto);

        return new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();
    }
    /**
     * 删除新闻
     */
    @RequestMapping(value = "/deletenews",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public @ResponseBody String deleteNews(@RequestParam int newsId){

       if(! newsService.deleteNews(newsId)){
           return new JsonWrapper(false, Constants.ErrorType.FAILED).getAjaxMessage();
       }
        return new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();
    }
    /**
     * 修改新闻
     */
    @RequestMapping(value = "/updatenews",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public @ResponseBody String updateNews(NewsDto newsDto){


        if(!newsService.updateNews(newsDto.getId(), newsDto)){
            return new JsonWrapper(false, Constants.ErrorType.FAILED).getAjaxMessage();
        }
        return new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();
    }

    /**
     * 修改banner
     */
    @RequestMapping(value = "/updateBanner",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public @ResponseBody String updateBanner(){
        /*修改banner*/
//        bannerPicRepo.save()
        return new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();
    }

    /**
     * 添加二手/分类页面
     * @param type
     */
    @RequestMapping(value = "/addcategory", method = RequestMethod.GET)
    public String addCategoryPage(@RequestParam(defaultValue = "3") int type,Model model){
        model.addAttribute("type", type);
        return "pc/admin/addcategory";
    }

    /**
     * 添加二手\兼职分类
     * @param type  0为兼职分类,1为二手分类
     */
    @RequestMapping(value = "/addcategory",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public @ResponseBody String addCategory(@RequestParam int type,
                                            JobPostCategoryDto jobCate,
                                            SecondHandPostCategoryDto shCate){
        if(type==0){
            jobPostCateService.addCategory(jobCate);
        }else if(type==1){
            /*添加分类*/
            shPostCategoryService.addCategory(shCate);
        }else {
            return new JsonWrapper(true, Constants.ErrorType.PARAM_ILLEGAL).getAjaxMessage();
        }
        return new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();
    }

    /**
     * 兼职分类页面 Get
     * 页面左边显示所有分类，右边可以添加分类
     */
    @RequestMapping(value = "/jobcategory",method = RequestMethod.GET)
    public String jobCategory(){
        return "pc/admin/jobcategory";
    }

    /**
     * 二手分类页面 Get
     * 页面左边显示所有分类，右边可以添加分类
     */
    @RequestMapping(value = "/shcategory",method = RequestMethod.GET)
    public String shCategory(){
        return "pc/admin/shcategory";
    }


    /**
     * 兼职分类获取 ajax
     * @return
     */
    @RequestMapping(value = "/jobcatelist",method = RequestMethod.GET,produces = "application/json;charset=utf-8")
    public @ResponseBody String jobCateList(){
        int capcity = Constants.PAGE_CAPACITY;
        List<JobPostCategoryDto> list = jobPostCateService.getCategoryList(0,Integer.MAX_VALUE,new ObjWrapper());
        return JSON.toJSONString(list);
    }



    /**
     * 删除兼职分类
     * @param id 分类id
     * @param type  0为兼职分类,1为二手分类
     */
    @RequestMapping(value = "/deljobcate/{id}",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public @ResponseBody String delJobCate(@PathVariable int id,@RequestParam int type){
        if(type == 0){
            try {
                jobPostCateService.deleteCategory(id);
            } catch (CategoryNotEmptyException e) {
                return new JsonWrapper(false,e.getMessage()).getAjaxMessage();
            }
        }else if(type == 1){
            try {
                shPostCategoryService.deleteCategory(id);
            } catch (CascadeDeleteException e) {
                return new JsonWrapper(false,e.getMessage()).getAjaxMessage();
            }
        }else{
            return new JsonWrapper(true, Constants.ErrorType.PARAM_ILLEGAL).getAjaxMessage();
        }
        return new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();
    }

    /**
     * 更新兼职分类
     * @param  id 要更新的id
     * @param  jobPostCategoryDto 更新的数据
     * @return
     */
    @RequestMapping(value = "/updatejobcate",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public @ResponseBody String updateJobCate(@PathVariable int id,
                                              @Valid JobPostCategoryDto jobPostCategoryDto,
                                              BindingResult result){
        if(!jobPostCateService.updateCategory(id,jobPostCategoryDto)){
            return new JsonWrapper(true, Constants.ErrorType.ERROR).getAjaxMessage();
        }
        return new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();
    }


    /**
     * 二手分类获取 ajax
     * @return
     */
    @RequestMapping(value = "/shcatelist",method = RequestMethod.GET,produces = "application/json;charset=utf-8")
    public @ResponseBody String shCateList(){
        System.out.println("hello");
        int capcity = Constants.PAGE_CAPACITY;
        List<SecondHandPostCategoryDto> list =shPostCategoryService.getCategoryList(0,Integer.MAX_VALUE, new ObjWrapper());
        return JSON.toJSONString(list);
    }


    /**
     * 更新二手分类
     * @param  id 要更新的id
     * @param  secondHandPostCategoryDto 更新的数据
     * @return
     */
    @RequestMapping(value = "/updateshate",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public @ResponseBody String updateShCate(@PathVariable int id,
                                              @Valid SecondHandPostCategoryDto secondHandPostCategoryDto,
                                              BindingResult result){
        if(!shPostCategoryService.updateCategory(id,secondHandPostCategoryDto)){
            return new JsonWrapper(true, Constants.ErrorType.ERROR).getAjaxMessage();
        }
        return new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();
    }


}

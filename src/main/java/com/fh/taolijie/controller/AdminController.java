package com.fh.taolijie.controller;

import cn.fh.security.credential.Credential;
import cn.fh.security.utils.CredentialUtils;
import com.alibaba.fastjson.JSON;
import com.fh.taolijie.controller.dto.*;
import com.fh.taolijie.exception.checked.CascadeDeleteException;
import com.fh.taolijie.exception.checked.CategoryNotEmptyException;
import com.fh.taolijie.exception.checked.DuplicatedUsernameException;
import com.fh.taolijie.service.*;
import com.fh.taolijie.service.repository.BannerPicRepo;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.ObjWrapper;
import com.fh.taolijie.utils.json.JsonWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

/**
 * Created by wynfrith on 15-3-30.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AccountService accountService;
    @Autowired
    JobPostService jobPostService;
    @Autowired
    JobPostCateService jobPostCateService;
    @Autowired
    SHPostService shPostService;
    @Autowired
    SHPostCategoryService shPostCategoryService;
    @Autowired
    SchoolService schoolService;
    @Autowired
    NewsService newsService;
    @Autowired
    BannerPicRepo bannerPicRepo;
    /**
     * 用户列表页面
     * @return
     */
    @RequestMapping(value = "userlist",method = RequestMethod.GET)
    public String userlist(){
       return "";
    }


    /**
     * 添加或修改用户页面（填表单）
     */
    @RequestMapping(value = "adduser",method = RequestMethod.GET)
    public String addUser(){
        return "";
    }

    /**
     * 添加用户
     * @return
     */
    @RequestMapping(value = "/adduser",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public @ResponseBody String  addUser(GeneralMemberDto dto){
        if(null == accountService.findMember(dto.getUsername(),new GeneralMemberDto[0],false)){
            return new JsonWrapper(false, Constants.ErrorType.USERNAME_EXISTS).getAjaxMessage();
        }
        try {
            accountService.register(dto);
        } catch (DuplicatedUsernameException e) {
            return new JsonWrapper(true,Constants.ErrorType.FAILED).getAjaxMessage();
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
    @RequestMapping(value = "/deleteUser",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public @ResponseBody String deleterUser(HttpSession session,GeneralMemberDto member){
        Credential credential = CredentialUtils.getCredential(session);
        if(member.getUsername().equals(credential.getUsername())){
            return new JsonWrapper(true,Constants.ErrorType.CANT_DELETE_CURRENT_USER).getAjaxMessage();
        }
/*        try {
        } catch (UserNotExistsException e) {
            return new JsonWrapper(true,Constants.ErrorType.USERNAME_NOT_EXISTS).getAjaxMessage();
        }*/
        if(!accountService.deleteMember(member.getUsername())){
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
        List<GeneralMemberDto> list = accountService.getMemberList(0, 0, new ObjWrapper());
        return JSON.toJSONString(list);
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
     * 添加学校
     */
    @Deprecated
    @RequestMapping(value = "/addschool",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public @ResponseBody String addSchool(@Valid SchoolDto school,BindingResult result){
        if(result.hasErrors()){
            return new JsonWrapper(false,result.getAllErrors()).getAjaxMessage();
        }
        if(!schoolService.addSchool(school)){
            return new JsonWrapper(false, Constants.ErrorType.FAILED).getAjaxMessage();
        }


        return new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();
    }

    /**
     * 删除学校
     */
    @Deprecated
    @RequestMapping(value = "/deleteschool",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public @ResponseBody String deleteSchool(SchoolDto schoolDto){
        try {
            if(!schoolService.deleteSchool(schoolDto.getId())){
                return new JsonWrapper(false, Constants.ErrorType.FAILED).getAjaxMessage();
            }
        } catch (CascadeDeleteException e) {
            return new JsonWrapper(false, Constants.ErrorType.DELETE_FAILED).getAjaxMessage();
        }
        return new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();
    }

    /**
     * 修改学校
     */
    @Deprecated
    @RequestMapping(value = "/updateschool",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public @ResponseBody String updateSchool(SchoolDto schoolDto){
        /*需要传入学校的id放入schoolDto中*/
        if(!schoolService.updateSchoolInfo(schoolDto.getId(),schoolDto)){
            return new JsonWrapper(false, Constants.ErrorType.FAILED).getAjaxMessage();
        }
        return new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();
    }

    /**
     * 添加学院
     */
    @Deprecated
    @RequestMapping(value = "/addacademy",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public @ResponseBody String addAcademy(@RequestParam int schoolId,AcademyDto academyDto){
        schoolService.addAcademy(schoolId,academyDto);
        return new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();
    }

    /**
     * 删除学院
     */
    @Deprecated
    @RequestMapping(value = "/deleteacacdemy",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public @ResponseBody String deleteAcacdemy(@RequestParam int academyId){
        if(!schoolService.deleteAcademy(academyId)){
            return new JsonWrapper(false, Constants.ErrorType.FAILED).getAjaxMessage();
        }
        return new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();
    }


    /**
     * 修改学院
     */
    @RequestMapping(value = "/updateAcacdemy",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public @ResponseBody String updateAcacdemy(@Valid AcademyDto academyDto){
        if(!schoolService.updateAcademy(academyDto.getId(), academyDto)){
            return new JsonWrapper(false, Constants.ErrorType.FAILED).getAjaxMessage();
        }
        return new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();
    }



    /**
     * 添加新闻
     */
    @RequestMapping(value = "/addnews",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public @ResponseBody String addNews(@Valid NewsDto newsDto,BindingResult result){
        if(result.hasErrors()){
            return new JsonWrapper(false,result.getAllErrors()).getAjaxMessage();
        }

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
     * 添加二手\兼职分类
     * @param categoryType  0为兼职分类,1为二手分类
     */
    @RequestMapping(value = "/addctegory",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public @ResponseBody String addCategory(@RequestParam int categoryType,
                                            JobPostCategoryDto jobCate,
                                            SecondHandPostCategoryDto shCate){
        if(categoryType==0){
            jobPostCateService.addCategory(jobCate);
        }else if(categoryType==1){
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
    @RequestMapping(value = "/jobcate",method = RequestMethod.GET)
    public String jobCate(){
        return "";
    }

    /**
     * 兼职分类获取 ajax
     * @param page
     * @return
     */
    @RequestMapping(value = "/jobcatelist",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public @ResponseBody String jobCateList(@PathVariable int page){
        int capcity = Constants.PAGE_CAPACITY;
        List<JobPostCategoryDto> list = jobPostCateService.getCategoryList(page-1,capcity,new ObjWrapper());
        return JSON.toJSONString(list);
    }



    /**
     * 删除兼职分类
     * @param id 分类id
     * @param type  0为兼职分类,1为二手分类
     */
    @RequestMapping(value = "/deljobcate",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public @ResponseBody String delJobCate(@RequestParam int id,@RequestParam int type){
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
     * 二手分类页面 Get
     * 页面左边显示所有分类，右边可以添加分类
     */
    @RequestMapping(value = "/shcate",method = RequestMethod.GET)
    public String shCate(){
        return "";

    }

    /**
     * 二手分类获取 ajax
     * @param page
     * @return
     */
    @RequestMapping(value = "/shcatelist",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public @ResponseBody String shCateList(@PathVariable int page){
        int capcity = Constants.PAGE_CAPACITY;
        List<SecondHandPostCategoryDto> list =shPostCategoryService.getCategoryList(page - 1, capcity, new ObjWrapper());
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

    /**
     * 获取投诉列表
     * 此共功能暂未实现
     * @return
     */
    public @ResponseBody String complaintList(){
        return "";
    }



}

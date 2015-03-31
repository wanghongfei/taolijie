package com.fh.taolijie.controller;

import cn.fh.security.credential.Credential;
import cn.fh.security.utils.CredentialUtils;
import com.alibaba.fastjson.JSON;
import com.fh.taolijie.controller.dto.*;
import com.fh.taolijie.exception.checked.CascadeDeleteException;
import com.fh.taolijie.exception.checked.DuplicatedUsernameException;
import com.fh.taolijie.exception.checked.UserNotExistsException;
import com.fh.taolijie.service.*;
import com.fh.taolijie.service.repository.BannerPicRepo;
import com.fh.taolijie.service.repository.SHPostCategoryRepo;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.json.JsonWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
    JobPostCateService jobPostCateService;
    @Autowired
    SHPostCategoryService shPostCategoryService;
    @Autowired
    SchoolService schoolService;
    @Autowired
    NewsService newsService;
    @Autowired
    BannerPicRepo bannerPicRepo;

    /**
     * 测试admin的请求权限
     */
    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public @ResponseBody String test(){
        return "you are administrator!";
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
        accountService.assignRole(role.getId(), member.getUsername());

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
        try {
            if(!accountService.deleteMember(member.getUsername())){
                return new JsonWrapper(true,Constants.ErrorType.DELETE_FAILED).getAjaxMessage();
            }
        } catch (UserNotExistsException e) {
            return new JsonWrapper(true,Constants.ErrorType.USERNAME_NOT_EXISTS).getAjaxMessage();
        }
        return new JsonWrapper(true,Constants.ErrorType.SUCCESS).getAjaxMessage();
    }


    /**
     * 获取所有账户
     * 获得一个包含所有账户的json数组
     */
    @RequestMapping(value = "/getuser",method = RequestMethod.GET,produces = "application/json;charset=utf-8")
    public @ResponseBody String getUser(){
        List<GeneralMemberDto> list = accountService.getMemberList(0, 0);
        return JSON.toJSONString(list);
    }

    /**
     * 审核用户的兼职或二手等发布信息
     */
    @RequestMapping(value = "/checkinfo",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public @ResponseBody String checkInfo(){
        /*修改审核的状态,并给用户返回一条审核成功的消息*/
        return new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();
    }

    /**
     * 删除用户的兼职或者二手等发布信息
     */
    @RequestMapping(value = "/deleteinfo",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public @ResponseBody String deleteInfo(){
        /*修改审核的状态,并给用户返回一条审核失败的消息*/
        return new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();
    }

    /**
     * 添加二手\兼职分类
     * @param categoryType  1为兼职分类,2为二手分类
     */
    @RequestMapping(value = "/addctegory",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public @ResponseBody String addCategory(@RequestParam int categoryType,
                                            JobPostCategoryDto jobCate,
                                            SecondHandPostCategoryDto shCate){
        if(categoryType==1){
            jobPostCateService.addCategory(jobCate);
        }else if(categoryType==2){
            /*添加分类*/
           // shPostCategoryService.
        }else {
            return new JsonWrapper(true, Constants.ErrorType.PARAM_ILLEGAL).getAjaxMessage();
        }
        return new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();
    }

    /**
     * 添加学校
     */
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
    @RequestMapping(value = "/addacademy",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public @ResponseBody String addAcademy(@RequestParam int schoolId,AcademyDto academyDto){
        schoolService.addAcademy(schoolId,academyDto);
        return new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();
    }

    /**
     * 删除学院
     */
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
        if(!schoolService.updateAcademy(academyDto.getId(),academyDto)){
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

}

package com.fh.taolijie.controller.user;

import cn.fh.security.credential.Credential;
import cn.fh.security.utils.CredentialUtils;
import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.constant.OperationType;
import com.fh.taolijie.domain.*;
import com.fh.taolijie.exception.checked.InvalidNumberStringException;
import com.fh.taolijie.service.*;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.ControllerHelper;
import com.fh.taolijie.utils.ObjWrapper;
import com.fh.taolijie.utils.json.JsonWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by wynfrith on 15-6-11.
 * 用户简历控制器  仅限于 学生
 */
@Controller
@RequestMapping("user/resume")
public class UResumeController {


    @Autowired
    ResumeService resumeService;
    @Autowired
    AccountService accountService;
    @Autowired
    JobPostCateService jobPostCateService;
    @Autowired
    ApplicationIntendService intendService;
    @Autowired
    UserService userService;

    /**
     * 创建简历 get
     * 如果简历不存在,创建简历
     * 简历以存在,则跳转到简历预览界面,自己的简历简历界面可以修改和删除
     * @param session
     * @return
     */
    @RequestMapping(value = "/create" ,method = RequestMethod.GET)
    public String create(HttpSession session,Model model){
        int page = 1;
        int capacity = 9999;
        Credential credential = CredentialUtils.getCredential(session);
        List<ResumeModel> resumeDtoList = resumeService.getResumeList(credential.getId(), 0, 0);
        if(resumeDtoList.size()>0){
            return "redirect:/user/resume/view";
        }

        ListResult<JobPostCategoryModel> jobCateList = jobPostCateService.getCategoryList(page-1,capacity,new ObjWrapper());
        model.addAttribute("cates",jobCateList.getList());
        model.addAttribute("isChange",false);
        return "pc/user/myresume";
    }

    /**
     * 创建简历 ajax
     * @param resume 简历提交的表单
     * @param result 表单的错误信息
     * @param session
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    //region 创建简历
    public @ResponseBody
    String create(@Valid ResumeModel resume,
                  BindingResult result,
                  @RequestParam(required = false) Integer intend, // 留着这个只是为了兼容以前的接口，防止400
                  @RequestParam(required = false) String intendIds,
                  HttpSession session){
        if (null == intendIds || intendIds.isEmpty()) {
            return new JsonWrapper(false, "intendIds cannot be null").getAjaxMessage();
        }

        // 判断角色权限
        Credential credential = CredentialUtils.getCredential(session);
        MemberModel mem = null;

        String roleName = credential.getRoleList().iterator().next();
        if(roleName.equals(Constants.RoleType.EMPLOYER.toString())){
            return new JsonWrapper(false, Constants.ErrorType.PERMISSION_ERROR).getAjaxMessage();
        }

        // 判断是否已经有简历了
        // 如果已经有了则不允许创建新简历
        List<ResumeModel> rList = resumeService.getResumeList(credential.getId(), 0, 1);
        if(rList.size() !=0){
            return new JsonWrapper(false, Constants.ErrorType.ALREADY_EXISTS).getAjaxMessage();
        }

        if (result.hasErrors()) {
            return new JsonWrapper(false, result.getAllErrors()).getAjaxMessage();
        }

        mem = accountService.findMember(credential.getUsername(), false);

        /*创建信息*/
        resume.setMemberId(mem.getId());
        resume.setCreatedTime(new Date());
        resumeService.addResume(resume);
        // 加分
        userService.changeCredits(mem.getId(), OperationType.POST, mem.getCredits());


        // 设置求职意向
        try {
            List<Integer> idList = splitIntendIds(intendIds);
            setNewIntend(idList, resume.getId());

        } catch (InvalidNumberStringException e) {
            return new JsonWrapper(false, e.getMessage()).getAjaxMessage();
        }

        return new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();
    }
    //endregion


    /**
     * 查看我的简历 GET
     * @param session 用户的角色
     * @return
     */

    @RequestMapping(value = "/view",method = RequestMethod.GET)
    public String view(HttpSession session,Model model)  {
        Credential credential = CredentialUtils.getCredential(session);
        List<ResumeModel> list = resumeService.getResumeList(credential.getId(), 0, 0);

        /*因为简历只有一张,所以直接用遍历得到*/

        /*如果没有简历,跳转到创建简历页面*/
        if(list.size()==0)
        {
            return "redirect:/user/resume/create";
        }
        ResumeModel resume = list.get(0);

        MemberModel user = accountService.findMember(resume.getMemberId());


        //查询求职意向(new)
        String intendIdString = queryIntend(resume.getId());
        model.addAttribute("resumeIntendIds",intendIdString);

        //查询求职意向(old)
        List<ApplicationIntendModel> intends = resumeService.getIntendByResume(resume.getId());
        model.addAttribute("intendJobs", intends);

        model.addAttribute("resume",resume);
        model.addAttribute("isShow",false);
        model.addAttribute("postUser", user);
        return  "pc/resumedetail";

    }

    /**
     * 删除简历
     * 先判断有木有简历
     * @param session  用户的信息
     * @return
     */
    @RequestMapping(value = "del",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public @ResponseBody String del(HttpSession session,HttpServletResponse response) throws IOException {
        Credential credential = CredentialUtils.getCredential(session);
        MemberModel mem = accountService.findMember(credential.getUsername(), false);

        List<ResumeModel> list = resumeService.getResumeList(mem.getId(), 0, 0);
        /*因为简历只有一张,所以直接用遍历得到*/
        ResumeModel resume = null;
        for(ResumeModel r : list){
            resume = r;
        }
        /*如果没有简历,跳转到创建简历页面*/
        if(resume == null)
        {
            response.sendRedirect("/user/resume/create");
            return null;
        }
        List<ApplicationIntendModel> intend = intendService.getByResume(resume.getId());
        intend.forEach(i -> {
            intendService.deleteIntend(i);
        });

        if(!resumeService.deleteResume(resume.getId())){
            return new JsonWrapper(false, Constants.ErrorType.ERROR).getAjaxMessage();
        }
        return  new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();

    }


    /**
     * 收藏一条兼职
     */
    @RequestMapping(value = "/fav/{id}",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    //region 收藏一条兼职 fav
    public @ResponseBody String fav (@PathVariable int id,HttpSession session){
        Credential credential = CredentialUtils.getCredential(session);
        if(credential == null)
            return  new JsonWrapper(false, Constants.ErrorType.PERMISSION_ERROR).getAjaxMessage();
        if(resumeService.findResume(id) == null)
            return  new JsonWrapper(false, Constants.ErrorType.NOT_FOUND).getAjaxMessage();

        //遍历用户的收藏列表
        //如果没有这条兼职则添加,反之删除
        MemberModel mem = accountService.findMember(credential.getId());

        String status = "0";
        if(resumeService.isAlreadyFavorite(mem.getId(),id)){
            resumeService.unFavorite(mem.getId(), id);
            status = "1";
        }else{
            resumeService.favoriteResume(mem.getId(), id);
            status = "0";
        }
        return new JsonWrapper(true, "status",status).getAjaxMessage();
    }
    //endregion


    /**
     * 修改简历
     * 与创建简历同一个页面，只不过修改简历会填充好之前的字段
     * @param session 用户的信息
     * @param model
     * @return
     */
    @RequestMapping(value = "/change",method = RequestMethod.GET)
    public String changeJob(HttpSession session,Model model) {
        /**
         * 先得到用户的简历
         */
        Credential credential = CredentialUtils.getCredential(session);
        MemberModel mem = accountService.findMember(credential.getUsername(), false);

        List<ResumeModel> list = resumeService.getResumeList(mem.getId(), 0, 0);
        /*因为简历只有一张,所以直接用遍历得到*/
        ResumeModel resume = null;
        for(ResumeModel r : list){
            resume = r;
        }
        if(resume == null|| !ControllerHelper.isCurrentUser(credential, resume)){
            return "redirect:/user/resume/create";
        }

        //查询求职意向(old)
        List<ApplicationIntendModel> intends = resumeService.getIntendByResume(resume.getId());
        model.addAttribute("intendJobs", intends);

        //查询求职意向(new)
        String intendIdString = queryIntend(resume.getId());
        model.addAttribute("resumeIntendJobs",intendIdString);

        ListResult<JobPostCategoryModel> jobCateList = jobPostCateService.getCategoryList(0,9999,new ObjWrapper());
        model.addAttribute("cates",jobCateList.getList());
        model.addAttribute("resume",resume);
        model.addAttribute("isChange", true);
        return "pc/user/myresume";
    }

    /**
     * 修改简历 ajax
     * 之后考虑时候可以把创建简历和修改合并
     * @param resume
     * @param session  用户的信息
     * @return
     */
    @RequestMapping(value = "/change",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public @ResponseBody String change(@Valid ResumeModel resume,
                                       BindingResult result,
                                       @RequestParam(required = false) String intendIds,
                                       HttpSession session){
        if (null == intendIds || intendIds.isEmpty()) {
            return new JsonWrapper(false, "intendIds cannot be null").getAjaxMessage();
        }

        Credential credential = CredentialUtils.getCredential(session);

        List<ResumeModel> rList = resumeService.getResumeList(credential.getId(),0,1);
        if(rList.size()<1 ){
            return new JsonWrapper(false, Constants.ErrorType.NOT_FOUND).getAjaxMessage();
        }
        ResumeModel oldResume = rList.get(0);


        resume.setMemberId(oldResume.getMemberId());

        /*因为简历只有一张,所以直接用遍历得到*/


        if(resume == null|| !ControllerHelper.isCurrentUser(credential,resume)){
            return  new JsonWrapper(false, Constants.ErrorType.PERMISSION_ERROR).getAjaxMessage();
        }

        if(result.hasErrors()){
            return new JsonWrapper(false, result.getAllErrors()).getAjaxMessage();
        }

        if(!resumeService.updateResume(oldResume.getId(), resume)){
            return new JsonWrapper(false, Constants.ErrorType.ERROR).getAjaxMessage();
        }

        // 删除老意向
        intendService.deleteByResume(oldResume.getId());

        // 设置求职意向
        try {
            List<Integer> idList = splitIntendIds(intendIds);
            setNewIntend(idList, resume.getId());
        } catch (InvalidNumberStringException e) {
            // 此异常说明字符串非数字
            return new JsonWrapper(false, e.getMessage()).getAjaxMessage();
        }

        return new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();
    }

    /**
     * 刷新简历数据 ajax
     * 更新一下posttime
     * @param session  用户的信息
     */
    @RequestMapping(value = "refresh",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public @ResponseBody String refresh(HttpSession session){
        /**
         * 如果该job不是用户发送的,则错误json
         */
        Credential credential = CredentialUtils.getCredential(session);
        MemberModel mem = accountService.findMember(credential.getUsername(), false);

        List<ResumeModel> list = resumeService.getResumeList(mem.getId(), 0, 0);
        /*因为简历只有一张,所以直接用遍历得到*/
        ResumeModel resume = null;
        for(ResumeModel r : list){
            resume = r;
        }

        if(resume == null || !ControllerHelper.isCurrentUser(credential,resume)){
            return  new JsonWrapper(false, Constants.ErrorType.PERMISSION_ERROR).getAjaxMessage();
        }

        resume.setCreatedTime(new Date());
        if(!resumeService.updateResume(resume.getId(), resume)){
            return new JsonWrapper(false, Constants.ErrorType.ERROR).getAjaxMessage();
        }

        return new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();
    }

    /**
     * 公开,不公开简历
     * @param session
     * @return
     */
    @RequestMapping(value = "open",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public @ResponseBody String open(HttpSession session){
        Credential credential = CredentialUtils.getCredential(session);
        MemberModel mem = accountService.findMember(credential.getUsername(), false);

        List<ResumeModel> list = resumeService.getResumeList(mem.getId(), 0, 0);
        /*因为简历只有一张,所以直接用遍历得到*/
        ResumeModel resume = null;
        for(ResumeModel r : list){
            resume = r;
        }

        if(resume == null || !ControllerHelper.isCurrentUser(credential,resume)){
            return  new JsonWrapper(false, Constants.ErrorType.PERMISSION_ERROR).getAjaxMessage();
        }
        //切换公开状态
        int status = 0;
        if(resume.getAccessAuthority().equals(Constants.AccessAuthority.ALL.toString())){
            resume.setAccessAuthority(Constants.AccessAuthority.ME_ONLY.toString());
            status = 1; //只有我 1
        }else if(resume.getAccessAuthority().equals(Constants.AccessAuthority.ME_ONLY.toString())){
            resume.setAccessAuthority(Constants.AccessAuthority.ALL.toString());
            status = 2; //公开 2
        }

        return new JsonWrapper(true,"status",status+"").getAjaxMessage();

    }

    /**
     * 为简历设置新的意向
     * @param intendIds
     * @param resumeId
     */
    private void setNewIntend(List<Integer> intendIds, Integer resumeId) {
        ApplicationIntendModel intendModel = null;
        // 可能有多个求职意向
        for (Integer id : intendIds) {
            intendModel = new ApplicationIntendModel();
            intendModel.setResumeId(resumeId);
            intendModel.setJobPostCategoryId(id);

            intendService.addIntend(intendModel);
        }

    }

    /**
     * 将以分号分隔的id字符串转换成id List
     * @param intendIds
     * @return
     * @throws InvalidNumberStringException
     */
    private List<Integer> splitIntendIds(String intendIds) throws InvalidNumberStringException {
        String[] ids = intendIds.split(Constants.DELIMITER);
        if (null == ids || 0 == ids.length) {
            throw new InvalidNumberStringException("intendIds格式错误");
        }

        List<Integer> idList = new ArrayList<>(10);
        for (String idStr : ids) {
            try {
                Integer id = Integer.valueOf(idStr);
                idList.add(id);
            } catch (NumberFormatException ex) {
                throw new InvalidNumberStringException("intendIds参数非数字");
            }
        }

        return idList;
    }

    /**
     * 转换为id;id;id格式的字符串
     * @param resumeId
     * @return
     */
    private String queryIntend(Integer resumeId) {
        List<ApplicationIntendModel> intends = resumeService.getIntendByResume(resumeId);
        StringBuilder intendIdString = new StringBuilder();


        int LEN = intends.size();
        ApplicationIntendModel it = null;
        for (int ix = 0 ; ix < LEN ; ++ix) {
            it = intends.get(ix);
            intendIdString.append(it.getJobPostCategoryId());

            if (ix != LEN - 1) {
                intendIdString.append(Constants.DELIMITER);
            }
        }

        return intendIdString.toString();
    }

}

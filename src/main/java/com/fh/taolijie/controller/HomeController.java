package com.fh.taolijie.controller;

import cn.fh.security.credential.Credential;
import cn.fh.security.utils.CredentialUtils;
import com.alibaba.fastjson.JSON;
import com.fh.taolijie.controller.dto.*;
import com.fh.taolijie.exception.checked.DuplicatedUsernameException;
import com.fh.taolijie.exception.checked.PasswordIncorrectException;
import com.fh.taolijie.exception.checked.UserNotExistsException;
import com.fh.taolijie.service.*;
import com.fh.taolijie.utils.*;
import com.fh.taolijie.utils.json.JsonWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;


@Controller
public class HomeController {

    @Autowired
    NewsService newsService;
    @Autowired
    JobPostService jobPostService;
    @Autowired
    ResumeService resumeService;
    @Autowired
    SHPostService shPostService;
    @Autowired
    AccountService accountService;

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * 主页 get
	 */
	@RequestMapping(value = {"index","/"}, method = {RequestMethod.GET,RequestMethod.HEAD})
	public String home(HttpSession session,
                       Model model,
                       HttpServletRequest req) {
        System.out.println("session"+session);
        Credential credential =  CredentialUtils.getCredential(session);
        String username = "个人中心";
        List<NewsDto> list = null;

        if(credential!=null){
            username = credential.getUsername();
        }

        list = newsService.getNewsList(0,3, new ObjWrapper());

        System.out.println(username);
        model.addAttribute("username", username);
        model.addAttribute("newsList",list);


        return "mobile/index";
	}


    /**
     * 切换学校 ajax post
     * 学校信息放入session中，并保存cookie
     * @return
     */
    @Deprecated
    @RequestMapping(value = "changeschool",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public @ResponseBody String changeSchool(){
        return "";
    }


    /**
     * 兼职列表页面 get
     */
    @RequestMapping(value = {"list/job"},method = RequestMethod.GET)
    public String joblist(){
        return "mobile/joblist";
    }

    /**
     * 兼职详情页 get
     */
    @RequestMapping(value = "detail/job/{jobId}",method = RequestMethod.GET)
    public String jobdetail(@PathVariable("jobId") int jobId,Model model){
        JobPostDto jobPostDto = jobPostService.findJobPost(jobId);
        model.addAttribute("job",jobPostDto);
        return "mobile/jobdetail";
    }

    /**
     * 二手列表
     */
    @RequestMapping(value = "list/sh",method = RequestMethod.GET)
    public String shList(){
        return "mobile/shlist";
    }


    /**
     * 二手详情页
     */
    @RequestMapping(value = "detail/sh/{id}",method = RequestMethod.GET)
    public String shDetail(@PathVariable int id,Model model){
        SecondHandPostDto sh = shPostService.findPost(id);
        model.addAttribute("sh",sh);
         return "";
    }


    /**
     * 简历库列表
     */
    @RequestMapping(value = {"list/resume"},method = RequestMethod.GET)
    public String resumeList(){
        return "mobile/resumelist";
    }

    /**
     * 简历详情页
     *
     * 权限 : 对为 未注册用户 和 学生 联系方式不可见
     * @param id 简历的id号
     * @param model
     * @param session
     */
    @RequestMapping(value = "detail/resume/{id}",method = RequestMethod.GET)
    public String resumedetail(@PathVariable("id") int id,Model model,HttpSession session){
        boolean contactDisplay = false;
        RoleDto role = null;
        Credential credential = CredentialUtils.getCredential(session);
        if(credential.getUsername() != null){
            GeneralMemberDto member= accountService.findMember(credential.getUsername(),new GeneralMemberDto[0],true);
           for(String roleName:credential.getRoleList()){
               System.out.println(roleName);
               if(roleName.equals(Constants.RoleType.EMPLOYER.toString())||
                       roleName.equals(Constants.RoleType.ADMIN.toString())){
                   contactDisplay = true;
               }
           }
        }
        System.out.println("resume:"+id);
        ResumeDto resumeDto = resumeService.findResume(id);

        /*如果是用户或者为登陆,不会显示联系方式*/
        if(contactDisplay == false){
            resumeDto.setQq(null);
            resumeDto.setEmail(null);
            /*
             可以不需要显示的东西设为null
             前端
             */
        }

        model.addAttribute("resume",resumeDto);
        model.addAttribute("contactDisplay",contactDisplay);
        return "mobile/resume";
    }



    /**
     * 新闻列表
     */
    @RequestMapping(value = "list/news",method = RequestMethod.GET)
    public String newslist(){
        return "mobile/newslist";
    }

    /**
     * 新闻详细页面
     */
    @RequestMapping(value = "detail/news/{nid}",method = RequestMethod.GET)
    public String news(@PathVariable int nid,Model model) {
        NewsDto news = newsService.findNews(nid);
        model.addAttribute("news", news);
        return "mobile/news";
    }

    /**
     * 获取新闻列表请求 ajax Get
     */
    @RequestMapping(value = "list/news/{pageid}",method = RequestMethod.GET,produces = "application/json;charset=utf-8")
    public @ResponseBody String newslist(@PathVariable int pageid){
        int capcity = Constants.PAGE_CAPACITY;
        int start = (pageid-1)*capcity;
        List<NewsDto> list = newsService.getNewsList(start,capcity,new ObjWrapper());
        return JSON.toJSONString(list);
    }

    /**
     * 服务宗旨
     */
    @RequestMapping(value = {"tenet"},method = RequestMethod.GET)
    public String tenet(){
        return "mobile/tenet";
    }

    /**
     * 成员介绍
     */
    @RequestMapping(value = {"member"},method =RequestMethod.GET)
    public String member(){
        return "mobile/member";
    }

    /**
     * 加入我们
     */
    @RequestMapping(value = "join",method = RequestMethod.GET)
    public String joinus(){
        return "mobile/join";
    }



    /**
     * 注册页面
     */
    @RequestMapping(value = "/register",method = RequestMethod.GET)
    public String register(HttpServletRequest req){
        return ResponseUtils.determinePage(req,"user/register");
    }




    /**
     * ajax注册请求
     * 后续会添加邮箱验证或手机验证功能
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public @ResponseBody String register(@Valid RegisterDto mem,
                                         BindingResult result,
                                         HttpSession session,
                                         HttpServletResponse res){
        GeneralMemberDto newMember = null;
        GeneralMemberDto memDto = null;
        RoleDto role = null;
        int cookieExpireTime = 1*24*60*60;//1天

        /*
         * 注册需要的表单内容
         * 1.用户名
         * 2.密码
         * 3.邮箱
         */

        if(result.hasErrors()){
            return new JsonWrapper(false,result.getAllErrors()).getAjaxMessage();
        }
        /*用户名重复*/
        if(accountService.findMember(mem.getUsername(),new GeneralMemberDto[0],false)!=null){
            return new JsonWrapper(false, Constants.ErrorType.USERNAME_EXISTS).getAjaxMessage();
        }
        /*两次密码不一致*/
        if(!mem.getPassword().equals(mem.getRepassword())){
            return new JsonWrapper(false,Constants.ErrorType.REPASSWORD_ERROR).getAjaxMessage();
        }


        newMember = new GeneralMemberDto();
        if(mem.getIsEmployer()){
            newMember.setRoleIdList(Arrays.asList(
                    Constants.RoleType.EMPLOYER.ordinal()));

        }else{
            newMember.setRoleIdList(Arrays.asList(
                    Constants.RoleType.STUDENT.ordinal()));
        }
        newMember.setUsername(mem.getUsername());
        newMember.setPassword(mem.getPassword());
        newMember.setProfilePhotoPath(DefaultAvatarGenerator.getRandomAvatar());



        /*注册*/
        try {
            accountService.register(newMember);
        } catch (DuplicatedUsernameException e) {
            return new JsonWrapper(false,e.getMessage()).getAjaxMessage();
        }


        /*用户登陆*/

        memDto = accountService.findMember(mem.getUsername(),new GeneralMemberDto[0],true);
        Credential credential = new TaolijieCredential(memDto.getId(),memDto.getUsername());
        for(Integer rid:memDto.getRoleIdList()){
            role = accountService.findRole(rid);
            credential.addRole(role.getRolename());

            if(logger.isDebugEnabled()){
                logger.debug("RoleId:{}",rid);
                logger.debug("RoleName:{}",role.getRolename());
            }
        }
        CredentialUtils.createCredential(session,credential);

        return new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();


    }



    /**
     * 登陆页面
     * @param req
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login(HttpServletRequest req){
        return ResponseUtils.determinePage(req,"user/login");
    }

    /**
     * ajax登陆请求
     * @param mem
     * @param result
     * @param session
     * @param res
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public @ResponseBody String login(@Valid LoginDto mem,
                                      BindingResult result,
                                      HttpSession session,
                                      HttpServletResponse res){

        GeneralMemberDto memDto = null;
        RoleDto role = null;
        int cookieExpireTime = 1*24*60*60;//1天

        /*验证用户信息*/
        if(result.hasErrors()){
            return new JsonWrapper(false,result.getAllErrors()).getAjaxMessage();
        }

        /*验证用户是否存在*/
        try {
            accountService.login(mem.getUsername(),mem.getPassword());
        } catch (UserNotExistsException e) {
            return new JsonWrapper(false,e.getMessage()).getAjaxMessage();
        } catch (PasswordIncorrectException e) {
            return new JsonWrapper(false,e.getMessage()).getAjaxMessage();
        }

        /*获取用户信息和用户权限*/

        memDto = accountService.findMember(mem.getUsername(),new GeneralMemberDto[0],true);
        Credential credential = new TaolijieCredential(memDto.getId(),memDto.getUsername());
        for(Integer rid:memDto.getRoleIdList()){
            role = accountService.findRole(rid);
            credential.addRole(role.getRolename());

            if(logger.isDebugEnabled()){
                logger.debug("RoleId:{}",rid);
                logger.debug("RoleName:{}",role.getRolename());
            }
        }
        CredentialUtils.createCredential(session,credential);



        /*如果选择自动登陆,加入cookie*/
        if(mem.getRememberMe().equals("true")){
            Cookie usernameCookie = new Cookie("username", mem.getUsername());
            usernameCookie.setMaxAge(cookieExpireTime);
            Cookie passwordCookie = new Cookie("password", mem.getPassword());
            passwordCookie.setMaxAge(cookieExpireTime);
            res.addCookie(usernameCookie);
            res.addCookie(passwordCookie);
        }


        /*如果自动登陆为true ,返回cookie*/
        return new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();

    }



}

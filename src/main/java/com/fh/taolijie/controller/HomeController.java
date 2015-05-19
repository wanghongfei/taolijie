package com.fh.taolijie.controller;

import cn.fh.security.credential.Credential;
import cn.fh.security.utils.CredentialUtils;
import com.alibaba.fastjson.JSON;
import com.fh.taolijie.controller.dto.*;
import com.fh.taolijie.exception.checked.DuplicatedUsernameException;
import com.fh.taolijie.exception.checked.PasswordIncorrectException;
import com.fh.taolijie.exception.checked.UserInvalidException;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.Date;
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
    @Autowired
    ReviewService reviewService;

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * 主页 get
	 */
	@RequestMapping(value = {"index","/"}, method = {RequestMethod.GET,RequestMethod.HEAD})
	public String home(HttpSession session,
                       Model model,
                       HttpServletRequest req,ModelAndView modelAndView) {
        System.out.println("session"+session);
        Credential credential =  CredentialUtils.getCredential(session);

        if(credential!=null){

        }

        // 二手物品需要加入一个发布人
        List<NewsDto> news = newsService.getNewsList(0,3, new ObjWrapper());
        List<JobPostDto> jobs = jobPostService.getAllJobPostList(0, 6, new ObjWrapper());
        List<SecondHandPostDto> shs = shPostService.getAllPostList(0, 3, new ObjWrapper());

        model.addAttribute("news", news);
       model.addAttribute("jobs", jobs);
        model.addAttribute("shs", shs);
        model.addAttribute("mem",credential);

        return "pc/index";
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
    public String joblist(@RequestParam(defaultValue = "0") int page,
                          Model model){
        List<JobPostDto> jobs = jobPostService.getAllJobPostList(page,Constants.PAGE_CAPACITY,new ObjWrapper());
        model.addAttribute("jobs",jobs);
        return "pc/joblist";
    }

    /**
     * 查询一条兼职
     *
     */
    @RequestMapping(value = "item/job/{id}",method = RequestMethod.GET)
    public String jobItem(@PathVariable int id,HttpSession session,Model model){
        JobPostDto job = jobPostService.findJobPost(id);
        List<ReviewDto> reviews = reviewService.getReviewList(id,0,9999,new ObjWrapper());
        if(job == null){
            return "redirect:/404";
        }
        GeneralMemberDto poster = accountService.findMember(job.getMemberId());
        int roleId = poster.getRoleIdList().iterator().next();
        RoleDto role = accountService.findRole(roleId);

        model.addAttribute("job",job);
        model.addAttribute("reviews",reviews);
        model.addAttribute("poster",poster);
        model.addAttribute("posterRole",role);
        return "pc/jobdetail";
    }


    /**
     * 二手列表
     */
    @RequestMapping(value = "list/sh",method = RequestMethod.GET)
    public String shList(){
        return "pc/shlist";
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
     * 查询一条二手
     *
     */
    @RequestMapping(value = "item/sh/{id}",method = RequestMethod.GET)
    public String shItem(@PathVariable int id,HttpSession session,Model model){
        SecondHandPostDto sh = shPostService.findPost(id);
        if(sh == null){
            return "redirect:/404";
        }
        List<ReviewDto> reviews = reviewService.getReviewList(id,0,9999,new ObjWrapper());
        //对应的用户和用户类别
        GeneralMemberDto poster = accountService.findMember(sh.getMemberId());
        int roleId =poster.getRoleIdList().iterator().next();
        RoleDto role = accountService.findRole(roleId);

        model.addAttribute("sh",sh);
        model.addAttribute("reviews",reviews);
        model.addAttribute("poster",poster);
        model.addAttribute("posterRole",role);
        return "pc/shdetail";
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
        if(credential!= null){
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
        return "pc/resume";
    }



    /**
     * 新闻列表
     */
    @RequestMapping(value = "list/news",method = RequestMethod.GET)
    public String newslist(){
        return "pc/newslist";
    }

    /**
     * 新闻详细页面
     */
    @RequestMapping(value = "detail/news/{nid}",method = RequestMethod.GET)
    public String news(@PathVariable int nid,Model model) {
        NewsDto news = newsService.findNews(nid);
        model.addAttribute("news", news);
        return "pc/news";
    }

    /**
     * 获取新闻列表请求 ajax Get
     */
    @RequestMapping(value = "list/news/{pageid}",method = RequestMethod.GET,produces = "application/json;charset=utf-8")
    public @ResponseBody String newslist(@PathVariable int pageid){
        int capcity = Constants.PAGE_CAPACITY;
        List<NewsDto> list = newsService.getNewsList(pageid-1, capcity, new ObjWrapper());
        return JSON.toJSONString(list);
    }


    /**
     * 注册页面
     */
    @RequestMapping(value = "/register",method = RequestMethod.GET)
    public String register(HttpServletRequest req){
        return "pc/register";
    }


    /**
     * 注册用户
     * Method : POST AJAX
     * @param mem
     * @param result
     * @param session
     * @param res
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST,
            produces = "application/json;charset=utf-8")
    //region 注册用户 public @ResponseBody String register
    public @ResponseBody String register(@Valid RegisterDto mem,
                                         BindingResult result,
                                         HttpSession session,
                                         HttpServletResponse res){
        // TODO: 需要验证邮箱的唯一性
        //验证表单错误
        if(result.hasErrors()){
            return new JsonWrapper(false,result.getAllErrors()).getAjaxMessage();
        }
        //两次密码不一致
        if(!(mem.getPassword().equals(mem.getRepassword()))){
            return new JsonWrapper(false,Constants.ErrorType.REPASSWORD_ERROR).getAjaxMessage();
        }

        //注册不同权限的账户
        //1.根据权限的名称找到对应的权限id,如果没有找到,返回false
        //2.创建一个用户
        //3.为该账户添加权限, assignRole方法
        String roleName = mem.getIsEmployer() ?
                Constants.RoleType.EMPLOYER.toString() :
                Constants.RoleType.STUDENT.toString();
        int roleId = ControllerHelper.getRoleId(roleName,accountService);
        if(roleId == -1)
            return new JsonWrapper(false,Constants.ErrorType.ERROR).getAjaxMessage();

        GeneralMemberDto newMember = new GeneralMemberDto();
        newMember.setUsername(mem.getUsername());
        newMember.setPassword(CredentialUtils.sha(mem.getPassword()));
        newMember.setValid(true);
        newMember.setCreated_time(new Date());
        newMember.setRoleIdList(Arrays.asList(roleId));
        //注册并且检查用户名是否存在
        try {
            accountService.register(newMember);
        } catch (DuplicatedUsernameException e) {
            return new JsonWrapper(false,e.getMessage()).getAjaxMessage();
        }

        //accountService.assignRole(roleId,mem.getUsername());

        return new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();
    }
    //endregion








    /**
     * 登陆页面
     * @param req
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login(HttpServletRequest req){
        return "pc/login";
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
        } catch (UserInvalidException e) {
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
        //验证身份的session
        CredentialUtils.createCredential(session,credential);
        session.setAttribute("user", memDto);

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


    /**
     * 后台登陆页面
     */
    @RequestMapping(value = "login/admin",method = RequestMethod.GET)
    public String AdminLogin(){
        return "pc/admin/login";
    }
    /**
     * 管理员后台登陆
     */
    @RequestMapping(value = "login/admin",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public @ResponseBody String AdminLogin(@Valid LoginDto login,
                                      BindingResult result,
                                      HttpSession session,
                                      HttpServletResponse res){

        System.out.println("后台管理员登陆:::");
        System.out.println(login.getUsername());
        System.out.println(login.getRememberMe());

        GeneralMemberDto memDto = null;
        RoleDto role = null;
        int cookieExpireTime = 1*24*60*60;//1天

        if(result.hasErrors()){
            return new JsonWrapper(false,result.getAllErrors()).getAjaxMessage();
        }

        /*需要给管理员新增一个登陆方法*/
        try {
            accountService.login(login.getUsername(),login.getPassword());
        } catch (UserNotExistsException e) {
            return new JsonWrapper(false,e.getMessage()).getAjaxMessage();
        } catch (PasswordIncorrectException e) {
            return new JsonWrapper(false,e.getMessage()).getAjaxMessage();
        } catch (UserInvalidException e) {
            return new JsonWrapper(false,e.getMessage()).getAjaxMessage();
        }

        /*获取用户信息和用户权限*/

        memDto = accountService.findMember(login.getUsername(),new GeneralMemberDto[0],true);
        Credential credential = new TaolijieCredential(memDto.getId(),memDto.getUsername());
        for(Integer rid:memDto.getRoleIdList()){
            role = accountService.findRole(rid);
            /*如果不是管理员用户,返回登录失败信息*/
            if(!role.getRolename().equals(Constants.RoleType.ADMIN.toString())){
                return new JsonWrapper(false, Constants.ErrorType.USERNAME_NOT_EXISTS).getAjaxMessage();
            }else{
                credential.addRole(role.getRolename());
            }
        }

        CredentialUtils.createCredential(session,credential);
         /*如果选择自动登陆,加入cookie*/
        if(login.getRememberMe().equals("true")){
            Cookie usernameCookie = new Cookie("username", login.getUsername());
            usernameCookie.setMaxAge(cookieExpireTime);
            Cookie passwordCookie = new Cookie("password", login.getPassword());
            passwordCookie.setMaxAge(cookieExpireTime);
            res.addCookie(usernameCookie);
            res.addCookie(passwordCookie);
        }

        return new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();

    }


    @RequestMapping(value = "/404",method = RequestMethod.GET)
    public String error(){
        return "pc/404";
    }


}


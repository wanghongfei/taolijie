package com.fh.taolijie.controller.user;

import cn.fh.security.credential.Credential;
import cn.fh.security.utils.CredentialUtils;
import com.fh.taolijie.constant.ErrorCode;
import com.fh.taolijie.domain.ImageModel;
import com.fh.taolijie.domain.acc.MemberModel;
import com.fh.taolijie.dto.ChangePasswordDto;
import com.fh.taolijie.dto.ProfileDto;
import com.fh.taolijie.service.AccountService;
import com.fh.taolijie.service.ImageService;
import com.fh.taolijie.service.impl.Mail;
import com.fh.taolijie.utils.LogUtils;
import com.fh.taolijie.utils.UploadUtil;
import com.fh.taolijie.utils.json.JsonWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

/**
 * Created by wynfrith on 15-6-11.
 */
@RequestMapping(value = "/user")
@Controller
public class UIndexController {

    @Autowired
    AccountService accountService;
    @Autowired
    Mail mail;
    @Autowired
    ImageService imageService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(){
        return "";
    }


    /**
     * 个人资料GET
     */
    @RequestMapping(value = "/profile",method = RequestMethod.GET)
    public String user(HttpSession session,Model model){
        Credential credential = CredentialUtils.getCredential(session);
        if(credential==null){
            return "redirect:/login";
        }
        MemberModel memberDto = accountService.findMember(credential.getUsername(), false);
        model.addAttribute("user", memberDto);
//        long notifaicationNum = notificationService.getNotificationAmount(credential.getId(),false);
//        model.addAttribute("notificationNum",notifaicationNum);
        model.addAttribute("role",credential.getRoleList().iterator().next());

        return "pc/user/profile";

    }

    /**
     * 个人资料修改
     * @param session
     * @param profileDto
     * @return
     */
    //region 个人资料修改 profile
    @RequestMapping(value = "/profile", method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public @ResponseBody String profile(HttpSession session,ProfileDto profileDto){
        Credential credential = CredentialUtils.getCredential(session);
        if(credential==null){
            return "redirect:/login";
        }
        MemberModel user = accountService.findMember(credential.getId());
        user.setLastPostTime(new Date());
        user.setName(profileDto.getName());
        user.setGender(profileDto.getGender());

        accountService.updateMember(user);
        return  new JsonWrapper(true, ErrorCode.SUCCESS).getAjaxMessage();
    }
    //endregion



    /**
     * 安全信息(密码)
     * @param req
     * @return
     */
    @RequestMapping(value = "/setting/security", method = RequestMethod.GET)
    public String security(HttpServletRequest req){
        return "pc/user/security";
    }

    /**
     * 修改密码
     * @param session
     * @return
     */
    //region 修改密码 security
    @RequestMapping(value = "/setting/security",method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public @ResponseBody String security(@Valid ChangePasswordDto dto,
                                         BindingResult result,
                                         HttpSession session){

        if(result.hasErrors()){
            return new JsonWrapper(false,result.getAllErrors()).getAjaxMessage();
        }

        String username = CredentialUtils.getCredential(session).getUsername();

        MemberModel mem = accountService.findMember(username, false);


        if(!mem.getPassword().equals(CredentialUtils.sha(dto.getOldPassword()))){
            return new JsonWrapper(false, ErrorCode.BAD_PASSWORD).getAjaxMessage();
        }else if(!dto.getNewPassword().equals(dto.getRePassword())){
            return  new JsonWrapper(false, ErrorCode.RE_PASSWORD_ERROR).getAjaxMessage();
        }

        //加密
        mem.setPassword(CredentialUtils.sha(dto.getNewPassword()));

        System.out.println("更新后的密码"+mem.getPassword());
        System.out.println(CredentialUtils.sha(mem.getPassword()));
        accountService.updateMember(mem);


        return new JsonWrapper(true, ErrorCode.SUCCESS).getAjaxMessage();
    }
    //endregion


    /**
     * 意见反馈页面 get
     */
    @RequestMapping(value = "feedback",method = RequestMethod.GET)
    public String feedback(){
        return "/pc/user/feedback";
    }

    /**
     * 意见反馈页面 post ajax
     * @param session
     * @return
     */
    @RequestMapping(value = "feedback",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public @ResponseBody String feedback(@RequestParam String content,@RequestParam String  email, HttpSession session){
        Credential credential = CredentialUtils.getCredential(session);
//        mail.sendMailAsync("反馈人:  "+credential.getUsername()+"/n"
//                +"用户类型:  "+credential.getRoleList()+"/n"
//                +"反馈内容:  "+content+"/n"
//                +"用户邮箱:"+email+"/n"
//                +"时间:  "+new Date(), Constants.MailType.FEEDBACK,"wfc5582563@126.com");

        return new JsonWrapper(true, ErrorCode.SUCCESS).getAjaxMessage();
    }


    /**
     * 上传图片options方法验证
     */
    @RequestMapping(value = "changePhoto", method =RequestMethod.OPTIONS, produces = "application/json; charset=utf-8")
    public @ResponseBody
    String uploadOptions(HttpServletResponse response) {
        return "{code:0}";
    }

    /**
     * 用户上传图片
     * @return
     */
    @RequestMapping(value = "changePhoto", method =RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody String upload(@RequestParam MultipartFile file,
                                       HttpServletResponse response,
                                       HttpSession session) {
        Credential credential = CredentialUtils.getCredential(session);
        if(credential == null){
            return  new JsonWrapper(false, ErrorCode.PERMISSION_ERROR).getAjaxMessage();
        }
        MemberModel user = accountService.findMember(credential.getId());


        ImageModel imageDto = new ImageModel();
        Integer imageId = 0;

        try (InputStream inStream = file.getInputStream()) {
            // 读取byte数据
            byte[] imageByte = UploadUtil.writeToBuffer(inStream, file.getSize());
            if (null == imageByte) {
                // 不是图片文件
                inStream.close();
                return "invalid image!";
            }

            imageDto.setBinData(imageByte);

            String fileName = file.getOriginalFilename();
            String fileExt =UploadUtil.getExtensionName(fileName);

            imageDto.setFileName(fileName);
            imageDto.setExtension(fileExt);

            // 写入数据库
            imageId = imageService.saveImage(imageDto);
            user.setId(credential.getId());
            user.setProfilePhotoId(imageId);

            accountService.updateMember(user);

            session.setAttribute("user", user);

            System.out.println(imageId);
            // 返回成功信息

        } catch (IOException ex) {
            // 记录错误到日志
            LogUtils.getErrorLogger()
                    .error(ex.getMessage());

            // 返回上传失败错误信息
            return ex.getMessage();
        }

        return imageId+"";
    }






}

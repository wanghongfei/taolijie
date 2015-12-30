package com.fh.taolijie.controller.restful.acc;

import cn.fh.security.credential.Credential;
import cn.fh.security.utils.CredentialUtils;
import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.component.ResponseText;
import com.fh.taolijie.constant.ErrorCode;
import com.fh.taolijie.dto.CreditsInfo;
import com.fh.taolijie.domain.acc.MemberModel;
import com.fh.taolijie.exception.checked.GeneralCheckedException;
import com.fh.taolijie.service.AccountService;
import com.fh.taolijie.service.UserService;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.PageUtils;
import com.fh.taolijie.utils.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.security.krb5.internal.CredentialsUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by wanghongfei on 15-6-21.
 */
@RestController
@RequestMapping("/api/user")
public class RestUserController {
    @Autowired
    AccountService accService;
    @Autowired
    UserService userService;


    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", produces = Constants.Produce.JSON)
    public ResponseText getById(@PathVariable Integer id,
                                HttpServletRequest req) {

        Credential credential = SessionUtils.getCredential(req);

        // 管理员可查询任何用户
        MemberModel mem = null;
        if (SessionUtils.isAdmin(credential)) {
            mem = accService.findMember(id);
        } else {
            // 其它用户只能查询自己
            mem = accService.findMember(credential.getId());
        }

        if (null == mem) {
            return new ResponseText(ErrorCode.NOT_FOUND);
        }

        mem.setPassword(null);
        mem.setAppToken(null);
        mem.setResetPasswordToken(null);

        return new ResponseText(mem);
    }

    /**
     * 查询积分信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}/levelInfo", produces = Constants.Produce.JSON)
    public ResponseText queryCreditsInfo(@PathVariable Integer id) {
        CreditsInfo info = userService.queryCreditsInfo(id);

        return new ResponseText(info);
    }


    /**
     * 更新用户基本信息
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.PUT, produces = Constants.Produce.JSON)
    public ResponseText updateProfile(@RequestParam(required = false) String name,
                                      @RequestParam(required = false) String gender,
                                      @RequestParam(required = false) String photoPath,
                                      HttpServletRequest req) {

        Credential credential = SessionUtils.getCredential(req);

        MemberModel user = new MemberModel();
        user.setId(credential.getId());
        user.setName(name);
        user.setGender(gender);
        user.setProfilePhotoPath(photoPath);

        accService.updateMember(user);
        return ResponseText.getSuccessResponseText();


    }

    /**
     * 修改密码
     * @return
     */
    @RequestMapping(value = "/pwd", method = RequestMethod.PUT, produces = Constants.Produce.JSON)
    public ResponseText chPwd(@RequestParam String oldPwd,
                              @RequestParam String newPwd,
                              HttpServletRequest req) throws GeneralCheckedException {

        Integer memId = SessionUtils.getCredential(req).getId();

        // 参数验证
        int LEN = newPwd.length();
        if (LEN < 1 || LEN > 30) {
            return new ResponseText(ErrorCode.INVALID_PARAMETER);
        }

        // 修改密码
        accService.changePwd(memId, CredentialUtils.sha(oldPwd), CredentialUtils.sha(newPwd));

        return ResponseText.getSuccessResponseText();
    }

    /**
     * 完善企业信息
     */
    @RequestMapping(value = "/emp", method = RequestMethod.PUT, produces = Constants.Produce.JSON)
    public ResponseText completeCompanyInfo(@RequestParam String compName,
                                            @RequestParam String compAddr,
                                            @RequestParam String compDesp,
                                            HttpServletRequest req) {

        Credential credential = SessionUtils.getCredential(req);

        MemberModel user = new MemberModel();
        user.setId(credential.getId());
        user.setCompanyName(compName);
        user.setCompanyAddr(compAddr);
        user.setCompanyDesp(compDesp);

        accService.updateMember(user);
        return ResponseText.getSuccessResponseText();

    }


}

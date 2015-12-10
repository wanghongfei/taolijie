package com.fh.taolijie.controller.restful;

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
     * 根据用户名查询用户
     * @param username
     * @return
     */
    @RequestMapping(value = "/name", method = RequestMethod.POST, produces = Constants.Produce.JSON)
    public ResponseText getByUsername(@RequestParam String username) {
        MemberModel mem = accService.findMember(username, true);
        if (null == mem) {
            return new ResponseText(ErrorCode.NOT_FOUND);
        }
        mem.setPassword(null);
        mem.setAppToken(null);
        mem.setResetPasswordToken(null);

        return new ResponseText(mem);
    }

    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", produces = Constants.Produce.JSON)
    public ResponseText getById(@PathVariable Integer id) {
        MemberModel mem = accService.findMember(id);
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
     * 查询所有用户
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/list", produces = Constants.Produce.JSON)
    public ResponseText getList(@RequestParam(defaultValue = "0") int pageNumber,
                                @RequestParam(defaultValue = Constants.PAGE_CAPACITY + "") int pageSize) {

        pageNumber = PageUtils.getFirstResult(pageNumber, pageSize);

        ListResult<MemberModel> list = accService.getMemberList(pageNumber, pageSize);
        return new ResponseText(list);
    }

    /**
     * 更新用户信息
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
    public ResponseText updateProfile(@RequestParam String compName,
                                      @RequestParam String compAddr,
                                      HttpServletRequest req) {

        Credential credential = SessionUtils.getCredential(req);

        MemberModel user = new MemberModel();
        user.setId(credential.getId());
        user.setCompanyName(compName);
        user.setCompanyAddr(compAddr);

        accService.updateMember(user);
        return ResponseText.getSuccessResponseText();

    }


}

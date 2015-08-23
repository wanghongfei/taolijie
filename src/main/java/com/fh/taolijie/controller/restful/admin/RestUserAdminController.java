package com.fh.taolijie.controller.restful.admin;

import cn.fh.security.credential.Credential;
import cn.fh.security.utils.CredentialUtils;
import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.component.ResponseText;
import com.fh.taolijie.constant.ErrorCode;
import com.fh.taolijie.domain.MemberModel;
import com.fh.taolijie.service.AccountService;
import com.fh.taolijie.service.UserService;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by whf on 8/22/15.
 */
@RestController
@RequestMapping("/api/manage/user")
public class RestUserAdminController {
    @Autowired
    AccountService accService;

    @Autowired
    UserService userService;


    /**
     * 给后台提供的登陆接口
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = Constants.Produce.JSON)
    public ResponseText login(@RequestParam String username,
                              @RequestParam String password) {
        if (null == password) {
            return new ResponseText(ErrorCode.BAD_PASSWORD);
        }

        MemberModel mem = accService.findMember(username, true);
        if (null == mem) {
            return new ResponseText(ErrorCode.USER_NOT_EXIST);
        }

        String hashPwd = CredentialUtils.sha(password);
        if (false == hashPwd.equals(mem.getPassword())) {
            return new ResponseText(ErrorCode.BAD_PASSWORD);

        }

        return new ResponseText(mem);
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
     * 封号
     * @return
     */
    @RequestMapping(value = "/block/{id}", produces = Constants.Produce.JSON)
    public ResponseText getList(@PathVariable("id") Integer memId) {
        accService.invalidAccount(memId);

        return ResponseText.getSuccessResponseText();
    }
}

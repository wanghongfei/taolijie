package com.fh.taolijie.controller.restful.admin;

import cn.fh.security.utils.CredentialUtils;
import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.component.ResponseText;
import com.fh.taolijie.constant.ErrorCode;
import com.fh.taolijie.domain.acc.MemberModel;
import com.fh.taolijie.service.AccountService;
import com.fh.taolijie.service.UserService;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 只有管理员可调用，与账号相关操作接口
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
     * 重置用户密码
     * @return
     */
    @RequestMapping(value = "/{id}/pwd", method = RequestMethod.PUT, produces = Constants.Produce.JSON)
    public ResponseText resetPwd(@PathVariable("id") Integer userId,
                                 @RequestParam String newPwd) {
        // 参数验证
        int LEN = newPwd.length();
        if (LEN <= 0 || LEN >= 30) {
            return new ResponseText(ErrorCode.INVALID_PARAMETER);
        }


        // 无条件更新密码
        MemberModel cmd = new MemberModel();
        cmd.setId(userId);
        cmd.setPassword(CredentialUtils.sha(newPwd));
        int rows = accService.updateMember(cmd);

        return rows > 0 ? ResponseText.getSuccessResponseText() : new ResponseText(ErrorCode.NOT_FOUND);
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

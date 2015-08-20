package com.fh.taolijie.controller.restful;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.component.ResponseText;
import com.fh.taolijie.constant.ErrorCode;
import com.fh.taolijie.dto.CreditsInfo;
import com.fh.taolijie.domain.MemberModel;
import com.fh.taolijie.service.AccountService;
import com.fh.taolijie.service.UserService;
import com.fh.taolijie.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        //mem.setPassword(null);

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

    @RequestMapping(value = "/list", produces = Constants.Produce.JSON)
    public ResponseText getList(@RequestParam(defaultValue = "0") int pageNumber,
                                @RequestParam(defaultValue = Constants.PAGE_CAPACITY + "") int pageSize) {

        ListResult<MemberModel> list = accService.getMemberList(pageNumber, pageSize);
        return new ResponseText(list);
    }


}

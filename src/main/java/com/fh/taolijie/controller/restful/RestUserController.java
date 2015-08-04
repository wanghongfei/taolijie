package com.fh.taolijie.controller.restful;

import com.fh.taolijie.component.ResponseText;
import com.fh.taolijie.domain.MemberModel;
import com.fh.taolijie.service.AccountService;
import com.fh.taolijie.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by wanghongfei on 15-6-21.
 */
@RestController
@RequestMapping("/api/user")
public class RestUserController {
    @Autowired
    AccountService accService;

    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    @RequestMapping(value = "/name/{username}", method = RequestMethod.POST, produces = Constants.Produce.JSON)
    public ResponseText getByUsername(@PathVariable String username) {
        MemberModel mem = accService.findMember(username, true);
        if (null == mem) {
            return new ResponseText("not found");
        }

        mem.setPassword(null);

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

        return new ResponseText(mem);
    }

/*    @RequestMapping(value = "/list", produces = Constants.Produce.JSON)
    public ResponseText getList(@RequestParam(defaultValue = "0") int pageNumber,
                                @RequestParam(defaultValue = Constants.PAGE_CAPACITY + "") int pageSize) {

        List<MemberModel> list = accService.getMemberList(pageNumber, pageSize, null);
        return new ResponseText(list);
    }*/


}

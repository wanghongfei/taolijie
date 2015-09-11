package com.fh.taolijie.controller.admin;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.constant.ErrorCode;
import com.fh.taolijie.domain.JobPostModel;
import com.fh.taolijie.domain.SHPostModel;
import com.fh.taolijie.service.ShPostCategoryService;
import com.fh.taolijie.service.ShPostService;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.ObjWrapper;
import com.fh.taolijie.utils.json.JsonWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by wynfrith on 15-6-11.
 */
@Controller
@RequestMapping("manage/sh")
public class AShController {

    @Autowired
    ShPostCategoryService shPostCategoryService;
    @Autowired
    ShPostService shPostService;


    /**
     * 二手列表页面
     */
    @RequestMapping(value = "",method = RequestMethod.GET)
    public String jobs(Model model){
        int page = 0;
        int pageSize = Integer.MAX_VALUE;

        List<SHPostModel> secondHands=shPostService.getAllPostList(0,9999).getList();


        model.addAttribute("secondHands", secondHands);
        return "pc/admin/shs";
    }

    /**
     * 删除用户的二手
     */
    @RequestMapping(value = "/del/{id}",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public @ResponseBody
    String deleteInfo(@PathVariable int id){
        SHPostModel sh = shPostService.findPost(id);

        if(!shPostService.deletePost(id)){
            return new JsonWrapper(false, ErrorCode.FAILED).getAjaxMessage();
        }

        return new JsonWrapper(true, ErrorCode.SUCCESS).getAjaxMessage();

    }
}

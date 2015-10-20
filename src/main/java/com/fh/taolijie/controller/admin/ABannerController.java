package com.fh.taolijie.controller.admin;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.constant.ErrorCode;
import com.fh.taolijie.domain.BannerPicModel;
import com.fh.taolijie.service.BannerPicService;
import com.fh.taolijie.utils.json.JsonWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * Created by wynfrith on 15-6-11.
 */
@Controller
@RequestMapping("manage/banner")
public class ABannerController {

    @Autowired
    BannerPicService bannerPicService;

    /**
     * 添加一张banner
     * @param pic
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    @ResponseBody
    public String add(BannerPicModel pic){

        pic.setCreatedTime(new Date());
        bannerPicService.addBanner(pic);

        return new JsonWrapper(true, ErrorCode.SUCCESS).getAjaxMessage();
    }


    /**
     * 删除一条banner
     * @param id
     * @return
     */
    @RequestMapping(value = "/del",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    @ResponseBody
    public String del(@RequestParam int id){
        if(!bannerPicService.deleteBanner(id)){ //如果删除失败可能原因是id未找到
            return new JsonWrapper(false, ErrorCode.NOT_FOUND).getAjaxMessage();
        }

        return new JsonWrapper(true, ErrorCode.SUCCESS).getAjaxMessage();
    }

    /**
     * banner管理页面
     */
    @RequestMapping(value = "",method = RequestMethod.GET)
    public String manage(Model model){
        int page = 0;
        int pageSize = Integer.MAX_VALUE;

        ListResult<BannerPicModel> banners = bannerPicService.getBannerList(page, pageSize);

        model.addAttribute("bannerList",banners.getList());
        model.addAttribute("bannerCount", banners.getResultCount());

        return "pc/admin/banner";
    }
}

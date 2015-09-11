package com.fh.taolijie.controller.admin;

import com.fh.taolijie.domain.RecommendedPostModel;
import com.fh.taolijie.service.RecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by wyn on 9/9/15.
 * 审核
 */


@Controller
@RequestMapping("manage/recommend")
public class ARecommendController {
    @Autowired
    RecommendService rService;


    //兼职
    @RequestMapping(value="", method = RequestMethod.GET)
    public String showJob(Model model,
                          @RequestParam(value = "validation", defaultValue = "false") boolean validation){
        List<RecommendedPostModel> list = rService.findNewAppliedRequest(validation, 0, 9999).getList();
        model.addAttribute("list", list);
        model.addAttribute("validation", validation);
        return "pc/admin/recommend";
    }
}

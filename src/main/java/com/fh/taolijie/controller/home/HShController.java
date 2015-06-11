package com.fh.taolijie.controller.home;

import cn.fh.security.credential.Credential;
import cn.fh.security.utils.CredentialUtils;
import com.fh.taolijie.domain.*;
import com.fh.taolijie.service.AccountService;
import com.fh.taolijie.service.ReviewService;
import com.fh.taolijie.service.ShPostCategoryService;
import com.fh.taolijie.service.ShPostService;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.ObjWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wynfrith on 15-6-11.
 */
@Controller
public class HShController {

    @Autowired
    ShPostService shPostService;
    @Autowired
    ShPostCategoryService shPostCategoryService;
    @Autowired
    AccountService accountService;
    @Autowired
    ReviewService reviewService;

    /**
     * 二手列表
     * 默认每页12个
     * @param page
     * @param cate
     * @param pageSize
     * @param model
     * @return
     */
    @RequestMapping(value = "list/sh", method = RequestMethod.GET)
    public String shList(@RequestParam(defaultValue = "1") int page,
                         @RequestParam(defaultValue = "0") int cate,
                         @RequestParam(defaultValue = "12") int pageSize,
                         Model model) {

        ObjWrapper objWrapper = new ObjWrapper();
        List<SHPostModel> shs;
        if (cate > 0) {
            shs = shPostService.getAndFilter(cate, false, page-1, pageSize, objWrapper);
        } else {
            shs = shPostService.getAllPostList(page-1, pageSize, objWrapper);
        }

//        int totalPage = (Integer) objWrapper.getObj();

        model.addAttribute("shs", shs);
        model.addAttribute("page", page);
//        model.addAttribute("totalPage", totalPage);
        return "pc/shlist";
    }


    /**
     * 查询一条二手
     */
    @RequestMapping(value = "item/sh/{id}", method = RequestMethod.GET)
    public String shItem(@PathVariable int id, HttpSession session, Model model) {
        SHPostModel sh = shPostService.findPost(id);
        if (sh == null) {
            return "redirect:/404";
        }
        List<ReviewModel> reviews = reviewService.getReviewList(id, 0, 9999, new ObjWrapper());
        //对应的用户和用户类别
        MemberModel poster = accountService.findMember(sh.getMemberId());
        RoleModel role = poster.getRoleList().iterator().next();

        //二手的图片
        List<String> picIds = new ArrayList<>();
        if (sh.getPicturePath() != null) {
            for (String pid : sh.getPicturePath().split(";")) {
                picIds.add(pid);
            }
        }


        //收藏的显示状态
        boolean status = false; //不显示
        Credential credential = CredentialUtils.getCredential(session);
        if (credential == null)
            status = false;
        else { //查找有没有收藏
            status = shPostService.isPostFavorite(credential.getId(),id);
        }

        model.addAttribute("sh", sh);
        model.addAttribute("reviews", reviews);
        model.addAttribute("poster", poster);
        model.addAttribute("posterRole", role);
        model.addAttribute("pids", picIds);
        model.addAttribute("favStatus", status);

        return "pc/shdetail";
    }

    /**
     * 搜索一条兼职
     * @return
     */
    @RequestMapping(value = "/search/sh", method = RequestMethod.GET)
    public String search(@RequestParam SHPostModel shPostModel,
                         @RequestParam(defaultValue = "1") int page,
                         @RequestParam(defaultValue = Constants.PAGE_CAPACITY + "") int pageSize,
                         Model model) {


        //TODO:没有分页
        ObjWrapper objWrapper = new ObjWrapper();
        List<SHPostModel> list =shPostService.runSearch(shPostModel, objWrapper);
        int totalPage = (Integer) objWrapper.getObj();
        model.addAttribute("shs", list);
        model.addAttribute("page", page);
//            model.addAttribute("totalPage", totalPage);
        return "pc/joblist";
    }


}

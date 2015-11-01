package com.fh.taolijie.controller.home;

import cn.fh.security.credential.Credential;
import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.domain.acc.MemberModel;
import com.fh.taolijie.domain.ReviewModel;
import com.fh.taolijie.domain.acc.RoleModel;
import com.fh.taolijie.domain.sh.SHPostModel;
import com.fh.taolijie.service.AccountService;
import com.fh.taolijie.service.ReviewService;
import com.fh.taolijie.service.sh.ShPostCategoryService;
import com.fh.taolijie.service.sh.ShPostService;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.PageUtils;
import com.fh.taolijie.utils.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
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
    public String shList(@RequestParam(defaultValue = "0") int page,
                         @RequestParam(defaultValue = "0") int cate,
                         @RequestParam(defaultValue = "12") int pageSize,
                         Model model) {

        page = PageUtils.getFirstResult(page, pageSize);
        ListResult<SHPostModel> shs;
        if (cate > 0) {
            shs = shPostService.getAndFilter(cate, false, page, pageSize);
        } else {
            shs = shPostService.getAllPostList(page, pageSize);
        }

//        int totalPage = (Integer) objWrapper.getObj();

        int pageStatus = 1;
        if(shs.getList().size() == 0){
            pageStatus = 0;
        }else if(shs.getList().size() == pageSize){
            pageStatus = 2;
        }
        model.addAttribute("pageStatus",pageStatus);
        model.addAttribute("shs", shs.getList());
        model.addAttribute("page", page);
//        model.addAttribute("totalPage", totalPage);
        return "pc/shlist";
    }


    /**
     * 查询一条二手
     */
    @RequestMapping(value = "item/sh/{id}", method = RequestMethod.GET)
    public String shItem(@PathVariable int id,
                         HttpServletRequest req,
                         @RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber,
                         @RequestParam(value = "pageSize", defaultValue = Constants.PAGE_CAPACITY + "") Integer pageSize,
                         Model model) {
        SHPostModel sh = shPostService.findPostWithPV(id);
        if (sh == null) {
            return "redirect:/404";
        }

        // 查询二手的评论
        pageNumber = pageNumber.intValue() * pageSize.intValue();
        ReviewModel reviewCommand = new ReviewModel(pageNumber, pageSize);
        reviewCommand.setShPostId(id);
        ListResult<ReviewModel> reviewResult = reviewService.getReviewList(reviewCommand);
        List<ReviewModel> reviews = reviewResult.getList();
        long pageCount = reviewResult.getResultCount();

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
        //Credential credential = CredentialUtils.getCredential(session);
        Credential credential = SessionUtils.getCredential(req);
        if (credential == null)
            status = false;
        else { //查找有没有收藏
            status = shPostService.isPostFavorite(credential.getId(),id);
        }

        model.addAttribute("sh", sh);
        model.addAttribute("reviews", reviews);
        model.addAttribute("reviewCount", pageCount);
        model.addAttribute("poster", poster);
        model.addAttribute("posterRole", role);
        model.addAttribute("pids", picIds);
        model.addAttribute("favStatus", status);

        return "pc/shdetail";
    }




}

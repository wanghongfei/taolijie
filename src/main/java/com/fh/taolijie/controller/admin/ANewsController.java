package com.fh.taolijie.controller.admin;

import cn.fh.security.credential.Credential;
import cn.fh.security.utils.CredentialUtils;
import com.fh.taolijie.domain.NewsModel;
import com.fh.taolijie.service.NewsService;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.json.JsonWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Date;

/**
 * Created by wynfrith on 15-6-11.
 */
@Controller
@RequestMapping("/manage/news")
public class ANewsController {
    @Autowired
    NewsService newsService;

    /**
     * 添加或修改新闻
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addNews(NewsModel dto, Model model){
        boolean isChange = false;
        if(dto != null){
            isChange = true;
        }
        model.addAttribute("isChange",isChange);
        model.addAttribute("news",dto);
        return "pc/admin/addnews";
    }

    /**
     * 添加新闻
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public @ResponseBody
    String addNews(@Valid NewsModel newsDto,BindingResult result,HttpSession session){

        Credential credential = CredentialUtils.getCredential(session);
        newsDto.setMemberId(credential.getId());
        newsDto.setTime(new Date());
        newsService.addNews(newsDto);

        return new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();
    }
    /**
     * 删除新闻
     */
    @RequestMapping(value = "/del",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public @ResponseBody String deleteNews(@RequestParam int newsId){

        if(! newsService.deleteNews(newsId)){
            return new JsonWrapper(false, Constants.ErrorType.FAILED).getAjaxMessage();
        }
        return new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();
    }
    /**
     * 修改新闻
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public @ResponseBody String updateNews(NewsModel newsDto){


        if(!newsService.updateNews(newsDto.getId(), newsDto)){
            return new JsonWrapper(false, Constants.ErrorType.FAILED).getAjaxMessage();
        }
        return new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();
    }

}

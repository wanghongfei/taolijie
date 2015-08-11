package com.fh.taolijie.controller.admin;

import cn.fh.security.credential.Credential;
import cn.fh.security.utils.CredentialUtils;
import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.domain.NewsModel;
import com.fh.taolijie.service.NewsService;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.ObjWrapper;
import com.fh.taolijie.utils.json.JsonWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * Created by wynfrith on 15-6-11.
 */
@Controller
@RequestMapping("/manage/news")
public class ANewsController {
    @Autowired
    NewsService newsService;

    /**
     * 新闻管理
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String news(Model model){
        int page = 0;
        int pageSize = Integer.MAX_VALUE;

        ListResult<NewsModel> news = newsService.getNewsList(page, pageSize);
        model.addAttribute("news",news.getList());

        return "pc/admin/news";
    }


    /**
     * 添加新闻
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addNews(Model model){
        model.addAttribute("isEdit",false);
        return "pc/admin/addnews";
    }
    /**
     * 修改新闻
     */
    @RequestMapping(value="/edit/{id}", method = RequestMethod.GET)
    public String editNews(@PathVariable int id,Model model){
        NewsModel aNews = newsService.findNews(id);
        model.addAttribute("isEdit",true);
        model.addAttribute("aNews",aNews);
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

        return new JsonWrapper(true, Constants.ErrorType.ADD_SUCCESS).getAjaxMessage();
    }

    /**
     * 修改新闻
     */
    @RequestMapping(value = "/edit/{id}",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public @ResponseBody String updateNews(@PathVariable int id,NewsModel newsDto){
        if(!newsService.updateNews(newsDto)){
            return new JsonWrapper(false, Constants.ErrorType.FAILED).getAjaxMessage();
        }
        return new JsonWrapper(true, Constants.ErrorType.EDIT_SUCCESS).getAjaxMessage();
    }

    /**
     * 删除新闻
     */
    @RequestMapping(value = "/del/{id}",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public @ResponseBody String deleteNews(@PathVariable int id){

        if(! newsService.deleteNews(id)){
            return new JsonWrapper(false, Constants.ErrorType.FAILED).getAjaxMessage();
        }
        return new JsonWrapper(true, Constants.ErrorType.DEL_SUCCESS).getAjaxMessage();
    }


}

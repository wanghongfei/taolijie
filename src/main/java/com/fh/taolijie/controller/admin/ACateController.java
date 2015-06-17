package com.fh.taolijie.controller.admin;

import com.fh.taolijie.domain.JobPostCategoryModel;
import com.fh.taolijie.domain.SHPostCategoryModel;
import com.fh.taolijie.dto.CategoryDto;
import com.fh.taolijie.exception.checked.CascadeDeleteException;
import com.fh.taolijie.exception.checked.CategoryNotEmptyException;
import com.fh.taolijie.service.JobPostCateService;
import com.fh.taolijie.service.JobPostService;
import com.fh.taolijie.service.ShPostCategoryService;
import com.fh.taolijie.service.ShPostService;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.ObjWrapper;
import com.fh.taolijie.utils.json.JsonWrapper;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by wynfrith on 15-6-11.
 */
@Controller
@RequestMapping("manage/cate")
public class ACateController {
    @Autowired
    JobPostService jobPostService;
    @Autowired
    JobPostCateService jobPostCateService;
    @Autowired
    ShPostService shPostService;
    @Autowired
    ShPostCategoryService shPostCategoryService;


    /**
     * 添加二手/兼职分类页面
     * @param type
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addCategoryPage(@RequestParam(defaultValue = "3") int type,
                                  CategoryDto dto, //二手或兼职分类dto
                                  Model model){
        boolean isChange = false;
        if(dto != null){
            isChange = true;
        }
        model.addAttribute("type", type);
        model.addAttribute("cate",dto);
        model.addAttribute("isChange",isChange);
        return "pc/admin/addcategory";
    }

    /**
     * 添加二手\兼职分类
     * @param type  0为兼职分类,1为二手分类
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public @ResponseBody
    String addCategory(@RequestParam int type,
                       JobPostCategoryModel jobCate,
                       SHPostCategoryModel shCate){
        if(type==0){
            jobPostCateService.addCategory(jobCate);
        }else if(type==1){
            /*添加分类*/
            shPostCategoryService.addCategory(shCate);
        }else {
            return new JsonWrapper(true, Constants.ErrorType.PARAM_ILLEGAL).getAjaxMessage();
        }
        return new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();
    }

    /**
     * 管理兼职分类
     */
    @RequestMapping(value = "/job",method = RequestMethod.GET)
    public String jobCategory(Model model){
        int page = 1;
        int capacity = 9999;
        List<JobPostCategoryModel> list = jobPostCateService.getCategoryList(page-1, capacity, new ObjWrapper());
        model.addAttribute("jobCates",list);
        return "pc/admin/jobcategory";
    }

    /**
     * 二手分类页面 Get
     * 页面左边显示所有分类，右边可以添加分类
     */
    @RequestMapping(value = "/sh",method = RequestMethod.GET)
    public String shCategory(Model model){
        int page = 1;
        int capacity = 9999;
        List<SHPostCategoryModel> list =shPostCategoryService.getCategoryList(page-1, capacity, new ObjWrapper());
        model.addAttribute("shCates",list);
        return "pc/admin/shcategory";
    }

    /**
     * 删除兼职分类
     */



    /**
     * 删除分类
     * @param id 分类id
     * @param type  0为兼职分类,1为二手分类
     */
    @RequestMapping(value = "/del/{id}",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public @ResponseBody String delCate(@PathVariable int id,@RequestParam int type){
        if(type == 0){
            try {
                jobPostCateService.deleteCategory(id);
            } catch (CategoryNotEmptyException e) {
                return new JsonWrapper(false,e.getMessage()).getAjaxMessage();
            }
        }else if(type == 1){
            try {
                shPostCategoryService.deleteCategory(id);
            } catch (CascadeDeleteException e) {
                return new JsonWrapper(false,e.getMessage()).getAjaxMessage();
            }
        }else{
            return new JsonWrapper(true, Constants.ErrorType.PARAM_ILLEGAL).getAjaxMessage();
        }
        return new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();
    }


    /**
     * 更新分类
     * @param  id 要更新的id
     * @param  dto 更新的数据
     * @return
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public @ResponseBody String updateJobCate(@PathVariable int id,
                                              @Valid JobPostCategoryModel dto,
                                              BindingResult result){
        if(!jobPostCateService.updateCategory(id,dto)){
            return new JsonWrapper(true, Constants.ErrorType.ERROR).getAjaxMessage();
        }
        return new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();
    }




}

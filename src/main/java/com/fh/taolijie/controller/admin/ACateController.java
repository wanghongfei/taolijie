package com.fh.taolijie.controller.admin;

import com.fh.taolijie.component.ListResult;
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
    @RequestMapping(value = "{type}/add", method = RequestMethod.GET)
    public String addCategoryPage(@PathVariable String type,
                                  CategoryDto dto, //二手或兼职分类dto
                                  Model model){
        boolean isEdit = false;
        model.addAttribute("type", type);
        model.addAttribute("isEdit",isEdit);
        return "pc/admin/addcategory";
    }

    /**
     * 编辑兼职分类页面
     */
    @RequestMapping(value = "{type}/{id}/edit", method = RequestMethod.GET)
    public String editJob(@PathVariable int id,
                       @PathVariable String type,
                       Model model){
        if(type.equals("job")){
            //JobPostCategoryModel cate= jobPostCateService.findCategory(id);
            JobPostCategoryModel cate= jobPostCateService.findById(id);
            model.addAttribute("cate",cate);
            model.addAttribute("type", type);
            model.addAttribute("isEdit",true);
        }else if(type.equals("sh")){
            SHPostCategoryModel cate = shPostCategoryService.findCategory(id);
            model.addAttribute("cate",cate);
            model.addAttribute("type", type);
            model.addAttribute("isEdit",true);
        }
        return "pc/admin/addcategory";
    }





    /**
     * 添加二手\兼职分类
     * @param type  0为兼职分类,1为二手分类
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public @ResponseBody
    String addCategory(@RequestParam String type,
                       @RequestParam boolean isEdit,
                       @RequestParam(required = false) int id,
                       CategoryDto cate){
        if(type.equals("job")){
            JobPostCategoryModel jobCate = new JobPostCategoryModel();
            if(id!=0)
                jobCate.setId(id);
            jobCate.setName(cate.getName());
            jobCate.setLevel(cate.getLevel());
            jobCate.setThemeColor(cate.getThemeColor());
            jobCate.setMemo(cate.getMemo());
            if(isEdit)
                //jobPostCateService.updateCategory(jobCate.getId(),jobCate);
                jobPostCateService.updateByIdSelective(jobCate);
            else
                jobPostCateService.addCategory(jobCate);
        }else if(type.equals("sh")){
            SHPostCategoryModel shCate = new SHPostCategoryModel();
            if(id!=0)
                shCate.setId(id) ;
            shCate.setName(cate.getName());
            shCate.setLevel(cate.getLevel());
            shCate.setThemeColor(cate.getThemeColor());
            shCate.setMemo(cate.getMemo());
            if(isEdit)
                shPostCategoryService.updateCategory(shCate.getId(), shCate);
            else
                shPostCategoryService.addCategory(shCate);
        }else {
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
        dto.setId(id);
        if(!jobPostCateService.updateByIdSelective(dto)){
            return new JsonWrapper(true, Constants.ErrorType.ERROR).getAjaxMessage();
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
        ListResult<JobPostCategoryModel> list = jobPostCateService.getCategoryList(page-1, capacity, new ObjWrapper());
        model.addAttribute("jobCates",list.getList());
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
        ListResult<SHPostCategoryModel> list =shPostCategoryService.getCategoryList(page-1, capacity, new ObjWrapper());
        model.addAttribute("shCates",list.getList());
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
    @RequestMapping(value = "{type}/del/{id}",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public @ResponseBody String delCate(@PathVariable int id,@PathVariable String type){
        if(type.equals("job")){
            try {
                jobPostCateService.deleteCategory(id);
            } catch (CategoryNotEmptyException e) {
                return new JsonWrapper(false,e.getMessage()).getAjaxMessage();
            }
        }else if(type.equals("sh")){
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









}

package com.fh.taolijie.controller.admin;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.constant.ErrorCode;
import com.fh.taolijie.domain.job.JobPostCategoryModel;
import com.fh.taolijie.domain.sh.SHPostCategoryModel;
import com.fh.taolijie.dto.CategoryDto;
import com.fh.taolijie.exception.checked.CascadeDeleteException;
import com.fh.taolijie.exception.checked.CategoryNotEmptyException;
import com.fh.taolijie.service.job.JobPostCateService;
import com.fh.taolijie.service.job.JobPostService;
import com.fh.taolijie.service.sh.ShPostCategoryService;
import com.fh.taolijie.service.sh.ShPostService;
import com.fh.taolijie.utils.json.JsonWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
            JobPostCategoryModel cate= jobPostCateService.findCategory(id);
            model.addAttribute("cate",cate);
            model.addAttribute("type", type);
            model.addAttribute("isEdit",true);
        }else if(type.equals("sh")){
            SHPostCategoryModel cate = shPostCategoryService.findById(id);
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
        if (type.equals("job")) {
            JobPostCategoryModel jobCate = new JobPostCategoryModel();
            if (id != 0) {
                jobCate.setId(id);
            }

            jobCate.setName(cate.getName());
            jobCate.setLevel(cate.getLevel());
            jobCate.setThemeColor(cate.getThemeColor());
            jobCate.setMemo(cate.getMemo());

            if (isEdit) {
                jobPostCateService.updateCategory(jobCate.getId(), jobCate);
            } else {
                jobPostCateService.addCategory(jobCate);
            }

        } else if (type.equals("sh")) {
            SHPostCategoryModel shCate = new SHPostCategoryModel();
            if (id!=0) {
                shCate.setId(id) ;
            }
            shCate.setName(cate.getName());
            shCate.setLevel(cate.getLevel());
            shCate.setThemeColor(cate.getThemeColor());
            shCate.setMemo(cate.getMemo());

            if(isEdit) {
                shPostCategoryService.updateCategory(shCate);
            } else {
                shPostCategoryService.addCategory(shCate);
            }

        } else {
            return new JsonWrapper(true, ErrorCode.INVALID_PARAMETER).getAjaxMessage();
        }

        return new JsonWrapper(true, ErrorCode.SUCCESS).getAjaxMessage();
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
        if(!jobPostCateService.updateCategory(id, dto)){
            return new JsonWrapper(true, ErrorCode.FAILED).getAjaxMessage();
        }
        return new JsonWrapper(true, ErrorCode.SUCCESS).getAjaxMessage();
    }


    /**
     * 管理兼职分类
     */
    @RequestMapping(value = "/job",method = RequestMethod.GET)
    public String jobCategory(Model model){
        int page = 0;
        int capacity = Integer.MAX_VALUE;

        ListResult<JobPostCategoryModel> list = jobPostCateService.getCategoryList(page, capacity);
        model.addAttribute("jobCates",list.getList());

        return "pc/admin/jobcategory";
    }

    /**
     * 二手分类页面 Get
     * 页面左边显示所有分类，右边可以添加分类
     */
    @RequestMapping(value = "/sh",method = RequestMethod.GET)
    public String shCategory(Model model){
        int page = 0;
        int capacity = Integer.MAX_VALUE;

        ListResult<SHPostCategoryModel> list = shPostCategoryService.getCategoryList(page, capacity);
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
                return new JsonWrapper(false, ErrorCode.CATE_NOT_EMPTY)
                        .getAjaxMessage();
            }

        } else if(type.equals("sh")){
            try {
                shPostCategoryService.deleteCategory(id);
            } catch (CascadeDeleteException e) {
                return new JsonWrapper(false, ErrorCode.CATE_NOT_EMPTY).getAjaxMessage();
            }

        } else {
            return new JsonWrapper(true, ErrorCode.INVALID_PARAMETER).getAjaxMessage();
        }

        return new JsonWrapper(true, ErrorCode.SUCCESS).getAjaxMessage();
    }

}

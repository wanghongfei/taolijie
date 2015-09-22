package com.fh.taolijie.controller.restful;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.component.ResponseText;
import com.fh.taolijie.constant.ErrorCode;
import com.fh.taolijie.domain.JobPostCategoryModel;
import com.fh.taolijie.domain.JobPostModel;
import com.fh.taolijie.service.job.JobPostCateService;
import com.fh.taolijie.service.job.JobPostService;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.PageUtils;
import com.fh.taolijie.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by wanghongfei on 15-6-19.
 */
@RestController
@RequestMapping(value = "/api/job")
public class RestJobController {
    @Autowired
    JobPostService jobService;
    @Autowired
    JobPostCateService cateService;

    /**
     * 得到所有兼职信息, 最新的在前
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = Constants.Produce.JSON)
    public ResponseText getAllPost(@RequestParam(defaultValue = "0") int pageNumber,
                                    @RequestParam(defaultValue = Constants.PAGE_CAPACITY + "") int pageSize) {
        pageNumber = PageUtils.getFirstResult(pageNumber, pageSize);
        ListResult<JobPostModel> jobList = jobService.getAllJobPostList(pageNumber, pageSize);

        return new ResponseText(jobList);
    }

    /**
     * 过虑查询
     * @return
     */
    @RequestMapping(value = "/filter", method = RequestMethod.GET, produces = Constants.Produce.JSON)
    public ResponseText filter(JobPostModel model) {
        model.setPageNumber(PageUtils.getFirstResult(model.getPageNumber(), model.getPageSize()));
        ListResult<JobPostModel> list = jobService.findByExample(model);

        return new ResponseText(list);
    }

    /**
     * 得到指定用户发布的兼职信息
     * @param memberId
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/user/{memberId}", method = RequestMethod.GET, produces = Constants.Produce.JSON)
    public ResponseText getPostByMember(@PathVariable("memberId") Integer memberId,
                                  @RequestParam(defaultValue = "0") int pageNumber,
                                  @RequestParam(defaultValue = Constants.PAGE_CAPACITY + "") int pageSize) {
        if (null == memberId) {
            return new ResponseText("memberId cannot be null");
        }

        pageNumber = PageUtils.getFirstResult(pageNumber, pageSize);
        ListResult<JobPostModel> list = jobService.getJobPostListByMember(memberId, pageNumber, pageSize);

        return new ResponseText(list);
    }

    /**
     * 根据分类查询post
     * @param categoryId
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/category/{categoryId}", method = RequestMethod.GET, produces = Constants.Produce.JSON)
    public ResponseText getPostByCategory(@PathVariable("categoryId") Integer categoryId,
                                    @RequestParam(defaultValue = "0") int pageNumber,
                                    @RequestParam(defaultValue = Constants.PAGE_CAPACITY + "") int pageSize) {
        pageNumber = PageUtils.getFirstResult(pageNumber, pageSize);
        ListResult<JobPostModel> list = jobService.getJobPostListByCategory(categoryId, pageNumber, pageSize);

        return new ResponseText(list);
    }


    @RequestMapping(value = "/list", method = RequestMethod.POST, produces = Constants.Produce.JSON)
    public ResponseText getPostInBatch(@RequestParam(value = "ids") String idString) {
        List<Integer> idList = StringUtils.toIdList(idString);
        if (null == idList) {
            return new ResponseText(ErrorCode.INVALID_PARAMETER);
        }

        ListResult<JobPostModel> postList = jobService.getPostListByIds(idList.toArray(new Integer[0]));
        return new ResponseText(postList);
    }

    /**
     * 搜索
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.GET, produces = Constants.Produce.JSON)
    public ResponseText searchPost(JobPostModel model,
                                   @RequestParam(defaultValue = "0") int pageNumber,
                                   @RequestParam(defaultValue = Constants.PAGE_CAPACITY + "") int pageSize) {
        pageNumber = PageUtils.getFirstResult(pageNumber, pageSize);
        ListResult<JobPostModel> postList = jobService.runSearch(model, pageNumber, pageSize);
        return new ResponseText(postList);
    }

    /**
     * 根据id得到post
     * @param postId
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = Constants.Produce.JSON)
    public ResponseText getPostById(@PathVariable(value = "id") Integer postId) {

        JobPostModel model = jobService.findJobPost(postId);
        return new ResponseText(model);
    }

    /**
     * 根据id查找分类
     * @param cateId
     * @return
     */
    @RequestMapping(value = "/cate/{id}", method = RequestMethod.GET, produces = Constants.Produce.JSON)
    public ResponseText getCategoryById(@PathVariable(value = "id") Integer cateId) {
        //JobPostCategoryModel cate = cateService.findCategory(cateId);
        JobPostCategoryModel cate = cateService.findCategory(cateId);

        return new ResponseText(cate);
    }

    /**
     * 查询所有兼职分类
     * @return
     */
    @RequestMapping(value = "/cate/list", method = RequestMethod.GET, produces = Constants.Produce.JSON)
    public ResponseText getCategoryList() {
        ListResult<JobPostCategoryModel> list = cateService.getCategoryList(0, Integer.MAX_VALUE);

        return new ResponseText(list);
    }
}

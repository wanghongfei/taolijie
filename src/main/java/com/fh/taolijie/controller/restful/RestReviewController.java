package com.fh.taolijie.controller.restful;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.component.ResponseText;
import com.fh.taolijie.domain.ReviewModel;
import com.fh.taolijie.service.JobPostService;
import com.fh.taolijie.service.ReviewService;
import com.fh.taolijie.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * Created by whf on 7/5/15.
 */
@RestController
@RequestMapping(value = "/api/review")
public class RestReviewController {
    @Autowired
    ReviewService reviewService;

    /**
     * 得到兼职信息的评论
     * @return
     */
    @RequestMapping(value = "/job/{id}", produces = Constants.Produce.JSON)
    public ResponseText getReviewsByJobPost(@PathVariable("id") Integer jobId,
                                            @RequestParam(defaultValue = "0") int pageNumber,
                                            @RequestParam(defaultValue = Constants.PAGE_CAPACITY + "") int pageSize,
                                            HttpSession session) {

        pageNumber = pageNumber * pageSize;
        ReviewModel reviewCommand = new ReviewModel(pageNumber, pageSize);
        reviewCommand.setJobPostId(jobId);
        ListResult<ReviewModel> lr = reviewService.getReviewList(reviewCommand);

        return new ResponseText(lr);
    }

    /**
     * 得到二手的评论
     * @return
     */
    @RequestMapping(value = "/sh/{id}", produces = Constants.Produce.JSON)
    public ResponseText getReviewsByShPost(@PathVariable("id") Integer shId,
                                            @RequestParam(defaultValue = "0") int pageNumber,
                                            @RequestParam(defaultValue = Constants.PAGE_CAPACITY + "") int pageSize,
                                            HttpSession session) {

        pageNumber = pageNumber * pageSize;
        ReviewModel reviewCommand = new ReviewModel(pageNumber, pageSize);
        reviewCommand.setShPostId(shId);
        ListResult<ReviewModel> lr = reviewService.getReviewList(reviewCommand);

        return new ResponseText(lr);
    }
}

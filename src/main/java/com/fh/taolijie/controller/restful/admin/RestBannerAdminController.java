package com.fh.taolijie.controller.restful.admin;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.component.ResponseText;
import com.fh.taolijie.constant.ErrorCode;
import com.fh.taolijie.domain.BannerPicModel;
import com.fh.taolijie.service.BannerPicService;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.json.JsonWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * Created by whf on 8/22/15.
 */
@Controller
@RequestMapping("/api/manage/ban")
public class RestBannerAdminController {

    @Autowired
    BannerPicService bannerPicService;

    /**
     * 添加一张banner
     * @param pic
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, produces = Constants.Produce.JSON)
    public ResponseText add(BannerPicModel pic){

        pic.setCreatedTime(new Date());
        bannerPicService.addBanner(pic);

        return ResponseText.getSuccessResponseText();
    }


    /**
     * 删除一条banner
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE, produces = Constants.Produce.JSON)
    public ResponseText del(@PathVariable int id) {
        bannerPicService.deleteBanner(id);

        return ResponseText.getSuccessResponseText();
    }

    /**
     * 修改banner
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT, produces = Constants.Produce.JSON)
    public ResponseText update(@PathVariable int id,
                               BannerPicModel model) {
        model.setId(id);
        bannerPicService.updateBanner(id, model);

        return ResponseText.getSuccessResponseText();
    }
}

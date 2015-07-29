package com.fh.taolijie.controller.restful;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.component.ResponseText;
import com.fh.taolijie.domain.BannerPicModel;
import com.fh.taolijie.service.BannerPicService;
import com.fh.taolijie.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by whf on 7/29/15.
 */
@RestController
@RequestMapping("/api/ban")
public class RestBannerController {
    @Autowired
    BannerPicService banService;

    @RequestMapping(value = "/list", produces = Constants.Produce.JSON)
    public ResponseText allBannerList(@RequestParam(defaultValue = "0") int pageNumber,
                                      @RequestParam(defaultValue = Constants.PAGE_CAPACITY + "") int pageSize) {
        pageNumber = pageNumber * pageSize;
        ListResult<BannerPicModel> lr = banService.getBannerList(pageNumber, pageSize);

        return new ResponseText(lr);
    }
}

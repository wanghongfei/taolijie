package com.fh.taolijie.controller.restful.admin;

import com.fh.taolijie.component.ResponseText;
import com.fh.taolijie.service.StatisticsService;
import com.fh.taolijie.servlet.OnlineListener;
import com.fh.taolijie.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by whf on 7/7/15.
 */
@RestController
@RequestMapping("/api/manage/statistics")
public class RestStatisticsAdminController {
    @Autowired
    StatisticsService staService;


    /**
     * 页面访问量
     * @return
     */
    @RequestMapping(value = "/pageView", produces = Constants.Produce.JSON)
    public ResponseText pageViewStatistics() {
        Map<String, String> map = staService.getPageViewStatistics();

        return new ResponseText(map);
    }

    /**
     * 在线人数
     * @return
     */
    @RequestMapping(value = "/online", produces = Constants.Produce.JSON)
    public ResponseText onlineStatistics() {
        Integer tot = OnlineListener.getOnlineAmount();
        return new ResponseText(tot);
    }

}

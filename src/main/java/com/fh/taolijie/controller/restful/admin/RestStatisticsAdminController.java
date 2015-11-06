package com.fh.taolijie.controller.restful.admin;

import com.fh.taolijie.component.ResponseText;
import com.fh.taolijie.service.PVService;
import com.fh.taolijie.service.StatisticsService;
import com.fh.taolijie.servlet.OnlineListener;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * Created by whf on 7/7/15.
 */
@RestController
@RequestMapping("/api/manage/statistics")
public class RestStatisticsAdminController {
    @Autowired
    StatisticsService staService;

    @Autowired
    private PVService pvService;

    /**
     * @deprecated
     * 页面访问量
     * @return
     */
    @RequestMapping(value = "/pageView", produces = Constants.Produce.JSON)
    public ResponseText pageViewStatistics() {
        Map<String, String> map = staService.getPageViewStatistics();

        return new ResponseText(map);
    }

    /**
     * @deprecated
     * 在线人数
     * @return
     */
    @RequestMapping(value = "/online", produces = Constants.Produce.JSON)
    public ResponseText onlineStatistics() {
        Integer tot = OnlineListener.getOnlineAmount();
        return new ResponseText(tot);
    }

    /**
     * 查询pv统计数据
     * @return
     */
    @RequestMapping(value = "/pv", method = RequestMethod.GET, produces = Constants.Produce.JSON)
    public ResponseText pv(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date start,
                           @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date end) {

        Date startTime = TimeUtil.calculateDate(start, Calendar.DAY_OF_MONTH, 1);
        Date endTime = TimeUtil.calculateDate(end, Calendar.DAY_OF_MONTH, 1);

        return new ResponseText(pvService.queryPv(startTime, endTime));
    }

}

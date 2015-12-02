package com.fh.taolijie.controller.restful.quest;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.component.ResponseText;
import com.fh.taolijie.domain.OffQuestModel;
import com.fh.taolijie.exception.checked.GeneralCheckedException;
import com.fh.taolijie.service.quest.OffQuestService;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.PageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

/**
 * 线下任务控制器
 * Created by whf on 12/1/15.
 */
@RestController
@RequestMapping("/api/offquest")
public class RestOffQuestCtr {
    private static Logger logger = LoggerFactory.getLogger(RestOffQuestCtr.class);

    /**
     * 20km
     */
    public static final int NEAR_RANGE = 1000 * 20;

    @Autowired
    private OffQuestService questService;

    /**
     * 附近的任务
     * @return
     */
    @RequestMapping(value = "/near", method = RequestMethod.GET, produces = Constants.Produce.JSON)
    public ResponseText nearBy(@RequestParam(defaultValue = Constants.PAGE_CAP) int ps,
                               @RequestParam("lo") BigDecimal longitude,
                               @RequestParam("la") BigDecimal latitude,
                               HttpServletRequest req) throws GeneralCheckedException {

        ListResult<OffQuestModel> lr = questService.nearbyQuest(longitude, latitude, NEAR_RANGE, 0, ps);

        return new ResponseText(lr);
    }


    /**
     * 根据区域查询线下任务
     * @return
     */
    @RequestMapping(value = "/region", method = RequestMethod.GET, produces = Constants.Produce.JSON)
    public ResponseText byRegion(@RequestParam("rid") Integer regionId,
                                 @RequestParam(defaultValue = "") int pn,
                                 @RequestParam(defaultValue = Constants.PAGE_CAP) int ps,
                                 HttpServletRequest req) {

        pn = PageUtils.getFirstResult(pn, ps);

        OffQuestModel cmd = new OffQuestModel(pn, ps);
        cmd.setRegionId(regionId);

        ListResult<OffQuestModel> lr = questService.findBy(cmd);

        return new ResponseText(lr);
    }
}

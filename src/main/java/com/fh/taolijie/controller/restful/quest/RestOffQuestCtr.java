package com.fh.taolijie.controller.restful.quest;

import cn.fh.security.credential.Credential;
import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.component.ResponseText;
import com.fh.taolijie.constant.ErrorCode;
import com.fh.taolijie.domain.OffQuestModel;
import com.fh.taolijie.exception.checked.GeneralCheckedException;
import com.fh.taolijie.service.certi.IdCertiService;
import com.fh.taolijie.service.quest.OffQuestService;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.PageUtils;
import com.fh.taolijie.utils.SessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

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

    @Autowired
    private IdCertiService idService;

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

        // 根据用户状态处理联系手机号的显示
        Credential credential = SessionUtils.getCredential(req);
        hideMobile(lr.getList(), credential);

        return new ResponseText(lr);
    }

    private void hideMobile(List<OffQuestModel> list, Credential credential) {
        // 未登陆
        if (null == credential) {
            hideMobile(list);

        } else {
            // 已登陆
            boolean verified = idService.checkVerified(credential.getId());
            // 但是未认证
            if (false == verified) {
                hideMobile(list);
            }
        }

    }

    private void hideMobile(List<OffQuestModel> list) {
        list.forEach( model -> {
            String num = model.getContactPhone();

            StringBuffer sb = new StringBuffer(num);
            sb.replace(3, 7, "XXXX");

            model.setContactPhone(sb.toString());
        });

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

    /**
     * 根据id查询线下任务
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = Constants.Produce.JSON)
    public ResponseText byId(@PathVariable Integer id,
                             HttpServletRequest req) {

        OffQuestModel cmd = new OffQuestModel();
        cmd.setId(id);

        ListResult<OffQuestModel> list = questService.findBy(cmd);
        if (list.getResultCount() == 0) {
            return new ResponseText(ErrorCode.NOT_FOUND);
        }

        // 根据用户状态处理联系手机号的显示
        Credential credential = SessionUtils.getCredential(req);
        hideMobile(list.getList(), credential);

        return new ResponseText(list.getList().get(0));
    }
}

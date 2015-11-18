package com.fh.taolijie.controller.restful.acc;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.component.ResponseText;
import com.fh.taolijie.constant.ErrorCode;
import com.fh.taolijie.constant.certi.CertiStatus;
import com.fh.taolijie.domain.IdCertiModel;
import com.fh.taolijie.domain.certi.EmpCertiModel;
import com.fh.taolijie.domain.certi.StuCertiModel;
import com.fh.taolijie.service.certi.EmpCertiService;
import com.fh.taolijie.service.certi.IdCertiService;
import com.fh.taolijie.service.certi.StuCertiService;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 认证审核控制器
 * Created by whf on 9/26/15.
 */
@RestController
@RequestMapping("/api/manage/certi")
public class RestCertiAdminCtr {
    @Autowired
    private StuCertiService stuService;

    @Autowired
    private EmpCertiService empService;

    @Autowired
    private IdCertiService idService;

    /**
     * 审核个人身份
     * @return
     */
    @RequestMapping(value = "/id", method = RequestMethod.PUT, produces = Constants.Produce.JSON)
    public ResponseText verifyId(@RequestParam Integer certiId,
                                 @RequestParam String status,
                                 @RequestParam(required = false) String memo) {

        // 验证参数合法性
        CertiStatus st = CertiStatus.fromCode(status);
        if (null == st) {
            return new ResponseText(ErrorCode.INVALID_PARAMETER);
        }


        IdCertiModel model = idService.findById(certiId);
        if (null == model) {
            return new ResponseText(ErrorCode.NOT_FOUND);
        }

        idService.updateStatus(certiId, model.getMemId(), st, memo);

        return ResponseText.getSuccessResponseText();
    }


    /**
     * 查询个人认证审核列表
     * @return
     */
    @RequestMapping(value = "/id/list", method = RequestMethod.GET, produces = Constants.Produce.JSON)
    public ResponseText idList(@RequestParam String status,
                               @RequestParam(defaultValue = "0") int pn,
                               @RequestParam(defaultValue = Constants.PAGE_CAP) int ps) {

        CertiStatus cs = CertiStatus.fromCode(status);
        if (null == cs) {
            return new ResponseText(ErrorCode.INVALID_PARAMETER);
        }

        pn = PageUtils.getFirstResult(pn, ps);
        ListResult<IdCertiModel> lr = idService.findByStatus(cs, pn, ps);


        return new ResponseText(lr);
    }

    /**
     * 审核学生身份
     * @return
     */
    @RequestMapping(value = "/stu", method = RequestMethod.PUT, produces = Constants.Produce.JSON)
    public ResponseText verifyStu(@RequestParam Integer certiId,
                                  @RequestParam String status,
                                  @RequestParam(required = false) String memo) {

        StuCertiModel stuModel = stuService.findById(certiId);
        if (null == stuModel) {
            return new ResponseText(ErrorCode.NOT_FOUND);
        }

        CertiStatus st = CertiStatus.fromCode(status);
        if (null == st) {
            return new ResponseText(ErrorCode.INVALID_PARAMETER);
        }

        stuService.updateStatus(certiId, stuModel.getMemberId(), st, memo);

        return ResponseText.getSuccessResponseText();
    }

    /**
     * 查询学生身份认证申请列表
     * @return
     */
    @RequestMapping(value = "/stu/list", method = RequestMethod.GET, produces = Constants.Produce.JSON)
    public ResponseText stuList(@RequestParam String status,
                                @RequestParam(defaultValue = "0") int pn,
                                @RequestParam(defaultValue = Constants.PAGE_CAP) int ps) {
        CertiStatus cs = CertiStatus.fromCode(status);
        if (null == cs) {
            return new ResponseText(ErrorCode.INVALID_PARAMETER);
        }

        pn = PageUtils.getFirstResult(pn, ps);
        ListResult<StuCertiModel> lr = stuService.findByStatus(cs, pn, ps);

        return new ResponseText(lr);
    }

    /**
     * 审核商家身份
     * @return
     */
    @RequestMapping(value = "/emp", method = RequestMethod.PUT, produces = Constants.Produce.JSON)
    public ResponseText verifyEmp(@RequestParam Integer certiId,
                                  @RequestParam String status,
                                  @RequestParam(required = false) String memo) {

        EmpCertiModel stuModel = empService.findById(certiId);
        if (null == stuModel) {
            return new ResponseText(ErrorCode.NOT_FOUND);
        }

        CertiStatus st = CertiStatus.fromCode(status);
        if (null == st) {
            return new ResponseText(ErrorCode.INVALID_PARAMETER);
        }

        empService.updateStatus(certiId, stuModel.getMemberId(), st, memo);

        return ResponseText.getSuccessResponseText();
    }
}

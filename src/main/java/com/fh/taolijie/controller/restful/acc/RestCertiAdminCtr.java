package com.fh.taolijie.controller.restful.acc;

import com.fh.taolijie.component.ResponseText;
import com.fh.taolijie.constant.ErrorCode;
import com.fh.taolijie.constant.certi.CertiStatus;
import com.fh.taolijie.domain.certi.EmpCertiModel;
import com.fh.taolijie.domain.certi.StuCertiModel;
import com.fh.taolijie.service.certi.EmpCertiService;
import com.fh.taolijie.service.certi.StuCertiService;
import com.fh.taolijie.utils.Constants;
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

    /**
     * 审核学生身份
     * @return
     */
    @RequestMapping(value = "/stu", method = RequestMethod.PUT, produces = Constants.Produce.JSON)
    public ResponseText verifyStu(@RequestParam Integer certiId,
                                  @RequestParam String status,
                                  @RequestParam(required = false) String memo) {

        StuCertiModel stuModel = stuService.findById(certiId);
        if (null == certiId) {
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
     * 审核商家身份
     * @return
     */
    @RequestMapping(value = "/emp", method = RequestMethod.PUT, produces = Constants.Produce.JSON)
    public ResponseText verifyEmp(@RequestParam Integer certiId,
                                  @RequestParam String status,
                                  @RequestParam(required = false) String memo) {

        EmpCertiModel stuModel = empService.findById(certiId);
        if (null == certiId) {
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

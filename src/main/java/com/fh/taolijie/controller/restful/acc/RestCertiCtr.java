package com.fh.taolijie.controller.restful.acc;

import cn.fh.security.credential.Credential;
import com.fh.taolijie.component.ResponseText;
import com.fh.taolijie.constant.ErrorCode;
import com.fh.taolijie.constant.certi.CertiStatus;
import com.fh.taolijie.dao.mapper.MemberModelMapper;
import com.fh.taolijie.domain.IdCertiModel;
import com.fh.taolijie.domain.certi.EmpCertiModel;
import com.fh.taolijie.domain.acc.MemberModel;
import com.fh.taolijie.domain.certi.StuCertiModel;
import com.fh.taolijie.dto.CertiInfoDto;
import com.fh.taolijie.exception.checked.GeneralCheckedException;
import com.fh.taolijie.exception.checked.certi.ApplicationDuplicatedException;
import com.fh.taolijie.service.AccountService;
import com.fh.taolijie.service.certi.EmpCertiService;
import com.fh.taolijie.service.certi.IdCertiService;
import com.fh.taolijie.service.certi.StuCertiService;
import com.fh.taolijie.service.impl.IntervalCheckService;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 身份认证申请控制器
 * Created by whf on 9/26/15.
 */
@RestController
@RequestMapping("/api/user/certi")
public class RestCertiCtr {
    @Autowired
    private StuCertiService stuService;

    @Autowired
    private EmpCertiService empService;

    @Autowired
    private IdCertiService idService;

    @Autowired
    private AccountService accService;


    /**
     * 查询当前用户的认证状态
     * @return
     */
    @RequestMapping(value = "/status", method = RequestMethod.GET, produces = Constants.Produce.JSON)
    public ResponseText checkCertiStatus(HttpServletRequest req) {
        Integer memId = SessionUtils.getCredential(req).getId();

        CertiInfoDto dto = accService.selectCertiStatus(memId);

        return new ResponseText(dto);
    }

    /**
     * 个人身份认证
     * @return
     */
    @RequestMapping(value = "/id", method = RequestMethod.POST, produces = Constants.Produce.JSON)
    public ResponseText idApply(@RequestParam String name,
                                @RequestParam String id,
                                @RequestParam String picIds,
                                HttpServletRequest req) throws GeneralCheckedException {

        Integer memId = SessionUtils.getCredential(req).getId();
        IdCertiModel model = new IdCertiModel();

        model.setName(name);
        model.setIdNum(id);
        model.setPicIds(picIds);
        model.setMemId(memId);

        idService.addApplication(model);

        return ResponseText.getSuccessResponseText();
    }

    /**
     * 学生申请认证
     * @return
     */
    @RequestMapping(value = "/stu", method = RequestMethod.POST, produces = Constants.Produce.JSON)
    public ResponseText stuApply(@RequestParam String name,
                                 @RequestParam Integer cityId,
                                 @RequestParam Integer collegeId,
                                 //@RequestParam Integer schoolId,
                                 @RequestParam String picIds,
                                 @RequestParam(required = false) String cname, // 班级
                                 HttpServletRequest req) {

        // 先检查是否已经认证过了
        Credential credential = SessionUtils.getCredential(req);
        MemberModel mem = accService.findMember(credential.getId());

        String certiStatus = mem.getStuCerti();

        if (null != certiStatus && certiStatus.equals(CertiStatus.DONE.code())) {
            return new ResponseText(ErrorCode.ALREADY_VERIFIED);
        }

        // 检查是否是学生用户
        if (!credential.getRoleList().get(0).equals(Constants.RoleType.STUDENT.toString())) {
            return new ResponseText(ErrorCode.PERMISSION_ERROR);
        }

        StuCertiModel model = new StuCertiModel();
        model.setMemberId(mem.getId());
        model.setName(name);
        model.setCityId(cityId);
        model.setCollegeId(collegeId);
        //model.setSchoolId(schoolId);
        model.setPicIds(picIds);
        model.setClazz(cname);

        stuService.addApplication(model);

        return ResponseText.getSuccessResponseText();
    }


    /**
     * 商家申请认证
     * @return
     */
    @RequestMapping(value = "/emp", method = RequestMethod.POST, produces = Constants.Produce.JSON)
    public ResponseText empApply(@RequestParam String compName,
                                 @RequestParam String contName,
                                 @RequestParam(required = false) String compPhone,
                                 @RequestParam String addr,
                                 @RequestParam String picIds,
                                 @RequestParam Integer cateId, // 行业分类id
                                 HttpServletRequest req) {


        Credential credential = SessionUtils.getCredential(req);
        MemberModel mem = accService.findMember(credential.getId());

        // 检查是否是商家用户
        if (!SessionUtils.isEmployer(credential)) {
            return new ResponseText(ErrorCode.PERMISSION_ERROR);
        }

        // 先检查是否已经认证过了
        String certiStatus = mem.getEmpCerti();
        if (null != certiStatus && certiStatus.equals(CertiStatus.DONE.code())) {
            return new ResponseText(ErrorCode.ALREADY_VERIFIED);
        }


        EmpCertiModel model = new EmpCertiModel();
        model.setMemberId(mem.getId());
        model.setCompanyName(compName);
        model.setAddress(addr);
        model.setPicIds(picIds);
        model.setCompPhone(compPhone);
        model.setIndustryId(cateId);
        model.setContactName(contName);

        empService.addApplication(model);

        return ResponseText.getSuccessResponseText();
    }
}

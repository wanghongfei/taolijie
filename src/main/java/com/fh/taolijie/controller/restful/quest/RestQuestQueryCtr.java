package com.fh.taolijie.controller.restful.quest;

import cn.fh.security.credential.Credential;
import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.component.ResponseText;
import com.fh.taolijie.constant.ErrorCode;
import com.fh.taolijie.constant.quest.AssignStatus;
import com.fh.taolijie.constant.quest.EmpQuestStatus;
import com.fh.taolijie.constant.quest.QuestStatus;
import com.fh.taolijie.domain.quest.QuestAssignModel;
import com.fh.taolijie.domain.quest.QuestModel;
import com.fh.taolijie.service.quest.QuestService;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.PageUtils;
import com.fh.taolijie.utils.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 轻兼职查询控制器
 * Created by whf on 9/27/15.
 */
@RestController
@RequestMapping("/api/quest")
public class RestQuestQueryCtr {
    @Autowired
    private QuestService questService;

    /**
     * 条件查询
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = Constants.Produce.JSON)
    public ResponseText query(@RequestParam(required = false) Integer cateId,
                              // 任务对象信息
                              @RequestParam(required = false) Integer collegeId,
                              @RequestParam(required = false) Integer schoolId,
                              @RequestParam(required = false) Integer cityId,
                              @RequestParam(required = false) Integer proId,

                              @RequestParam(required = false) BigDecimal min,
                              @RequestParam(required = false) BigDecimal max,
                              @RequestParam(required = false) Integer empStatus, // 只有管理员才能使用该参数

                              @RequestParam(required = false, defaultValue = "0") int pn,
                              @RequestParam(required = false, defaultValue = Constants.PAGE_CAP) int ps,
                              HttpServletRequest req) {

        pn = PageUtils.getFirstResult(pn, ps);

        QuestModel command = new QuestModel(pn, ps);
        if (null != min || null != max) {
            // 开启赏金范围查询
            command.setAwardRangeQuery(true);
            command.setMinAward(min);
            command.setMaxAward(max);
        }

        command.setQuestCateId(cateId);
        //command.setRegionId(regionId);
        command.setCollegeId(collegeId);
        command.setSchoolId(schoolId);
        command.setCityId(cityId);
        command.setProvinceId(proId);

        command.setEmpStatus(EmpQuestStatus.DONE.code());

        // 判断是不是管理员调用
        Credential credential = SessionUtils.getCredential(req);
        if (null != credential && SessionUtils.isAdmin(credential)) {
            // 如果是，则允许查询所有状态的任务
            // 先检查empStatus参数合法性
            if (null != empStatus) {
                EmpQuestStatus status = EmpQuestStatus.fromCode(empStatus);
                if (null == status) {
                    return new ResponseText(ErrorCode.INVALID_PARAMETER);
                }

            }

            command.setEmpStatus(empStatus);
        }


        ListResult<QuestModel> lr = questService.findBy(command);

        return new ResponseText(lr);
    }

    /**
     * 根据id查询单个任务
     * @return
     */
    @RequestMapping(value = "/{questId}", method = RequestMethod.GET, produces = Constants.Produce.JSON)
    public ResponseText query(@PathVariable("questId") Integer questId,
                              HttpServletRequest req) {
        QuestModel model = questService.findById(questId);
        if (null == model) {
            return new ResponseText(ErrorCode.NOT_FOUND);
        }

        Credential credential = SessionUtils.getCredential(req);

        // 得到商家任务状态
        EmpQuestStatus empStatus = EmpQuestStatus.fromCode(model.getEmpStatus());
        // 如果商家任务状态为没有通过审核, 且该任务不是当前用户发布的,
        // 返回权限错误
        if (EmpQuestStatus.DONE != empStatus) {
            if (null == credential || false == credential.getId().equals(model.getMemberId())) {
                return new ResponseText(ErrorCode.PERMISSION_ERROR);
            }
        }

        // 判断是否登陆
        if (null != credential) {
            // 如果用户已登陆了
            // 则要设置任务状态字段(status)
            QuestAssignModel assign = questService.findAssignByMember(credential.getId(), questId);
            if (null != assign) {
                // 用户已经领取了任务
                String assignStatus = assign.getStatus();
                AssignStatus st = AssignStatus.fromCode(assignStatus);
                if (st == AssignStatus.DONE) {
                    // 状态为已经完成
                    model.setStatus(QuestStatus.DONE.code());
                } else {
                    // 状态为已领取
                    model.setStatus(QuestStatus.ASSIGNED.code());
                }
            }
        } else {
            // 未登陆用户
            // 过期判断
            if (model.getEndTime().compareTo(new Date()) <= 0) {
                // 已经过期
                model.setStatus(QuestStatus.ENDED.code());
            } else {
                // 显示未领取
                model.setStatus(QuestStatus.NOT_ASSIGNED.code());
            }
        }


        return new ResponseText(model);
    }
}

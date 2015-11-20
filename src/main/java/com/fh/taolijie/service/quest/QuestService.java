package com.fh.taolijie.service.quest;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.constant.quest.AssignStatus;
import com.fh.taolijie.constant.quest.EmpQuestStatus;
import com.fh.taolijie.domain.*;
import com.fh.taolijie.domain.quest.*;
import com.fh.taolijie.exception.checked.FinalStatusException;
import com.fh.taolijie.exception.checked.GeneralCheckedException;
import com.fh.taolijie.exception.checked.PermissionException;
import com.fh.taolijie.exception.checked.acc.BalanceNotEnoughException;
import com.fh.taolijie.exception.checked.acc.CashAccNotExistsException;
import com.fh.taolijie.exception.checked.acc.OrderNotFoundException;
import com.fh.taolijie.exception.checked.quest.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 轻兼职业务接口
 * Created by whf on 9/21/15.
 */
public interface QuestService {
    /**
     * 商家发布任务
     * @param accId 商家账户
     * @param model
     */
    void publishQuest(Integer accId, QuestModel model, Integer orderId, CouponModel coupon, Integer save)
            throws BalanceNotEnoughException, CashAccNotExistsException, OrderNotFoundException, FinalStatusException, PermissionException;


    /**
     * 发布处于草稿状态的任务(变为待审核状态)
     * @param memId
     * @param questId
     * @param orderId
     */
    void publishDraft(Integer memId, Integer questId, Integer orderId) throws QuestionNotFoundException, PermissionException, OrderNotFoundException, FinalStatusException, BalanceNotEnoughException, CashAccNotExistsException;

    /**
     * 批量插入 任务-学校 关联
     * @param list
     */
    void addQuestCollegeRel(List<QuestCollRelModel> list);

    /**
     * 批量插入 任务-学院 关联
     * @param list
     */
    void addQuestSchoolRel(List<QuestSchRelModel> list);

    /**
     * 批量插入 任务-城市关联
     * @param list
     */
    void addQuestCityRel(List<QuestCiRel> list);

    /**
     * 批量插入 任务-省关联
     * @param list
     */
    void addQuestProvinceRel(List<QuestProRel> list);

    /**
     * 管理员修改任务审核状态
     * @param questId
     * @param status
     * @return
     */
    int changeEmpStatus(Integer questId, EmpQuestStatus status) throws QuestNotFoundException;

    /**
     * 更新标签过期时间
     * @param postId
     * @param newDate
     * @return 修改的结果数
     */
    int updateTagExpireTime(Integer postId, Date newDate);

    /**
     * 刷新时间
     * @param postId
     * @return
     */
    int flush(Integer memId, Integer postId)
            throws QuestNotFoundException, BalanceNotEnoughException, CashAccNotExistsException;

    /**
     * 用户领取任务
     * @param memId 领取任务的用户id.
     * @param questId 任务id
     */
    void assignQuest(Integer memId, Integer questId)
            throws GeneralCheckedException;

    /**
     * 检查任务是否已经领取
     * @return
     */
    Boolean checkAssigned(Integer memId, Integer questId);

    /**
     * 让任务过期
     * @param assignId
     */
    void assignExpired(Integer assignId);

    /**
     * 任务到达过期时间
     * @param questId
     */
    void questExpired(Integer questId)
            throws CashAccNotExistsException;

    QuestModel findById(Integer questId);

    /**
     * 根据用户查询指定任务的领取情况
     * @param memId
     * @return
     */
    QuestAssignModel findAssignByMember(Integer memId, Integer questId);

    /**
     * @deprecated
     * 根据分类查找任务
     * @param cateId 分类id
     * @return
     */
    ListResult<QuestModel> findByCate(Integer cateId, int pn, int ps);

    /**
     * 根据id批量查询
     * @param idList
     * @return
     */
    List<QuestModel> findInBatch(List<Integer> idList);

    /**
     * @deprecated
     * 根据分类和赏金查询任务
     * @param cateId 分类id
     * @param min 最低赏金
     * @param max 最高赏金
     * @return
     */
    ListResult<QuestModel> findByCate(Integer cateId, BigDecimal min, BigDecimal max, int pn, int ps);

    ListResult<QuestModel> findBy(QuestModel command);

    /**
     * 查询指定用户发布的任务记录
     * @param memId
     * @param pn
     * @param ps
     * @return
     */
    ListResult<QuestModel> findByMember(Integer memId, int pn, int ps);


    /**
     * 查询指定用户的任务领取记录
     * @param memId
     * @param pn
     * @param ps
     * @return
     */
    ListResult<QuestAssignModel> queryAssignRecords(Integer memId, int pn, int ps);

    /**
     * 查询指定用户指定状态的任务领取记录
     *
     * @param memId
     * @param status
     * @param pn
     * @param ps
     * @return
     */
    ListResult<QuestAssignModel> queryAssignRecords(Integer memId, AssignStatus status, int pn, int ps);
}

package com.fh.taolijie.service.quest.impl;

import com.alibaba.fastjson.JSON;
import com.fh.taolijie.cache.message.model.MsgProtocol;
import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.constant.MsgType;
import com.fh.taolijie.constant.ScheduleChannel;
import com.fh.taolijie.constant.acc.AccFlow;
import com.fh.taolijie.constant.acc.OrderStatus;
import com.fh.taolijie.constant.acc.OrderType;
import com.fh.taolijie.constant.quest.AssignStatus;
import com.fh.taolijie.constant.quest.EmpQuestStatus;
import com.fh.taolijie.constant.quest.RequestStatus;
import com.fh.taolijie.controller.restful.acc.RestCertiAdminCtr;
import com.fh.taolijie.controller.restful.schedule.RestScheduleCtr;
import com.fh.taolijie.dao.mapper.*;
import com.fh.taolijie.domain.*;
import com.fh.taolijie.domain.acc.CashAccModel;
import com.fh.taolijie.domain.acc.MemberModel;
import com.fh.taolijie.domain.order.PayOrderModel;
import com.fh.taolijie.domain.quest.*;
import com.fh.taolijie.exception.checked.FinalStatusException;
import com.fh.taolijie.exception.checked.GeneralCheckedException;
import com.fh.taolijie.exception.checked.PermissionException;
import com.fh.taolijie.exception.checked.acc.BalanceNotEnoughException;
import com.fh.taolijie.exception.checked.acc.CashAccNotExistsException;
import com.fh.taolijie.exception.checked.acc.OrderNotFoundException;
import com.fh.taolijie.exception.checked.quest.*;
import com.fh.taolijie.service.acc.CashAccService;
import com.fh.taolijie.service.acc.OrderService;
import com.fh.taolijie.service.quest.CouponService;
import com.fh.taolijie.service.quest.QuestService;
import com.fh.taolijie.service.quest.QuestTargetService;
import com.fh.taolijie.utils.JedisUtils;
import com.fh.taolijie.utils.LogUtils;
import com.fh.taolijie.utils.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 轻兼职业务实现
 * Created by whf on 9/21/15.
 */
@Service
public class DefaultQuestService implements QuestService {
    private static Logger logger = LoggerFactory.getLogger(DefaultQuestService.class);

    @Autowired
    private QuestModelMapper questMapper;

    @Autowired
    private SysConfigModelMapper configMapper;

    @Autowired
    private FeeCalculator feeCal;

    @Autowired
    private QuestAssignModelMapper assignMapper;

    @Autowired
    private MemberModelMapper memMapper;

    @Autowired
    private CashAccModelMapper accMapper;


    @Autowired
    private CashAccService accService;

    @Autowired
    private QuestCollRelModelMapper qcMapper;
    @Autowired
    private QuestSchRelModelMapper qsMapper;
    @Autowired
    private QuestCiRelMapper qciMapper;
    @Autowired
    private QuestProRelMapper qproRel;

    @Autowired
    private CouponService couponService;

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private OrderService orderService;

    @Autowired
    private QuestTargetService targetService;

    /**
     * 商家发布任务.
     *
     * 要先检查商家账户余额是否充足
     * @param accId 商家账户id
     * @param model
     */
    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public void publishQuest(Integer accId, QuestModel model, Integer orderId, CouponModel coupon, Integer save)
            throws BalanceNotEnoughException, CashAccNotExistsException, OrderNotFoundException, FinalStatusException, PermissionException {

        // save == 1 表示当前只是保存任务而不需要发布
        // 只有当 save == 0时才执行扣钱流程
        if (0 == save) {
            // 计算总钱数
            BigDecimal tot = feeCal.computeQuestFee(model.getAward().doubleValue(), model.getTotalAmt());

            // 如果orderId存在, 则不从钱包扣钱
            if (orderId != null) {
                // 订单检查
                orderService.orderPayCheck(orderId, model.getMemberId(), OrderType.QUEST_PUBLISH, tot);
                // 更新订单状态
                orderService.updateStatus(orderId, OrderStatus.DONE, null);

                // 允许发布

            } else {
                // 从钱包扣钱
                accService.reduceAvailableMoney(accId, tot, AccFlow.CONSUME);
            }

            // 置状态为审核中
            model.setEmpStatus(EmpQuestStatus.WAIT_AUDIT.code());

        } else {
            // 置状态为未发布
            model.setEmpStatus(EmpQuestStatus.UNPUBLISH.code());
        }





        // 如果设置了coupon
        // 则添加coupon信息到任务中
        if (null != coupon) {
            model.setCoupon(true);
            model.setCouponLeft(coupon.getAmt());
            model.setCouponTot(coupon.getAmt());
        }

        // 发布任务
        // 只有当前是真正发布任务时( save == 0 ), 第二个参数才为true, 表示投递定时任务信息
        doPublish(model, 0 == save, false);

        // 插入coupon数据
        if (null != coupon) {
            couponService.add(coupon, model);
        }
    }

    private void doPublish(QuestModel model, boolean postMessage, boolean update) {
        // 检查任务状态是不是未发布
        if (update) {
            // 如果是
            // 只修改任务状态
            QuestModel example = new QuestModel();
            example.setId(model.getId());
            example.setEmpStatus(EmpQuestStatus.WAIT_AUDIT.code());
            questMapper.updateByPrimaryKeySelective(example);

            return;
        }


        // 发布任务
        Date now = new Date();
        model.setCreatedTime(now);
        model.setFlushTime(now);
        questMapper.insertSelective(model);

        Integer questId = model.getId();

        // 添加任务对象关联信息
        List<Integer> collList = model.getCollegeIdList();
        List<Integer> schList = model.getSchoolIdList();
        List<Integer> cityList = model.getCityIdList();
        List<Integer> proList = model.getProvinceIdList();

        // 关联学校
        if (!collList.isEmpty()) {
            List<QuestCollRelModel> qcList = collList.stream()
                    .map( id -> new QuestCollRelModel(questId, id) )
                    .collect(Collectors.toList());

            addQuestCollegeRel(qcList);
        }

        // 关联学院
        if (!schList.isEmpty()) {
            List<QuestSchRelModel> qsList = schList.stream()
                    .map( id -> new QuestSchRelModel(questId, id) )
                    .collect(Collectors.toList());

            addQuestSchoolRel(qsList);

        }

        // 关联城市
        if (!cityList.isEmpty()) {
            List<QuestCiRel> qcList = cityList.stream()
                    .map( id -> new  QuestCiRel(questId, id) )
                    .collect(Collectors.toList());

            addQuestCityRel(qcList);
        }

        // 关联省
        if (!proList.isEmpty()) {
            List<QuestProRel> qpList = proList.stream()
                    .map( id -> new QuestProRel(questId, id) )
                    .collect(Collectors.toList());

            addQuestProvinceRel(qpList);
        }


        if (postMessage) {
            // 投递任务过期定时任务
            // 构造参数列表
            Map<String, String> map = new HashMap<>();
            map.put("taskId", questId.toString());
            map.put("questId", questId.toString());

            // 构造消息体
            MsgProtocol msg = new MsgProtocol.Builder(
                    MsgType.DATE_STYLE,
                    "localhost",
                    8080,
                    RestScheduleCtr.fullUrl(RestScheduleCtr.URL_QUEST_EXPIRE),
                    "GET",
                    // 任务结束后的第25小时执行
                    TimeUtil.calculateDate(model.getEndTime(), Calendar.HOUR_OF_DAY, 25))
                    //TimeUtil.calculateDate(new Date(), Calendar.SECOND, 20))
                    .setParmMap(map)
                    .build();


            // 序列化成JSON
            String json = JSON.toJSONString(msg);
            if (logger.isDebugEnabled()) {
                logger.debug("sending message: {}", json);
            }

            // 发布消息
            Jedis jedis = JedisUtils.getClient(jedisPool);
            Long recvAmt = jedis.publish(ScheduleChannel.POST_JOB.code(), json);
            if (recvAmt.longValue() <= 0) {
                LogUtils.getErrorLogger().error("schedule center failed to receive task!");
            }
            JedisUtils.returnJedis(jedisPool, jedis);


        }

    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public void publishDraft(Integer memId, Integer questId, Integer orderId) throws QuestionNotFoundException, PermissionException, OrderNotFoundException, FinalStatusException, BalanceNotEnoughException, CashAccNotExistsException {
        // 检查任务存在性
        QuestModel quest = questMapper.selectByPrimaryKey(questId);
        if (null == quest) {
            throw new QuestionNotFoundException();
        }

        // 检查是不是自己的任务
        if (false == memId.equals(quest.getMemberId())) {
            throw new PermissionException();
        }

        // 检查任务状态是不是未发布
        EmpQuestStatus status = EmpQuestStatus.fromCode(quest.getEmpStatus());
        if (status != EmpQuestStatus.UNPUBLISH) {
            throw new PermissionException();
        }


        // 计算总钱数
        BigDecimal tot = feeCal.computeQuestFee(quest.getAward().doubleValue(), quest.getTotalAmt());

        // 如果orderId存在, 则不从钱包扣钱
        if (orderId != null) {
            // 订单检查
            orderService.orderPayCheck(orderId, quest.getMemberId(), OrderType.QUEST_PUBLISH, tot);
            // 更新订单状态
            orderService.updateStatus(orderId, OrderStatus.DONE, null);

            // 允许发布

        } else {
            // 从钱包扣钱
            Integer accId = accMapper.findIdByMemberId(memId);
            accService.reduceAvailableMoney(accId, tot, AccFlow.CONSUME);
        }

        doPublish(quest, false, true);

    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public void addQuestCollegeRel(List<QuestCollRelModel> list) {
        if (list.isEmpty()) {
            return;
        }

        qcMapper.insertInBatch(list);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public void addQuestSchoolRel(List<QuestSchRelModel> list) {
        if (list.isEmpty()) {
            return;
        }

        qsMapper.insertInBatch(list);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public void addQuestCityRel(List<QuestCiRel> list) {
        if (list.isEmpty()) {
            return;
        }

       qciMapper.insertInBatch(list) ;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public void addQuestProvinceRel(List<QuestProRel> list) {
        if (list.isEmpty()) {
            return;
        }

        qproRel.insertInBatch(list);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public int changeEmpStatus(Integer questId, EmpQuestStatus status) throws QuestNotFoundException {
        QuestModel model = questMapper.selectByPrimaryKey(questId);

        if (null == model) {
            throw new QuestNotFoundException("");
        }

        if (status == EmpQuestStatus.DONE) {
            // 置状态为审核通过

            // 此时投递定时任务信息

            // 投递任务过期定时任务
            // 构造参数列表
            Map<String, String> map = new HashMap<>();
            map.put("taskId", questId.toString());
            map.put("questId", questId.toString());

            // 构造消息体
            MsgProtocol msg = new MsgProtocol.Builder(
                    MsgType.DATE_STYLE,
                    "localhost",
                    8080,
                    RestScheduleCtr.fullUrl(RestScheduleCtr.URL_QUEST_EXPIRE),
                    "GET",
                    // 任务结束后的第25小时执行
                    TimeUtil.calculateDate(model.getEndTime(), Calendar.HOUR_OF_DAY, 25))
                    //TimeUtil.calculateDate(new Date(), Calendar.SECOND, 20))
                    .setParmMap(map)
                    .build();


            // 序列化成JSON
            String json = JSON.toJSONString(msg);
            if (logger.isDebugEnabled()) {
                logger.debug("sending message: {}", json);
            }

            // 发布消息
            Jedis jedis = JedisUtils.getClient(jedisPool);
            Long recvAmt = jedis.publish(ScheduleChannel.POST_JOB.code(), json);
            if (recvAmt.longValue() <= 0) {
                LogUtils.getErrorLogger().error("schedule center failed to receive task!");
            }

            JedisUtils.returnJedis(jedisPool, jedis);

        }

        // 修改状态值
        QuestModel example = new QuestModel();
        example.setId(questId);
        example.setEmpStatus(status.code());
        questMapper.updateByPrimaryKeySelective(example);


        return 0;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public int updateTagExpireTime(Integer postId, Date newDate) {
        QuestModel example = new QuestModel();
        example.setId(postId);
        example.setTagExpireTime(newDate);

        return questMapper.updateByPrimaryKeySelective(example);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public int flush(Integer memId, Integer postId)
            throws QuestNotFoundException, BalanceNotEnoughException, CashAccNotExistsException {

        // 扣钱
        BigDecimal fee = feeCal.computeFlushFee();
        Integer accId = accMapper.findIdByMemberId(memId);
        accService.reduceAvailableMoney(accId, fee, AccFlow.CONSUME);

        // 执行刷新
        int row = questMapper.flush(postId);

        if (row <= 0) {
            throw new QuestNotFoundException("");
        }

        return row;
    }

    /**
     * 领取任务。
     * 需要行锁。
     *
     * @param memId 领取任务的用户id.
     * @param questId 任务id
     */
    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public void assignQuest(Integer memId, Integer questId) throws GeneralCheckedException {
        QuestModel questModel = questMapper.selectByPrimaryKey(questId);
        // 检查任务是否存在
        if (null == questModel) {
            throw new QuestNotFoundException("");
        }

        Date now = new Date();
        // 检查任务是否过期
        if (questModel.getEndTime().compareTo(now) < 0) {
            throw new QuestExpiredException("");
        }
        // 检查任务是否开始
        if(questModel.getStartTime().compareTo(now) > 0) {
            throw new QuestNotStartException("");
        }

        // 检查任务是否已经领取
        boolean repeat = assignMapper.checkMemberIdAndQuestIdExists(memId, questId);
        if (repeat) {
            throw new QuestAssignedException("");
        }

        if (false == targetService.checkTarget(memId, questId)) {
            throw new QuestTargetMismatchException();
        }

        // 判断剩余任务数量
        // 这句查询会加行锁
        int leftAmt = questMapper.selectQuestLeftAmountWithLock(questId);
        if (leftAmt <= 0) {
            // 任务已经没了
            throw new QuestZeroException("");
        }


        // 领取任务
        // 向分配表中插入记录
        QuestAssignModel assignModel = new QuestAssignModel();
        assignModel.setMemberId(memId);
        assignModel.setQuestId(questId);
        assignModel.setAssignTime(new Date());
        // 设置冗余字段username
        MemberModel m = memMapper.selectByPrimaryKey(memId);
        assignModel.setUsername(m.getUsername());
        // 设置冗余字段quest_title
        QuestModel quest = questMapper.selectByPrimaryKey(questId);
        assignModel.setQuestTitle(quest.getTitle());
        assignModel.setStatus(AssignStatus.ASSIGNED.code());
        assignMapper.insertSelective(assignModel);

        final int assignId = assignModel.getId();


        // 任务剩余数量减少1
        questMapper.decreaseLeftAmount(questId);

        // todo 投递定时任务请求
        // 构造参数列表
        Map<String, String> map = new HashMap<>();
        map.put("taskId", String.valueOf(assignId));
        map.put("assignId", String.valueOf(assignId));

        // 构造消息体
        MsgProtocol msg = new MsgProtocol.Builder(
                MsgType.DATE_STYLE,
                "localhost",
                8080,
                RestScheduleCtr.fullUrl(RestScheduleCtr.URL_AUTO_EXPIRE),
                "GET",
                TimeUtil.calculateDate(new Date(), Calendar.HOUR_OF_DAY, 2))
                .setParmMap(map)
                .build();


        // 序列化成JSON
        String json = JSON.toJSONString(msg);
        if (logger.isDebugEnabled()) {
            logger.debug("sending message: {}", json);
        }

        // 发布消息
        Jedis jedis = JedisUtils.getClient(jedisPool);
        Long recvAmt = jedis.publish(ScheduleChannel.POST_JOB.code(), json);
        if (recvAmt.longValue() <= 0) {
            LogUtils.getErrorLogger().error("schedule center failed to receive task!");
        }
        JedisUtils.returnJedis(jedisPool, jedis);


        // 方法结束 == 事务结束，行锁释放
    }

    @Override
    @Transactional(readOnly = false)
    public Boolean checkAssigned(Integer memId, Integer questId) {
        return assignMapper.checkMemberIdAndQuestIdExists(memId, questId);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public void assignExpired(Integer assignId) {
        // 检查任务领取表的状态是不是"03:已经提交"
        // 如果是，则不进行任何操作，方法直接返回
        QuestAssignModel model = assignMapper.selectByPrimaryKey(assignId);
        if (model.getStatus().equals(AssignStatus.SUBMITTED.code())) {
            return;
        }

        // 修改状态为"已超时"
        QuestAssignModel example = new QuestAssignModel();
        example.setId(assignId);
        example.setStatus(AssignStatus.ENDED.code());
        assignMapper.updateByPrimaryKeySelective(example);

        // 释放任务数量
        questMapper.increaseLeftAmount(model.getQuestId());

    }

    /**
     * 检查是否有未领取的任务，如果有则退款给商家
     * @param questId
     */
    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public void questExpired(Integer questId) throws CashAccNotExistsException {
        // 检查是否还有未领取的任务
        QuestModel quest = questMapper.selectByPrimaryKey(questId);
        Integer left = quest.getLeftAmt();
        if (left.intValue() <= 0) {
            return;
        }

        // 计算任务的钱数
        BigDecimal leftAmt = new BigDecimal(left);
        BigDecimal refund = quest.getFinalAward().multiply(leftAmt);

        // 向用户的现金账户中加钱
        CashAccModel acc = accMapper.findByMemberId(quest.getMemberId());
        accService.addAvailableMoney(acc.getId(), refund);
    }

    @Override
    @Transactional(readOnly = true)
    public QuestModel findById(Integer questId) {
        return questMapper.selectByPrimaryKey(questId);
    }

    @Override
    @Transactional(readOnly = true)
    public QuestAssignModel findAssignByMember(Integer memId, Integer questId) {
        return assignMapper.selectAssignByMemberAndQuest(memId, questId);
    }

    /**
     * @deprecated
     */
    @Override
    @Transactional(readOnly = true)
    public ListResult<QuestModel> findByCate(Integer cateId, int pn, int ps) {
        QuestModel example = new QuestModel(pn, ps);
        example.setQuestCateId(cateId);

        List<QuestModel> list = questMapper.findBy(example);
        long tot = questMapper.countFindBy(example);

        return new ListResult<>(list, tot);
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuestModel> findInBatch(List<Integer> idList) {
        if (idList.isEmpty()) {
            return new ArrayList<>(0);
        }

        return questMapper.selectInBatch(idList);
    }

    /**
     * @deprecated
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public ListResult<QuestModel> findByCate(Integer cateId, BigDecimal min, BigDecimal max, int pn, int ps) {
        QuestModel example = new QuestModel(pn, ps);
        example.setQuestCateId(cateId);
        example.setAwardRangeQuery(true);
        example.setMinAward(min);
        example.setMaxAward(max);

        List<QuestModel> list = questMapper.findBy(example);
        long tot = questMapper.countFindBy(example);

        return new ListResult<>(list, tot);
    }

    @Override
    @Transactional(readOnly = true)
    public ListResult<QuestModel> findBy(QuestModel command) {
        List<QuestModel> list = questMapper.findBy(command);
        long tot = questMapper.countFindBy(command);

        return new ListResult<>(list, tot);
    }

    @Override
    @Transactional(readOnly = true)
    public ListResult<QuestModel> findByMember(Integer memId, int pn, int ps) {
        QuestModel example = new QuestModel(pn, ps);
        example.setMemberId(memId);

        List<QuestModel> list = questMapper.findBy(example);
        long tot = questMapper.countFindBy(example);

        return new ListResult<>(list, tot);
    }

    @Override
    @Transactional(readOnly = true)
    public ListResult<QuestAssignModel> queryAssignRecords(Integer memId, int pn, int ps) {
        QuestAssignModel example = new QuestAssignModel(pn, ps);
        example.setMemberId(memId);

        List<QuestAssignModel> list = assignMapper.findBy(example);
        long tot = assignMapper.countFindBy(example);

        return new ListResult<>(list, tot);
    }

    @Override
    @Transactional(readOnly = true)
    public ListResult<QuestAssignModel> queryAssignRecords(Integer memId, AssignStatus status, int pn, int ps) {
        QuestAssignModel example = new QuestAssignModel(pn, ps);
        example.setMemberId(memId);
        example.setStatus(status.code());

        List<QuestAssignModel> list = assignMapper.findBy(example);
        long tot = assignMapper.countFindBy(example);

        return new ListResult<>(list, tot);
    }
}

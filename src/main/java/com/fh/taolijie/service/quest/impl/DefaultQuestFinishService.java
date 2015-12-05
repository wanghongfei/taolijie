package com.fh.taolijie.service.quest.impl;

import com.alibaba.fastjson.JSON;
import com.fh.taolijie.cache.message.model.MsgProtocol;
import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.constant.MsgType;
import com.fh.taolijie.constant.ScheduleChannel;
import com.fh.taolijie.constant.acc.AccFlow;
import com.fh.taolijie.constant.quest.AssignStatus;
import com.fh.taolijie.constant.quest.RequestStatus;
import com.fh.taolijie.controller.restful.schedule.RestScheduleCtr;
import com.fh.taolijie.dao.mapper.*;
import com.fh.taolijie.domain.acc.CashAccModel;
import com.fh.taolijie.domain.quest.FinishRequestModel;
import com.fh.taolijie.domain.acc.MemberModel;
import com.fh.taolijie.domain.quest.QuestModel;
import com.fh.taolijie.exception.checked.GeneralCheckedException;
import com.fh.taolijie.exception.checked.HackException;
import com.fh.taolijie.exception.checked.acc.CashAccNotExistsException;
import com.fh.taolijie.exception.checked.quest.*;
import com.fh.taolijie.service.acc.CashAccService;
import com.fh.taolijie.service.quest.CouponService;
import com.fh.taolijie.service.quest.QuestFinishService;
import com.fh.taolijie.service.quest.TljAuditService;
import com.fh.taolijie.utils.JedisUtils;
import com.fh.taolijie.utils.LogUtils;
import com.fh.taolijie.utils.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 任务提交申请业务实现
 * Created by whf on 9/22/15.
 */
@Service
public class DefaultQuestFinishService implements QuestFinishService {
    private static Logger logger = LoggerFactory.getLogger(DefaultQuestFinishService.class);


    @Autowired
    private FinishRequestModelMapper fiMapper;

    @Autowired
    private CashAccService accService;

    @Autowired
    private QuestModelMapper questMapper;

    @Autowired
    private CashAccModelMapper accMapper;

    @Autowired
    private MemberModelMapper memMapper;

    @Autowired
    private QuestAssignModelMapper assignMapper;

    @Autowired
    private TljAuditService auditService;

    @Autowired
    private CouponService couponService;

    @Autowired
    private QuestCategoryModelMapper questCateMapper;


    @Autowired
    private JedisPool jedisPool;

    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public void submitRequest(FinishRequestModel model)
            throws GeneralCheckedException {

        // 先检查用户是否已经领取该任务且状态不为"02:已超时"
        boolean assigned = assignMapper.checkMemberIdAndQuestIdExists(model.getMemberId(), model.getQuestId());
        if (!assigned) {
            throw new QuestNotAssignedException("");
        }

        // 再检查是否重复提交申请
        boolean repeated = fiMapper.checkMemberIdAndQuestIdExists(model.getMemberId(), model.getQuestId());
        if (repeated) {
            throw new RequestRepeatedException("");
        }

        // 领取表状态变为"03: 已提交"
        assignMapper.updateStatus(model.getMemberId(), model.getQuestId(), AssignStatus.SUBMITTED.code());

        Date now = new Date();
        model.setStatus(RequestStatus.WAIT_AUDIT.code());
        model.setCreatedTime(now);

        // 设置冗余字段username
        MemberModel m = memMapper.selectByPrimaryKey(model.getMemberId());
        model.setUsername(m.getUsername());
        // 设置冗余字段emp_id
        QuestModel quest = questMapper.selectByPrimaryKey(model.getQuestId());
        model.setEmpId(quest.getMemberId());
        // 设置冗余字段cate_name
        String cateName = questCateMapper.selectByPrimaryKey(quest.getQuestCateId()).getName();
        model.setCateName(cateName);

        fiMapper.insertSelective(model);
        Integer reqId = model.getId();



        // 投递24小时后自动通过定时任务
        // 构造参数列表
        Map<String, String> parmMap = new HashMap<>();
        parmMap.put("taskId", reqId.toString());
        parmMap.put("reqId", reqId.toString());

        // 构造消息体
        MsgProtocol msg = new MsgProtocol.Builder(
                MsgType.DATE_STYLE,
                "localhost",
                8080,
                RestScheduleCtr.fullUrl(RestScheduleCtr.URL_AUTO_AUDIT),
                "GET",
                TimeUtil.calculateDate(new Date(), Calendar.HOUR_OF_DAY, 24)
        ).setParmMap(parmMap)
                .build();


        // 序列化成JSON
        String json = JSON.toJSONString(msg);
        if (logger.isDebugEnabled()) {
            logger.debug("sending message: {}", json);
        }

        // 发布消息
        Jedis jedis = JedisUtils.getClient(jedisPool);
        Long recvAmt = jedis.publish(ScheduleChannel.POST_JOB.code(), json);
        if (recvAmt.longValue() <= 0 ) {
            LogUtils.getErrorLogger().error("schedule center failed to receive task!");
        }
        JedisUtils.returnJedis(jedisPool, jedis);

    }

    /**
     * 更新审核状态
     * @param requestId
     * @param status
     * @param memo
     */
    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public void updateStatus(Integer requestId, RequestStatus status, String memo)
            throws CashAccNotExistsException, RequestNotExistException, RequestCannotChangeException, AuditNotEnoughException, NotEnoughCouponException, HackException {

        FinishRequestModel req = fiMapper.selectByPrimaryKey(requestId);
        if (null == req) {
            throw new RequestNotExistException("");
        }

        // 判断任务申请状态
        // 如果状态为最终状态，则不允许再修改状态
        RequestStatus st = RequestStatus.fromCode(req.getStatus());
        if (RequestStatus.isFinalStatus(st)) {
            throw new RequestCannotChangeException();
        }

        QuestModel quest = questMapper.selectByPrimaryKey(req.getQuestId());
        CashAccModel acc = accMapper.findByMemberId(req.getMemberId());

        // 如果是审核通过
        if (status == RequestStatus.EMP_PASSED || status == RequestStatus.TLJ_PASSED || status == RequestStatus.AUTO_PASSED) {
            // 则向账户加钱
            BigDecimal amt = quest.getAward();
            accService.addAvailableMoney(acc.getId(), amt, AccFlow.AWARD);

            // 如果任务有coupon, 则领取一张coupon
            Boolean hasCoupon = quest.getCoupon();
            if (hasCoupon) {
                couponService.acquireCoupon(quest.getId(), req.getMemberId());
            }

            // todo 如果是tlj审核, 则代审核申请 -1
            if (status == RequestStatus.TLJ_PASSED) {
                auditService.decreaseLeftAmtByQuest(quest.getId());
            }

        } else if (status == RequestStatus.TLJ_FAILED || status == RequestStatus.EMP_FAILED) {
            // 如果审核失败
            // 则删除任务领取记录
            assignMapper.deleteAssign(req.getMemberId(), quest.getId());

            // 同时释放任务剩余数量
            questMapper.increaseLeftAmount(quest.getId());
        }


        // 更新数据库
        FinishRequestModel example = new FinishRequestModel();
        example.setId(requestId);
        example.setStatus(status.code());
        example.setMemo(memo);
        example.setAuditTime(new Date());
        fiMapper.updateByPrimaryKeySelective(example);
    }

    @Override
    @Transactional(readOnly = true)
    public ListResult<FinishRequestModel> findAll(int pn, int ps) {
        FinishRequestModel example = new FinishRequestModel(pn, ps);

        List<FinishRequestModel> list = fiMapper.findBy(example);
        long tot = fiMapper.countFindBy(example);

        return new ListResult<>(list, tot);
    }

    @Override
    @Transactional(readOnly = true)
    public ListResult<FinishRequestModel> findGroupByQuest(Integer empId, int pn, int ps) {
        // 查任务提交表, 执行group操作
        List<FinishRequestModel> fiList = fiMapper.selectByEmpIdGroupByQuest(empId, pn, ps);
        if (fiList.isEmpty()) {
            return new ListResult<>();
        }
        List<Long> totList = fiMapper.countSelectByEmpIdGroupByQuest(empId);

        // 查询任务提交表对应的任务信息
        List<Integer> idList = fiList.stream()
                .map(FinishRequestModel::getQuestId)
                .collect(Collectors.toList());
        List<QuestModel> questList = questMapper.selectInBatch(idList);
        // 设置到对应的quest变量中
        fiList.forEach( fi -> {
            QuestModel qu = questList.stream().filter( quest -> quest.getId().equals(fi.getQuestId()) ).findFirst().get();
            fi.setQuest(qu);
        });


        //long tot = totList.stream().reduce(0L, (sum, elem) -> sum + elem);


        return new ListResult<>(fiList, totList.size());
    }

    @Override
    @Transactional(readOnly = true)
    public ListResult<FinishRequestModel> findByStatus(RequestStatus status, int pn, int ps) {
        FinishRequestModel example = new FinishRequestModel(pn, ps);
        example.setStatus(status.code());

        List<FinishRequestModel> list = fiMapper.findBy(example);
        long tot = fiMapper.countFindBy(example);

        return new ListResult<>(list, tot);
    }

    @Override
    @Transactional(readOnly = true)
    public ListResult<FinishRequestModel> findByMember(Integer memId, int pn, int ps) {
        FinishRequestModel example = new FinishRequestModel(pn, ps);
        example.setMemberId(memId);

        List<FinishRequestModel> list = fiMapper.findBy(example);
        long tot = fiMapper.countFindBy(example);

        return new ListResult<>(list, tot);
    }

    @Override
    @Transactional(readOnly = true)
    public ListResult<FinishRequestModel> findByMember(Integer memId, RequestStatus status, int pn, int ps) {
        FinishRequestModel example = new FinishRequestModel(pn, ps);
        example.setMemberId(memId);
        if (null != status) {
            example.setStatus(status.code());
        }

        List<FinishRequestModel> list = fiMapper.findBy(example);
        long tot = fiMapper.countFindBy(example);

        return new ListResult<>(list, tot);
    }

    @Override
    @Transactional(readOnly = true)
    public ListResult<FinishRequestModel> findByQuest(Integer questId, RequestStatus status, int pn, int ps) {
        FinishRequestModel example = new FinishRequestModel(pn, ps);
        example.setQuestId(questId);
        if (null != status) {
            example.setStatus(status.code());
        }

        List<FinishRequestModel> list = fiMapper.findBy(example);
        long tot = fiMapper.countFindBy(example);

        return new ListResult<>(list, tot);
    }

    @Override
    @Transactional(readOnly = true)
    public FinishRequestModel findById(Integer reqId) {
        return fiMapper.selectByPrimaryKey(reqId);
    }
}

package com.fh.taolijie.service.quest.impl;

import com.alibaba.fastjson.JSON;
import com.fh.taolijie.cache.message.model.MsgProtocol;
import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.constant.MsgType;
import com.fh.taolijie.constant.ScheduleChannel;
import com.fh.taolijie.constant.quest.AssignStatus;
import com.fh.taolijie.dao.mapper.*;
import com.fh.taolijie.domain.QuestCollRelModel;
import com.fh.taolijie.domain.QuestSchRelModel;
import com.fh.taolijie.domain.acc.MemberModel;
import com.fh.taolijie.domain.quest.QuestAssignModel;
import com.fh.taolijie.domain.quest.QuestModel;
import com.fh.taolijie.exception.checked.ObjectGenerationException;
import com.fh.taolijie.exception.checked.acc.BalanceNotEnoughException;
import com.fh.taolijie.exception.checked.acc.CashAccNotExistsException;
import com.fh.taolijie.exception.checked.quest.*;
import com.fh.taolijie.service.acc.CashAccService;
import com.fh.taolijie.service.quest.QuestService;
import com.fh.taolijie.utils.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.applet.resources.MsgAppletViewer_zh_TW;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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

    @Qualifier("redisTemplateForString")
    @Autowired
    StringRedisTemplate rt;

    /**
     * 商家发布任务.
     *
     * 要先检查商家账户余额是否充足
     * @param accId 商家账户id
     * @param model
     */
    @Override
    @Transactional(readOnly = false)
    public void publishQuest(Integer accId, QuestModel model)
            throws BalanceNotEnoughException, CashAccNotExistsException {

        // 计算单个任务的最终价格
        // 获取费率
        int rate = configMapper.selectByPrimaryKey(1).getQuestFeeRate();
        // 单个任务的原始价格
        BigDecimal single = model.getAward();
        // 单个任务的最终价格
        single = feeCal.calculateFee(rate, single);
        // 将最终价格设置到model中
        model.setFinalAward(single);
        // 计算总钱数
        BigDecimal tot = single.multiply(new BigDecimal(model.getTotalAmt()));


        // 扣钱
        accService.reduceAvailableMoney(accId, tot);

        // 发布任务
        model.setCreatedTime(new Date());
        questMapper.insertSelective(model);

        Integer questId = model.getId();

        // 添加任务对象关联信息
        List<Integer> collList = model.getCollegeIdList();
        List<Integer> schList = model.getSchoolIdList();

        // 关联学校名
        if (!collList.isEmpty()) {
            List<QuestCollRelModel> qcList = collList.stream()
                    .map( id -> new QuestCollRelModel(questId, id) )
                    .collect(Collectors.toList());

            addQuestCollegeRel(qcList);
        }

        // 关联学院名
        if (!schList.isEmpty()) {
            List<QuestSchRelModel> qsList = schList.stream()
                    .map( id -> new QuestSchRelModel(questId, id) )
                    .collect(Collectors.toList());

            addQuestSchoolRel(qsList);

        }
    }

    @Override
    @Transactional(readOnly = false)
    public void addQuestCollegeRel(List<QuestCollRelModel> list) {
        if (list.isEmpty()) {
            return;
        }

        qcMapper.insertInBatch(list);
    }

    @Override
    @Transactional(readOnly = false)
    public void addQuestSchoolRel(List<QuestSchRelModel> list) {
        if (list.isEmpty()) {
            return;
        }

        qsMapper.insertInBatch(list);
    }

    /**
     * 领取任务。
     * 需要行锁。
     *
     * @param memId 领取任务的用户id.
     * @param questId 任务id
     */
    @Override
    @Transactional(readOnly = false)
    public void assignQuest(Integer memId, Integer questId)
            throws QuestAssignedException, QuestZeroException, QuestNotFoundException, QuestExpiredException, QuestNotStartException {

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
        rt.execute( (RedisConnection redisConn) -> {
            StringRedisConnection strConn = (StringRedisConnection) redisConn;

            // 构造消息体
            MsgProtocol msg = new MsgProtocol();
            msg.setBeanName("QuestExpiredJob");
            msg.setType(MsgType.DATE_STYLE.code());
            // 2小时以 后执行
            msg.setExeAt(TimeUtil.calculateDate(new Date(), Calendar.HOUR_OF_DAY, 2));
            //msg.setExeAt(TimeUtil.calculateDate(new Date(), Calendar.SECOND, 10));
            // 构造参数列表
            List<Object> parmList = new ArrayList<>(2);
            parmList.add(assignId);
            parmList.add(AssignStatus.ENDED.code());
            msg.setParmList(parmList);

            // 序列化成JSON
            String json = JSON.toJSONString(msg);
            if (logger.isDebugEnabled()) {
                logger.debug("sending message: {}", json);
            }

            // 发布消息
            strConn.publish(ScheduleChannel.POST_JOB.code(), json);

            return null;
        });

        // 方法结束 == 事务结束，行锁释放
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

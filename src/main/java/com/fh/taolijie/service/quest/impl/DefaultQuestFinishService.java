package com.fh.taolijie.service.quest.impl;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.constant.quest.RequestStatus;
import com.fh.taolijie.dao.mapper.*;
import com.fh.taolijie.domain.acc.CashAccModel;
import com.fh.taolijie.domain.quest.FinishRequestModel;
import com.fh.taolijie.domain.acc.MemberModel;
import com.fh.taolijie.domain.quest.QuestModel;
import com.fh.taolijie.exception.checked.acc.CashAccNotExistsException;
import com.fh.taolijie.exception.checked.quest.QuestNotAssignedException;
import com.fh.taolijie.exception.checked.quest.RequestNotExistException;
import com.fh.taolijie.exception.checked.quest.RequestRepeatedException;
import com.fh.taolijie.service.acc.CashAccService;
import com.fh.taolijie.service.quest.QuestFinishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 任务提交申请业务实现
 * Created by whf on 9/22/15.
 */
@Service
public class DefaultQuestFinishService implements QuestFinishService {
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

    @Override
    @Transactional(readOnly = false)
    public void submitRequest(FinishRequestModel model)
            throws QuestNotAssignedException, RequestRepeatedException {

        // 先检查用户是否已经领取该任务
        boolean assigned = assignMapper.checkMemberIdAndQuestIdExists(model.getMemberId(), model.getQuestId());
        if (!assigned) {
            throw new QuestNotAssignedException("");
        }

        // 再检查是否重复提交申请
        boolean repeated = fiMapper.checkMemberIdAndQuestIdExists(model.getMemberId(), model.getQuestId());
        if (repeated) {
            throw new RequestRepeatedException("");
        }

        Date now = new Date();
        model.setStatus(RequestStatus.WAIT_AUDIT.code());
        model.setCreatedTime(now);

        // 设置冗余字段username
        MemberModel m = memMapper.selectByPrimaryKey(model.getMemberId());
        model.setUsername(m.getUsername());

        fiMapper.insertSelective(model);
    }

    /**
     * 更新审核状态
     * @param requestId
     * @param status
     * @param memo
     */
    @Override
    @Transactional(readOnly = false)
    public void updateStatus(Integer requestId, RequestStatus status, String memo)
            throws CashAccNotExistsException, RequestNotExistException {

        FinishRequestModel req = fiMapper.selectByPrimaryKey(requestId);
        if (null == req) {
            throw new RequestNotExistException("");
        }

        QuestModel quest = questMapper.selectByPrimaryKey(req.getQuestId());
        CashAccModel acc = accMapper.findByMemberId(req.getMemberId());

        // 如果是审核通过
        // 则向账户加钱
        if (status == RequestStatus.DONE) {
            BigDecimal amt = quest.getAward();
            accService.addAvailableMoney(acc.getId(), amt);

        } else if (status == RequestStatus.FAILED) {
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
        example.setStatus(status.code());

        List<FinishRequestModel> list = fiMapper.findBy(example);
        long tot = fiMapper.countFindBy(example);

        return new ListResult<>(list, tot);
    }
}

package com.fh.taolijie.service.acc.impl;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.constant.acc.AccFlow;
import com.fh.taolijie.dao.mapper.AccFlowModelMapper;
import com.fh.taolijie.dao.mapper.CashAccModelMapper;
import com.fh.taolijie.domain.AccFlowModel;
import com.fh.taolijie.domain.CashAccModel;
import com.fh.taolijie.exception.checked.acc.CashAccNotExistsException;
import com.fh.taolijie.service.acc.AccFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 账户流水查询业务实现
 * Created by whf on 9/21/15.
 */
@Service
public class DefaultAccFlowService implements AccFlowService {
    @Autowired
    private AccFlowModelMapper flowMapper;

    @Autowired
    private CashAccModelMapper accMapper;

    @Override
    @Transactional(readOnly = false)
    public void recordAvaBalanceChange(Integer accId, AccFlow type, BigDecimal amt)
            throws CashAccNotExistsException {

        CashAccModel acc = accMapper.selectByPrimaryKey(accId);
        if (null == acc) {
            throw new CashAccNotExistsException("");
        }

        // 创建流水
        AccFlowModel flowModel = new AccFlowModel();
        flowModel.setAccId(accId);
        flowModel.setActionType(type.code());
        flowModel.setCreatedTime(new Date());

        // 记录可用余额变化
        flowModel.setAvaBalanceCh(amt);
        flowModel.setAvaBalanceNew(amt.add(acc.getAvailableBalance()));


        flowMapper.insertSelective(flowModel);

    }

    @Override
    @Transactional(readOnly = true)
    public ListResult<AccFlowModel> findByAcc(Integer accId, Date start, Date end, int pn, int ps) {
        List<AccFlowModel> list = flowMapper.selectByAccId(accId, start, end, pn, ps);
        long tot = flowMapper.countSelectByAccId(accId, start, end);

        return new ListResult<>(list, tot);
    }

    @Override
    @Transactional(readOnly = true)
    public ListResult<AccFlowModel> findByAccAndInterval(Integer accId, Date start, Date end, int pn, int ps) {
        List<AccFlowModel> list = flowMapper.selectByAccIdAndInterval(accId, start, end, pn, ps);
        long tot = flowMapper.countSelectByAccIdAndInterval(accId, start, end);

        return new ListResult<>(list, tot);
    }
}

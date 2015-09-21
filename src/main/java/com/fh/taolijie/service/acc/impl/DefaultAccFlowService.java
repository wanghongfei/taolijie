package com.fh.taolijie.service.acc.impl;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.dao.mapper.AccFlowModelMapper;
import com.fh.taolijie.domain.AccFlowModel;
import com.fh.taolijie.service.acc.AccFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    @Transactional(readOnly = true)
    public ListResult<AccFlowModel> findByAcc(Integer accId, int pn, int ps) {
        List<AccFlowModel> list = flowMapper.selectByAccId(accId, pn, ps);
        long tot = flowMapper.countSelectByAccId(accId);

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

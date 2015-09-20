package com.fh.taolijie.service.quest.impl;

import com.fh.taolijie.constant.quest.CashAccStatus;
import com.fh.taolijie.dao.mapper.CashAccModelMapper;
import com.fh.taolijie.dao.mapper.MemberModelMapper;
import com.fh.taolijie.domain.CashAccModel;
import com.fh.taolijie.domain.MemberModel;
import com.fh.taolijie.exception.checked.UserNotExistsException;
import com.fh.taolijie.exception.checked.quest.CashAccExistsException;
import com.fh.taolijie.service.quest.CashAccService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by whf on 9/20/15.
 */
@Service
public class DefaultCashAccService implements CashAccService {
    @Autowired
    private CashAccModelMapper accMapper;

    @Autowired
    private MemberModelMapper memMapper;

    @Override
    @Transactional(readOnly = false)
    public void addAcc(CashAccModel model) throws CashAccExistsException, UserNotExistsException {
        if (checkCashAccExists(model.getMemberId())) {
            throw new CashAccExistsException("用户" + model.getMemberId() + "的现金账户已经存在");
        }

        // 设置username冗余字段
        MemberModel m = memMapper.selectByPrimaryKey(model.getMemberId());
        if (null == m) {
            throw new UserNotExistsException("");
        }
        model.setUsername(m.getUsername());

        model.setUpdateTime(new Date());
        accMapper.insertSelective(model);
    }

    /**
     * 检查指定用户的现金账户是否已经开通了
     * @param memId
     * @return
     */
    public boolean checkCashAccExists(Integer memId) {
        return accMapper.checkAccExists(memId);
    }

    @Override
    public void updateStatus(Integer accId, CashAccStatus status) {

    }

    @Override
    public void addMoney(Integer accId, BigDecimal amt) {

    }

    @Override
    public void reduceMoney(Integer accId, BigDecimal amt) {

    }

    @Override
    public void frozenMoney(Integer accId, BigDecimal amt) {

    }

    @Override
    public CashAccModel findByMember(Integer memId) {
        return null;
    }

    @Override
    public CashAccModel findByAcc(Integer accId) {
        return null;
    }
}

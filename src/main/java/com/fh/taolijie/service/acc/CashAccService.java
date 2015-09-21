package com.fh.taolijie.service.acc;

import com.fh.taolijie.constant.quest.CashAccStatus;
import com.fh.taolijie.domain.CashAccModel;
import com.fh.taolijie.exception.checked.UserNotExistsException;
import com.fh.taolijie.exception.checked.quest.BalanceNotEnoughException;
import com.fh.taolijie.exception.checked.quest.CashAccExistsException;
import com.fh.taolijie.exception.checked.quest.CashAccNotExistsException;

import java.math.BigDecimal;

/**
 * Created by whf on 9/20/15.
 */
public interface CashAccService {
    /**
     * 创建账户
     * @param memId
     * @exception CashAccExistsException 该用户已经有现金账户了
     * @exception UserNotExistsException memberId不存在
     */
    void addAcc(CashAccModel model)
            throws CashAccExistsException, UserNotExistsException;

    /**
     * 检查指定用户的现金账户是否已经存在了
     * @param memId
     * @return
     */
    boolean checkCashAccExists(Integer memId);

    /**
     * 更新账户状态
     * @param accId
     * @param status
     */
    void updateStatus(Integer accId, CashAccStatus status)
            throws CashAccNotExistsException;

    /**
     * 根据id检查现金账户是否存在
     * @param accId
     * @return
     */
    boolean checkAccIdExists(Integer accId);

    /**
     * 添加可用余额
     * @param accId
     * @param amt
     */
    boolean addAvailableMoney(Integer accId, BigDecimal amt)
            throws CashAccNotExistsException;

    /**
     * 减少可用余额
     * @param accId
     * @param amt
     */
    void reduceAvailableMoney(Integer accId, BigDecimal amt)
            throws CashAccNotExistsException, BalanceNotEnoughException;

    /**
     * 冻结余额
     * @param accId
     * @param amt
     */
    void frozenMoney(Integer accId, BigDecimal amt)
            throws CashAccNotExistsException, BalanceNotEnoughException ;

    /**
     * 根据用户id查找现金账户
     * @param memId
     * @return
     */
    CashAccModel findByMember(Integer memId);

    /**
     * 根据账户id查找
     * @param accId
     * @return
     */
    CashAccModel findByAcc(Integer accId);
}

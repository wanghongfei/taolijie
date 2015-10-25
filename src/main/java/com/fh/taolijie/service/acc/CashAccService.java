package com.fh.taolijie.service.acc;

import com.fh.taolijie.constant.acc.AccFlow;
import com.fh.taolijie.constant.acc.CashAccStatus;
import com.fh.taolijie.domain.SeQuestionModel;
import com.fh.taolijie.domain.acc.CashAccModel;
import com.fh.taolijie.exception.checked.UserNotExistsException;
import com.fh.taolijie.exception.checked.acc.BalanceNotEnoughException;
import com.fh.taolijie.exception.checked.acc.CashAccExistsException;
import com.fh.taolijie.exception.checked.acc.CashAccNotExistsException;
import com.fh.taolijie.exception.checked.acc.SecretQuestionExistException;

import java.math.BigDecimal;

/**
 * Created by whf on 9/20/15.
 */
public interface CashAccService {
    /**
     * 创建账户
     * @exception CashAccExistsException 该用户已经有现金账户了
     * @exception UserNotExistsException memberId不存在
     */
    void addAcc(CashAccModel model, SeQuestionModel question)
            throws CashAccExistsException, UserNotExistsException, SecretQuestionExistException;

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
     * 更新支付宝
     * @param accId
     * @param alipay
     * @throws CashAccNotExistsException
     */
    void updateAlipay(Integer accId, String alipay)
            throws CashAccNotExistsException;

    /**
     * 更新银行卡号
     * @param accId
     * @param bank
     * @throws CashAccNotExistsException
     */
    void updateBankAcc(Integer accId, String bank)
            throws CashAccNotExistsException;

    /**
     * 修改手机号
     * @param accId
     * @param phone
     */
    void updatePhone(Integer accId, String phone);

    /**
     * 修改交易密码
     * @param memId
     * @param pwd
     */
    void updateDealPwd(Integer memId, String pwd) throws CashAccNotExistsException;

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

    boolean addAvailableMoney(Integer accId, BigDecimal amt, AccFlow type)
            throws CashAccNotExistsException;

    /**
     * 减少可用余额
     * @param accId
     * @param amt
     */
    void reduceAvailableMoney(Integer accId, BigDecimal amt)
            throws CashAccNotExistsException, BalanceNotEnoughException;

    void reduceAvailableMoney(Integer accId, BigDecimal amt, AccFlow type)
            throws CashAccNotExistsException, BalanceNotEnoughException;

    /**
     * @deprecated
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
     * 根据用户id查询现金账户id
     * @param memId
     * @return
     */
    Integer findIdByMember(Integer memId);

    /**
     * 根据账户id查找
     * @param accId
     * @return
     */
    CashAccModel findByAcc(Integer accId);
}

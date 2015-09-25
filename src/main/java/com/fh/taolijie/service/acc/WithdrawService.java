package com.fh.taolijie.service.acc;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.constant.acc.WithdrawStatus;
import com.fh.taolijie.domain.WithdrawApplyModel;
import com.fh.taolijie.exception.checked.acc.BalanceNotEnoughException;
import com.fh.taolijie.exception.checked.acc.CashAccNotExistsException;
import com.fh.taolijie.exception.checked.acc.WithdrawNotExistsException;

/**
 * 提现申请相关业务接口
 * Created by whf on 9/21/15.
 */
public interface WithdrawService {
    /**
     * 创建提现申请
     * @param model
     */
    void addWithdraw(WithdrawApplyModel model)
            throws CashAccNotExistsException, BalanceNotEnoughException;

    /**
     * 更新状态
     * @param withId
     * @param status 新状态
     * @param memo 审核备注
     * @return 成功返回true
     */
    boolean updateStatus(Integer withId, WithdrawStatus status, String memo)
            throws WithdrawNotExistsException, CashAccNotExistsException, BalanceNotEnoughException;

    /**
     * 查询所有申请
     * @param pn
     * @param ps
     * @return
     */
    ListResult<WithdrawApplyModel> findAll(int pn, int ps);

    /**
     * 根据状态查询申请
     * @param status
     * @param pn
     * @param ps
     * @return
     */
    ListResult<WithdrawApplyModel> findAllByStatus(WithdrawStatus status, int pn, int ps);

    /**
     * 查询指定用户的申请
     * @param memId
     * @param pn
     * @param ps
     * @return
     */
    ListResult<WithdrawApplyModel> findByMember(Integer memId, int pn, int ps);

    /**
     * 查询指定用户指定状态的申请
     * @param memId
     * @param status
     * @param pn
     * @param ps
     * @return
     */
    ListResult<WithdrawApplyModel> findByMember(Integer memId, WithdrawStatus status, int pn, int ps);
}

package com.fh.taolijie.service.quest;

import com.fh.taolijie.constant.quest.RequestStatus;
import com.fh.taolijie.domain.FinishRequestModel;
import com.fh.taolijie.exception.checked.acc.CashAccNotExistsException;
import com.fh.taolijie.exception.checked.quest.QuestNotAssignedException;
import com.fh.taolijie.exception.checked.quest.RequestRepeatedException;

/**
 * 任务提交审核业务接口
 * Created by whf on 9/22/15.
 */
public interface QuestFinishService {
    /**
     * 提交任务完成申请
     * @param model
     */
    void submitRequest(FinishRequestModel model)
            throws QuestNotAssignedException, RequestRepeatedException;

    /**
     * 更新审核状态
     * @param requestId
     * @param status
     * @param memo
     */
    void updateStatus(Integer requestId, RequestStatus status, String memo)
            throws CashAccNotExistsException;
}

package com.fh.taolijie.cache.message;

import com.fh.taolijie.cache.message.model.MsgProtocol;
import com.fh.taolijie.constant.quest.RequestStatus;
import com.fh.taolijie.exception.checked.InvalidMessageException;
import com.fh.taolijie.exception.checked.acc.CashAccNotExistsException;
import com.fh.taolijie.exception.checked.quest.RequestCannotChangeException;
import com.fh.taolijie.exception.checked.quest.RequestNotExistException;
import com.fh.taolijie.service.quest.QuestFinishService;
import com.fh.taolijie.utils.LogUtils;
import com.fh.taolijie.utils.MessageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by whf on 9/30/15.
 */
@Component
public class AutoAuditListener extends MessageListenerAdapter {
    private static Logger logger = LoggerFactory.getLogger(AutoAuditListener.class);

    @Autowired
    private QuestFinishService fiService;



    @Override
    public void onMessage(Message message, byte[] pattern) {
        if (logger.isDebugEnabled()) {
            logger.debug("收到Redis消息");
        }

        // 解码
        try {
            MsgProtocol msg = MessageUtils.deserailize(message.getBody());
            if (logger.isDebugEnabled()) {
                logger.debug("解码结果: {}", msg.toString());
            }

            doAutoAudit((Integer)msg.getParmList().get(0));

        } catch (InvalidMessageException e) {
            e.printStackTrace();

            // 记日志
            LogUtils.logException(e);
        }
    }

    @Transactional(readOnly = false)
    private void doAutoAudit(Integer requestId) {
        try {
            fiService.updateStatus(requestId, RequestStatus.AUTO_PASSED, "");
        } catch (Exception e) {
            e.printStackTrace();;
        }

    }
}

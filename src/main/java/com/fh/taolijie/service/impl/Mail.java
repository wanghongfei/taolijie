package com.fh.taolijie.service.impl;

import com.fh.taolijie.service.pool.FixSizeThreadPool;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.LogUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

/**
 * Created by wanghongfei on 15-6-5.
 */
@Service
public class Mail {
    private static Logger logger = LoggerFactory.getLogger(Mail.class);

    @Autowired
    MailSender sender;

    @Autowired
    FixSizeThreadPool pool;

    /**
     * 发送邮件，该方法会block直到发送完成
     * @param content
     * @param type
     * @param toAddresses
     * @throws MailException
     */
    public void sendMail(String content, Constants.MailType type, String... toAddresses) throws MailException {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setSubject(type.toString());
        msg.setFrom(Constants.SENDER_EMAIL);
        msg.setTo(toAddresses);
        msg.setText(content);

        sender.send(msg);
    }

    /**
     * 异步发送邮件，方法立刻返回
     * @param content
     * @param type
     * @param toAddresses
     */
    public void sendMailAsync(String content, Constants.MailType type, String... toAddresses) {
        logger.info("发送邮件给");

        if (logger.isDebugEnabled()) {
            logger.debug("类型 = {}, 内容 = {}", type, content);
        }

        Thread worker = new Thread( () -> {
            try {
                sendMail(content,type, toAddresses);
            } catch (MailException ex) {
                logger.info("发送失败，尝试重新发送");
                ex.printStackTrace();

                // 写入错误日志
                String msg = LogUtils.getStackTrace(ex);
                LogUtils.getErrorLogger().error("一次发送失败. type = {},  content = {}, msg = {}", type, content, msg);

                // 尝试重新发送一次
                try {
                    sendMail(content,type, toAddresses);
                } catch (MailException e) {
                    // 写入错误日志
                    // 不再尝试重新发送
                    msg = LogUtils.getStackTrace(ex);
                    LogUtils.getErrorLogger().error("二次发送失败. type = {},  content = {}, msg = {}", type, content, msg);
                }
            }
        });

        // 向线程池提交一个任务
        pool.getPool().submit(worker);
    }
}

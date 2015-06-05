package com.fh.taolijie.service.impl;

import com.fh.taolijie.utils.Constants;
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

    /**
     * 发送邮件，该方法会block直到发送完成
     * @param content
     * @param toAddresses
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
     * 异步发送邮件，方法立刻返回。
     * @param content
     * @param toAddresses
     */
    public void sendMailAsync(String content, Constants.MailType type, String... toAddresses) {
        logger.info("发送邮件");
        new Thread( () -> {
            try {
                sendMail(content,type, toAddresses);
            } catch (MailException ex) {
                // 尝试重新发送一次
                logger.info("发送失败，尝试重新发送");
                ex.printStackTrace();

                try {
                    sendMail(content,type, toAddresses);
                } catch (MailException e) {
                    logger.error("二次邮件发送失败");
                    ex.printStackTrace();
                }
            }
        }).start();
    }
}

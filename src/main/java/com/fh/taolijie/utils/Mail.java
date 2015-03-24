package com.fh.taolijie.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

/**
 * 负责发送邮件
 */
@Component
public class Mail {
    @Autowired
    MailSender sender;

    /**
     * 发送邮件，该方法会block直到发送完成
     * @param content
     * @param toAddresses
     */
    public void sendMail(String content, String... toAddresses) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(Constants.SENDER_EMAIL);
        msg.setTo(toAddresses);
        msg.setText(content);

        try {
            sender.send(msg);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 异步发送邮件，方法立刻返回。
     * @param content
     * @param toAddresses
     */
    public void sendMailAsync(String content, String... toAddresses) {
        new Thread( () -> {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setFrom(Constants.SENDER_EMAIL);
            msg.setTo(toAddresses);
            msg.setText(content);

            sender.send(msg);
        }).start();
    }
}

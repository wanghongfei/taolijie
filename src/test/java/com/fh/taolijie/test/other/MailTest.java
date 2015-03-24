package com.fh.taolijie.test.other;

import com.fh.taolijie.utils.Mail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Properties;

/**
 * Created by wanghongfei on 15-3-24.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        Mail.class,
        MailTest.ConfigBean.class
})
public class MailTest {
    @Autowired
    Mail mail;

    @Test
    public void testSendMail() {
        mail.sendMail("brucewhf@gmail.com", "ERROR!!!!!");
    }

    @Configuration
    public static class ConfigBean {
        @Bean
        public JavaMailSenderImpl mailSender() {
            JavaMailSenderImpl sender = new JavaMailSenderImpl();

            sender.setUsername("wfc5582563@126.com");
            sender.setPassword("wang700327");

            sender.setHost("smtp.126.com");
            sender.setPort(25);

            Properties prop = new Properties();
            prop.put("mail.transport.protocol", "smtp");
            prop.put("mail.smtp.auth", true);
            prop.put("mail.smtp.starttls.enable", true);
            sender.setJavaMailProperties(prop);

            return sender;
        }
    }
}

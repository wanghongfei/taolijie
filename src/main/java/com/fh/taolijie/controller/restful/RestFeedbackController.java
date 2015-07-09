package com.fh.taolijie.controller.restful;

import com.fh.taolijie.component.ResponseText;
import com.fh.taolijie.service.impl.Mail;
import com.fh.taolijie.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by whf on 7/9/15.
 */
@RestController
@RequestMapping("/api/feedback")
public class RestFeedbackController {
    @Autowired
    Mail mail;

    @RequestMapping(value = "/send", method = RequestMethod.POST, produces = Constants.Produce.JSON)
    public ResponseText sendFeedback(@RequestParam String content,
                                     @RequestParam String email) {

        mail.sendMailAsync(content, Constants.MailType.FEEDBACK, email);
        return new ResponseText();
    }
}

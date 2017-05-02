package com.rbkmoney.dudoser.utils.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Map;

@Component
public class MailSenderUtils {

    private static Logger log = LoggerFactory.getLogger(MailSenderUtils.class);

    @Autowired
    JavaMailSender mailSender;

    public boolean send(String from, String to, String subject, String text, List<Map.Entry<String, byte[]>> listAttach) {
        boolean isSuccess = false;
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            if (listAttach != null) {
                for (Map.Entry<String, byte[]> pair : listAttach) {
                    helper.addAttachment(pair.getKey(), new ByteArrayResource(pair.getValue()));
                }
            }
            helper.setText(text, true);
            mailSender.send(message);
            isSuccess = true;
        } catch (Exception e) {
            log.error("Exception MailSenderUtils. From: {}, to: {}, subject: {}", from ,to, subject, e);
        }

        return isSuccess;
    }
}


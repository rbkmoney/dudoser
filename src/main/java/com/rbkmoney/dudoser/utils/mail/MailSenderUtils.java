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

@Component
public class MailSenderUtils {

    private static Logger log = LoggerFactory.getLogger(MailSenderUtils.class);

    @Autowired
    JavaMailSender mailSender;

    public boolean send(String from, String to, String subject, String text, List<Pair> listAttach) {
        boolean isSuccess = false;

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            if (listAttach != null) {
                for (Pair pair : listAttach) {
                    helper.addAttachment(pair.getName(), new ByteArrayResource(pair.getData()));
                }
            }
            log.info("Template content: {}", text);
            helper.setText(text, true);
            mailSender.send(message);
            isSuccess = true;
        } catch (Exception e) {
            log.error("Exception MailUtils", e);
        }

        return isSuccess;
    }

    public static class Pair {
        private String name;
        private byte[] data;

        public Pair(String name, byte[] data) {
            this.name = name;
            this.data = data;
        }

        public String getName() {
            return name;
        }
        public byte[] getData() {
            return data;
        }
    }
}


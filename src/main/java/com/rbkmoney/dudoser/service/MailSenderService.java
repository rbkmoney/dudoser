package com.rbkmoney.dudoser.service;

import com.rbkmoney.dudoser.exception.MailNotSendException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

import java.util.List;
import java.util.Map;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class MailSenderService {

    private final List<JavaMailSender> mailSenders;

    public void send(String from, String[] to, String subject, String text, List<Map.Entry<String, byte[]>> listAttach)
            throws MailNotSendException {
        try {
            JavaMailSender mailSender = getRandomMailSender();
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
        } catch (Exception e) {
            throw new MailNotSendException("Couldn't send mail", e);
        }
    }

    private JavaMailSender getRandomMailSender() {
        return mailSenders.get(new Random().nextInt(mailSenders.size()));
    }
}


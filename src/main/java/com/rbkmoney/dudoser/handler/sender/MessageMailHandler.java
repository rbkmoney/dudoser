package com.rbkmoney.dudoser.handler.sender;

import com.rbkmoney.damsel.base.InvalidRequest;
import com.rbkmoney.damsel.message_sender.Message;
import com.rbkmoney.damsel.message_sender.MessageMail;
import com.rbkmoney.dudoser.service.MailSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.AbstractMap;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by inal on 24.11.2016.
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class MessageMailHandler implements MessageHandler {

    private final MailSenderService mailSenderService;

    @Override
    public boolean accept(Message value) {
        return value.isSetMessageMail();
    }

    @Override
    public void handle(Message message) throws Exception {
        MessageMail mail = message.getMessageMail();
        List<Map.Entry<String, byte[]>> listAttach = null;
        if (mail.getAttachments() != null) {
            listAttach =
                    mail.getAttachments()
                            .stream()
                            .map(x -> new AbstractMap.SimpleImmutableEntry<>(x.getName(), x.getData()))
                            .collect(Collectors.toList());
            log.debug("Attach count = {}", listAttach.size());
        }
        log.info("Mail send from {} to {}. Subject: {}", mail.getFromEmail(), mail.getToEmails(), mail.getSubject());
        if (mail.getToEmails().isEmpty()) {
            throw new InvalidRequest(Collections.singletonList("Mailing list shouldn't be empty"));
        }
        mailSenderService.send(mail.getFromEmail(), mail.getToEmails().toArray(new String[mail.getToEmails().size()]),
                mail.getSubject(), mail.getMailBody().getText(), listAttach);
        log.info("Mail has been sent to {}", mail.getToEmails());
    }
}

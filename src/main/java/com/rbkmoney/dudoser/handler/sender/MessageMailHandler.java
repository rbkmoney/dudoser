package com.rbkmoney.dudoser.handler.sender;

import com.rbkmoney.damsel.message_sender.Message;
import com.rbkmoney.damsel.message_sender.MessageMail;
import com.rbkmoney.dudoser.exception.MailNotSendException;
import com.rbkmoney.dudoser.utils.mail.MailSenderUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by inal on 24.11.2016.
 */
@Component
public class MessageMailHandler implements MessageHandler<Message> {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MailSenderUtils mailSenderUtils;

    @Override
    public boolean accept(Message value) {
        return value.isSetMessageMail();
    }

    @Override
    public void handle(Message message) throws Exception {
        log.debug("MessageMailHandler started.");
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
        for (String to : mail.getToEmails()) {
            mailSenderUtils.send(mail.getFromEmail(), to, mail.getSubject(), mail.getMailBody().getText(), listAttach);
        }
        log.debug("MessageMailHandler end.");
    }
}

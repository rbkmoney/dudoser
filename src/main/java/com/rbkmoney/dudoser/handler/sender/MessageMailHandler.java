package com.rbkmoney.dudoser.handler.sender;

import com.rbkmoney.damsel.message_sender.Message;
import com.rbkmoney.damsel.message_sender.MessageMail;
import com.rbkmoney.dudoser.utils.mail.MailSenderUtils;
import com.rbkmoney.thrift.filter.Filter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by inal on 24.11.2016.
 */
@Component
public class MessageMailHandler implements MessageHandler<Message> {

    Logger log = LoggerFactory.getLogger(this.getClass());

    private Filter filter;

    public MessageMailHandler() {
        this.filter = o -> ((Message) o).isSetMessageMail();
    }

    @Autowired
    MailSenderUtils mailSenderUtils;

    @Override
    public void handle(Message message) {
        MessageMail mail = message.getMessageMail();
        List<MailSenderUtils.Pair> listAttach = null;
        if (mail.getAttachments() != null) {
            listAttach =
                    mail.getAttachments()
                            .stream()
                            .map(x -> new MailSenderUtils.Pair(x.getName(), x.getData()))
                            .collect(Collectors.toList());
        }
        for (String to : mail.getToEmails()) {
            if (mailSenderUtils.send(mail.getFromEmail(), to, mail.getSubject(), mail.getMailBody().getText(), listAttach)) {
                log.info("Mail send from {} to {}", mail.getFromEmail(), to);
            } else {
                log.error("Mail not send from {} to {}", mail.getFromEmail(), to);
            }
        }
    }

    @Override
    public Filter getFilter() {
        return filter;
    }
}

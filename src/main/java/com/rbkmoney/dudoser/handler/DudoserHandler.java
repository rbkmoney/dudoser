package com.rbkmoney.dudoser.handler;

import com.rbkmoney.damsel.base.InvalidRequest;
import com.rbkmoney.damsel.message_sender.Message;
import com.rbkmoney.damsel.message_sender.MessageMail;
import com.rbkmoney.damsel.message_sender.MessageSenderSrv;
import com.rbkmoney.dudoser.utils.mail.MailSenderUtils;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by inal on 18.11.2016.
 */
@Component
public class DudoserHandler implements MessageSenderSrv.Iface {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MailSenderUtils mailSenderUtils;

    @Override
    public void send(Message message) throws InvalidRequest, TException {
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
                log.info("Mail send {}", to);
            } else {
                log.error("Mail not send {}", to);
                throw new TException("Mail not send");
            }
        }
    }
}

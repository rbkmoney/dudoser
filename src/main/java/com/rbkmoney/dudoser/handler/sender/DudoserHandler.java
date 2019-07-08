package com.rbkmoney.dudoser.handler.sender;

import com.rbkmoney.damsel.base.InvalidRequest;
import com.rbkmoney.damsel.message_sender.Message;
import com.rbkmoney.damsel.message_sender.MessageSenderSrv;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import org.springframework.stereotype.Component;

/**
 * Created by inal on 18.11.2016.
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class DudoserHandler implements MessageSenderSrv.Iface {

    private final MessageSendHandler messageSendHandler;

    @Override
    public void send(Message message) throws TException {
        try {
            log.info("Start mail sending from {} to {}", message.getMessageMail().getFromEmail(), message.getMessageMail().getToEmails());
            messageSendHandler.handleEvent(message);
            log.info("Mail sending completed.");
        } catch (InvalidRequest e) {
            log.warn("Invalid request parameters for mail {}", message.getMessageMail().getToEmails(), e);
            throw e;
        } catch (Exception e) {
            log.warn("Mail not send to {}", message.getMessageMail().getToEmails(), e);
            throw new TException(e);
        }
    }
}

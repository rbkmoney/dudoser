package com.rbkmoney.dudoser.handler.sender;

import com.rbkmoney.damsel.base.InvalidRequest;
import com.rbkmoney.damsel.message_sender.Message;
import com.rbkmoney.damsel.message_sender.MessageSenderSrv;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by inal on 18.11.2016.
 */
@Component
public class DudoserHandler implements MessageSenderSrv.Iface {

    Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private MessageSendHandler messageSendHandler;

    @Override
    public void send(Message message) throws InvalidRequest, TException {
        messageSendHandler.handleEvent(message);
    }
}

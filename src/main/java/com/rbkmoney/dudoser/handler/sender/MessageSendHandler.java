package com.rbkmoney.dudoser.handler.sender;

import com.rbkmoney.damsel.message_sender.Message;
import com.rbkmoney.eventstock.client.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by inal on 24.11.2016.
 */
@Component
public class MessageSendHandler {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private List<MessageHandler> messageHandlers;

    public void handleEvent(Message message) {
        for (MessageHandler messageHandler : messageHandlers) {
            if (messageHandler.accept(message)) {
                messageHandler.handle(message);
                break;
            }
        }
    }
}

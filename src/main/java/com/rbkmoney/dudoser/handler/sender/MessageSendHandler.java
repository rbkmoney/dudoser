package com.rbkmoney.dudoser.handler.sender;

import com.rbkmoney.damsel.message_sender.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by inal on 24.11.2016.
 */
@RequiredArgsConstructor
@Component
public class MessageSendHandler {

    private final List<MessageHandler> messageHandlers;

    public void handleEvent(Message message) throws Exception {
        for (MessageHandler messageHandler : messageHandlers) {
            if (messageHandler.accept(message)) {
                messageHandler.handle(message);
                break;
            }
        }
    }
}

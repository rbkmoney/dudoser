package com.rbkmoney.dudoser.handler.sender;

import com.rbkmoney.damsel.message_sender.Message;

interface MessageHandler {
    boolean accept(Message value);

    void handle(Message message) throws Exception;
}

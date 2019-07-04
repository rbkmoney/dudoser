package com.rbkmoney.dudoser.dao;

import com.rbkmoney.dudoser.dao.model.MessageToSend;

import java.time.LocalDateTime;
import java.util.List;

public interface MessageDao {

    boolean store(String receiver, String subject, String text);

    List<MessageToSend> getUnsentMessages();

    boolean deleteSentMessages(LocalDateTime before);

    void markAsSent(List<MessageToSend> messages);
}

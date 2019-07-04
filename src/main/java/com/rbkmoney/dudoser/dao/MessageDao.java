package com.rbkmoney.dudoser.dao;

import com.rbkmoney.dudoser.dao.model.MessageToSend;

import java.time.Instant;
import java.util.List;

public interface MessageDao {

    boolean store(String receiver, String subject, String text);

    List<MessageToSend> getUnsentMessages();

    boolean deleteSentMessages(Instant before);

    void markAsSent(List<MessageToSend> messages);
}

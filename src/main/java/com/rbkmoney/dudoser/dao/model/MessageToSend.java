package com.rbkmoney.dudoser.dao.model;

import lombok.Data;

import java.time.Instant;

@Data
public class MessageToSend {

    String subject;
    String receiver;
    String body;
    Instant dateCreated;
    Boolean sent;

}

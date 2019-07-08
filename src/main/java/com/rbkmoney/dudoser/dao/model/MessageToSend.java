package com.rbkmoney.dudoser.dao.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageToSend {

    String subject;
    String receiver;
    String body;
    LocalDateTime dateCreated;
    Boolean sent;

}

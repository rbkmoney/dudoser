package com.rbkmoney.dudoser.dao.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PaymentPayer {

    private BigDecimal amount;
    private BigDecimal refundAmount;
    private String currency;
    private String cardType;
    private String cardMaskPan;
    private String invoiceId;
    private String paymentId;
    private String refundId;
    private String partyId;
    private String shopId;
    private String shopUrl;
    private LocalDateTime date;
    private String toReceiver;
    private Content metadata;

}

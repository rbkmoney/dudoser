package com.rbkmoney.dudoser.dao;

import java.math.BigDecimal;

public class PaymentPayer {

    private BigDecimal amount;
    private String currency;
    private String cardType;
    private String cardMaskPan;
    private String invoiceId;
    private String date;
    private String to;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAmountWithCurrency() {
        return String.format("%.2f %s", amount, currency);
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardMaskPan() {
        return cardMaskPan;
    }

    public void setCardMaskPan(String cardMaskPan) {
        this.cardMaskPan = cardMaskPan;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String dateTime) {
        this.date = dateTime;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

}

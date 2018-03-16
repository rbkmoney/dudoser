package com.rbkmoney.dudoser.dao;

import java.math.BigDecimal;
import java.util.Locale;

public class PaymentPayer{

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
    private String date;
    private String toReceiver;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(BigDecimal refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
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

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getRefundId() {
        return refundId;
    }

    public void setRefundId(String refundId) {
        this.refundId = refundId;
    }

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopUrl() {
        return shopUrl;
    }

    public void setShopUrl(String shopUrl) {
        this.shopUrl = shopUrl;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getToReceiver() {
        return toReceiver;
    }

    public void setToReceiver(String toReceiver) {
        this.toReceiver = toReceiver;
    }
}

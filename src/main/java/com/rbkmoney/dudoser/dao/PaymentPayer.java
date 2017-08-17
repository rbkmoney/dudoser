package com.rbkmoney.dudoser.dao;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PaymentPayer{

    private BigDecimal amount;
    private String currency;
    private String cardType;
    private String cardMaskPan;
    private String invoiceId;
    private String partyId;
    private String shopId;
    private String shopUrl;
    private String date;
    private String toReceiver;

    public String getAmountWithCurrency() {
        return String.format(Locale.US, "%.2f %s", amount, currency);
    }

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

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardMaskPan() {
        return "**** **** **** " + cardMaskPan;
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
        String formattedDate;
        if (date.isEmpty()) {
            formattedDate = date;
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date dateStr = formatter.parse(date);
                formattedDate = formatter.format(dateStr);
            } catch (ParseException e) {
                formattedDate = date;
            }
        }

        this.date = formattedDate;
    }

    public static void main(String[] args) {
        String date = "2016-03-22T06:12:27Z";
        String formattedDate;
        if (date.isEmpty()) {
            formattedDate = date;
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date dateStr = formatter.parse(date);
                formattedDate = formatter.format(dateStr);
            } catch (ParseException e) {
                formattedDate = date;
            }
        }
        System.out.println(formattedDate);

    }

    public String getToReceiver() {
        return toReceiver;
    }

    public void setToReceiver(String toReceiver) {
        this.toReceiver = toReceiver;
    }
}

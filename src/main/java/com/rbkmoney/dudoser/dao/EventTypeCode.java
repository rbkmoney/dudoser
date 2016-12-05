package com.rbkmoney.dudoser.dao;

/**
 * Created by inal on 30.11.2016.
 */
public enum EventTypeCode {
    INVOICE_STATUS_CHANGED("INVOICE.STATUS.CHANGED"),
    PAYMENT_STARTED("PAYMENT.STARTED");

    private final String code;

    EventTypeCode(String s) {
        this.code = s;
    }

    public String getCode() {
        return code;
    }
}

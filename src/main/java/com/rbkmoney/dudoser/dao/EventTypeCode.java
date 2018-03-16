package com.rbkmoney.dudoser.dao;

/**
 * Created by inal on 30.11.2016.
 */
public enum EventTypeCode {
    PAYMENT_STATUS_CHANGED_PROCESSED("INVOICE.PAYMENT.STATUS.CHANGED.PROCESSED"),
    REFUND_STATUS_CHANGED_SUCCEEDED("INVOICE.PAYMENT.REFUND.STATUS.CHANGED.SUCCEEDED");

    private final String code;

    EventTypeCode(String s) {
        this.code = s;
    }

    public String getCode() {
        return code;
    }
}

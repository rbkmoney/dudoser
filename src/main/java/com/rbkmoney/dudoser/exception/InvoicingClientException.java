package com.rbkmoney.dudoser.exception;

public class InvoicingClientException extends RuntimeException {

    public InvoicingClientException(String msg) {
        super(msg);
    }

    public InvoicingClientException(String msg, Throwable cause) {
        super(msg, cause);
    }
}

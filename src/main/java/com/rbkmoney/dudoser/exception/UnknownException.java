package com.rbkmoney.dudoser.exception;


public class UnknownException extends RuntimeException {

    public UnknownException(Throwable cause) {
        super(cause);
    }

    protected UnknownException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public UnknownException() {
        super();
    }

    public UnknownException(String message) {
        super(message);
    }

    public UnknownException(String message, Throwable cause) {
        super(message, cause);
    }

}

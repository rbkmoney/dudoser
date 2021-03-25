package com.rbkmoney.dudoser.exception;


public class FillTemplateException extends RuntimeException {

    public FillTemplateException(Throwable cause) {
        super(cause);
    }

    protected FillTemplateException(String message, Throwable cause, boolean enableSuppression,
                                    boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public FillTemplateException() {
        super();
    }

    public FillTemplateException(String message) {
        super(message);
    }

    public FillTemplateException(String message, Throwable cause) {
        super(message, cause);
    }

}

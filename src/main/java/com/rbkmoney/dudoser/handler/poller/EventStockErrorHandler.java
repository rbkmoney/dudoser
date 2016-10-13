package com.rbkmoney.dudoser.handler.poller;

import com.rbkmoney.eventstock.client.ErrorActionType;
import com.rbkmoney.eventstock.client.ErrorHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventStockErrorHandler implements ErrorHandler {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public ErrorActionType handleError(String subsKey, Throwable errCause) {
        String message = String.format("Subscribe id %s throw error", subsKey);
        log.error(message, errCause);
        return ErrorActionType.RETRY;
    }

}

package com.rbkmoney.dudoser.handler.poller;

import com.rbkmoney.damsel.event_stock.StockEvent;
import com.rbkmoney.dudoser.handler.Handler;
import com.rbkmoney.eventstock.client.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class EventStockHandler implements EventHandler<StockEvent> {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    List<Handler> handlers;

    public EventStockHandler(List<Handler> handlers) {
        this.handlers = handlers;
    }

    @Override
    public void handleEvent(StockEvent stockEvent, String subsKey) {
        for (Handler handler : handlers) {
            if (handler.accept(stockEvent)) {
                handler.handle(stockEvent);
                break;
            }
        }
    }

    @Override
    public void handleNoMoreElements(String subsKey) {
        log.info("HandleNoMoreElements with subsKey {}", subsKey);
    }

}

package com.rbkmoney.dudoser.handler.poller;

import com.rbkmoney.damsel.event_stock.StockEvent;
import com.rbkmoney.eventstock.client.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class EventStockHandler implements EventHandler<StockEvent> {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    List<PollingEventHandler> pollingEventHandlers;

    public EventStockHandler(List<PollingEventHandler> pollingEventHandlers) {
        this.pollingEventHandlers = pollingEventHandlers;
    }

    @Override
    public void handleEvent(StockEvent stockEvent, String subsKey) {
        for (PollingEventHandler pollingEventHandler : pollingEventHandlers) {
            if (pollingEventHandler.accept(stockEvent)) {
                pollingEventHandler.handle(stockEvent);
                break;
            }
        }
    }

    @Override
    public void handleNoMoreElements(String subsKey) {
        log.info("HandleNoMoreElements with subsKey {}", subsKey);
    }

}

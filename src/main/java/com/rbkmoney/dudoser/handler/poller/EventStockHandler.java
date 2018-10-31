package com.rbkmoney.dudoser.handler.poller;

import com.rbkmoney.damsel.event_stock.StockEvent;
import com.rbkmoney.damsel.payment_processing.InvoiceChange;
import com.rbkmoney.eventstock.client.EventAction;
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
    public EventAction handle(StockEvent stockEvent, String subsKey) {
        if (stockEvent.getSourceEvent().getProcessingEvent().getPayload().isSetInvoiceChanges()) {
            List<InvoiceChange> invoiceChanges = stockEvent.getSourceEvent().getProcessingEvent().getPayload().getInvoiceChanges();
            for (InvoiceChange ic : invoiceChanges) {
                for (PollingEventHandler pollingEventHandler : pollingEventHandlers) {
                    if (pollingEventHandler.accept(ic)) {
                        try {
                            pollingEventHandler.handle(ic, stockEvent);
                        } catch (RuntimeException e) {
                            log.error("Error when poller handling", e);
                            return EventAction.DELAYED_RETRY;
                        }
                        break;
                    }
                }
            }
        }
        return EventAction.CONTINUE;
    }
}

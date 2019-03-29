package com.rbkmoney.dudoser.handler.poller;

import com.rbkmoney.damsel.event_stock.StockEvent;
import com.rbkmoney.damsel.payment_processing.Event;
import com.rbkmoney.damsel.payment_processing.InvoiceChange;
import com.rbkmoney.dudoser.utils.HashUtil;
import com.rbkmoney.eventstock.client.EventAction;
import com.rbkmoney.eventstock.client.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class EventStockHandler implements EventHandler<StockEvent> {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final List<PollingEventHandler> pollingEventHandlers;
    private final int divider;
    private final int mod;

    public EventStockHandler(List<PollingEventHandler> pollingEventHandlers, int divider, int mod) {
        this.pollingEventHandlers = pollingEventHandlers;
        this.divider = divider;
        this.mod = mod;
    }

    public int getDivider() {
        return divider;
    }

    public int getMod() {
        return mod;
    }

    @Override
    public EventAction handle(StockEvent stockEvent, String subsKey) {
        Event processingEvent = stockEvent.getSourceEvent().getProcessingEvent();
        if (processingEvent.getPayload().isSetInvoiceChanges()) {
            if (HashUtil.checkHashMod(processingEvent.getSource().getInvoiceId(), divider, mod)) {
                List<InvoiceChange> invoiceChanges = processingEvent.getPayload().getInvoiceChanges();
                for (InvoiceChange ic : invoiceChanges) {
                    for (PollingEventHandler pollingEventHandler : pollingEventHandlers) {
                        if (pollingEventHandler.accept(ic)) {
                            try {
                                pollingEventHandler.handle(ic, stockEvent, mod);
                            } catch (RuntimeException e) {
                                log.error("Error when poller handling", e);
                                return EventAction.DELAYED_RETRY;
                            }
                            break;
                        }
                    }
                }
            }
        }
        return EventAction.CONTINUE;
    }
}

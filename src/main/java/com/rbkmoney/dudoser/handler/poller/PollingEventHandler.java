package com.rbkmoney.dudoser.handler.poller;

import com.rbkmoney.damsel.event_stock.StockEvent;
import com.rbkmoney.damsel.payment_processing.InvoiceChange;
import com.rbkmoney.dudoser.handler.Handler;

public interface PollingEventHandler extends Handler<InvoiceChange, StockEvent> {
}

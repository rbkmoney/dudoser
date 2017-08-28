package com.rbkmoney.dudoser.handler.poller;

import com.rbkmoney.dudoser.handler.ChangeType;
import org.springframework.stereotype.Component;

@Component
public class InvoicePaymentStatusChangedProcessedHandler extends InvoicePaymentStatusChangedHandler {

    @Override
    public ChangeType getChangeType() {
        return ChangeType.INVOICE_PAYMENT_STATUS_CHANGED_PROCESSED;
    }
}

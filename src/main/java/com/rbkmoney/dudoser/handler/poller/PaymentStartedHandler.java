package com.rbkmoney.dudoser.handler.poller;

import com.rbkmoney.damsel.domain.InvoicePayment;
import com.rbkmoney.damsel.domain.Payer;
import com.rbkmoney.damsel.event_stock.StockEvent;
import com.rbkmoney.damsel.payment_processing.Event;
import com.rbkmoney.damsel.payment_processing.InvoiceChange;
import com.rbkmoney.dudoser.dao.PaymentPayer;
import com.rbkmoney.dudoser.dao.PaymentPayerDaoImpl;
import com.rbkmoney.dudoser.handler.ChangeType;
import com.rbkmoney.dudoser.service.EventService;
import com.rbkmoney.dudoser.utils.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentStartedHandler implements PollingEventHandler{

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    EventService eventService;

    @Autowired
    PaymentPayerDaoImpl paymentPayerDaoImpl;

    @Override
    public void handle(InvoiceChange ic, StockEvent value) {
        Event event = value.getSourceEvent().getProcessingEvent();
        long eventId = event.getId();
        String invoiceId = event.getSource().getInvoiceId();
        InvoicePayment invoicePayment = ic.getInvoicePaymentChange().getPayload().getInvoicePaymentStarted().getPayment();
        Payer payer = invoicePayment.getPayer();
        log.info("Start PaymentStartedHandler: event_id {}, invoiceId {}", eventId, invoiceId);
        if (payer.getPaymentTool().isSetBankCard() && payer.isSetContactInfo() && payer.getContactInfo().isSetEmail()) {
            PaymentPayer paymentPayer = new PaymentPayer();
            paymentPayer.setInvoiceId(invoiceId);
            paymentPayer.setPaymentId(ic.getInvoicePaymentChange().getId());
            paymentPayer.setAmount(Converter.longToBigDecimal(invoicePayment.getCost().getAmount()));
            paymentPayer.setCurrency(invoicePayment.getCost().getCurrency().getSymbolicCode());
            paymentPayer.setCardMaskPan(payer.getPaymentTool().getBankCard().getMaskedPan());
            paymentPayer.setCardType(payer.getPaymentTool().getBankCard().getPaymentSystem().name());
            paymentPayer.setInvoiceId(event.getSource().getInvoiceId());
            paymentPayer.setDate(Converter.getFormattedDate(invoicePayment.getCreatedAt()));
            paymentPayer.setToReceiver(payer.getContactInfo().getEmail());

            if (!paymentPayerDaoImpl.updatePayment(paymentPayer)) {
                log.warn("PaymentStartedHandler: couldn't save payment info, invoiceId {}", invoiceId);
            } else {
                log.debug("PaymentStartedHandler: saved payment info, invoiceId {}", invoiceId);
            }
        }

        eventService.setLastEventId(eventId);
        log.debug("End PaymentStartedHandler: event_id {}, invoiceId {}", eventId, invoiceId);
    }
    @Override
    public ChangeType getChangeType() {
        return ChangeType.INVOICE_PAYMENT_STARTED;
    }
}

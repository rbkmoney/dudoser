package com.rbkmoney.dudoser.handler.poller;

import com.rbkmoney.damsel.domain.ContactInfo;
import com.rbkmoney.damsel.domain.InvoicePayment;
import com.rbkmoney.damsel.domain.PaymentTool;
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
        ContactInfo contactInfo = null;
        PaymentTool paymentTool = null;
        if (invoicePayment.getPayer().isSetCustomer()) {
            contactInfo = invoicePayment.getPayer().getCustomer().getContactInfo();
            paymentTool = invoicePayment.getPayer().getCustomer().getPaymentTool();
        } else if (invoicePayment.getPayer().isSetPaymentResource()) {
            contactInfo = invoicePayment.getPayer().getPaymentResource().getContactInfo();
            paymentTool = invoicePayment.getPayer().getPaymentResource().getResource().getPaymentTool();
        }
        String paymentId = ic.getInvoicePaymentChange().getId();
        log.info("Start PaymentStartedHandler: event_id {}, payment {}.{}", eventId, invoiceId, paymentId);
        if (contactInfo != null && contactInfo.isSetEmail()) {
            PaymentPayer paymentPayer = new PaymentPayer();
            paymentPayer.setInvoiceId(invoiceId);
            paymentPayer.setPaymentId(paymentId);
            paymentPayer.setAmount(Converter.longToBigDecimal(invoicePayment.getCost().getAmount()));
            paymentPayer.setCurrency(invoicePayment.getCost().getCurrency().getSymbolicCode());
            if (paymentTool.isSetBankCard()) {
                paymentPayer.setCardMaskPan(paymentTool.getBankCard().getMaskedPan());
                paymentPayer.setCardType(paymentTool.getBankCard().getPaymentSystem().name());
            }
            paymentPayer.setInvoiceId(event.getSource().getInvoiceId());
            paymentPayer.setDate(Converter.getFormattedDate(invoicePayment.getCreatedAt()));
            paymentPayer.setToReceiver(contactInfo.getEmail());

            if (!paymentPayerDaoImpl.updatePayment(paymentPayer)) {
                log.warn("PaymentStartedHandler: couldn't save payment info, payment {}.{}", invoiceId, paymentId);
            } else {
                log.debug("PaymentStartedHandler: saved payment info, payment {}.{}", invoiceId, paymentId);
            }
        }

        eventService.setLastEventId(eventId);
        log.info("End PaymentStartedHandler: event_id {}, payment {}.{}", eventId, invoiceId, paymentId);
    }
    @Override
    public ChangeType getChangeType() {
        return ChangeType.INVOICE_PAYMENT_STARTED;
    }
}

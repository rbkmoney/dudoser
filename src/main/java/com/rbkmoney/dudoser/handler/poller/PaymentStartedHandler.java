package com.rbkmoney.dudoser.handler.poller;

import com.rbkmoney.damsel.domain.ContactInfo;
import com.rbkmoney.damsel.domain.InvoicePayment;
import com.rbkmoney.damsel.domain.Payer;
import com.rbkmoney.damsel.domain.PaymentTool;
import com.rbkmoney.damsel.event_stock.StockEvent;
import com.rbkmoney.damsel.payment_processing.Event;
import com.rbkmoney.damsel.payment_processing.InvoiceChange;
import com.rbkmoney.dudoser.dao.PaymentPayer;
import com.rbkmoney.dudoser.dao.PaymentPayerDaoImpl;
import com.rbkmoney.dudoser.handler.ChangeType;
import com.rbkmoney.dudoser.service.EventService;
import com.rbkmoney.dudoser.utils.Converter;
import com.rbkmoney.geck.common.util.TypeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PaymentStartedHandler implements PollingEventHandler{

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    EventService eventService;

    @Autowired
    PaymentPayerDaoImpl paymentPayerDaoImpl;

    @Override
    public void handle(InvoiceChange ic, StockEvent value, int mod) {
        Event event = value.getSourceEvent().getProcessingEvent();
        long eventId = event.getId();
        String invoiceId = event.getSource().getInvoiceId();
        InvoicePayment invoicePayment = ic.getInvoicePaymentChange().getPayload().getInvoicePaymentStarted().getPayment();
        ContactInfo contactInfo = null;
        PaymentTool paymentTool = null;
        Payer payer = invoicePayment.getPayer();
        if (payer.isSetCustomer()) {
            contactInfo = payer.getCustomer().getContactInfo();
            paymentTool = payer.getCustomer().getPaymentTool();
        } else if (payer.isSetPaymentResource()) {
            contactInfo = payer.getPaymentResource().getContactInfo();
            paymentTool = payer.getPaymentResource().getResource().getPaymentTool();
        } else if (payer.isSetRecurrent()) {
            contactInfo = payer.getRecurrent().getContactInfo();
            paymentTool = payer.getRecurrent().getPaymentTool();
        }
        String paymentId = ic.getInvoicePaymentChange().getId();
        log.info("Start PaymentStartedHandler: event_id {}, payment {}.{}", eventId, invoiceId, paymentId);
        if (contactInfo != null) {
            Optional<PaymentPayer> paymentPayerOptional = paymentPayerDaoImpl.getInvoice(invoiceId);
            if (paymentPayerOptional.isPresent()) {
                PaymentPayer paymentPayer = paymentPayerOptional.get();
                paymentPayer.setPaymentId(paymentId);
                paymentPayer.setAmount(Converter.longToBigDecimal(invoicePayment.getCost().getAmount()));
                paymentPayer.setCurrency(invoicePayment.getCost().getCurrency().getSymbolicCode());
                if (paymentTool.isSetBankCard()) {
                    paymentPayer.setCardMaskPan(paymentTool.getBankCard().getMaskedPan());
                    paymentPayer.setCardType(paymentTool.getBankCard().getPaymentSystem().name());
                }
                paymentPayer.setInvoiceId(event.getSource().getInvoiceId());
                paymentPayer.setDate(TypeUtil.stringToLocalDateTime(invoicePayment.getCreatedAt()));
                paymentPayer.setToReceiver(contactInfo.getEmail());

                if (!paymentPayerDaoImpl.addPayment(paymentPayer)) {
                    log.warn("PaymentStartedHandler: couldn't save payment info, payment {}.{}", invoiceId, paymentId);
                } else {
                    log.debug("PaymentStartedHandler: saved payment info, payment {}.{}", invoiceId, paymentId);
                }
            } else {
                log.warn("PaymentStartedHandler: invoice {} not found in repository", invoiceId);
            }
        } else {
            log.warn("ContactInfo for payment {}.{} not found", invoiceId, paymentId);
        }

        eventService.setLastEventId(eventId, mod);
        log.info("End PaymentStartedHandler: event_id {}, payment {}.{}", eventId, invoiceId, paymentId);
    }
    @Override
    public ChangeType getChangeType() {
        return ChangeType.INVOICE_PAYMENT_STARTED;
    }
}

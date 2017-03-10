package com.rbkmoney.dudoser.handler.poller;

import com.rbkmoney.damsel.domain.InvoicePayment;
import com.rbkmoney.damsel.domain.Payer;
import com.rbkmoney.damsel.event_stock.StockEvent;
import com.rbkmoney.damsel.payment_processing.Event;
import com.rbkmoney.dudoser.dao.EventTypeCode;
import com.rbkmoney.dudoser.dao.PaymentPayer;
import com.rbkmoney.dudoser.dao.PaymentPayerDaoImpl;
import com.rbkmoney.dudoser.dao.TemplateDao;
import com.rbkmoney.dudoser.service.EventService;
import com.rbkmoney.dudoser.utils.Converter;
import com.rbkmoney.dudoser.utils.mail.MailSubject;
import com.rbkmoney.dudoser.utils.mail.TemplateMailSenderUtils;
import com.rbkmoney.thrift.filter.Filter;
import com.rbkmoney.thrift.filter.PathConditionFilter;
import com.rbkmoney.thrift.filter.rule.PathConditionRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class PaymentStartedHandler implements PollingEventHandler<StockEvent> {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    EventService eventService;

    @Autowired
    PaymentPayerDaoImpl paymentPayerDaoImpl;

    @Value("${notification.create.invoice.from}")
    private String from;

    @Autowired
    TemplateDao templateDao;

    @Autowired
    TemplateMailSenderUtils mailSenderUtils;

    private Filter filter;

    private String path = "source_event.processing_event.payload.invoice_event.invoice_payment_event.invoice_payment_started.payment";

    public PaymentStartedHandler() {
        filter = new PathConditionFilter(new PathConditionRule(path));
    }

    @Override
    public void handle(StockEvent value) {
        Event event = value.getSourceEvent().getProcessingEvent();
        long eventId = event.getId();
        String invoiceId = event.getSource().getInvoice();
        InvoicePayment invoicePayment = event.getPayload().getInvoiceEvent().getInvoicePaymentEvent().getInvoicePaymentStarted().getPayment();
        Payer payer = invoicePayment.getPayer();
        log.info("Start PaymentStartedHandler: event_id {}, invoiceId {}", eventId, invoiceId);

        if (payer.getPaymentTool().isSetBankCard() && payer.isSetContactInfo() && payer.getContactInfo().isSetEmail()) {
            PaymentPayer paymentPayer = new PaymentPayer();
            paymentPayer.setInvoiceId(invoiceId);
            paymentPayer.setAmount(Converter.longToBigDecimal(invoicePayment.getCost().getAmount()));
            paymentPayer.setCurrency(invoicePayment.getCost().getCurrency().getSymbolicCode());
            paymentPayer.setCardMaskPan(payer.getPaymentTool().getBankCard().getMaskedPan());
            paymentPayer.setCardType(payer.getPaymentTool().getBankCard().getPaymentSystem().name());
            paymentPayer.setInvoiceId(event.getSource().getInvoice());
            paymentPayer.setDate(invoicePayment.getCreatedAt());
            paymentPayer.setToReceiver(payer.getContactInfo().getEmail());

            if (!paymentPayerDaoImpl.add(paymentPayer)) {
                log.error("PaymentStartedHandler: not save Payment Payer, invoiceId {}", invoiceId);
            } else {
                log.info("PaymentStartedHandler: save Payment Payer, invoiceId {}", invoiceId);
            }
        }

        try {
            eventService.setLastEventId(eventId);
        } catch (Exception e) {
            log.error("Exception: not save Last id. Reason: " + e.getMessage());
        }
        log.info("End PaymentStartedHandler: event_id {}, invoiceId {}", eventId, invoiceId);
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

}

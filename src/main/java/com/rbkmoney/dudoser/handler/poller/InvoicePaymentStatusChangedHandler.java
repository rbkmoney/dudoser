package com.rbkmoney.dudoser.handler.poller;

import com.rbkmoney.damsel.event_stock.StockEvent;
import com.rbkmoney.damsel.payment_processing.Event;
import com.rbkmoney.damsel.payment_processing.InvoiceChange;
import com.rbkmoney.dudoser.dao.*;
import com.rbkmoney.dudoser.exception.MailNotSendException;
import com.rbkmoney.dudoser.service.EventService;
import com.rbkmoney.dudoser.service.MailSenderService;
import com.rbkmoney.dudoser.service.TemplateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public abstract class InvoicePaymentStatusChangedHandler implements PollingEventHandler {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    EventService eventService;

    @Value("${notification.payment.paid.from}")
    private String from;

    @Autowired
    TemplateDao templateDao;

    @Autowired
    PaymentPayerDaoImpl paymentPayerDaoImpl;

    @Autowired
    TemplateService templateService;

    @Autowired
    MailSenderService mailSenderService;

    @Override
    public void handle(InvoiceChange ic, StockEvent value, int mod) {
        Event event = value.getSourceEvent().getProcessingEvent();
        long eventId = event.getId();
        String invoiceId = event.getSource().getInvoiceId();
        String paymentId = ic.getInvoicePaymentChange().getId();
        log.info("Start InvoicePaymentStatusChangedHandler: event_id {}, payment change {}.{}", event.getId(), invoiceId, paymentId);
        Optional<PaymentPayer> paymentPayer = getPaymentPayer(invoiceId, ic);
        if (paymentPayer.isPresent()) {
            PaymentPayer payment = paymentPayer.get();
            if (payment.getToReceiver() == null) {
                log.info("Email not found for payment change {}.{}", invoiceId, paymentId);
            } else {
                String formattedAmount = getFormattedAmount(payment);
                Map<String, Object> model = new HashMap<>();
                model.put("paymentPayer", payment);
                model.put("formattedAmount", formattedAmount);
                String subject = String.format(getMailSubject(),
                        payment.getInvoiceId(),
                        payment.getDate(),
                        formattedAmount
                );
                String partyId = payment.getPartyId();
                String shopId = payment.getShopId();
                Template template = templateDao.getTemplateBodyByMerchShopParams(getEventTypeCode(), partyId, shopId);
                if (!template.isActive()) {
                    log.info("Not found active template for partyId={}, shopId={}", partyId, shopId);
                } else {
                    String text = templateService.getFilledContent(template.getBody(), model);
                    try {
                        log.info("Mail send from {} to {}. Subject: {}", from, payment.getToReceiver(), subject);
                        mailSenderService.send(from, new String[]{payment.getToReceiver()}, subject, text, null);
                        log.info("Mail has been sent to {}", payment.getToReceiver());
                    } catch (MailNotSendException e) {
                        log.warn("Mail not send to {}", payment.getToReceiver(), e);
                    }
                }
            }
            eventService.setLastEventId(eventId, mod);
        } else {
            log.warn("InvoicePaymentStatusChangedHandler: payment change {}.{} not found in repository", invoiceId, paymentId);
        }
        log.info("End InvoicePaymentStatusChangedHandler: event_id {}, payment change {}.{}", event.getId(), invoiceId, paymentId);
    }

    protected abstract String getFormattedAmount(PaymentPayer payment);

    protected abstract Optional<PaymentPayer> getPaymentPayer(String invoiceId, InvoiceChange ic);

    protected abstract String getMailSubject();

    protected abstract EventTypeCode getEventTypeCode();
}

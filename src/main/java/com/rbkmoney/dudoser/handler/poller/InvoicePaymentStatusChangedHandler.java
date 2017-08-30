package com.rbkmoney.dudoser.handler.poller;


import com.rbkmoney.damsel.event_stock.StockEvent;
import com.rbkmoney.damsel.payment_processing.Event;
import com.rbkmoney.damsel.payment_processing.InvoiceChange;
import com.rbkmoney.dudoser.dao.EventTypeCode;
import com.rbkmoney.dudoser.dao.PaymentPayer;
import com.rbkmoney.dudoser.dao.PaymentPayerDaoImpl;
import com.rbkmoney.dudoser.dao.TemplateDao;
import com.rbkmoney.dudoser.exception.MailNotSendException;
import com.rbkmoney.dudoser.service.EventService;
import com.rbkmoney.dudoser.utils.mail.MailSubject;
import com.rbkmoney.dudoser.utils.mail.TemplateMailSenderUtils;
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
    TemplateMailSenderUtils mailSenderUtils;

    @Override
    public void handle(InvoiceChange ic, StockEvent value) {
        Event event = value.getSourceEvent().getProcessingEvent();
        long eventId = event.getId();
        String invoiceId = event.getSource().getInvoiceId();
        Optional<PaymentPayer> paymentPayer = getPaymentPayer(invoiceId, ic);

        if (paymentPayer.isPresent()) {
            PaymentPayer payment = paymentPayer.get();
            log.info("Start InvoicePaymentStatusChangedHandler: event_id {}, invoiceId {}, to {}", event.getId(), invoiceId, payment.getToReceiver());
            Map<String, Object> model = new HashMap<>();
            model.put("paymentPayer", payment);
            String subject = String.format(MailSubject.PAYMENT_PAID.pattern,
                    payment.getInvoiceId(),
                    payment.getDate(),
                    payment.getAmountWithCurrency()
            );
            //TODO should be getTemplateBodyByMerchShopParams but we haven't merchId and shopId
            String freeMarkerTemplateContent = templateDao.getTemplateBodyByTypeCode(getEventTypeCode());
            mailSenderUtils.setFreeMarkerTemplateContent(freeMarkerTemplateContent);
            mailSenderUtils.setModel(model);

            try {
                mailSenderUtils.send(from, payment.getToReceiver(), subject);
            } catch (MailNotSendException e) {
                log.warn("Mail not send from {} to {}", from, payment.getToReceiver(), e);
            }

            eventService.setLastEventId(eventId);
        } else {
            log.warn("InvoicePaymentStatusChangedHandler: invoiceId {} not found in repository", invoiceId);
        }
        log.debug("End InvoicePaymentStatusChangedHandler: event_id {}, invoiceId {}", event.getId(), invoiceId);
    }

    protected abstract Optional<PaymentPayer> getPaymentPayer(String invoiceId, InvoiceChange ic);

    protected abstract EventTypeCode getEventTypeCode();
}

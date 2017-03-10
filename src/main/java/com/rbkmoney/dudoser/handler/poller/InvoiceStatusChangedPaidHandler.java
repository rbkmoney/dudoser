package com.rbkmoney.dudoser.handler.poller;


import com.rbkmoney.damsel.event_stock.StockEvent;
import com.rbkmoney.damsel.payment_processing.Event;
import com.rbkmoney.dudoser.dao.EventTypeCode;
import com.rbkmoney.dudoser.dao.PaymentPayer;
import com.rbkmoney.dudoser.dao.PaymentPayerDaoImpl;
import com.rbkmoney.dudoser.dao.TemplateDao;
import com.rbkmoney.dudoser.service.EventService;
import com.rbkmoney.dudoser.utils.mail.MailSubject;
import com.rbkmoney.dudoser.utils.mail.TemplateMailSenderUtils;
import com.rbkmoney.thrift.filter.Filter;
import com.rbkmoney.thrift.filter.PathConditionFilter;
import com.rbkmoney.thrift.filter.condition.CompareCondition;
import com.rbkmoney.thrift.filter.condition.Relation;
import com.rbkmoney.thrift.filter.rule.PathConditionRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class InvoiceStatusChangedPaidHandler implements PollingEventHandler<StockEvent> {

    Logger log = LoggerFactory.getLogger(this.getClass());

    private String path = "source_event.processing_event.payload.invoice_event.invoice_status_changed.status";
    private Filter filter;

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

    public InvoiceStatusChangedPaidHandler() {
        filter = new PathConditionFilter(
                new PathConditionRule(path, new CompareCondition("paid", Relation.EQ))
        );
    }

    @Override
    public void handle(StockEvent value) {
        Event event = value.getSourceEvent().getProcessingEvent();
        long eventId = event.getId();
        String invoiceId = event.getSource().getInvoice();
        log.info("Start InvoiceStatusChangedPaidHandler: event_id {}, invoiceId {}", event.getId(), invoiceId);
        Optional<PaymentPayer> paymentPayer = paymentPayerDaoImpl.getById(invoiceId);

        if (paymentPayer.isPresent()) {
            PaymentPayer payment = paymentPayer.get();
            Map<String, Object> model = new HashMap<>();
            model.put("paymentPayer", payment);
            String subject = String.format(MailSubject.PAYMENT_PAID.pattern,
                    payment.getInvoiceId(),
                    payment.getDate(),
                    payment.getAmountWithCurrency()
            );
            //TODO should be getTemplateBodyByMerchShopParams but we haven't merchId and shopId
            String freeMarkerTemplateContent = templateDao.getTemplateBodyByTypeCode(EventTypeCode.INVOICE_STATUS_CHANGED);
            mailSenderUtils.setFreeMarkerTemplateContent(freeMarkerTemplateContent);
            mailSenderUtils.setModel(model);

            if (mailSenderUtils.send(from, payment.getToReceiver(), subject)) {
                log.info("Mail send from {} to {}", from, payment.getToReceiver());
            } else {
                log.error("Mail not send from {} to {}", from, payment.getToReceiver());
            }

            paymentPayerDaoImpl.delete(invoiceId);

            try {
                eventService.setLastEventId(eventId);
            } catch (Exception e) {
                log.error("Exception: not save Last id. Reason: " + e.getMessage());
            }
        } else {
            log.error("InvoiceStatusChangedPaidHandler: invoiceId {} not found in repository", invoiceId);
        }
        log.info("End InvoiceStatusChangedPaidHandler: event_id {}, invoiceId {}", event.getId(), invoiceId);
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

}

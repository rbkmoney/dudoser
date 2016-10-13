package com.rbkmoney.dudoser.handler;


import com.rbkmoney.damsel.event_stock.StockEvent;
import com.rbkmoney.damsel.payment_processing.Event;
import com.rbkmoney.dudoser.dao.InMemoryPaymentPayerDao;
import com.rbkmoney.dudoser.dao.PaymentPayer;
import com.rbkmoney.dudoser.utils.mail.MailSenderUtils;
import com.rbkmoney.dudoser.utils.mail.MailSubject;
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
public class InvoiceStatusChangedPaidHandler implements Handler<StockEvent> {

    Logger log = LoggerFactory.getLogger(this.getClass());

    private String path = "source_event.processing_event.payload.invoice_event.invoice_status_changed.status";

    private Filter filter;

    @Value("${notification.payment.paid.from}")
    private String from;

    @Value("${notification.payment.paid.fileNameTemplate}")
    private String fileNameTemplate;

    @Autowired
    InMemoryPaymentPayerDao inMemoryPaymentPayerDao;

    @Autowired
    MailSenderUtils mailSenderUtils;

    public InvoiceStatusChangedPaidHandler() {
        filter = new PathConditionFilter(
                new PathConditionRule(path, new CompareCondition("paid", Relation.EQ))
        );
    }

    @Override
    public void handle(StockEvent value) {

        Event event = value.getSourceEvent().getProcessingEvent();
        String invoiceId = event.getSource().getInvoice();

        log.info("InvoiceStatusChangedPaidHandler: event_id {}, invoiceId {}", event.getId(), invoiceId);

        Optional<PaymentPayer> paymentPayer = inMemoryPaymentPayerDao.getById(invoiceId);

        if (paymentPayer.isPresent()) {

            PaymentPayer payment = paymentPayer.get();

            Map<String, Object> model = new HashMap<>();
            model.put("paymentPayer", payment);

            String subject = String.format(MailSubject.PAYMENT_PAID.pattern,
                    payment.getInvoiceId(),
                    payment.getDate(),
                    payment.getAmountWithCurrency()
            );

            mailSenderUtils.setFileNameTemplate(fileNameTemplate).setModel(model);

            if (mailSenderUtils.send(from, payment.getTo(), subject)) {
                log.info("Mail send {}", payment.getTo());
            } else {
                log.error("Mail not send {}", payment.getTo());
            }

            inMemoryPaymentPayerDao.delete(invoiceId);
        } else {
            log.error("InvoiceStatusChangedPaidHandler: invoiceId {} not found in repository", invoiceId);
        }


    }

    @Override
    public Filter getFilter() {
        return filter;
    }

}

package com.rbkmoney.dudoser.handler;


import com.rbkmoney.damsel.domain.InvoicePayment;
import com.rbkmoney.damsel.domain.Payer;
import com.rbkmoney.damsel.event_stock.StockEvent;
import com.rbkmoney.damsel.payment_processing.Event;
import com.rbkmoney.damsel.payment_processing.InvoiceEvent;
import com.rbkmoney.dudoser.model.PaymentPaid;
import com.rbkmoney.dudoser.utils.Converter;
import com.rbkmoney.dudoser.utils.FileHelper;
import com.rbkmoney.dudoser.utils.mail.MailSenderUtils;
import com.rbkmoney.dudoser.utils.mail.MailSubject;
import com.rbkmoney.thrift.filter.Filter;
import com.rbkmoney.thrift.filter.PathConditionFilter;
import com.rbkmoney.thrift.filter.rule.PathConditionRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class InvoiceStatusChangedPaidHandler implements Handler<StockEvent> {

    Logger log = LoggerFactory.getLogger(this.getClass());

    private String path = "source_event.processing_event.payload.invoice_event.invoice_status_changed.status.paid";

    private Filter filter;

    @Value("${notification.payment.paid.from}")
    private String from;

    @Value("${notification.payment.paid.fileNameTemplate}")
    private String fileNameTemplate;

    @Value("${file.pathToFolder}")
    private String pathToFolder;

    @Autowired
    MailSenderUtils mailSenderUtils;

    @Autowired
    PaymentPaid paymentPaid;

    public InvoiceStatusChangedPaidHandler() {
        filter = new PathConditionFilter(new PathConditionRule(path));
    }

    @Override
    public void handle(StockEvent value) {

        Event event = value.getSourceEvent().getProcessingEvent();
        InvoiceEvent invoiceEvent = event.getPayload().getInvoiceEvent();

        InvoicePayment invoicePayment = invoiceEvent
                .getInvoicePaymentEvent()
                .getInvoicePaymentStarted()
                .getPayment();

        Payer payer = invoicePayment.getPayer();

        if (payer.getPaymentTool().isSetBankCard() && payer.isSetContactInfo() && payer.getContactInfo().isSetEmail()) {

            paymentPaid.setAmount(Converter.longToBigDecimal(invoicePayment.getCost().getAmount()));
            paymentPaid.setCurrency(invoicePayment.getCost().getCurrency().getSymbolicCode());
            paymentPaid.setCardMaskPan(payer.getPaymentTool().getBankCard().getMaskedPan());
            paymentPaid.setCardType(payer.getPaymentTool().getBankCard().getPaymentSystem().name());
            paymentPaid.setInvoiceId(event.getSource().getInvoice());
            paymentPaid.setDate(invoicePayment.getCreatedAt());
            paymentPaid.setTo(payer.getContactInfo().getEmail());

            Map<String, Object> model = new HashMap<>();
            model.put("paymentPaid", paymentPaid);

            String subject = String.format(MailSubject.PAYMENT_PAID.pattern,
                    paymentPaid.getInvoiceId(),
                    paymentPaid.getDate(),
                    paymentPaid.getAmountWithCurrency()
            );

            mailSenderUtils.setFileNameTemplate(fileNameTemplate).setModel(model);

            if (!mailSenderUtils.send(from, paymentPaid.getTo(), subject)) {
                log.info("Mail send {}", paymentPaid.getTo());
            } else {
                log.error("Mail not send {}", paymentPaid.getTo());
            }

        }

        try {
            FileHelper.pathToFolder = pathToFolder;
            String fileName = FileHelper.getFile(FileHelper.FILENAME_LAST_EVENT_ID).getAbsolutePath();
            FileHelper.writeFile(fileName, String.valueOf(event.getId()));
        } catch (IOException e) {
            log.error("Exception: not save Last id");
        }

    }

    @Override
    public Filter getFilter() {
        return filter;
    }

}

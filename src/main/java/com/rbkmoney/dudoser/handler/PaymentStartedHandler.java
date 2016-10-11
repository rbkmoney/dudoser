package com.rbkmoney.dudoser.handler;

import com.rbkmoney.damsel.domain.InvoicePayment;
import com.rbkmoney.damsel.domain.Payer;
import com.rbkmoney.damsel.event_stock.StockEvent;
import com.rbkmoney.damsel.payment_processing.Event;
import com.rbkmoney.dudoser.dao.InMemoryPaymentPayerDao;
import com.rbkmoney.dudoser.dao.PaymentPayer;
import com.rbkmoney.dudoser.utils.Converter;
import com.rbkmoney.dudoser.utils.FileHelper;
import com.rbkmoney.thrift.filter.Filter;
import com.rbkmoney.thrift.filter.PathConditionFilter;
import com.rbkmoney.thrift.filter.rule.PathConditionRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;

public class PaymentStartedHandler implements Handler<StockEvent> {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${file.pathToFolder}")
    private String pathToFolder;

    @Autowired
    InMemoryPaymentPayerDao inMemoryPaymentPayerDao;

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

        log.info("PaymentStartedHandler: event_id {}, invoiceId {}", eventId, invoiceId);

        if (payer.getPaymentTool().isSetBankCard() && payer.isSetContactInfo() && payer.getContactInfo().isSetEmail()) {

            PaymentPayer paymentPayer = new PaymentPayer();
            paymentPayer.setInvoiceId(invoiceId);
            paymentPayer.setAmount(Converter.longToBigDecimal(invoicePayment.getCost().getAmount()));
            paymentPayer.setCurrency(invoicePayment.getCost().getCurrency().getSymbolicCode());
            paymentPayer.setCardMaskPan(payer.getPaymentTool().getBankCard().getMaskedPan());
            paymentPayer.setCardType(payer.getPaymentTool().getBankCard().getPaymentSystem().name());
            paymentPayer.setInvoiceId(event.getSource().getInvoice());
            paymentPayer.setDate(invoicePayment.getCreatedAt());
            paymentPayer.setTo(payer.getContactInfo().getEmail());

            if(!inMemoryPaymentPayerDao.add(paymentPayer)) {
                log.error("PaymentStartedHandler: not save Payment Payer, invoiceId {}", invoiceId);
            } else {
                log.info("PaymentStartedHandler: save Payment Payer, invoiceId {}", invoiceId);
            }

        }

        try {
            FileHelper.pathToFolder = pathToFolder;
            String fileName = FileHelper.getFile(FileHelper.FILENAME_LAST_EVENT_ID).getAbsolutePath();
            FileHelper.writeFile(fileName, String.valueOf(eventId));
        } catch (IOException e) {
            log.error("Exception: not save Last id. Reason: " + e.getMessage());
        }
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

}

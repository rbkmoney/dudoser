package com.rbkmoney.dudoser.handler.poller;

import com.rbkmoney.damsel.payment_processing.Invoice;
import com.rbkmoney.damsel.payment_processing.InvoiceChange;
import com.rbkmoney.dudoser.dao.EventTypeCode;
import com.rbkmoney.dudoser.dao.TemplateDao;
import com.rbkmoney.dudoser.dao.model.PaymentPayer;
import com.rbkmoney.dudoser.handler.ChangeType;
import com.rbkmoney.dudoser.service.InvoicingService;
import com.rbkmoney.dudoser.service.PaymentPayerService;
import com.rbkmoney.dudoser.service.ScheduledMailHandlerService;
import com.rbkmoney.dudoser.service.TemplateService;
import com.rbkmoney.dudoser.utils.Converter;
import com.rbkmoney.dudoser.utils.mail.MailSubject;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InvoicePaymentStatusChangedProcessedHandler extends InvoicePaymentStatusChangedHandler {

    private final InvoicingService invoicingService;
    private final PaymentPayerService paymentPayerService;

    public InvoicePaymentStatusChangedProcessedHandler(
            TemplateDao templateDao,
            TemplateService templateService,
            ScheduledMailHandlerService mailHandlerService,
            InvoicingService invoicingService,
            PaymentPayerService paymentPayerService) {
        super(templateDao, templateService, mailHandlerService);
        this.invoicingService = invoicingService;
        this.paymentPayerService = paymentPayerService;
    }

    @Override
    public ChangeType getChangeType() {
        return ChangeType.INVOICE_PAYMENT_STATUS_CHANGED_PROCESSED;
    }

    @Override
    protected String getFormattedAmount(PaymentPayer payment) {
        return Converter.getFormattedAmount(payment.getAmount(), payment.getCurrency());
    }

    @Override
    protected Optional<PaymentPayer> getPaymentPayer(
            InvoiceChange invoiceChange,
            String invoiceId,
            Long sequenceId) {
        String paymentId = invoiceChange.getInvoicePaymentChange().getId();
        Invoice invoice = invoicingService.get(invoiceId, sequenceId);
        PaymentPayer paymentPayer = paymentPayerService.convert(invoice, invoiceId, paymentId);

        return Optional.ofNullable(paymentPayer);
    }

    @Override
    protected String getMailSubject() {
        return MailSubject.PAYMENT_PAID.pattern;
    }

    @Override
    protected EventTypeCode getEventTypeCode() {
        return EventTypeCode.PAYMENT_STATUS_CHANGED_PROCESSED;
    }
}

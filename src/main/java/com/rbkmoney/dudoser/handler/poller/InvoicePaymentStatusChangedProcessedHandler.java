package com.rbkmoney.dudoser.handler.poller;

import com.rbkmoney.damsel.payment_processing.InvoiceChange;
import com.rbkmoney.dudoser.dao.EventTypeCode;
import com.rbkmoney.dudoser.dao.PaymentPayer;
import com.rbkmoney.dudoser.handler.ChangeType;
import com.rbkmoney.dudoser.utils.Converter;
import com.rbkmoney.dudoser.utils.mail.MailSubject;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class InvoicePaymentStatusChangedProcessedHandler extends InvoicePaymentStatusChangedHandler {

    @Override
    public ChangeType getChangeType() {
        return ChangeType.INVOICE_PAYMENT_STATUS_CHANGED_PROCESSED;
    }

    @Override
    protected String getFormattedAmount(PaymentPayer payment) {
        return Converter.getFormattedAmount(payment.getAmount(), payment.getCurrency());
    }

    @Override
    protected Optional<PaymentPayer> getPaymentPayer(String invoiceId, InvoiceChange ic) {
        String paymentId = ic.getInvoicePaymentChange().getId();
        return paymentPayerDaoImpl.getPayment(invoiceId, paymentId);
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

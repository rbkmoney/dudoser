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
public class RefundStatusChangedHandler extends InvoicePaymentStatusChangedHandler {

    @Override
    public ChangeType getChangeType() {
        return ChangeType.INVOICE_PAYMENT_REFUND_STATUS_CHANGED_SUCCEEDED;
    }

    @Override
    protected String getFormattedAmount(PaymentPayer payment) {
        return Converter.getFormattedAmount(payment.getRefundAmount(), payment.getCurrency());
    }

    @Override
    protected Optional<PaymentPayer> getPaymentPayer(String invoiceId, InvoiceChange ic) {
        String paymentId = ic.getInvoicePaymentChange().getId();
        String refundId = ic.getInvoicePaymentChange().getPayload().getInvoicePaymentRefundChange().getId();
        return paymentPayerDaoImpl.getRefund(invoiceId, paymentId, refundId);
    }

    @Override
    protected String getMailSubject() {
        return MailSubject.REFUNDED.pattern;
    }

    @Override
    protected EventTypeCode getEventTypeCode() {
        return EventTypeCode.REFUND_STATUS_CHANGED_SUCCEEDED;
    }
}
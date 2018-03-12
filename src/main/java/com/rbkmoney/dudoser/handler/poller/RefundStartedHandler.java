package com.rbkmoney.dudoser.handler.poller;

import com.rbkmoney.damsel.domain.Cash;
import com.rbkmoney.damsel.domain.FinalCashFlowPosting;
import com.rbkmoney.damsel.domain.InvoicePaymentRefund;
import com.rbkmoney.damsel.event_stock.StockEvent;
import com.rbkmoney.damsel.payment_processing.Event;
import com.rbkmoney.damsel.payment_processing.InvoiceChange;
import com.rbkmoney.damsel.payment_processing.InvoicePaymentRefundCreated;
import com.rbkmoney.dudoser.dao.PaymentPayer;
import com.rbkmoney.dudoser.dao.PaymentPayerDaoImpl;
import com.rbkmoney.dudoser.handler.ChangeType;
import com.rbkmoney.dudoser.service.EventService;
import com.rbkmoney.dudoser.utils.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class RefundStartedHandler implements PollingEventHandler{
    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    EventService eventService;

    @Autowired
    PaymentPayerDaoImpl paymentPayerDaoImpl;

    @Override
    public void handle(InvoiceChange ic, StockEvent value) {
        Event event = value.getSourceEvent().getProcessingEvent();
        long eventId = event.getId();
        String invoiceId = event.getSource().getInvoiceId();
        String paymentId = ic.getInvoicePaymentChange().getId();
        InvoicePaymentRefundCreated invoicePaymentRefundCreated = ic.getInvoicePaymentChange().getPayload().getInvoicePaymentRefundChange().getPayload().getInvoicePaymentRefundCreated();
        InvoicePaymentRefund refund = invoicePaymentRefundCreated.getRefund();
        String refundId = refund.getId();
        log.info("Start RefundStartedHandler: event_id {}, refund {}.{}.{}", eventId, invoiceId, paymentId, refundId);
        Optional<PaymentPayer> paymentPayerOptional = paymentPayerDaoImpl.getPayment(invoiceId, paymentId);
        if (paymentPayerOptional.isPresent()) {
            PaymentPayer paymentPayer = paymentPayerOptional.get();
            List<FinalCashFlowPosting> cashFlow = invoicePaymentRefundCreated.getCashFlow();
            if (invoicePaymentRefundCreated.getRefund().isSetCash()) {
                Cash cash = invoicePaymentRefundCreated.getRefund().getCash();
                paymentPayer.setRefundAmount(Converter.longToBigDecimal(cash.getAmount()));
                paymentPayer.setCurrency(cash.getCurrency().getSymbolicCode());
            } else {
                paymentPayer.setRefundAmount(Converter.longToBigDecimal(getAmount(cashFlow)));
                paymentPayer.setCurrency(cashFlow.get(0).getVolume().getCurrency().getSymbolicCode());
            }

            paymentPayer.setRefundId(refundId);
            paymentPayer.setDate(refund.getCreatedAt());
            if (!paymentPayerDaoImpl.addRefund(paymentPayer)) {
                log.warn("RefundStartedHandler: couldn't save refund info: {}.{}.{}", invoiceId, paymentId, refundId);
            }
            eventService.setLastEventId(eventId);
        } else {
            log.warn("RefundStartedHandler: payment {}.{} not found in repository", invoiceId, paymentId);
        }
        log.info("End RefundStartedHandler: event_id {}, refund {}.{}.{}", eventId, invoiceId, paymentId, refundId);
    }
    public static long getAmount(List<FinalCashFlowPosting> finalCashFlow) {
        return finalCashFlow.stream()
                .filter(c -> c.getSource().getAccountType().isSetMerchant() &&
                        c.getDestination().getAccountType().isSetProvider())
                .mapToLong(c -> c.getVolume().getAmount())
                .sum();
    }
    @Override
    public ChangeType getChangeType() {
        return ChangeType.INVOICE_PAYMENT_REFUND_CREATED;
    }
}

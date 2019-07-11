package com.rbkmoney.dudoser.handler.poller;

import com.rbkmoney.damsel.domain.Cash;
import com.rbkmoney.damsel.domain.FinalCashFlowPosting;
import com.rbkmoney.damsel.domain.InvoicePaymentRefund;
import com.rbkmoney.damsel.payment_processing.InvoiceChange;
import com.rbkmoney.damsel.payment_processing.InvoicePaymentRefundCreated;
import com.rbkmoney.dudoser.dao.PaymentPayerDaoImpl;
import com.rbkmoney.dudoser.dao.model.PaymentPayer;
import com.rbkmoney.dudoser.handler.ChangeType;
import com.rbkmoney.dudoser.utils.Converter;
import com.rbkmoney.geck.common.util.TypeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class RefundStartedHandler implements PollingEventHandler{

    private final PaymentPayerDaoImpl paymentPayerDaoImpl;

    @Override
    public void handle(InvoiceChange ic, String sourceId) {
        String paymentId = ic.getInvoicePaymentChange().getId();
        InvoicePaymentRefundCreated invoicePaymentRefundCreated = ic.getInvoicePaymentChange().getPayload().getInvoicePaymentRefundChange().getPayload().getInvoicePaymentRefundCreated();
        InvoicePaymentRefund refund = invoicePaymentRefundCreated.getRefund();
        String refundId = refund.getId();
        log.info("Start RefundStartedHandler: refund {}.{}.{}", sourceId, paymentId, refundId);
        Optional<PaymentPayer> paymentPayerOptional = paymentPayerDaoImpl.getPayment(sourceId, paymentId);
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
            paymentPayer.setDate(TypeUtil.stringToLocalDateTime(refund.getCreatedAt()));
            paymentPayerDaoImpl.addRefund(paymentPayer);
        } else {
            log.warn("RefundStartedHandler: payment {}.{} not found in repository", sourceId, paymentId);
        }
        log.info("End RefundStartedHandler: refund {}.{}.{}", sourceId, paymentId, refundId);
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

package com.rbkmoney.dudoser.handler.poller;

import com.rbkmoney.damsel.domain.ContactInfo;
import com.rbkmoney.damsel.domain.InvoicePayment;
import com.rbkmoney.damsel.domain.Payer;
import com.rbkmoney.damsel.domain.PaymentTool;
import com.rbkmoney.damsel.payment_processing.InvoiceChange;
import com.rbkmoney.dudoser.dao.PaymentPayerDaoImpl;
import com.rbkmoney.dudoser.dao.model.PaymentPayer;
import com.rbkmoney.dudoser.handler.ChangeType;
import com.rbkmoney.dudoser.utils.Converter;
import com.rbkmoney.geck.common.util.TypeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class PaymentStartedHandler implements PollingEventHandler{

    private final PaymentPayerDaoImpl paymentPayerDaoImpl;

    @Override
    public void handle(InvoiceChange ic, String sourceId) {
        InvoicePayment invoicePayment = ic.getInvoicePaymentChange().getPayload().getInvoicePaymentStarted().getPayment();
        ContactInfo contactInfo = null;
        PaymentTool paymentTool = null;
        Payer payer = invoicePayment.getPayer();
        if (payer.isSetCustomer()) {
            contactInfo = payer.getCustomer().getContactInfo();
            paymentTool = payer.getCustomer().getPaymentTool();
        } else if (payer.isSetPaymentResource()) {
            contactInfo = payer.getPaymentResource().getContactInfo();
            paymentTool = payer.getPaymentResource().getResource().getPaymentTool();
        } else if (payer.isSetRecurrent()) {
            contactInfo = payer.getRecurrent().getContactInfo();
            paymentTool = payer.getRecurrent().getPaymentTool();
        }
        String paymentId = ic.getInvoicePaymentChange().getId();
        log.info("Start PaymentStartedHandler: payment {}.{}", sourceId, paymentId);
        if (contactInfo != null) {
            Optional<PaymentPayer> paymentPayerOptional = paymentPayerDaoImpl.getInvoice(sourceId);
            if (paymentPayerOptional.isPresent()) {
                PaymentPayer paymentPayer = paymentPayerOptional.get();
                paymentPayer.setPaymentId(paymentId);
                paymentPayer.setAmount(Converter.longToBigDecimal(invoicePayment.getCost().getAmount()));
                paymentPayer.setCurrency(invoicePayment.getCost().getCurrency().getSymbolicCode());
                if (paymentTool.isSetBankCard()) {
                    paymentPayer.setCardMaskPan(paymentTool.getBankCard().getMaskedPan());
                    paymentPayer.setCardType(paymentTool.getBankCard().getPaymentSystem().name());
                }
                paymentPayer.setInvoiceId(sourceId);
                paymentPayer.setDate(TypeUtil.stringToLocalDateTime(invoicePayment.getCreatedAt()));
                paymentPayer.setToReceiver(contactInfo.getEmail());

                if (!paymentPayerDaoImpl.addPayment(paymentPayer)) {
                    log.warn("PaymentStartedHandler: couldn't save payment info, payment {}.{}", sourceId, paymentId);
                } else {
                    log.debug("PaymentStartedHandler: saved payment info, payment {}.{}", sourceId, paymentId);
                }
            } else {
                log.warn("PaymentStartedHandler: invoice {} not found in repository", sourceId);
            }
        } else {
            log.warn("ContactInfo for payment {}.{} not found", sourceId, paymentId);
        }
        log.info("End PaymentStartedHandler: payment {}.{}", sourceId, paymentId);
    }
    @Override
    public ChangeType getChangeType() {
        return ChangeType.INVOICE_PAYMENT_STARTED;
    }
}

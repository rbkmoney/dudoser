package com.rbkmoney.dudoser.handler.poller;

import com.rbkmoney.damsel.payment_processing.InvoiceChange;
import com.rbkmoney.dudoser.dao.EventTypeCode;
import com.rbkmoney.dudoser.dao.PaymentPayerDaoImpl;
import com.rbkmoney.dudoser.dao.Template;
import com.rbkmoney.dudoser.dao.TemplateDao;
import com.rbkmoney.dudoser.dao.model.PaymentPayer;
import com.rbkmoney.dudoser.service.ScheduledMailHandlerService;
import com.rbkmoney.dudoser.service.TemplateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public abstract class InvoicePaymentStatusChangedHandler implements PollingEventHandler {

    private final TemplateDao templateDao;

    private final TemplateService templateService;

    protected final PaymentPayerDaoImpl paymentPayerDaoImpl;

    private final ScheduledMailHandlerService mailHandlerService;

    @Override
    public void handle(InvoiceChange ic, String sourceId) {
        String paymentId = ic.getInvoicePaymentChange().getId();
        log.info("Start InvoicePaymentStatusChangedHandler: payment change {}.{}", sourceId, paymentId);
        Optional<PaymentPayer> paymentPayer = getPaymentPayer(sourceId, ic);
        if (paymentPayer.isPresent()) {
            PaymentPayer payment = paymentPayer.get();
            if (payment.getToReceiver() == null) {
                log.info("Email not found for payment change {}.{}", sourceId, paymentId);
            } else {
                String formattedAmount = getFormattedAmount(payment);
                Map<String, Object> model = new HashMap<>();
                model.put("paymentPayer", payment);
                model.put("formattedAmount", formattedAmount);
                String subject = String.format(getMailSubject(),
                        payment.getInvoiceId(),
                        payment.getDate(),
                        formattedAmount
                );
                String partyId = payment.getPartyId();
                String shopId = payment.getShopId();
                Template template = templateDao.getTemplateBodyByMerchShopParams(getEventTypeCode(), partyId, shopId);
                if (!template.isActive()) {
                    log.info("Not found active template for partyId={}, shopId={}", partyId, shopId);
                } else {
                    String text = templateService.getFilledContent(template.getBody(), model);
                    mailHandlerService.storeMessage(payment.getToReceiver(), subject, text);
                }
            }
        } else {
            log.warn("InvoicePaymentStatusChangedHandler: payment change {}.{} not found in repository", sourceId, paymentId);
        }
        log.info("End InvoicePaymentStatusChangedHandler: payment change {}.{}", sourceId, paymentId);
    }

    protected abstract String getFormattedAmount(PaymentPayer payment);

    protected abstract Optional<PaymentPayer> getPaymentPayer(String invoiceId, InvoiceChange ic);

    protected abstract String getMailSubject();

    protected abstract EventTypeCode getEventTypeCode();
}

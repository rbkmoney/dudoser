package com.rbkmoney.dudoser.handler.poller;

import com.rbkmoney.damsel.payment_processing.InvoiceChange;
import com.rbkmoney.dudoser.dao.*;
import com.rbkmoney.dudoser.exception.MailNotSendException;
import com.rbkmoney.dudoser.service.MailSenderService;
import com.rbkmoney.dudoser.service.TemplateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public abstract class InvoicePaymentStatusChangedHandler implements PollingEventHandler {

    @Value("${notification.payment.paid.from}")
    private String from;

    private final TemplateDao templateDao;

    private final TemplateService templateService;

    protected final PaymentPayerDaoImpl paymentPayerDaoImpl;

    private final MailSenderService mailSenderService;

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
                    try {
                        log.info("Mail send from {} to {}. Subject: {}", from, payment.getToReceiver(), subject);
                        mailSenderService.send(from, new String[]{payment.getToReceiver()}, subject, text, null);
                        log.info("Mail has been sent to {}", payment.getToReceiver());
                    } catch (MailNotSendException e) {
                        log.warn("Mail not send to {}", payment.getToReceiver(), e);
                    }
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

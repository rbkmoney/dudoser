package com.rbkmoney.dudoser.handler.poller;

import com.rbkmoney.damsel.payment_processing.InvoiceChange;
import com.rbkmoney.dudoser.dao.EventTypeCode;
import com.rbkmoney.dudoser.dao.Template;
import com.rbkmoney.dudoser.dao.TemplateDao;
import com.rbkmoney.dudoser.dao.model.PaymentPayer;
import com.rbkmoney.dudoser.exception.FillTemplateException;
import com.rbkmoney.dudoser.service.ScheduledMailHandlerService;
import com.rbkmoney.dudoser.service.TemplateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.tools.StringUtils;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public abstract class InvoicePaymentStatusChangedHandler implements PollingEventHandler {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    private final TemplateDao templateDao;
    private final TemplateService templateService;
    private final ScheduledMailHandlerService mailHandlerService;

    @Override
    public void handle(
            InvoiceChange invoiceChange,
            String sourceId,
            Long sequenceId) {
        String paymentId = invoiceChange.getInvoicePaymentChange().getId();
        log.info("Start InvoicePaymentStatusChangedHandler: payment change {}.{}", sourceId, paymentId);

        Optional<PaymentPayer> paymentPayer = getPaymentPayer(invoiceChange, sourceId, sequenceId);
        if (paymentPayer.isEmpty()) {
            log.warn("InvoicePaymentStatusChangedHandler: payment change {}.{} not found in repository", sourceId,
                    paymentId);
            return;
        }

        PaymentPayer payment = paymentPayer.get();
        if (payment.getToReceiver() == null) {
            log.info("Email not found for payment change {}.{}", sourceId, paymentId);
            return;
        }

        String partyId = payment.getPartyId();
        String shopId = payment.getShopId();

        Template template = templateDao.getTemplateBodyByMerchShopParams(getEventTypeCode(), partyId, shopId);
        if (!template.isActive()) {
            log.info("Not found active template for partyId={}, shopId={}", partyId, shopId);
            return;
        }

        prepareMessage(payment, partyId, shopId, template);

        log.info("End InvoicePaymentStatusChangedHandler: payment change {}.{}", sourceId, paymentId);
    }

    private void prepareMessage(
            PaymentPayer payment,
            String partyId,
            String shopId,
            Template template) {
        try {
            String formattedAmount = getFormattedAmount(payment);
            String subject = prepareSubject(payment, formattedAmount, template);
            String text = prepareText(payment, formattedAmount, template);

            mailHandlerService.storeMessage(payment.getToReceiver(), subject, text);
        } catch (FillTemplateException e) {
            log.error("Html creation failed for partyId={}, shopId={}", partyId, shopId, e);
        }
    }

    private String prepareSubject(PaymentPayer payment, String formattedAmount, Template template) {
        String formattedDate = getFormattedDate(payment);
        String subject = StringUtils.isEmpty(template.getSubject()) ? getMailSubject() : template.getSubject();
        return String.format(subject,
                payment.getInvoiceId(),
                formattedDate,
                formattedAmount);
    }

    private String prepareText(
            PaymentPayer payment,
            String formattedAmount,
            Template template) {
        Map<String, Object> model = new HashMap<>(Map.of(
                "paymentPayer", payment,
                "formattedAmount", formattedAmount));

        return templateService.getFilledContent(template.getBody(), model);
    }

    protected abstract String getFormattedAmount(PaymentPayer payment);

    protected abstract Optional<PaymentPayer> getPaymentPayer(
            InvoiceChange invoiceChange,
            String invoiceId,
            Long sequenceId
    );

    protected abstract String getMailSubject();

    protected abstract EventTypeCode getEventTypeCode();

    private String getFormattedDate(PaymentPayer payment) {
        return DATE_TIME_FORMATTER.format(payment.getDate());
    }
}

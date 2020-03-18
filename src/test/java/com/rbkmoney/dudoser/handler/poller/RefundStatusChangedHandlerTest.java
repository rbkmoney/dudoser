package com.rbkmoney.dudoser.handler.poller;

import com.rbkmoney.damsel.payment_processing.*;
import com.rbkmoney.dudoser.dao.Template;
import com.rbkmoney.dudoser.dao.TemplateDao;
import com.rbkmoney.dudoser.dao.model.PaymentPayer;
import com.rbkmoney.dudoser.service.InvoicingService;
import com.rbkmoney.dudoser.service.PaymentPayerService;
import com.rbkmoney.dudoser.service.ScheduledMailHandlerService;
import com.rbkmoney.dudoser.service.TemplateService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RefundStatusChangedHandlerTest {

    @InjectMocks
    private RefundStatusChangedHandler handler;

    @Mock private InvoicingService invoicingService;
    @Mock private PaymentPayerService paymentPayerService;
    @Mock private TemplateDao templateDao;
    @Mock private TemplateService templateService;
    @Mock private ScheduledMailHandlerService mailHandlerService;

    @Captor private ArgumentCaptor<String> subject;

    @Test
    public void shouldFormatDateInSubject() {
        // Given
        String sourceId = "source-id";
        Long sequenceId = 0L;
        InvoiceChange invoiceChange = InvoiceChange.invoice_payment_change(
                new InvoicePaymentChange(
                        new InvoicePaymentChange(
                                "id",
                                InvoicePaymentChangePayload.invoice_payment_refund_change(
                                        new InvoicePaymentRefundChange()))));

        PaymentPayer payment = PaymentPayer.builder()
                .invoiceId("invoice-id")
                .partyId("party-id")
                .shopId("shop-id")
                .toReceiver("to-receiver")
                .refundAmount(BigDecimal.ONE)
                .currency("RUB")
                .date(LocalDateTime.of(2019, Month.JANUARY, 7, 21, 0, 30))
                .build();

        when(invoicingService.get(any(), any()))
                .thenReturn(new Invoice());
        when(paymentPayerService.convert(any(), any(), any(), any()))
                .thenReturn(payment);
        when(templateDao.getTemplateBodyByMerchShopParams(any(), any(), any()))
                .thenReturn(new Template("body", null, true));
        when(templateService.getFilledContent(any(), any()))
                .thenReturn("content");

        // When
        handler.handle(invoiceChange, sourceId, sequenceId);

        // Then
        verify(mailHandlerService).storeMessage(any(), subject.capture(), any());
        assertThat(subject.getValue()).containsOnlyOnce("07.01.2019 21:00");
    }
}
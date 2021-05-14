package com.rbkmoney.dudoser.service;

import com.rbkmoney.damsel.domain.*;
import com.rbkmoney.damsel.payment_processing.Invoice;
import com.rbkmoney.damsel.payment_processing.InvoicePayment;
import com.rbkmoney.dudoser.AbstractIntegrationTest;
import com.rbkmoney.dudoser.dao.model.PaymentPayer;
import com.rbkmoney.dudoser.utils.Converter;
import com.rbkmoney.geck.common.util.TypeUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PaymentPayerServiceTest extends AbstractIntegrationTest {

    @MockBean
    private PartyManagementService partyManagementService;

    @Autowired
    private PaymentPayerService paymentPayerService;

    @Test
    public void paymentPayerServiceTest() throws Exception {
        Mockito.when(partyManagementService.getShopUrl(any(), any(), anyString())).thenReturn("8kun.net");

        InvoicePaymentRefund invoicePaymentRefund =
                random(InvoicePaymentRefund.class, "status", "party_revision", "cart", "cash_flow");
        invoicePaymentRefund.setStatus(InvoicePaymentRefundStatus.succeeded(new InvoicePaymentRefundSucceeded()));
        invoicePaymentRefund.setCreatedAt(TypeUtil.temporalToString(LocalDateTime.now()));

        CustomerPayer customerPayer = random(CustomerPayer.class, "payment_tool");
        PaymentTerminal paymentTerminal = new PaymentTerminal();
        paymentTerminal.setPaymentService(new PaymentServiceRef("euroset"));
        customerPayer
                .setPaymentTool(PaymentTool.payment_terminal(paymentTerminal));

        var invoicePayment =
                random(com.rbkmoney.damsel.domain.InvoicePayment.class, "status", "context", "flow", "payer",
                        "cash_flow");
        invoicePayment.setStatus(InvoicePaymentStatus.processed(new InvoicePaymentProcessed()));
        invoicePayment.setFlow(InvoicePaymentFlow.instant(new InvoicePaymentFlowInstant()));
        invoicePayment.setPayer(Payer.customer(customerPayer));
        invoicePayment.setCreatedAt(TypeUtil.temporalToString(LocalDateTime.now()));

        InvoicePayment invoicePaymentWrapper =
                random(InvoicePayment.class, "cash_flow", "target_status", "legacy_refunds",
                        "payment", "refunds", "adjustments", "sessions", "chargebacks");
        invoicePaymentWrapper.setPayment(invoicePayment);
        invoicePaymentWrapper.setRefunds(List.of(new com.rbkmoney.damsel.payment_processing.InvoicePaymentRefund()
                .setRefund(invoicePaymentRefund)));
        invoicePaymentWrapper.setAdjustments(List.of());

        var invoice = random(com.rbkmoney.damsel.domain.Invoice.class, "status", "details", "context");
        invoice.setStatus(InvoiceStatus.paid(new InvoicePaid()));
        InvoiceDetails invoiceDetails = new InvoiceDetails();
        invoiceDetails.setProduct("product");
        invoiceDetails.setDescription("desc");
        invoiceDetails.setBankAccount(new InvoiceBankAccount());
        invoice.setDetails(invoiceDetails);

        Invoice invoiceWrapper = random(Invoice.class, "invoice", "payments", "adjustments");
        invoiceWrapper.setInvoice(invoice);
        invoiceWrapper.setPayments(List.of(invoicePaymentWrapper));

        PaymentPayer paymentPayer = paymentPayerService
                .convert(invoiceWrapper, invoiceWrapper.getInvoice().getId(), invoicePayment.getId());

        assertEquals(Converter.longToBigDecimal(invoicePayment.getCost().getAmount()), paymentPayer.getAmount());

        paymentPayer = paymentPayerService
                .convert(invoiceWrapper, invoiceWrapper.getInvoice().getId(), invoicePayment.getId(),
                        invoicePaymentRefund.getId());

        assertEquals(Converter.longToBigDecimal(invoicePaymentRefund.getCash().getAmount()),
                paymentPayer.getRefundAmount());
    }
}

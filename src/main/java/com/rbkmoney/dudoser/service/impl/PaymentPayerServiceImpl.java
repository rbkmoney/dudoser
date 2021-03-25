package com.rbkmoney.dudoser.service.impl;

import com.rbkmoney.damsel.domain.*;
import com.rbkmoney.damsel.payment_processing.Invoice;
import com.rbkmoney.damsel.payment_processing.InvoicePayment;
import com.rbkmoney.dudoser.dao.model.Content;
import com.rbkmoney.dudoser.dao.model.PaymentPayer;
import com.rbkmoney.dudoser.exception.NotFoundException;
import com.rbkmoney.dudoser.service.PartyManagementService;
import com.rbkmoney.dudoser.service.PaymentPayerService;
import com.rbkmoney.dudoser.utils.Converter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static com.rbkmoney.geck.common.util.TypeUtil.stringToLocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentPayerServiceImpl implements PaymentPayerService {

    @Value("${subject.timezone.refund}")
    private String refundZoneId;

    @Value("${subject.timezone.payment}")
    private String paymentZoneId;

    private final PartyManagementService partyManagementService;

    @Override
    public PaymentPayer convert(Invoice invoiceWrapper, String invoiceId, String paymentId) {
        var invoice = invoiceWrapper.getInvoice();

        var invoicePayment = getInvoicePayment(invoiceWrapper, invoiceId, paymentId);

        ContactInfo contactInfo;
        PaymentTool paymentTool;
        Payer payer = invoicePayment.getPayer();
        if (payer.isSetCustomer()) {
            contactInfo = payer.getCustomer().getContactInfo();
            paymentTool = payer.getCustomer().getPaymentTool();
        } else if (payer.isSetPaymentResource()) {
            contactInfo = payer.getPaymentResource().getContactInfo();
            paymentTool = payer.getPaymentResource().getResource().getPaymentTool();
        } else {
            contactInfo = payer.getRecurrent().getContactInfo();
            paymentTool = payer.getRecurrent().getPaymentTool();
        }

        String maskedPan = null;
        String cardType = null;
        if (paymentTool.isSetBankCard()) {
            maskedPan = paymentTool.getBankCard().getLastDigits();
            cardType = paymentTool.getBankCard().getPaymentSystem().name();
        }

        return PaymentPayer.builder()
                .amount(getAmount(invoicePayment.getCost()))
                .refundAmount(null)
                .currency(getSymbolicCode(invoicePayment.getCost()))
                .cardType(cardType)
                .cardMaskPan(maskedPan)
                .invoiceId(invoiceId)
                .paymentId(paymentId)
                .refundId(null)
                .partyId(invoice.getOwnerId())
                .shopId(invoice.getShopId())
                .shopUrl(getShopUrl(invoice))
                .date(getDateByTimeZone(invoicePayment.getCreatedAt(), paymentZoneId))
                .toReceiver(contactInfo.getEmail())
                .metadata(getMetadata(invoicePayment))
                .invoiceMetadata(getInvoiceMetadata(invoice))
                .build();
    }

    @Override
    public PaymentPayer convert(Invoice invoiceWrapper, String invoiceId, String paymentId, String refundId) {
        PaymentPayer paymentPayer = convert(invoiceWrapper, invoiceId, paymentId);

        var invoicePaymentRefund = getInvoicePaymentRefund(invoiceWrapper, invoiceId, paymentId, refundId);

        BigDecimal refundAmount;
        String currency;
        if (invoicePaymentRefund.isSetCash()) {
            refundAmount = getAmount(invoicePaymentRefund.getCash());
            currency = getSymbolicCode(invoicePaymentRefund.getCash());
        } else {
            refundAmount = paymentPayer.getAmount();
            currency = paymentPayer.getCurrency();
        }

        paymentPayer.setRefundId(refundId);
        paymentPayer.setDate(getDateByTimeZone(invoicePaymentRefund.getCreatedAt(), refundZoneId));
        paymentPayer.setRefundAmount(refundAmount);
        paymentPayer.setCurrency(currency);

        return paymentPayer;
    }

    private com.rbkmoney.damsel.domain.InvoicePayment getInvoicePayment(Invoice invoice, String invoiceId,
                                                                        String paymentId) {
        return invoice.getPayments().stream()
                .filter(invoicePayment -> paymentId.equals(invoicePayment.getPayment().getId()))
                .findFirst()
                .map(InvoicePayment::getPayment)
                .orElseThrow(
                        () -> {
                            throw new NotFoundException(getNotFoundMessage(invoiceId, paymentId));
                        }
                );
    }

    private InvoicePaymentRefund getInvoicePaymentRefund(Invoice invoice, String invoiceId, String paymentId,
                                                         String refundId) {
        return invoice.getPayments().stream()
                .filter(invoicePayment -> paymentId.equals(invoicePayment.getPayment().getId()))
                .findFirst()
                .map(
                        invoicePayment -> invoicePayment.getRefunds().stream()
                                .map(com.rbkmoney.damsel.payment_processing.InvoicePaymentRefund::getRefund)
                                .filter(refund -> refundId.equals(refund.getId()))
                                .findFirst()
                                .orElse(null)
                )
                .orElseThrow(
                        () -> {
                            throw new NotFoundException(getNotFoundMessage(invoiceId, paymentId, refundId));
                        }
                );
    }

    private String getNotFoundMessage(String invoiceId, String paymentId) {
        return String.format("InvoicePayment not found, invoiceId='%s', paymentId='%s'", invoiceId, paymentId);
    }

    private String getNotFoundMessage(String invoiceId, String paymentId, String refundId) {
        return String.format("InvoicePaymentRefund not found, invoiceId='%s', paymentId='%s', refundId='%s'", invoiceId,
                paymentId, refundId);
    }

    private String getShopUrl(com.rbkmoney.damsel.domain.Invoice invoice) {
        String shopUrl;
        if (invoice.isSetPartyRevision()) {
            shopUrl = partyManagementService
                    .getShopUrl(invoice.getOwnerId(), invoice.getShopId(), invoice.getPartyRevision());
        } else {
            shopUrl = partyManagementService
                    .getShopUrl(invoice.getOwnerId(), invoice.getShopId(), invoice.getCreatedAt());
        }
        return shopUrl;
    }

    private Content getMetadata(com.rbkmoney.damsel.domain.InvoicePayment invoicePayment) {
        Content metadata = new Content();
        if (invoicePayment.isSetContext()) {
            metadata.setType(invoicePayment.getContext().getType());
            metadata.setData(invoicePayment.getContext().getData());
        }
        return metadata;
    }

    private Content getInvoiceMetadata(com.rbkmoney.damsel.domain.Invoice invoice) {
        Content invoiceMetadata = new Content();
        if (invoice.isSetContext()) {
            invoiceMetadata.setType(invoice.getContext().getType());
            invoiceMetadata.setData(invoice.getContext().getData());
        }
        return invoiceMetadata;
    }

    private LocalDateTime getDateByTimeZone(String createdAt, String zoneId) {
        return stringToLocalDateTime(createdAt, ZoneId.of(zoneId));
    }

    private BigDecimal getAmount(Cash cash) {
        return Converter.longToBigDecimal(cash.getAmount());
    }

    private String getSymbolicCode(Cash cash) {
        return cash.getCurrency().getSymbolicCode();
    }
}

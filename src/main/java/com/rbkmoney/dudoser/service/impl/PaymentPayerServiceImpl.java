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
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static com.rbkmoney.geck.common.util.TypeUtil.stringToLocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentPayerServiceImpl implements PaymentPayerService {

    private final PartyManagementService partyManagementService;

    @Override
    public PaymentPayer convert(Invoice invoiceWrapper, String invoiceId, String paymentId) {
        var invoice = invoiceWrapper.getInvoice();

        var invoicePayment = getInvoicePayment(invoiceWrapper, paymentId);

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
            maskedPan = paymentTool.getBankCard().getMaskedPan();
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
                .date(stringToLocalDateTime(invoicePayment.getCreatedAt()))
                .toReceiver(contactInfo.getEmail())
                .metadata(getMetadata(invoicePayment))
                .invoiceMetadata(getInvoiceMetadata(invoice))
                .build();
    }

    @Override
    public PaymentPayer convert(Invoice invoiceWrapper, String invoiceId, String paymentId, String refundId) {
        var invoice = invoiceWrapper.getInvoice();

        var invoicePayment = getInvoicePayment(invoiceWrapper, paymentId);

        var invoicePaymentRefund = getInvoicePaymentRefund(invoiceWrapper, paymentId, refundId);

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
            maskedPan = paymentTool.getBankCard().getMaskedPan();
            cardType = paymentTool.getBankCard().getPaymentSystem().name();
        }

        BigDecimal refundAmount;
        String currency;
        if (invoicePaymentRefund.isSetCash()) {
            refundAmount = getAmount(invoicePaymentRefund.getCash());
            currency = getSymbolicCode(invoicePaymentRefund.getCash());
        } else {
            refundAmount = getAmount(invoicePayment.getCost());
            currency = getSymbolicCode(invoicePayment.getCost());
        }

        return PaymentPayer.builder()
                .amount(getAmount(invoicePayment.getCost()))
                .refundAmount(refundAmount)
                .currency(currency)
                .cardType(cardType)
                .cardMaskPan(maskedPan)
                .invoiceId(invoiceId)
                .paymentId(paymentId)
                .refundId(refundId)
                .partyId(invoice.getOwnerId())
                .shopId(invoice.getShopId())
                .shopUrl(getShopUrl(invoice))
                .date(stringToLocalDateTime(invoicePaymentRefund.getCreatedAt()))
                .toReceiver(contactInfo.getEmail())
                .metadata(getMetadata(invoicePayment))
                .invoiceMetadata(getInvoiceMetadata(invoice))
                .build();
    }

    private com.rbkmoney.damsel.domain.InvoicePayment getInvoicePayment(Invoice invoice, String paymentId) {
        return invoice.getPayments().stream()
                .filter(invoicePayment -> paymentId.equals(invoicePayment.getPayment().getId()))
                .findFirst()
                .map(InvoicePayment::getPayment)
                .orElseThrow(
                        () -> {
                            throw new NotFoundException(getNotFoundMessage(invoice, paymentId));
                        }
                );
    }

    private InvoicePaymentRefund getInvoicePaymentRefund(Invoice invoice, String paymentId, String refundId) {
        return invoice.getPayments().stream()
                .filter(invoicePayment -> paymentId.equals(invoicePayment.getPayment().getId()))
                .findFirst()
                .map(
                        invoicePayment -> invoicePayment.getRefunds().stream()
                                .filter(invoicePaymentRefund -> refundId.equals(invoicePaymentRefund.getId()))
                                .findFirst()
                                .orElse(null)
                )
                .orElseThrow(
                        () -> {
                            throw new NotFoundException(getNotFoundMessage(invoice, paymentId, refundId));
                        }
                );
    }

    private String getNotFoundMessage(Invoice invoice, String paymentId) {
        return String.format("InvoicePayment not found, invoiceId='%s', paymentId='%s'", invoice.getInvoice().getId(), paymentId);
    }

    private String getNotFoundMessage(Invoice invoice, String paymentId, String refundId) {
        return String.format("InvoicePaymentRefund not found, invoiceId='%s', paymentId='%s', refundId='%s'", invoice.getInvoice().getId(), paymentId, refundId);
    }

    private String getShopUrl(com.rbkmoney.damsel.domain.Invoice invoice) {
        String shopUrl;
        if (invoice.isSetPartyRevision()) {
            shopUrl = partyManagementService.getShopUrl(invoice.getOwnerId(), invoice.getShopId(), invoice.getPartyRevision());
        } else {
            shopUrl = partyManagementService.getShopUrl(invoice.getOwnerId(), invoice.getShopId(), invoice.getCreatedAt());
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

    private BigDecimal getAmount(Cash cash) {
        return Converter.longToBigDecimal(cash.getAmount());
    }

    private String getSymbolicCode(Cash cash) {
        return cash.getCurrency().getSymbolicCode();
    }
}

package com.rbkmoney.dudoser.service;

import com.rbkmoney.damsel.payment_processing.Invoice;
import com.rbkmoney.dudoser.dao.model.PaymentPayer;

public interface PaymentPayerService {

    PaymentPayer convert(Invoice invoice, String invoiceId, String paymentId);

    PaymentPayer convert(Invoice invoice, String invoiceId, String paymentId, String refundId);
}

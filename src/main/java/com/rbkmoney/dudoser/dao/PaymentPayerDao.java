package com.rbkmoney.dudoser.dao;

import com.rbkmoney.dudoser.dao.model.Content;
import com.rbkmoney.dudoser.dao.model.PaymentPayer;

import java.util.Optional;

public interface PaymentPayerDao {

    void addPayment(PaymentPayer paymentPayer) throws Exception;

    void addInvoice(String invoiceId, String partyId, String shopId, String shopUrl, Content context);

    void addRefund(PaymentPayer paymentPayer);

    Optional<PaymentPayer> getPayment(String invoiceId, String paymentId);

    Optional<PaymentPayer> getInvoice(String invoiceId);

    Optional<PaymentPayer> getRefund(String invoiceId, String paymentId, String refundId);
}

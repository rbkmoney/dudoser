package com.rbkmoney.dudoser.dao;

import java.util.Optional;

public interface PaymentPayerDao {

    boolean addPayment(PaymentPayer paymentPayer) throws Exception;

    boolean addInvoice(String invoiceId, String partyId, String shopId, String shopUrl);

    boolean addRefund(PaymentPayer paymentPayer);

    Optional<PaymentPayer> getPayment(String invoiceId, String paymentId);

    Optional<PaymentPayer> getInvoice(String invoiceId);

    Optional<PaymentPayer> getLastRefund(String invoiceId, String paymentId);
}

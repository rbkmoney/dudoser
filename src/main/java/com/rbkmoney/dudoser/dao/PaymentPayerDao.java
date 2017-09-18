package com.rbkmoney.dudoser.dao;

import java.util.Optional;

public interface PaymentPayerDao {

    boolean updatePayment(PaymentPayer customer) throws Exception;

    boolean addInvoice(String invoiceId, String partyId, String shopId, String shopUrl);

    boolean addRefund(PaymentPayer paymentPayer);

    Optional<PaymentPayer> getPayment(String invoiceId, String paymentId);

    Optional<PaymentPayer> getRefund(String invoiceId, String paymentId);
}

package com.rbkmoney.dudoser.dao;

import java.util.Optional;

public interface PaymentPayerDao {
    Optional<PaymentPayer> getById(String id) throws Exception;

    boolean update(PaymentPayer customer) throws Exception;

    boolean add(String invoiceId, String partyId, String shopId, String shopUrl);

    boolean delete(String id) throws Exception;
}

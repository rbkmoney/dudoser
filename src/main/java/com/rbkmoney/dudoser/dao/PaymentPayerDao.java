package com.rbkmoney.dudoser.dao;

import java.util.Optional;

public interface PaymentPayerDao {
    Optional<PaymentPayer> getById(String id) throws Exception;

    boolean add(PaymentPayer customer) throws Exception;

    boolean delete(String id) throws Exception;
}

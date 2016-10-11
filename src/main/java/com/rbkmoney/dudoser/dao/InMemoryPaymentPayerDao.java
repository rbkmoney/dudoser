package com.rbkmoney.dudoser.dao;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Component
public class InMemoryPaymentPayerDao implements PaymentPayerDao {

    private static Map<String, PaymentPayer> idToPayer = new HashMap<>();

    @Override
    public Optional<PaymentPayer> getById(final String id) {
        return Optional.ofNullable(idToPayer.get(id));
    }

    @Override
    public boolean add(final PaymentPayer paymentPayer) {
        if (getById(paymentPayer.getInvoiceId()).isPresent()) {
            return false;
        }

        idToPayer.put(paymentPayer.getInvoiceId(), paymentPayer);
        return true;
    }

    @Override
    public boolean delete(final String id) {
        return idToPayer.remove(id) != null;
    }
}

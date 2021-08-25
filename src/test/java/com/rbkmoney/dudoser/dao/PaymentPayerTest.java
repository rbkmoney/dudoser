package com.rbkmoney.dudoser.dao;

import com.rbkmoney.dudoser.dao.model.PaymentPayer;
import com.rbkmoney.dudoser.utils.Converter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.Assert.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PaymentPayerTest {

    PaymentPayer paymentPayer;

    @BeforeAll
    public void setUp() {
        paymentPayer = PaymentPayer.builder().build();
    }

    @Test
    public void testGetAmountWithCurrency() {
        paymentPayer.setAmount(Converter.longToBigDecimal(12345L));
        paymentPayer.setCurrency("RUB");
        assertEquals("123.45 RUB", Converter.getFormattedAmount(paymentPayer.getAmount(), paymentPayer.getCurrency()));

        paymentPayer.setAmount(Converter.longToBigDecimal(12145345L));
        paymentPayer.setCurrency("USD");
        assertEquals("121453.45 USD",
                Converter.getFormattedAmount(paymentPayer.getAmount(), paymentPayer.getCurrency()));

        paymentPayer.setAmount(Converter.longToBigDecimal(0L));
        paymentPayer.setCurrency("BYR");
        assertEquals("0.00 BYR", Converter.getFormattedAmount(paymentPayer.getAmount(), paymentPayer.getCurrency()));

        paymentPayer.setAmount(Converter.longToBigDecimal(-1L));
        paymentPayer.setCurrency("FRA");
        assertEquals("-0.01 FRA", Converter.getFormattedAmount(paymentPayer.getAmount(), paymentPayer.getCurrency()));
    }
}

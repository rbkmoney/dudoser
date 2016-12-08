package com.rbkmoney.dudoser.dao;

import com.rbkmoney.dudoser.utils.Converter;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PaymentPayerTest {

    PaymentPayer paymentPayer;

    @Before
    public void setUp() {
        paymentPayer = new PaymentPayer();
    }

    @Test
    public void testGetAmountWithCurrency() throws Exception {
        paymentPayer.setAmount(Converter.longToBigDecimal(12345L));
        paymentPayer.setCurrency("RUB");
        assertEquals("123.45 RUB", paymentPayer.getAmountWithCurrency());

        paymentPayer.setAmount(Converter.longToBigDecimal(12145345L));
        paymentPayer.setCurrency("USD");
        assertEquals("121453.45 USD", paymentPayer.getAmountWithCurrency());

        paymentPayer.setAmount(Converter.longToBigDecimal(0L));
        paymentPayer.setCurrency("BYR");
        assertEquals("0.00 BYR", paymentPayer.getAmountWithCurrency());

        paymentPayer.setAmount(Converter.longToBigDecimal(-1L));
        paymentPayer.setCurrency("FRA");
        assertEquals("-0.01 FRA", paymentPayer.getAmountWithCurrency());
    }
}

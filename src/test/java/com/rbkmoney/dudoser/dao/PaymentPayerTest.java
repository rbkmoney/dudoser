package com.rbkmoney.dudoser.dao;

import com.rbkmoney.dudoser.dao.model.PaymentPayer;
import com.rbkmoney.dudoser.utils.Converter;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

@Ignore
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
        assertEquals("123.45 RUB", Converter.getFormattedAmount(paymentPayer.getAmount(), paymentPayer.getCurrency()));

        paymentPayer.setAmount(Converter.longToBigDecimal(12145345L));
        paymentPayer.setCurrency("USD");
        assertEquals("121453.45 USD", Converter.getFormattedAmount(paymentPayer.getAmount(), paymentPayer.getCurrency()));

        paymentPayer.setAmount(Converter.longToBigDecimal(0L));
        paymentPayer.setCurrency("BYR");
        assertEquals("0.00 BYR", Converter.getFormattedAmount(paymentPayer.getAmount(), paymentPayer.getCurrency()));

        paymentPayer.setAmount(Converter.longToBigDecimal(-1L));
        paymentPayer.setCurrency("FRA");
        assertEquals("-0.01 FRA", Converter.getFormattedAmount(paymentPayer.getAmount(), paymentPayer.getCurrency()));
    }
}

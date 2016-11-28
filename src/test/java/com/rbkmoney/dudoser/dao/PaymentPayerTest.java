package com.rbkmoney.dudoser.dao;

import com.rbkmoney.dudoser.utils.Converter;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PaymentPayerTest {

    PaymentPayer paymentPayer;

    @Before
    public void setUp() {
        paymentPayer = new PaymentPayer();
    }

    @Test
    @Ignore
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

//    @Test
//    public void testSetAndGetDate() {
//        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
//
//        String dateTime = "2016-03-22T00:12:00Z";
//        String expected = "2016-03-22";
//
//        paymentPayer.setDate(dateTime);
//        assertEquals(expected, paymentPayer.getDate());
//
//        LocalDateTime localDateTime = LocalDateTime.parse(dateTime, formatter);
//        assertEquals(expected, localDateTime.toLocalDate().toString());
//
//        ZonedDateTime result = ZonedDateTime.parse(dateTime, formatter);
//        assertEquals(expected, result.toLocalDate().toString());
//    }

}
